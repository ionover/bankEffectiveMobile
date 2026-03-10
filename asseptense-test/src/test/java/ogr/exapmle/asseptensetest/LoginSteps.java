package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.example.bank2.dto.LoginDto;

import static io.restassured.RestAssured.given;
import static ogr.exapmle.asseptensetest.BaseSteps.*;

public class LoginSteps {

    @When("я авторизую с логином {string} и паролем {string}")
    @Given("пользователь авторизован как {string} с паролем {string}")
    public void doLogin(String username, String password) {
        LoginDto login = new LoginDto(username, password);

        login(login);

        if (response.statusCode() == 200) {
            TOKEN = response.body().asString();
        }
    }

    private static void login(LoginDto login) {
        response = given()
                .header("Content-Type", "application/json")
                .body(gson.toJson(login))
                .when()
                .post(BASE_URL + "/oauth/login");
    }

    @When("я авторизуюсь с отсутствующим {string}")
    public void brokenLogin(String data) {
        LoginDto login = new LoginDto();

        if (data.equals("паролем")) {
            login.setLogin("noMatter");
        } else if (data.equals("логином")) {
            login.setPassword("noMatter");
        } else {
            throw new IllegalArgumentException("Нет возможно создать сломанные данные авторизации");
        }

        login(login);
    }
}
