package revolut.moneytransfer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import revolut.moneytransfer.model.Account;
import revolut.moneytransfer.model.File;

/**
 * Dao class for File entity
 *
 * @author szyl
 */
public class FileDao extends AbstractDao<File> {

    FileDao(Connection connection) {
        super(File.tableName, connection);
    }

    @Override
    public File findById(Integer id) throws SQLException {
        if (id == null) {
            return null;
        }
        PreparedStatement statement = connection.prepareStatement(findByIdSql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        resultSet.last();
        if (resultSet.getRow() == 1) {
            Account source = null;
            Account target = null;
            File file = new File(resultSet.getInt(1), resultSet.getTimestamp(2), resultSet.getString(3),
                    resultSet.getDouble(4), resultSet.getString(5), source, target);
            return file;
        }
        return null;
    }

    @Override
    public boolean persist(File entity) throws SQLException {
        if (entity == null) {
            return false;
        }
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + File.tableName + " VALUES (?,?,?,?,?,?,?)");
        statement.setInt(1, entity.getId());
        statement.setTimestamp(2, entity.getFileDate());
        statement.setString(3, entity.getStatus());
        statement.setDouble(4, entity.getAmount());
        statement.setString(5, entity.getResponse());
        if (entity.getSource() == null) {
            statement.setNull(6, Types.DECIMAL);
        } else {
            statement.setInt(6, entity.getSource().getId());
        }

        if (entity.getTarget() == null) {
            statement.setNull(7, Types.DECIMAL);
        } else {
            statement.setInt(7, entity.getTarget().getId());
        }
        statement.execute();
        statement.close();
        return true;
    }

    public boolean updateStatusAndResponse(File entity) throws SQLException {
        if (entity == null) {
            return false;
        }
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE " + File.tableName + " SET status = ?, response = ? WHERE id = ?");
        statement.setString(1, entity.getStatus());
        statement.setString(2, entity.getResponse());
        statement.setInt(3, entity.getId());
        statement.executeUpdate();
        statement.close();
        return false;
    }
}
