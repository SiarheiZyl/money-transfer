package revolut.moneytransfer.util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Initializing of DB
 *
 * @author szyl
 */
public class InitDatabase {

    private final static Logger logger = LoggerFactory.getLogger(InitDatabase.class);

    private static final String DRIVER_NAME = "org.h2.Driver";

    private static final String URL_DB = "jdbc:h2:~/test";

    private static final String PATH_TO_SCHEMA = "../resources/main/testschema.sql";

    private static final String PATH_TO_DATA = "../resources/main/testdata.sql";

    private static InitDatabase ourInstance = new InitDatabase();

    private static Connection connection;

    private InitDatabase() {
        initialize();
    }

    public static InitDatabase getInstance() {
        return ourInstance;
    }

    private static void initialize() {
        logger.debug("Starting of initializing of database.");
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL_DB);
            createSchema(connection);
        } catch (Exception e) {
            throw new RuntimeException("Initializing of database failed", e);
        }
        logger.debug("Finishing of initializing of database.");
    }

    private static void createSchema(Connection connection) {
        logger.debug("Starting of creating H2 schema.");
        try {
            RunScript.execute(connection, new FileReader(PATH_TO_SCHEMA));
            RunScript.execute(connection, new FileReader(PATH_TO_DATA));
        } catch (Exception e) {
            throw new RuntimeException("Creating of the schema was failed.", e);
        }
        logger.debug("H2 schema was created.");
    }

    public static Connection getConnection() {
        return connection;
    }
}