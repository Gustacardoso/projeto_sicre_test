package com.example.testes;

import com.example.config.BaseTest;
import com.example.utils.RequestProducts;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.utils.StartValues.pathProduct;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class PostProductTest extends BaseTest {

    @Test
    @DisplayName("Deve criar produto válido com sucesso")
    public void deveCriarProduto() {
        RequestProducts requestProducts = new RequestProducts(
                "Perfume Oil",
                "Produto criado no teste automatizado",
                13,
                8.4,
                4.26,
                65,
                "Impression of Acqua Di Gio",
                "fragrances",
                "https://i.dummyjson.com/data/products/11/thumbnail.jpg");

        given()
                .contentType(ContentType.JSON)
                .body(new Gson().toJson(requestProducts))
                .when()
                .post(pathProduct+"/add")
                .then().log().all()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo(requestProducts.title()));
        // doc diz: "Os produtos criados não serão persistidos." -> então não precisa apagar
    }

}
