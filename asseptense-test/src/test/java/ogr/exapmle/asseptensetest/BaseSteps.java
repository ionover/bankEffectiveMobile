package ogr.exapmle.asseptensetest;

import com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.example.bank2.dto.LoginDto;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseSteps {

    public static final String BASE_URL = "http://localhost:8080/";
    public static String TOKEN;

    public Response response;
    public static Gson gson;

    @Given("пользователь авторизован как {string} с паролем {string}")
    public void doLogin(String username, String password) {
        LoginDto login = new LoginDto(username, password);

        response = given()
                .body(gson.toJson(login))
                .when()
                .post(BASE_URL + "/oauth/login");

        if (response.statusCode() == 200) {
            TOKEN = response.body().asString();
        }
    }

    @Then("сервер отвечает статусом {int}")
    public void checkStatus(int status) {
        assertEquals(response.getStatusCode(), status);
    }
}
