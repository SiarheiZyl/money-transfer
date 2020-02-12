package revolut.moneytransfer.client;

import java.time.LocalDateTime;

/**
 * Account response type
 *
 * @author szyl
 */
public class AccountRs {
    private Integer id;

    private Double amount;

    private LocalDateTime opendate;

    private LocalDateTime closedate;

    private String status;

    public AccountRs() {
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

    public LocalDateTime getOpendate() {
        return opendate;
    }

    public void setOpendate(LocalDateTime opendate) {
        this.opendate = opendate;
    }

    public LocalDateTime getClosedate() {
        return closedate;
    }

    public void setClosedate(LocalDateTime closedate) {
        this.closedate = closedate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
