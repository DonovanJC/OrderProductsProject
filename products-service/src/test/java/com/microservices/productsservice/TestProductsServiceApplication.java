package com.microservices.productsservice;

import org.springframework.boot.SpringApplication;

public class TestProductsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProductsServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
