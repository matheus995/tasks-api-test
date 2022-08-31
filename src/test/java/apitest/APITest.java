package apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured.given()
                .when()
                .get("/todo")
                .then()
                .statusCode(200);
    }

    @Test
    public void deveAdicionarTarefaComSucesso() {
        JSONObject body = new JSONObject();
        body.put("task", "Teste via API");
        body.put("dueDate", "2024-10-05");

        RestAssured.given()
                .log().all()
                .when()
                .body(body.toString())
                .contentType(ContentType.JSON)
                .post("/todo")
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void naoDeveAdicionarTarefaComDataInvalida() {
        JSONObject body = new JSONObject();
        body.put("task", "Teste via API");
        body.put("dueDate", "2010-10-05");

        RestAssured.given()
                .log().all()
                .when()
                .body(body.toString())
                .contentType(ContentType.JSON)
                .post("/todo")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}
