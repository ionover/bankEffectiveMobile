package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.example.bank2.dto.UserRequest;

import static io.restassured.RestAssured.given;
import static ogr.exapmle.asseptensetest.BaseSteps.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersCrudSteps {

    @When("я получаю пользователей платформы")
    public void getAllUsers() {
        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .when()
                .get(BASE_URL + "/users");
    }

    @When("я получаю пользователей платформы с ID {int}")
    public void getUserById(int id) {
        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .when()
                .get(BASE_URL + "/users/" + id);
    }

    @When("я создаю пользователя по шаблону {string}")
    public void createUserByTemplate(String template) {
        UserRequest userRequest = usersByTemplate.computeIfAbsent(template, UserTemplates::getUserTemplate);

        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .body(gson.toJson(userRequest))
                .when()
                .post(BASE_URL + "/users");

        if (response.statusCode() == 201) {
            userId = response.jsonPath().getLong("id");
            userLogin = userRequest.getLogin();
            userPassword = userRequest.getPassword();
        }
    }

    @Given("существует пользователь по шаблону {string}")
    public void userExistsByTemplate(String template) {
        createUserByTemplate(template);
        assertEquals(201, response.statusCode(), "Пользователь не создан: " + response.asString());
    }
}
