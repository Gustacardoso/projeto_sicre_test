package com.example.testes;

import com.example.config.BaseTest;
import com.example.utils.RequestAuth;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Login com credenciais válidas retorna token de acesso")
    public void deveAutenticarComCredenciaisValidas() {
        given()
                .contentType(ContentType.JSON)
                .body(new RequestAuth(VALID_USERNAME, VALID_PASSWORD))
                .when()
                .post(AUTH_LOGIN)
                .then()
                .statusCode(200)
                .body("accessToken", not(emptyOrNullString()))
                .body("username", equalTo(VALID_USERNAME));
    }

    @Test
    @DisplayName("Login com credenciais inválidas é rejeitado")
    public void naoDeveAutenticarComCredenciaisInvalidas() {
        given()
                .contentType(ContentType.JSON)
                .body(new RequestAuth(VALID_USERNAME, "senha-incorreta"))
                .when()
                .post(AUTH_LOGIN)
                .then()
                .statusCode(400)
                .body("message", equalTo("Invalid credentials"));
    }
}
