package revolut.moneytransfer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import revolut.moneytransfer.model.Account;
import revolut.moneytransfer.model.Client;

/**
 * Dao class for Client entity
 *
 * @author szyl
 */
public class ClientDao extends AbstractDao<Client> {

    ClientDao(Connection connection) {
        super(Client.tableName, connection);
    }

    @Override
    public Client findById(Integer id) throws SQLException {
        if(id == null) {
            return null;
        }
        PreparedStatement statement = connection.prepareStatement(findByIdSql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        resultSet.last();
        if (resultSet.getRow() == 1) {
            AccountDao accountDao = (AccountDao) DaoManager.createDao(connection, Account.class);
            List<Account> accounts = null;
            if (accountDao != null) {
                accounts = accountDao.findAccountsByClientId(id);
            }
            Client client = new Client(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            client.setAccounts(accounts);
            return client;
        }
        return null;
    }

    @Override
    public boolean persist(Client entity) throws SQLException {
        if (entity == null) {
            return false;
        }
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + Client.tableName + " VALUES (?,?,?,?,?,?)");
        statement.setInt(1, entity.getId());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getSurname());
        statement.setString(4, entity.getCountry());
        statement.setString(5, entity.getCity());
        statement.setString(6, entity.getAddress());
        statement.execute();
        statement.close();
        return true;
    }

    public boolean update(Client entity) throws SQLException {
        if (entity == null) {
            return false;
        }
        PreparedStatement statement = connection.prepareStatement("UPDATE " + Client.tableName +
                " SET name = ?, surname = ?, country = ?, city = ?, address = ? WHERE id = ?");
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setString(3, entity.getCountry());
        statement.setString(4, entity.getCity());
        statement.setString(5, entity.getAddress());
        statement.setInt(6, entity.getId());
        statement.executeUpdate();
        statement.close();
        return true;
    }
}
