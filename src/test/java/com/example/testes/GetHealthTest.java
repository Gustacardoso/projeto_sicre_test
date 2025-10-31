package com.example.testes;

import com.example.config.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetHealthTest extends BaseTest {
    @Test
    public void deveRetornarStatusOkEDizerQueEMetodoGet() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/test")
                .then()
                .statusCode(200)
                .body("status", equalTo("ok"))
                .body("method", equalTo("GET"));
    }
}
