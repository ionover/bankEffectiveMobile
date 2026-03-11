package ogr.exapmle.asseptensetest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.example.bank2.dto.TransferMoneyRequest;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static ogr.exapmle.asseptensetest.BaseSteps.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationsSteps {

    @Given("баланс последней карты пополнен на {int} у.е.")
    @When("я пополняю баланс карты на {int} у.е.")
    public void topUpCardBalance(int amount) {
        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .body(gson.toJson(new HashMap<>() {{
                    put("depositAmount", amount);
                }}))
                .when()
                .post(BASE_URL + "/operations/topOnBalance/" + cardId);
    }

    @When("я перевожу деньги между картами {int} у.е.")
    public void transferMoneyBetweenCards(int money) {
        assertEquals(2, createdCards.size(), "Для перевода нужно строго две карты");

        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setCardFromId(createdCards.get(0));
        request.setCardWhereId(createdCards.get(1));
        request.setMoney((long) money);

        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .body(gson.toJson(request))
                .when()
                .post(BASE_URL + "/operations/transferMoney");
    }
}
