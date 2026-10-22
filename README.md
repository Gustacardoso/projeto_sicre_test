# Projeto Sicredi - Testes Automatizados de API

Testes automatizados de API do **Desafio Técnico QE - Sicredi**, escritos em **Java 17 + Maven + JUnit 5 + Rest Assured**.
Documentação do desafio: https://sicredi-desafio-qe.readme.io/

## O que o projeto faz

O projeto executa testes de API contra o serviço público [DummyJSON](https://dummyjson.com) (`https://dummyjson.com`) e valida:

- **Saúde da API**: o endpoint `/test` responde `status: ok`.
- **Autenticação**: login com usuário válido e retorno do `accessToken` (JWT).
- **Usuários**: a listagem traz usuários com `username` e `password`, que são os dados usados no login.
- **Produtos (público)**: listagem, busca por ID e produto inexistente (cenário negativo).
- **Produtos (protegido)**: acesso a `/auth/products` com token válido e rejeição com token inválido.
- **Criação de produto**: `POST /products/add` com um JSON válido.

## Pré-requisitos

- [JDK 17](https://adoptium.net/) ou superior
- [Maven 3.9+](https://maven.apache.org/)
- Acesso à internet, porque os testes chamam a API real do DummyJSON

Confira as versões instaladas:

```bash
java -version
mvn -version
```

## Como usar

Clone o repositório e entre na pasta do projeto:

```bash
git clone https://github.com/Gustacardoso/projeto_sicre_test.git
cd projeto_sicre_test
```

Executar todos os testes:

```bash
mvn clean test
```

Executar uma classe de teste específica:

```bash
mvn test -Dtest=LoginTest
```

Executar um único método:

```bash
mvn test -Dtest=ProductsTest#deveBuscarProdutoPorId
```

Gerar o relatório HTML do Surefire, depois de rodar os testes:

```bash
mvn surefire-report:report
```

O relatório fica em `target/site/surefire-report.html`. Os resultados brutos ficam em `target/surefire-reports/`.

Também é possível rodar pela IDE (IntelliJ IDEA): abra o projeto como Maven e execute as classes em `src/test/java/com/example/testes`.

## Testes implementados

| Classe | Teste | O que valida |
|---|---|---|
| `HealthTest` | `deveRetornarStatusOk` | `GET /test` retorna 200, `status = ok` e `method = GET` |
| `LoginTest` | `deveAutenticarComCredenciaisValidas` | `POST /auth/login` com `emilys` / `emilyspass` retorna 200, `accessToken` preenchido e o `username` |
| `LoginTest` | `naoDeveAutenticarComCredenciaisInvalidas` | Senha incorreta retorna 400 e `Invalid credentials` |
| `UsersTest` | `deveListarUsuariosComCamposParaLogin` | `GET /users` retorna 200 e a lista de usuários, com `username` e `password` no primeiro item |
| `ProductsTest` | `deveListarProdutos` | `GET /products` retorna 200 e ao menos 1 produto |
| `ProductsTest` | `deveBuscarProdutoPorId` | `GET /products/1` retorna 200, `id = 1` e `title` preenchido |
| `ProductsTest` | `naoDeveEncontrarProdutoInexistente` | `GET /products/999999` retorna 404 |
| `ProductsTest` | `deveCriarProduto` | `POST /products/add` retorna 201, com `id` gerado, `title` e `price` iguais aos enviados |
| `ProtectedProductsTest` | `deveListarProdutosProtegidosComTokenValido` | Com token válido, `GET /auth/products` retorna 200 e a lista de produtos com `title` e `price` |
| `ProtectedProductsTest` | `naoDeveAcessarProdutosProtegidosComTokenInvalido` | Com token inválido, `GET /auth/products` retorna 401 e `Invalid/Expired Token!` |

> O DummyJSON não persiste os dados criados. Por isso o teste de criação valida apenas a resposta e não precisa apagar o produto depois.

## Estrutura do projeto

```
src/test/java/com/example/
├── config/
│   └── BaseTest.java              # baseURI, endpoints, credenciais de teste e obterToken() (login)
├── testes/                        # Classes de teste (*Test.java)
└── utils/
    ├── RequestAuth.java           # Corpo da requisição de login
    └── RequestProducts.java       # Corpo da requisição de criação de produto
```

Todas as classes de teste estendem `BaseTest`. Ela define a URL base (`https://dummyjson.com`) e os endpoints, e ativa o log da requisição e da resposta quando uma validação falha.

Para apontar para outro ambiente, passe a URL na execução, sem alterar o código:

```bash
mvn clean test -Dbase.uri=https://outro-ambiente.exemplo.com
```

## Integração contínua

O workflow do GitHub Actions em `.github/workflows/tests.yml` roda os testes a cada push na `main` e em pull requests. Ele publica os relatórios do Surefire como artefato por 7 dias.

## Tecnologias

- Java 17
- Maven
- JUnit 5
- Rest Assured
- Gson
- Maven Surefire (execução e relatório)
