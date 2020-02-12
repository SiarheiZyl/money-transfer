package revolut.moneytransfer.dao;

import java.sql.Connection;

import revolut.moneytransfer.model.Account;
import revolut.moneytransfer.model.Client;
import revolut.moneytransfer.model.File;

/**
 * Class for creating dao singleton objects
 *
 * @author szyl
 */
public class DaoManager {

    private static AccountDao accountDao;

    private static ClientDao clientDao;

    private static FileDao fileDao;

    public static synchronized AbstractDao createDao(Connection connection, Class clazz) {
        if (connection == null) {
            throw new IllegalArgumentException("connectionSource argument cannot be null");
        } else {
            if (clazz.getSimpleName().equals(Account.class.getSimpleName())) {
                if (accountDao == null) {
                    accountDao = new AccountDao(connection);
                }
                return accountDao;
            } else if (clazz.getSimpleName().equals(Client.class.getSimpleName())) {
                if (clientDao == null) {
                    clientDao = new ClientDao(connection);
                }
                return clientDao;
            } else if (clazz.getSimpleName().equals(File.class.getSimpleName())) {
                if (fileDao == null) {
                    fileDao = new FileDao(connection);
                }
                return fileDao;
            }

        }
        return null;
    }
}
