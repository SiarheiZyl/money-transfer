package revolut.moneytransfer.model;

import java.sql.Timestamp;

/**
 * File entity
 *
 * @author szyl
 */
public class File {

    public static String tableName = "FILE";

    public static int objectsQuantity;

    private Integer id;

    private Timestamp fileDate;

    private String status;

    private Double amount;

    private String response;

    private Account source;

    private Account target;

    public File() {
    }

    public File(Integer fileId, Timestamp fileDate, String status, Double amount, String response, Account source,
            Account target) {
        this.id = fileId;
        this.fileDate = fileDate;
        this.status = status;
        this.amount = amount;
        this.response = response;
        this.source = source;
        this.target = target;
    }

    public static String getTableName() {
        return tableName;
    }

    public static void setTableName(String tableName) {
        File.tableName = tableName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getFileDate() {
        return fileDate;
    }

    public void setFileDate(Timestamp fileDate) {
        this.fileDate = fileDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Account getSource() {
        return source;
    }

    public void setSource(Account source) {
        this.source = source;
    }

    public Account getTarget() {
        return target;
    }

    public void setTarget(Account target) {
        this.target = target;
    }
}
