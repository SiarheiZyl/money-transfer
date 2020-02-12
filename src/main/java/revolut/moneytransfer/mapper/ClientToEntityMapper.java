package revolut.moneytransfer.mapper;

import java.util.stream.Collectors;

import revolut.moneytransfer.client.AccountRs;
import revolut.moneytransfer.client.ClientAccountsRs;
import revolut.moneytransfer.client.CreateAccountRq;
import revolut.moneytransfer.client.CreateUpdateClientRq;
import revolut.moneytransfer.model.Account;
import revolut.moneytransfer.model.Client;

/**
 * Mapping between client classes and entities
 *
 * @author szyl
 */
public class ClientToEntityMapper {

    public static Account createAccountRqToAccount(CreateAccountRq createAccountRq) {
        if (createAccountRq == null) {
            return null;
        }

        Account account = new Account();
        account.setId(createAccountRq.getId());
        account.setAmount(createAccountRq.getAmount());
        account.setOpendate(createAccountRq.getOpendate());

        return account;
    }

    public static Client createUpdateClientRqToClient(CreateUpdateClientRq clientRq) {
        if (clientRq == null) {
            return null;
        }

        Client client = new Client();
        client.setId(clientRq.getId());
        client.setName(clientRq.getName());
        client.setSurname(clientRq.getSurname());
        client.setCountry(clientRq.getCountry());
        client.setCity(clientRq.getCity());
        client.setAddress(clientRq.getAddress());

        return client;
    }

    public static ClientAccountsRs clientToClientAccountsRs(Client client) {
        if (client == null) {
            return null;
        }

        ClientAccountsRs clientAccountsRs = new ClientAccountsRs();
        clientAccountsRs.setId(client.getId());
        clientAccountsRs.setName(client.getName());
        clientAccountsRs.setSurname(client.getSurname());
        clientAccountsRs.setCountry(client.getCountry());
        clientAccountsRs.setCity(client.getCity());
        clientAccountsRs.setAddress(client.getAddress());
        clientAccountsRs.setAccounts(client.getAccounts()
                .stream()
                .map(ClientToEntityMapper::accountToAccountRs)
                .collect(Collectors.toList()));

        return clientAccountsRs;
    }

    private static AccountRs accountToAccountRs(Account account) {
        if (account == null) {
            return null;
        }
        AccountRs accountRs = new AccountRs();
        accountRs.setId(account.getId());
        accountRs.setAmount(account.getAmount());
        accountRs.setOpendate(account.getOpendate().toLocalDateTime());
        if (account.getClosedate() != null) {
            accountRs.setClosedate(account.getClosedate().toLocalDateTime());
        }
        accountRs.setStatus(account.getStatus());

        return accountRs;
    }
}