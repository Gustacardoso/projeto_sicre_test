package com.example.testes;

import com.example.config.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class UsersTest extends BaseTest {

    @Test
    @DisplayName("Lista usuários com os campos usados no login")
    public void deveListarUsuariosComCamposParaLogin() {
        given()
                .when()
                .get(USERS)
                .then()
                .statusCode(200)
                .body("users.size()", greaterThan(0))
                .body("users[0].username", notNullValue())
                .body("users[0].password", notNullValue());
    }
}
