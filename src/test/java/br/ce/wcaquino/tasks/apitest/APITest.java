package br.ce.wcaquino.tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

public class APITest {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarListaDeTarefas(){
        RestAssured
                .given()
                .when()
                    .get("/todo")
                .then()
                .statusCode(200)
        ;
    }

    @Test
    public void deveCadastrarUmaTask(){
        RestAssured.given()
                .when()
                .body(
                    "{\"task\": \"Teste via API\", \"dueDate\":" + "\"" + LocalDate.now()+ "\" }"
                )
                .contentType(ContentType.JSON)
                .post("/todo")
                .then()
                .statusCode(201)
        ;
    }

    @Test
    public void deveValidarDataInvalida(){
        RestAssured.given()
                .when()
                    .body(
                            "{\"task\": \"Teste via API\", \"dueDate\":" + "\"" + LocalDate.now().minusDays(1)+ "\" }"
                    )
                    .contentType(ContentType.JSON)
                    .post("/todo")
                .then()
                    .log().all()
                    .statusCode(400)
                    .body("message", CoreMatchers.is("Due date must not be in past"))
        ;
    }

}
