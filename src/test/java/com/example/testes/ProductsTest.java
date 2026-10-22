package com.example.testes;

import com.example.config.BaseTest;
import com.example.utils.RequestProducts;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;

public class ProductsTest extends BaseTest {

    @Test
    @DisplayName("Lista produtos")
    public void deveListarProdutos() {
        given()
                .when()
                .get(PRODUCTS)
                .then()
                .statusCode(200)
                .body("products.size()", greaterThan(0));
    }

    @Test
    @DisplayName("Busca produto por ID")
    public void deveBuscarProdutoPorId() {
        given()
                .when()
                .get(PRODUCTS + "/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", not(emptyOrNullString()));
    }

    @Test
    @DisplayName("Produto inexistente retorna 404")
    public void naoDeveEncontrarProdutoInexistente() {
        given()
                .when()
                .get(PRODUCTS + "/999999")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Cria produto válido")
    public void deveCriarProduto() {
        RequestProducts produto = new RequestProducts(
                "Perfume Oil",
                "Produto criado no teste automatizado",
                13,
                8.4,
                4.26,
                65,
                "Impression of Acqua Di Gio",
                "fragrances",
                "https://i.dummyjson.com/data/products/11/thumbnail.jpg");

        // O DummyJSON não persiste o produto criado, então só a resposta é validada.
        given()
                .contentType(ContentType.JSON)
                .body(produto)
                .when()
                .post(PRODUCTS + "/add")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo(produto.title()))
                .body("price", equalTo(produto.price()));
    }
}
