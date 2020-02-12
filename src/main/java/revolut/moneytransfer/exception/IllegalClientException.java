package revolut.moneytransfer.exception;

/**
 * Class that is used for exceptional situations with clients
 */
public class IllegalClientException extends Exception {

    public IllegalClientException(String message) {
        super(message);
    }

    public IllegalClientException(Throwable exception) {
        super(exception);
    }

    public IllegalClientException(String message, Throwable exception) {
        super(message, exception);
    }
}
