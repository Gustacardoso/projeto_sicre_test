package com.example.testes;

import com.example.config.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;

public class ProtectedProductsTest extends BaseTest {

    @Test
    @DisplayName("Lista produtos protegidos com token válido")
    public void deveListarProdutosProtegidosComTokenValido() {
        given()
                .header("Authorization", "Bearer " + obterToken())
                .when()
                .get(AUTH_PRODUCTS)
                .then()
                .statusCode(200)
                .body("products.size()", greaterThan(0))
                .body("products[0]", hasKey("title"))
                .body("products[0]", hasKey("price"));
    }

    @Test
    @DisplayName("Rejeita acesso com token inválido")
    public void naoDeveAcessarProdutosProtegidosComTokenInvalido() {
        given()
                .header("Authorization", "Bearer token-invalido")
                .when()
                .get(AUTH_PRODUCTS)
                .then()
                .statusCode(401)
                .body("message", equalTo("Invalid/Expired Token!"));
    }
}
