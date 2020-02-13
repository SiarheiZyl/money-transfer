package revolut.moneytransfer.service;

import java.sql.SQLException;
import java.sql.Timestamp;

import revolut.moneytransfer.client.MoneyTransferRq;
import revolut.moneytransfer.dao.AccountDao;
import revolut.moneytransfer.dao.DaoManager;
import revolut.moneytransfer.dao.FileDao;
import revolut.moneytransfer.exception.IllegalAccountException;
import revolut.moneytransfer.exception.WrongAmountException;
import revolut.moneytransfer.model.Account;
import revolut.moneytransfer.model.File;
import revolut.moneytransfer.util.AccountStatusEnum;
import revolut.moneytransfer.util.FileStatusEnum;
import revolut.moneytransfer.util.InitDatabase;
import revolut.moneytransfer.util.MessageConstants;

/**
 * Class that encapsulates business logic for files(transactions)
 *
 * @author szyl
 */
public class TransferMoneyService {

    private AccountDao accountDao;

    private FileDao fileDao;

    public TransferMoneyService() {
        accountDao = (AccountDao) DaoManager.createDao(InitDatabase.getConnection(), Account.class);
        fileDao = (FileDao) DaoManager.createDao(InitDatabase.getConnection(), File.class);
    }

    public boolean createAndProceedTransfer(MoneyTransferRq moneyTransferRq)
            throws IllegalAccountException, WrongAmountException {
        Integer sourceAccountId = moneyTransferRq.getSource();
        Integer targetAccountId = moneyTransferRq.getTarget();
        Double amount = moneyTransferRq.getAmount();

        Account sourceAccount = new Account();
        sourceAccount.setId(sourceAccountId);
        Account targetAccount = new Account();
        targetAccount.setId(targetAccountId);
        File file = new File(File.objectsQuantity++, new Timestamp(System.currentTimeMillis()),
                FileStatusEnum.WAITING.toString(), amount, null, sourceAccount, targetAccount);

        try {
            fileDao.persist(file);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String status = FileStatusEnum.REJECTED.toString();
        String response = MessageConstants.TRANSACTION_PROCEEDED_SUCCESSFULLY;
        try {
            sourceAccount = accountDao.findById(sourceAccountId);
            if (sourceAccount == null) {
                response = MessageConstants.SOURCE_ACCOUNT_NOT_FOUND;
                throw new IllegalAccountException(MessageConstants.SOURCE_ACCOUNT_NOT_FOUND);
            } else if (AccountStatusEnum.CLOSED.name().equalsIgnoreCase(sourceAccount.getStatus())) {
                throw new IllegalAccountException(MessageConstants.SOURCE_ACCOUNT_CLOSED);
            }

            if (amount == null || amount <= 0.0) {
                response = MessageConstants.WRONG_AMOUNT;
                throw new WrongAmountException(MessageConstants.WRONG_AMOUNT);
            } else if (sourceAccount.getAmount() == null || sourceAccount.getAmount() < amount) {
                throw new WrongAmountException(MessageConstants.NOT_ENOUGH_MONEY_ON_SOURCE_ACCOUNT);
            }

            targetAccount = accountDao.findById(targetAccountId);
            if (targetAccount == null) {
                response = MessageConstants.TARGET_ACCOUNT_NOT_FOUND;
                throw new IllegalAccountException(MessageConstants.TARGET_ACCOUNT_NOT_FOUND);
            } else if (targetAccountId.equals(sourceAccountId)) {
                response = MessageConstants.SOURCE_EQUALS_TO_TARGET;
                throw new IllegalAccountException(MessageConstants.SOURCE_EQUALS_TO_TARGET);
            } else if (AccountStatusEnum.CLOSED.name().equalsIgnoreCase(targetAccount.getStatus())) {
                response = MessageConstants.TARGET_ACCOUNT_CLOSED;
                throw new IllegalAccountException(MessageConstants.TARGET_ACCOUNT_CLOSED);
            }
            status = FileStatusEnum.OK.toString();
            sourceAccount.setAmount(sourceAccount.getAmount() - amount);
            accountDao.updateAccountAmount(sourceAccount);
            targetAccount.setAmount(targetAccount.getAmount() + amount);
            accountDao.updateAccountAmount(targetAccount);
        } catch (SQLException e) {
            return false;
        } finally {
            file.setStatus(status);
            file.setResponse(response);
            updateFile(file);
        }

        return true;
    }

    private void updateFile(File file) {
        try {
            fileDao.updateStatusAndResponse(file);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
