package com.microservices.orderservice.service;

import com.microservices.orderservice.client.InventoryClient;
import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());

        if(isProductInStock){
            // map orderRequest to order object
            Order order = new Order(null,UUID.randomUUID().toString(),orderRequest.skuCode(),
                    orderRequest.price(),orderRequest.quantity());
            //save order to orderRepository
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is not in stock");
        }
    }
}
