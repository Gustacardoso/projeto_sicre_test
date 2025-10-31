package com.example.testes;

import com.example.config.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static com.example.utils.StartValues.pathProduct;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class GetProductTest extends BaseTest {
    @Test
    public void deveListarProdutos() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(pathProduct)
                .then()
                .statusCode(200)
                .body("products", notNullValue())
                .body("products.size()", greaterThan(0));
    }

    @Test
    public void deveBuscarProdutoPorId() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(pathProduct+"/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", notNullValue());
    }

    @Test
    public void naoDeveEncontrarProdutoInexistente() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(pathProduct+"/999999")
                .then()
                .statusCode(anyOf(is(404), is(400))); // depende do dummyjson
    }
}
