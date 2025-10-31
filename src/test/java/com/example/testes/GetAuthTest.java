package com.example.testes;

import com.example.config.BaseTest;
import com.example.utils.RequestAuth;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetAuthTest  extends BaseTest {
    @Test
    @DisplayName("Login Com Sucesso")
    public void loginComSucesso() {
        RequestAuth login = new RequestAuth("emilys", "emilyspass");

        given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(pathAuth + "/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }
}
