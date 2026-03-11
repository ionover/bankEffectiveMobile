package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.bank2.dto.CardRequest;
import org.example.bank2.dto.CardResponse;

import java.util.List;
import java.util.Optional;

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

        if (response.statusCode() == 201) {
            cardId = response.jsonPath().getLong("id");
            createdCards.add(cardId);
        }
    }

    @When("я меняю статус текущей карты на {string}")
    public void changeCardStatus(String status) {
        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .body(gson.toJson(status))
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
        List<CardResponse> cards = response.jsonPath().getList("content", CardResponse.class);
        Optional<CardResponse> currentCard = cards.stream()
                                                  .filter(card -> cardId.equals(card.getId()))
                                                  .findFirst();

        assertTrue(currentCard.isPresent());
    }
}
