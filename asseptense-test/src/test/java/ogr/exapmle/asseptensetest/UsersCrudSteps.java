package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.bank2.dto.UserRequest;

import static ogr.exapmle.asseptensetest.UserTemplates.getUserTemplate;

public class UsersCrudSteps {

    @Given("существует пользователь по шаблону {string}")
    @When("я создаю пользователя по шаблону {string}")
    public void createUserByTemplate(String template) {
        UserRequest userRequest = getUserTemplate(template);
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
