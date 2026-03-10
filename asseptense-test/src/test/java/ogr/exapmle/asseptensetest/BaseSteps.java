package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.Given;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BaseSteps {


    public Response response;

    @Given("пользователь авторизован как {string} с паролем {string}")
    public void doLogin(String username,  String password) {
        LoginDto login = new LoginDto(username, password);

        response = given()
                .body(gson.toJson(login))
                .when()
                .post("http://localhost:8080/oauth/login");
    }

    @Then("сервер отвечает статусом {int}")
    public void checkStatus(int status) {
        Assertion.assertEaquals(response.getStatusCode(), status);
    }
}
