# Desafio Técnico QE - Sicredi

Testes automatizados de API feitos em **Java 17 + Maven + JUnit 5 + Rest Assured** com base na documentação: https://sicredi-desafio-qe.readme.io/

## Como rodar

```bash
mvn clean test
mvn surefire-report:report


## Sumário

1. [loginComSucesso](#1-logincomsucesso)
2. [deveListarProdutos](#2-develistarprodutos)
3. [deveBuscarProdutoPorId](#3-devebuscarprodutoporid)
4. [naoDeveEncontrarProdutoInexistente](#4-naodeveencontrarprodutoinexistente)
5. [deveCriarProduto](#5-devecriarproduto)
6. [Observações gerais](#observações-gerais)

---

## 1. `loginComSucesso`

**Objetivo:**  
Garantir que um usuário válido consegue se autenticar na API e receber um token de acesso (JWT).

**Fluxo:**
1. Monta o corpo da requisição com um objeto `RequestAuth("emilys", "emilyspass")`.
2. Envia `POST` para `/<pathAuth>/login`.
3. Verifica se o status é **200**.
4. Garante que a resposta contém um campo `accessToken` (não nulo).
5. (Opcional) extrai o token para ser usado em chamadas autenticadas.

**Validações:**
- `statusCode(200)`
- `body("accessToken", notNullValue())`

**Por que é importante:**  
Esse teste garante que o fluxo de **login está funcionando** e que as credenciais de teste estão válidas. Ele costuma ser **pré-requisito** para outros testes que exigem `Authorization: Bearer <token>`.

---

## 2. `deveListarProdutos`

**Objetivo:**  
Validar que o endpoint público (sem auth) de listagem de produtos está retornando uma lista válida.

**Fluxo:**
1. Envia `GET` para `pathProduct` (ex.: `/products`).
2. Verifica se o status é **200**.
3. Verifica se existe o campo `products`.
4. Verifica se há **pelo menos 1** produto na lista.

**Validações:**
- `statusCode(200)`
- `body("products", notNullValue())`
- `body("products.size()", greaterThan(0))`

**Por que é importante:**  
Esse é o **teste de sanidade** da API de produtos. Se ele falhar, provavelmente algo mais sério está acontecendo (ex.: API fora do ar, endpoint mudou, contrato quebrado).

---

## 3. `deveBuscarProdutoPorId`

**Objetivo:**  
Validar que o endpoint de **busca por ID** está funcionando e retornando o produto correto.

**Fluxo:**
1. Envia `GET` para `pathProduct + "/1"` (ex.: `/products/1`).
2. Verifica se o status é **200**.
3. Verifica se o campo `id` é exatamente **1**.
4. Verifica se o campo `title` não está vazio.

**Validações:**
- `statusCode(200)`
- `body("id", equalTo(1))`
- `body("title", notNullValue())`

**Por que é importante:**  
Consulta por ID é um dos **usos mais comuns** em APIs REST. Esse teste garante que o endpoint está retornando o **recurso correto** e que não houve quebra de rota (ex.: path alterado, mudança de versão, etc.).

---

## 4. `naoDeveEncontrarProdutoInexistente`

**Objetivo:**  
Garantir que a API responde corretamente quando o cliente tenta acessar um produto que **não existe**.

**Fluxo:**
1. Envia `GET` para `pathProduct + "/999999"` (um ID que não existe).
2. Verifica se o status é **404** (ou **400**, dependendo do comportamento da API DummyJSON).

**Validações:**
- `statusCode(anyOf(is(404), is(400)))`

**Por que é importante:**  
Esse é um **cenário negativo**. Toda API bem feita precisa retornar **códigos coerentes** quando o recurso não existe. Esse teste garante que não estamos recebendo 200 com corpo vazio, por exemplo, o que atrapalha muito o front e outros consumidores.

---

## 5. `deveCriarProduto`

**Objetivo:**  
Validar que a API permite **criar** um novo produto enviando um JSON válido.

**Fluxo:**
1. Monta um objeto `RequestProducts` com todos os campos necessários:
   - `title`: `"Perfume Oil"`
   - `description`: `"Produto criado no teste automatizado"`
   - `price`: `13`
   - `discountPercentage`: `8.4`
   - `rating`: `4.26`
   - `stock`: `65`
   - `brand`: `"Impression of Acqua Di Gio"`
   - `category`: `"fragrances"`
   - `thumbnail`: `"https://i.dummyjson.com/data/products/11/thumbnail.jpg"`
2. Converte o objeto em JSON (`new Gson().toJson(requestProducts)`) e envia `POST` para `pathProduct + "/add"`.
3. Verifica se o status é **201** (criado).
4. Verifica se o corpo da resposta contém um `id` gerado.
5. Verifica se o `title` retornado é o mesmo que foi enviado.

**Validações:**
- `statusCode(201)`
- `body("id", notNullValue())`
- `body("title", equalTo(requestProducts.title()))`

**Observação importante:**
> A documentação do DummyJSON diz:  
> **"Os produtos criados não serão persistidos."**  
> Ou seja, não é necessário fazer um `DELETE` depois. O teste só valida **a resposta**, não o banco.

**Por que é importante:**  
Esse é o teste que garante que a **parte de escrita da API** está funcionando. Em cenários reais, você pode usar esse POST para preparar massa de dados para outros testes.

---

## Observações gerais

- Todos os testes estão usando **RestAssured** com `contentType(ContentType.JSON)`.
- Os endpoints estão sendo montados a partir de variáveis como `pathAuth` e `pathProduct`, que provavelmente vêm de uma classe base (`BaseTest`).
- Quando a API é de terceiros (como o DummyJSON), alguns testes negativos precisam ser flexíveis, por isso o uso de:
  ```java
  .statusCode(anyOf(is(404), is(400)));