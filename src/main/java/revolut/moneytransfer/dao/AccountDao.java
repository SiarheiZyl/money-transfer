package revolut.moneytransfer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import revolut.moneytransfer.model.Account;
import revolut.moneytransfer.model.Client;

/**
 * Dao class for Account entity
 *
 * @author szyl
 */
public class AccountDao extends AbstractDao<Account> {

    AccountDao(Connection connection) {
        super(Account.tableName, connection);
    }

    @Override
    public Account findById(Integer id) throws SQLException {
        if (id == null) {
            return null;
        }
        PreparedStatement statement = connection.prepareStatement(findByIdSql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        resultSet.last();
        if (resultSet.getRow() == 1) {
            ClientDao clientDao = (ClientDao) DaoManager.createDao(connection, Client.class);
            Client client = null;
            if (clientDao != null) {
                client = clientDao.findById(resultSet.getInt(6));
            }
            Account account = new Account(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getTimestamp(3),
                    resultSet.getTimestamp(4), resultSet.getString(5));
            account.setClient(client);
            return account;
        }
        return null;
    }

    @Override
    public boolean persist(Account entity) throws SQLException {
        if (entity == null) {
            return false;
        }
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + Account.tableName + " VALUES (?,?,?,?,?,?)");
        statement.setInt(1, entity.getId());
        statement.setDouble(2, entity.getAmount());
        statement.setTimestamp(3, entity.getOpendate());
        statement.setTimestamp(4, entity.getClosedate());
        statement.setString(5, entity.getStatus());
        if (entity.getClient() == null) {
            statement.setNull(6, Types.DECIMAL);
        } else {
            statement.setInt(6, entity.getClient().getId());
        }
        statement.execute();
        statement.close();
        return true;
    }

    public boolean updateAccountAmount(Account entity) throws SQLException {
        if (entity == null) {
            return false;
        }
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE " + Account.tableName + " SET amount = ? WHERE id = ?");
        statement.setDouble(1, entity.getAmount());
        statement.setInt(2, entity.getId());
        statement.executeUpdate();
        statement.close();
        return true;
    }

    public boolean updateAccountStatusAndClosedate(Account entity) throws SQLException {
        if (entity == null) {
            return false;
        }
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE " + Account.tableName + " SET CLOSEDATE = ?, STATUS = ? WHERE id = ?");
        statement.setTimestamp(1, entity.getClosedate());
        statement.setString(2, entity.getStatus());
        statement.setInt(3, entity.getId());
        statement.executeUpdate();
        statement.close();
        return true;
    }

    List<Account> findAccountsByClientId(Integer clientId) throws SQLException {
        if (clientId == null) {
            return null;
        }
        List<Account> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM " + Account.tableName + " WHERE CLIENTFK = ?");
        statement.setInt(1, clientId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            result.add(new Account(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getTimestamp(3),
                    resultSet.getTimestamp(4), resultSet.getString(5)));
        }
        return result;
    }
}
