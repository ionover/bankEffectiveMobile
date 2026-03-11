package ogr.exapmle.asseptensetest;

import com.google.gson.Gson;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseSteps {

    public static final String BASE_URL = "http://localhost:8084";
    public static String TOKEN;
    public static Long userId;
    public static Long cardId;
    public static List<Long> createdCards = new ArrayList<>();

    public static Response response;
    public static Gson gson = new Gson();



    @Then("сервер отвечает статусом {int}")
    public void checkStatus(int status) {
        assertEquals(status, response.statusCode());
    }

    @And("в ответе содержится {string}")
    public void checkResponser(String msg) {
        assertTrue(response.prettyPrint().contains(msg));
    }

    @Given("удалены все пользователи кроме системного администратора")
    public void deleteAllUsersExceptFirst() {
        response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .when()
                .get(BASE_URL + "/users?size=1000");

        List<Long> userIds = response.jsonPath().getList("content.id", Long.class);
        if (userIds == null || userIds.isEmpty()) {
            return;
        }

        for (Long id : userIds) {
            if (id == null || id == 1L) {
                continue;
            }

            response = given()
                    .header("Authorization", "Bearer " + TOKEN)
                    .header("Content-Type", "application/json")
                    .when()
                    .delete(BASE_URL + "/users/" + id);
        }
    }

    @Given("очищен пул созданных ранее карт")
    public void clearCreatedCards() {
        createdCards.clear();
    }
}
