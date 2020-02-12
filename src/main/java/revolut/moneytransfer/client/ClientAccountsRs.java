package revolut.moneytransfer.client;

import java.util.List;

/**
 * ClientAccounts response type
 *
 * @author szyl
 */
public class ClientAccountsRs {

    private Integer id;

    private String name;

    private String surname;

    private String country;

    private String city;

    private String address;

    private List<AccountRs> accounts;

    public ClientAccountsRs() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<AccountRs> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountRs> accounts) {
        this.accounts = accounts;
    }
}
