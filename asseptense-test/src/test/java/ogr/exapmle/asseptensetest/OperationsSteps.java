package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import static io.restassured.RestAssured.given;
import static ogr.exapmle.asseptensetest.BaseSteps.*;

public class OperationsSteps {

    @Given("баланс последней карты пополнен")
    @When("я пополняю баланс карты")
    public void topUpCardBalance() {
        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .body(gson.toJson(100L))
                .when()
                .post(BASE_URL + "/operations/topOnBalance/" + cardId);
    }
}
