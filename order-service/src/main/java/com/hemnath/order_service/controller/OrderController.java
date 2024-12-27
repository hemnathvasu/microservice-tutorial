package com.hemnath.order_service.controller;

import com.hemnath.order_service.dto.OrderResponseDTO;
import com.hemnath.order_service.dto.ProductDTO;
import com.hemnath.order_service.entity.Order;
import com.hemnath.order_service.repository.OrderRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping("/placeorder")
    public Mono<ResponseEntity<OrderResponseDTO>> placeOrder(@RequestBody Order order){
        return webClientBuilder.build().get().uri("http://localhost:8081/products/" + order.getProductId()).retrieve()
                .bodyToMono(ProductDTO.class).map(productDTO -> {
                    OrderResponseDTO responseDTO = new OrderResponseDTO();
                    responseDTO.setProductID(order.getProductId());
                    responseDTO.setQuantity(order.getQuantity());

                    responseDTO.setProductName(productDTO.getName());
                    responseDTO.setProductPrice(productDTO.getPrice());
                    responseDTO.setTotalPrice(order.getQuantity() * productDTO.getPrice());

                    orderRepository.save(order);
                    responseDTO.setOrderID(order.getId());

                    return ResponseEntity.ok(responseDTO);
                });
    }
    @GetMapping
    public List<Order> getAllProduct(){
        return orderRepository.findAll();
    }
}
