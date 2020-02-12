package revolut.moneytransfer.model;

import java.util.List;

/**
 * Client entity
 *
 * @author szyl
 */
public class Client {

    public static String tableName = "CLIENT";

    public static int objectsQuantity;

    private Integer id;

    private String name;

    private String surname;

    private String country;

    private String city;

    private String address;

    private List<Account> accounts;

    public Client() {
    }

    public Client(Integer clientId, String name, String surname, String country, String city, String address) {
        this.id = clientId;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.city = city;
        this.address = address;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
