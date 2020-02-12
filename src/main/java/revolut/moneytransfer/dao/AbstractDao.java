package revolut.moneytransfer.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Base dao class
 *
 * @author szyl
 */
public abstract class AbstractDao<T> {

    final String findByIdSql;

    Connection connection;

    AbstractDao(String tableName, Connection connection) {
        this.findByIdSql = "SELECT * FROM " + tableName + " WHERE id = ?";
        this.connection = connection;
    }

    public abstract T findById(Integer id) throws SQLException;

    public abstract boolean persist(T entity) throws SQLException;
}
