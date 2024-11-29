package com.microservices.orderservice;

import com.microservices.orderservice.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.3.0");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mySQLContainer.start();
    }

    @Test
    void shouldSubmitOrder() {
        String requestBody = """
                {
                    "skuCode": "iphone_16",
                        "price": 12.3,
                        "quantity": 25
                }
                """;
        String responseBodyString;

        InventoryClientStub.stubInventoryCall("iphone_16", 25);

        responseBodyString = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/order")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body().asString();

        MatcherAssert.assertThat(responseBodyString, Matchers.is("Order Placed Successfully"));
    }

    @Test
    void shouldFailSubmitOrder(){
        String requestBody = """
                {
                    "skuCode": "iphone_19",
                        "price": 12.3,
                        "quantity": 25
                }
                """;
        InventoryClientStub.stubInventoryFailCall("iphone_19",12);

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/order")
                .then()
                .log().all()
                .statusCode(500);
    }

}
