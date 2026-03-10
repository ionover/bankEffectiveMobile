package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class UsersCrudSteps {

    @Given("я создаю пользователя по шаблону {string}")
    public void createUserByTemplate(String template) {

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
