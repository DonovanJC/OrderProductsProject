package com.microservices.orderservice.service;

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

    public void placeOrder(OrderRequest orderRequest){
        // map orderRequest to order object
        Order order = new Order(null,UUID.randomUUID().toString(),orderRequest.skuCode(),
                                orderRequest.price(),orderRequest.quantity());
        //save order to orderRepository
        orderRepository.save(order);
    }
}
