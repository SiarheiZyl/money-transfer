package revolut.moneytransfer.client;

/**
 * MoneyTransfer request type
 *
 * @author szyl
 */
public class MoneyTransferRq {

    private Integer source;

    private Integer target;

    private Double amount;

    public MoneyTransferRq() {
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
