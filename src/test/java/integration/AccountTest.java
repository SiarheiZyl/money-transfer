package integration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import integration.util.Constants;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class AccountTest {

    @Test
    public void createAndCloseAccountPositiveTest() {
        //creating a client
        String clientCreateRequest =
                "{\"id\":5,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                        "\"address\":\"LA\"}";
        HttpResponse<String> clientResponse = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(clientCreateRequest)
                .asString();
        assertEquals(200, clientResponse.getStatus());

        //creating an account
        String request =
                "{\"id\":1,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"5\"}";
        String expectedResponse = "Account created successfully!";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(request)
                .asString();
        assertEquals(200, response.getStatus());
        assertEquals(expectedResponse, response.getBody());

        //closing an account
        request = "{\"id\":1}";
        expectedResponse = "Account closed successfully!";
        response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CLOSE_ACCOUNT_CONTEXT_PATH)
                .body(request)
                .asString();
        assertEquals(200, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void createAccountRequestWithExistingIdTest() {
        //creating a client
        String clientCreateRequest =
                "{\"id\":6,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                        "\"address\":\"LA\"}";
        HttpResponse<String> clientResponse = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(clientCreateRequest)
                .asString();
        assertEquals(200, clientResponse.getStatus());

        //creating an account
        String request =
                "{\"id\":2,\"amount\":30000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"6\"}";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(request)
                .asString();
        assertEquals(200, response.getStatus());
        response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(request)
                .asString();
        String expectedResponse = "Source account already exists";
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void createAccountWithoutIdInRequest() {
        //creating a client
        String clientCreateRequest =
                "{\"id\":7,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                        "\"address\":\"LA\"}";
        HttpResponse<String> clientResponse = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(clientCreateRequest)
                .asString();
        assertEquals(200, clientResponse.getStatus());

        //creating an account
        String request = "{\"amount\":30000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"7\"}";
        String expectedResponse = "Account creation failed!";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(request)
                .asString();
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void closeNotExistingAccount() {
        String request = "{\"id\":666}";
        String expectedResponse = "Source account doesn't exist";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CLOSE_ACCOUNT_CONTEXT_PATH)
                .body(request)
                .asString();
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void createAccountWithoutExistingClientTest() {
        //creating an account
        String request =
                "{\"id\":1,\"amount\":20000.0,\"opendate\":" + System.currentTimeMillis() + ", \"client\":\"555\"}";
        String expectedResponse = "Client for this account doesn't exist";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.ACCOUNTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(request)
                .asString();
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }
}
