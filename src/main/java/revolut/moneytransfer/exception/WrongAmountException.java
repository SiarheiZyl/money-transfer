package revolut.moneytransfer.exception;

/**
 * Class for illegal amount value
 */
public class WrongAmountException extends Exception {
    public WrongAmountException(String message) {
        super(message);
    }

    public WrongAmountException(Throwable exception) {
        super(exception);
    }

    public WrongAmountException(String message, Throwable exception) {
        super(message, exception);
    }
}
