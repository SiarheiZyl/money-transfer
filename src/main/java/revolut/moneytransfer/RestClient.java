package revolut.moneytransfer;

import io.javalin.Javalin;
import revolut.moneytransfer.controller.UserController;

/**
 * Main class of the application
 *
 * @author szyl
 */
public class RestClient {

    public static void main(String... args) throws Exception {
        Javalin app = Javalin.create().start(7000);

        app.get("/hello", ctx -> ctx.json("Hello, Javalin!"));
        app.get("/clients/:id/accountsInfo", UserController.getClientInformationAndAccounts);
        app.post("/clients/create", UserController.createClient);
        app.post("/clients/update", UserController.updateClient);
        app.post("/accounts/create", UserController.createAccount);
        app.post("/accounts/close", UserController.closeAccount);
        app.post("/transferMoney", UserController.transferMoney);
    }
}