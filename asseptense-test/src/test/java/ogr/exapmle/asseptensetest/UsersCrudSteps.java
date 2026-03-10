package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.example.bank2.dto.UserRequest;

import static io.restassured.RestAssured.given;
import static ogr.exapmle.asseptensetest.BaseSteps.*;
import static ogr.exapmle.asseptensetest.UserTemplates.getUserTemplate;

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

    @Given("существует пользователь по шаблону {string}")
    @When("я создаю пользователя по шаблону {string}")
    public void createUserByTemplate(String template) {
        UserRequest userRequest = getUserTemplate(template);

        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .body(gson.toJson(userRequest))
                .when()
                .post(BASE_URL + "/users");

        if (response.statusCode() == 201) {
            userId = response.jsonPath().getLong("id");
        }
    }
}
