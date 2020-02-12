package integration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import integration.util.Constants;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class ClientTest {

    @Test
    public void createClientPositiveTest() {
        String request = "{\"id\":1,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                "\"address\":\"LA\"}";
        String expectedResponse = "Client created successfully!";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(request)
                .asString();
        assertEquals(200, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void createClientRequestWithExistingIdTest() {
        String request = "{\"id\":2,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                "\"address\":\"LA\"}";

        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(request)
                .asString();
        assertEquals(200, response.getStatus());
        response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(request)
                .asString();
        String expectedResponse = "Client already exists";
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void createClientWithoutIdInRequest() {
        String request =
                "{\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," + "\"address\":\"5\"}";
        String expectedResponse = "Client creation failed!";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(request)
                .asString();
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void updateClientPositiveTest() {
        String request = "{\"id\":3,\"name\":\"Kobe\",\"surname\":\"Bryant\",\"country\":\"USA\",\"city\":\"LA\"," +
                "\"address\":\"LA\"}";

        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.CREATE_CONTEXT_PATH)
                .body(request)
                .asString();
        assertEquals(200, response.getStatus());
        String updateRequest =
                "{\"id\":3,\"name\":\"Sergey\",\"surname\":\"Zyl\",\"country\":\"Belarus\",\"city\":\"Minsk\"," +
                        "\"address\":\"Minsk\"}";
        response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.UPDATE_CONTEXT_PATH)
                .body(updateRequest)
                .asString();
        String expectedResponse = "Client updated successfully!";
        assertEquals(200, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void updateNotExistingClientTest() {
        String updateRequest =
                "{\"id\":4,\"name\":\"Sergey\",\"surname\":\"Zyl\",\"country\":\"Belarus\",\"city\":\"Minsk\"," +
                        "\"address\":\"Minsk\"}";
        HttpResponse<String> response = Unirest.post(
                Constants.SERVER_ADDRESS + Constants.CLIENTS_CONTEXT_PATH + Constants.UPDATE_CONTEXT_PATH)
                .body(updateRequest)
                .asString();
        String expectedResponse = "Client doesn't exists";
        assertEquals(500, response.getStatus());
        assertEquals(expectedResponse, response.getBody());
    }
}
