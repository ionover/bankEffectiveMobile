package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.bank2.dto.CardRequest;
import org.example.bank2.dto.CardStatusUpdateRequest;
import org.example.bank2.dto.enums.Status;

import static io.restassured.RestAssured.given;
import static ogr.exapmle.asseptensetest.BaseSteps.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardCrudSteps {

    @Given("для последнего пользователя существует карта")
    @When("я создаю карту для последнего пользователя")
    public void createCard() {
        CardRequest cardRequest = new CardRequest(RandomStringUtils.randomAlphabetic(10), userId);

        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .body(gson.toJson(cardRequest))
                .when()
                .post(BASE_URL + "/cards");

        if (response.statusCode() == 201 || response.statusCode() == 200) {
            cardId = response.jsonPath().getLong("id");
        }
    }

    @When("я меняю статус текущей карты на {string}")
    public void changeCardStatus(String status) {
        CardStatusUpdateRequest request = new CardStatusUpdateRequest(Status.valueOf(status));

        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .body(gson.toJson(request))
                .when()
                .patch(BASE_URL + "/cards/" + cardId + "/status");
    }

    @When("я запрашиваю список карт")
    public void getCards() {
        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .when()
                .get(BASE_URL + "/cards");
    }

    @When("я запрашиваю текущую карту")
    public void getCurrentCard() {
        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .when()
                .get(BASE_URL + "/cards/" + cardId);
    }

    @When("я удаляю текущую карту")
    public void deleteCurrentCard() {
        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .when()
                .delete(BASE_URL + "/cards/" + cardId);
    }

    @Then("статус текущей карты {string}")
    public void checkCardStatus(String status) {
        assertEquals(status, response.jsonPath().getString("status"));
    }

    @And("в списке карт есть текущая карта")
    public void checkCardInList() {
        assertTrue(response.prettyPrint().contains("\"id\":" + cardId));
    }
}
