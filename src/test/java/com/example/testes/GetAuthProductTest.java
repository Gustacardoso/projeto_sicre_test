package com.example.testes;

import com.example.config.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class GetAuthProductTest extends BaseTest {
    @Test
    @DisplayName("Deve listar produtos protegidos com sucesso")
    public void deveAutenticarEListarProdutosProtegidos() {
        String token = obterToken();
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(pathAuth+"/products")
                .then()
                .log().all()
                .statusCode(200)
                .body("products", notNullValue())
                .body("products.size()", greaterThan(0))
                .body("products[0]", hasKey("title"))
                .body("products[0]", hasKey("price"));
    }

    @Test
    @DisplayName("Token Invalido")
    public void naoDeveAcessarProdutosProtegidosSemToken() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + "ff")
                .when()
                .get(pathAuth+"/products")
                .then()
                .log().all()
                .body("message", equalTo("Invalid/Expired Token!"))
                .statusCode(401); // depende de como eles configuraram
    }
}
