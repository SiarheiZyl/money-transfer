package revolut.moneytransfer.model;

import java.sql.Timestamp;

/**
 * Account entity
 *
 * @author szyl
 */
public class Account {

    public static String tableName = "ACCOUNT";

    public static int objectsQuantity;

    private Integer id;

    private Double amount;

    private Timestamp opendate;

    private Timestamp closedate;

    private String status;

    private Client client;

    public Account() {
    }

    public Account(Integer accountId, Double amount, Timestamp opendate, Timestamp closedate, String status) {
        this.id = accountId;
        this.amount = amount;
        this.opendate = opendate;
        this.closedate = closedate;
        this.status = status;
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

    public Timestamp getClosedate() {
        return closedate;
    }

    public void setClosedate(Timestamp closedate) {
        this.closedate = closedate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
