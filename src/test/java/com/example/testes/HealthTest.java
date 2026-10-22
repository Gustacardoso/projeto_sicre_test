package com.example.testes;

import com.example.config.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthTest extends BaseTest {

    @Test
    @DisplayName("API responde status ok no endpoint de saúde")
    public void deveRetornarStatusOk() {
        given()
                .when()
                .get(HEALTH)
                .then()
                .statusCode(200)
                .body("status", equalTo("ok"))
                .body("method", equalTo("GET"));
    }
}
