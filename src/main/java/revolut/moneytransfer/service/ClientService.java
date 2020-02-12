package revolut.moneytransfer.service;

import java.sql.SQLException;

import revolut.moneytransfer.client.ClientAccountsRs;
import revolut.moneytransfer.client.CreateUpdateClientRq;
import revolut.moneytransfer.dao.ClientDao;
import revolut.moneytransfer.dao.DaoManager;
import revolut.moneytransfer.exception.IllegalClientException;
import revolut.moneytransfer.mapper.ClientToEntityMapper;
import revolut.moneytransfer.model.Client;
import revolut.moneytransfer.util.InitDatabase;
import revolut.moneytransfer.util.MessageConstants;

/**
 * Class that encapsulates business logic for clients
 *
 * @author szyl
 */
public class ClientService {

    private ClientDao clientDao;

    public ClientService() {
        clientDao = (ClientDao) DaoManager.createDao(InitDatabase.getConnection(), Client.class);
    }

    public boolean createClient(CreateUpdateClientRq clientRq) throws IllegalClientException {
        Client client = ClientToEntityMapper.createUpdateClientRqToClient(clientRq);

        if (clientDao != null && client.getId() != null) {
            try {
                if (clientDao.findById(client.getId()) == null) {
                    return clientDao.persist(client);
                } else {
                    throw new IllegalClientException(MessageConstants.CLIENT_ALREADY_EXISTS);
                }
            } catch (SQLException e) {
                throw new IllegalClientException(e.getMessage());
            }
        }
        return false;
    }

    public boolean updateClient(CreateUpdateClientRq clientRq) throws IllegalClientException {
        Client client = ClientToEntityMapper.createUpdateClientRqToClient(clientRq);

        if (clientDao != null && client.getId() != null) {
            try {
                if (clientDao.findById(client.getId()) != null) {
                    return clientDao.update(client);
                } else {
                    throw new IllegalClientException(MessageConstants.CLIENT_DOESNT_EXIST);
                }
            } catch (SQLException e) {
                throw new IllegalClientException(e.getMessage());
            }
        }
        return false;
    }

    public ClientAccountsRs getClientInformationAndAccounts(Integer id) {
        try {
            return ClientToEntityMapper.clientToClientAccountsRs(clientDao.findById(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
