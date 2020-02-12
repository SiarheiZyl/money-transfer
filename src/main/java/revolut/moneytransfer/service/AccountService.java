package revolut.moneytransfer.service;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import revolut.moneytransfer.client.CreateAccountRq;
import revolut.moneytransfer.dao.AccountDao;
import revolut.moneytransfer.dao.ClientDao;
import revolut.moneytransfer.dao.DaoManager;
import revolut.moneytransfer.exception.IllegalAccountException;
import revolut.moneytransfer.mapper.ClientToEntityMapper;
import revolut.moneytransfer.model.Account;
import revolut.moneytransfer.model.Client;
import revolut.moneytransfer.util.AccountStatusEnum;
import revolut.moneytransfer.util.InitDatabase;
import revolut.moneytransfer.util.MessageConstants;

/**
 * Class that encapsulates business logic for accounts
 *
 * @author szyl
 */
public class AccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    private AccountDao accountDao;

    private ClientDao clientDao;

    public AccountService() {
        accountDao = (AccountDao) DaoManager.createDao(InitDatabase.getConnection(), Account.class);
        clientDao = (ClientDao) DaoManager.createDao(InitDatabase.getConnection(), Client.class);
    }

    public boolean createAccount(CreateAccountRq accountRq) throws IllegalAccountException {
        logger.debug("Starting of creating an account");
        Account account = ClientToEntityMapper.createAccountRqToAccount(accountRq);
        account.setStatus(AccountStatusEnum.OPEN.name());
        Integer clientId = accountRq.getClient();

        if (clientDao != null) {
            try {
                Client client = clientDao.findById(clientId);
                if (client == null) {
                    throw new IllegalAccountException(MessageConstants.CLIENT_FOR_ACCOUNT_NOT_FOUND);
                }
                account.setClient(clientDao.findById(clientId));
            } catch (SQLException e) {
                logger.error("Error during finding client for the account; clientId = {}", clientId);
            }
        }
        if (accountDao != null && account.getId() != null) {
            try {
                if (accountDao.findById(account.getId()) == null) {
                    return accountDao.persist(account);
                } else {
                    throw new IllegalAccountException(MessageConstants.SOURCE_ACCOUNT_ALREADY_EXISTS);
                }
            } catch (SQLException e) {
                logger.error("Error during persisting the account");
            }
        }
        return false;
    }

    public boolean closeAccount(Integer accountId) throws IllegalAccountException {
        logger.debug("Starting of closing an account");
        Account account;
        try {
            account = accountDao.findById(accountId);
            if (account == null) {
                throw new IllegalAccountException(MessageConstants.SOURCE_ACCOUNT_NOT_FOUND);
            }
            account.setStatus(AccountStatusEnum.CLOSED.name());
            account.setClosedate(new Timestamp(System.currentTimeMillis()));
            return accountDao.updateAccountStatusAndClosedate(account);
        } catch (SQLException e) {
            logger.error("Error during closing the account");
        }
        return false;
    }
}
