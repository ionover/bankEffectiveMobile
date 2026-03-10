package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.bank2.dto.UserRequest;

import static io.restassured.RestAssured.given;
import static ogr.exapmle.asseptensetest.BaseSteps.*;
import static ogr.exapmle.asseptensetest.UserTemplates.getUserTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersCrudSteps {

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

        assertEquals(200, response.statusCode());
    }

    @When("Admin creates a user with name {string}")
    public void adminCreatesUser(String name) {
        // TODO: Implement step
    }

    @Then("User {string} should be created")
    public void userShouldBeCreated(String name) {
        // TODO: Implement step
    }
}
