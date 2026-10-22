package com.example.config;

import com.example.utils.RequestAuth;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {

    protected static final String BASE_URI = System.getProperty("base.uri", "https://dummyjson.com");

    protected static final String HEALTH = "/test";
    protected static final String USERS = "/users";
    protected static final String PRODUCTS = "/products";
    protected static final String AUTH_LOGIN = "/auth/login";
    protected static final String AUTH_PRODUCTS = "/auth/products";

    protected static final String VALID_USERNAME = "emilys";
    protected static final String VALID_PASSWORD = "emilyspass";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected String obterToken() {
        return given()
                .contentType(ContentType.JSON)
                .body(new RequestAuth(VALID_USERNAME, VALID_PASSWORD))
                .when()
                .post(AUTH_LOGIN)
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }
}
