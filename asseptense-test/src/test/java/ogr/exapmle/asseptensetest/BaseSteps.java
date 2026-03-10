package ogr.exapmle.asseptensetest;

import com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.example.bank2.dto.LoginDto;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseSteps {

    public static final String BASE_URL = "http://localhost:8084";
    public static String TOKEN;

    public static Response response;
    public static Gson gson = new Gson();

    @Given("пользователь авторизован как {string} с паролем {string}")
    public void doLogin(String username, String password) {
        LoginDto login = new LoginDto(username, password);

        response = given()
                .header("Content-Type", "application/json")
                .body(gson.toJson(login))
                .when()
                .post(BASE_URL + "/oauth/login");

        assertEquals(200, response.getStatusCode());
        TOKEN = response.body().asString();
    }

    @Then("сервер отвечает статусом {int}")
    public void checkStatus(int status) {
        assertEquals(status, response.statusCode());
    }
}
