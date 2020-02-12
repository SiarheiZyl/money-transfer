package revolut.moneytransfer.exception;

/**
 * Class for exception when account was not found
 */
public class IllegalAccountException extends Exception {

    public IllegalAccountException(String message) {
        super(message);
    }

    public IllegalAccountException(Throwable exception) {
        super(exception);
    }

    public IllegalAccountException(String message, Throwable exception) {
        super(message, exception);
    }
}
