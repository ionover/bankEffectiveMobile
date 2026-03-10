package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.bank2.dto.CardRequest;

import static io.restassured.RestAssured.given;
import static ogr.exapmle.asseptensetest.BaseSteps.*;

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
    }

    @When("я меняю статус текущей карты на {string}")
    public void changeCardStatus(String status) {
     //TODO ты тут
    }
}
