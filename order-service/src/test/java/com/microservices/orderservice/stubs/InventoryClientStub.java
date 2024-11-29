package com.microservices.orderservice.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryClientStub {

    public static void stubInventoryCall(String skuCode, Integer quantity) {
        stubFor(get(urlEqualTo("/api/inventory/stock?skuCode=" + skuCode + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));
    }

    public static void stubInventoryFailCall(String skuCode, Integer quantity) {
        stubFor(get(urlEqualTo("/api/inventory/stock?skuCode=" + skuCode + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("false")));
    }
}
