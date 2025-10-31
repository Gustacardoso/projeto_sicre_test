package com.example.config;

import com.example.utils.RequestAuth;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected static String BASE_URI = "https://dummyjson.com";        // exemplo
    protected static String pathAuth = "/auth";                         // teu path

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
    protected String obterToken() {
        RequestAuth login = new RequestAuth("emilys", "emilyspass");

        return given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post(pathAuth + "/login")
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }
}
