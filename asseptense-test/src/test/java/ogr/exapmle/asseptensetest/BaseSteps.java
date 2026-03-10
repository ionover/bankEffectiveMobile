package ogr.exapmle.asseptensetest;

import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseSteps {

    public static final String BASE_URL = "http://localhost:8084";
    public static String TOKEN;
    public static Long userId;

    public static Response response;
    public static Gson gson = new Gson();
    public static Random random = new Random();

    @Then("сервер отвечает статусом {int}")
    public void checkStatus(int status) {
        assertEquals(status, response.statusCode());
    }

    @And("в ответе содержится {string}")
    public void checkResponser(String msg) {
        assertTrue(response.prettyPrint().contains(msg));
    }
}
