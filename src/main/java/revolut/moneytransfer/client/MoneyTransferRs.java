package revolut.moneytransfer.client;

/**
 * MoneyTransfer response type
 *
 * @author szyl
 */
public class MoneyTransferRs {

    private String responseMessage;

    public MoneyTransferRs() {
    }

    public MoneyTransferRs(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
