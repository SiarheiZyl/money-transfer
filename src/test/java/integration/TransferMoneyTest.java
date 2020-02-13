package integration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import integration.util.Constants;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class TransferMoneyTest {

    @Test
    public void TransferMoneyPositiveTest() {
        //creating a client
        String clientCreateRequest =
                "{\"id\":8,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                        "\"address\":\"LA\"}";
        HttpResponse<String> clientResponse = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(clientCreateRequest)
                .asString();
        assertEquals(200, clientResponse.getStatus());

        //creating the source account
        String accountCreateRequest =
                "{\"id\":3,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"8\"}";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(accountCreateRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //creating the target account
        accountCreateRequest =
                "{\"id\":4,\"amount\":30000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"8\"}";
        response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(accountCreateRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //transfer money between accounts
        String transferMoneyRequest = "{\"source\":3, \"target\":4, \"amount\":3000.0}";
        String expectedResponse = "{\"responseMessage\":\"Money transfer completed successfully!\"}";
        response = Unirest.post(Constants.SERVER_ADDRESS + Constants.TRANSFER_MONEY_CONTEXT_PATH)
                .body(transferMoneyRequest)
                .asString();
        assertEquals(200, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void transferMoneyWhenWithoutSourceAccountOrSourceAccountIsClosedTest() {
        //creating a client
        String clientCreateRequest =
                "{\"id\":9,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                        "\"address\":\"LA\"}";
        HttpResponse<String> clientResponse = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(clientCreateRequest)
                .asString();
        assertEquals(200, clientResponse.getStatus());

        //creating the target account
        String accountCreateRequest =
                "{\"id\":5,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"9\"}";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(accountCreateRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //transfer money between accounts
        String transferMoneyRequest = "{\"source\":6, \"target\":5, \"amount\":3000.0}";
        String expectedResponse = "{\"responseMessage\":\"Source account doesn't exist\"}";
        response = Unirest.post(Constants.SERVER_ADDRESS + Constants.TRANSFER_MONEY_CONTEXT_PATH)
                .body(transferMoneyRequest)
                .asString();
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());

        //creating the source account
        accountCreateRequest =
                "{\"id\":6,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"9\"}";
        response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(accountCreateRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //closing the source account
        String closingAccountRequest = "{\"id\":6}";
        response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CLOSE_ACCOUNT_CONTEXT_PATH)
                .body(closingAccountRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //transfer money between accounts again
        transferMoneyRequest = "{\"source\":6, \"target\":5, \"amount\":3333.0}";
        expectedResponse = "{\"responseMessage\":\"Source account was closed\"}";
        response = Unirest.post(Constants.SERVER_ADDRESS + Constants.TRANSFER_MONEY_CONTEXT_PATH)
                .body(transferMoneyRequest)
                .asString();
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void transferMoneyWithZeroAmount() {
        //creating a client
        String clientCreateRequest =
                "{\"id\":10,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                        "\"address\":\"LA\"}";
        HttpResponse<String> clientResponse = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(clientCreateRequest)
                .asString();
        assertEquals(200, clientResponse.getStatus());

        //creating the source account
        String accountCreateRequest =
                "{\"id\":7,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"10\"}";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(accountCreateRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //creating the target account
        accountCreateRequest =
                "{\"id\":8,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"10\"}";
        response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(accountCreateRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //transfer money between accounts
        String transferMoneyRequest = "{\"source\":8, \"target\":7, \"amount\":0.0}";
        String expectedResponse = "{\"responseMessage\":\"Wrong amount value passed\"}";
        response = Unirest.post(Constants.SERVER_ADDRESS + Constants.TRANSFER_MONEY_CONTEXT_PATH)
                .body(transferMoneyRequest)
                .asString();
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void transferMoneyWhenSourceAccountHasLessMoneyThanAmount() {
        //creating a client
        String clientCreateRequest =
                "{\"id\":11,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                        "\"address\":\"LA\"}";
        HttpResponse<String> clientResponse = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(clientCreateRequest)
                .asString();
        assertEquals(200, clientResponse.getStatus());

        //creating the source account
        String accountCreateRequest =
                "{\"id\":9,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"11\"}";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(accountCreateRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //creating the target account
        accountCreateRequest =
                "{\"id\":10,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"11\"}";
        response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(accountCreateRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //transfer money between accounts
        String transferMoneyRequest = "{\"source\":9, \"target\":10, \"amount\":300000.0}";
        String expectedResponse = "{\"responseMessage\":\"Source account doesn't have enough money\"}";
        response = Unirest.post(Constants.SERVER_ADDRESS + Constants.TRANSFER_MONEY_CONTEXT_PATH)
                .body(transferMoneyRequest)
                .asString();
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void transferMoneyWhenWithoutTargetAccountOrTargetAccountIsClosedTest() {
        //creating a client
        String clientCreateRequest =
                "{\"id\":12,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                        "\"address\":\"LA\"}";
        HttpResponse<String> clientResponse = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(clientCreateRequest)
                .asString();
        assertEquals(200, clientResponse.getStatus());

        //creating the source account
        String accountCreateRequest =
                "{\"id\":11,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"12\"}";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(accountCreateRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //transfer money between accounts
        String transferMoneyRequest = "{\"source\":11, \"target\":12, \"amount\":3000.0}";
        String expectedResponse = "{\"responseMessage\":\"Target account doesn't exist\"}";
        response = Unirest.post(Constants.SERVER_ADDRESS + Constants.TRANSFER_MONEY_CONTEXT_PATH)
                .body(transferMoneyRequest)
                .asString();
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());

        //creating the source account
        accountCreateRequest =
                "{\"id\":12,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"12\"}";
        response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(accountCreateRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //closing the source account
        String closingAccountRequest = "{\"id\":12}";
        response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CLOSE_ACCOUNT_CONTEXT_PATH)
                .body(closingAccountRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //transfer money between accounts again
        transferMoneyRequest = "{\"source\":11, \"target\":12, \"amount\":3333.0}";
        expectedResponse = "{\"responseMessage\":\"Target account was closed\"}";
        response = Unirest.post(Constants.SERVER_ADDRESS + Constants.TRANSFER_MONEY_CONTEXT_PATH)
                .body(transferMoneyRequest)
                .asString();
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void transferMoneyWhenSourceAndTargetAccountAreEqualTest() {
        //creating a client
        String clientCreateRequest =
                "{\"id\":13,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                        "\"address\":\"LA\"}";
        HttpResponse<String> clientResponse = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(clientCreateRequest)
                .asString();
        assertEquals(200, clientResponse.getStatus());

        //creating the source(target) account
        String accountCreateRequest =
                "{\"id\":13,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"13\"}";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(accountCreateRequest)
                .asString();
        assertEquals(200, response.getStatus());

        //transfer money between accounts
        String transferMoneyRequest = "{\"source\":13, \"target\":13, \"amount\":3000.0}";
        String expectedResponse = "{\"responseMessage\":\"Source and target accounts are the same\"}";
        response = Unirest.post(Constants.SERVER_ADDRESS + Constants.TRANSFER_MONEY_CONTEXT_PATH)
                .body(transferMoneyRequest)
                .asString();
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }
}
