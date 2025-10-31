package com.example.testes;

import com.example.config.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class GetUsersTest extends BaseTest {
    @Test
    @DisplayName("Deve Listar Usuarios Com Campos Para Login")
    public void deveListarUsuariosComCamposParaLogin() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .log().all()
                .body("users", notNullValue())
                .body("users.size()", greaterThan(0))
                .body("users[0].username", notNullValue())
                .body("users[0].password", notNullValue());
    }
}
