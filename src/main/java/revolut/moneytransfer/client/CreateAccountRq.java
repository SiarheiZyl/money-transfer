package revolut.moneytransfer.client;

import java.sql.Timestamp;

/**
 * CreateAccount request type
 *
 * @author szyl
 */
public class CreateAccountRq {

    private Integer id;

    private Double amount;

    private Timestamp opendate;

    private Integer client;

    public CreateAccountRq() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getOpendate() {
        return opendate;
    }

    public void setOpendate(Timestamp opendate) {
        this.opendate = opendate;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }
}
