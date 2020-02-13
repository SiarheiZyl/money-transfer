package revolut.moneytransfer.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.http.Handler;
import revolut.moneytransfer.client.ClientAccountsRs;
import revolut.moneytransfer.client.CloseAccountRq;
import revolut.moneytransfer.client.CreateAccountRq;
import revolut.moneytransfer.client.CreateUpdateClientRq;
import revolut.moneytransfer.client.MoneyTransferRq;
import revolut.moneytransfer.client.MoneyTransferRs;
import revolut.moneytransfer.exception.IllegalAccountException;
import revolut.moneytransfer.exception.IllegalClientException;
import revolut.moneytransfer.exception.WrongAmountException;
import revolut.moneytransfer.service.AccountService;
import revolut.moneytransfer.service.ClientService;
import revolut.moneytransfer.service.TransferMoneyService;
import revolut.moneytransfer.util.MessageConstants;

/**
 * Class that operates with REST requests
 *
 * @author szyl
 */
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private static AccountService accountService = new AccountService();

    private static ClientService clientService = new ClientService();

    private static TransferMoneyService transferMoneyService = new TransferMoneyService();

    public static Handler getClientInformationAndAccounts = ctx -> {
        logger.debug("getClientInformationAndAccounts request");
        Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
        ClientAccountsRs response = clientService.getClientInformationAndAccounts(id);
        if (response == null) {
            ctx.result("Not Found");
            logger.debug("getClientInformationAndAccounts response is null");
        } else {
            ctx.json(response);
            logger.debug("getClientInformationAndAccounts response was sent");
        }
    };

    public static Handler createAccount = ctx -> {
        logger.debug("createAccount request is started");
        String body = ctx.body();
        ObjectMapper mapper = new ObjectMapper();
        CreateAccountRq createAccountRq = mapper.readValue(body, CreateAccountRq.class);
        try {
            if (accountService.createAccount(createAccountRq)) {
                ctx.result(MessageConstants.ACCOUNT_CREATED_SUCCESSFULLY);
            } else {
                ctx.status(500);
                ctx.result(MessageConstants.ACCOUNT_CREATION_FAILED);
            }
        } catch (IllegalAccountException e) {
            ctx.status(500);
            ctx.result(e.getMessage());
        }
        logger.debug("createAccount request is finished");
    };

    public static Handler closeAccount = ctx -> {
        logger.debug("closeAccount request is started");
        String body = ctx.body();
        ObjectMapper mapper = new ObjectMapper();
        CloseAccountRq closeAccountRq = mapper.readValue(body, CloseAccountRq.class);
        try {
            if (accountService.closeAccount(closeAccountRq.getId())) {
                ctx.result(MessageConstants.ACCOUNT_CLOSED_SUCCESSFULLY);
            } else {
                ctx.status(500);
                ctx.result(MessageConstants.ACCOUNT_CLOSING_FAILED);
            }
        } catch (IllegalAccountException e) {
            ctx.status(500);
            ctx.result(e.getMessage());
        }
        logger.debug("closeAccount request is finished");
    };

    public static Handler createClient = ctx -> {
        logger.debug("createClient request is started");
        String body = ctx.body();
        ObjectMapper mapper = new ObjectMapper();
        CreateUpdateClientRq createClientRq = mapper.readValue(body, CreateUpdateClientRq.class);
        try {
            if (clientService.createClient(createClientRq)) {
                ctx.result(MessageConstants.CLIENT_CREATED_SUCCESSFULLY);
            } else {
                ctx.status(500);
                ctx.result(MessageConstants.CLIENT_CREATION_FAILED);
            }
        } catch (IllegalClientException e) {
            ctx.status(500);
            ctx.result(e.getMessage());
        }
        logger.debug("createClient request is finished");
    };

    public static Handler updateClient = ctx -> {
        logger.debug("updateClient request is started");
        String body = ctx.body();
        ObjectMapper mapper = new ObjectMapper();
        CreateUpdateClientRq updateClientRq = mapper.readValue(body, CreateUpdateClientRq.class);
        try {
            if (clientService.updateClient(updateClientRq)) {
                ctx.result(MessageConstants.CLIENT_UPDATED_SUCCESSFULLY);
            } else {
                ctx.result(MessageConstants.CLIENT_UPDATE_FAILED);
            }
        } catch (IllegalClientException e) {
            ctx.status(500);
            ctx.result(e.getMessage());
        }
        logger.debug("updateClient request is finished");
    };

    public static Handler transferMoney = ctx -> {
        logger.debug("moneyTransfer request is started");
        String body = ctx.body();
        ObjectMapper mapper = new ObjectMapper();
        MoneyTransferRq moneyTransferRq = mapper.readValue(body, MoneyTransferRq.class);
        try {
            boolean result = transferMoneyService.createAndProceedTransfer(moneyTransferRq);
            if (result) {
                ctx.json(new MoneyTransferRs(MessageConstants.SUCCESSFUL_MONEY_TRANSFER));
            } else {
                ctx.status(500);
                ctx.json(new MoneyTransferRs(MessageConstants.FAILED_MONEY_TRANSFER));
            }
        } catch (IllegalAccountException | WrongAmountException e) {
            ctx.status(500);
            ctx.json(new MoneyTransferRs(e.getMessage()));
        }
        logger.debug("moneyTransfer request is finished");
    };
}