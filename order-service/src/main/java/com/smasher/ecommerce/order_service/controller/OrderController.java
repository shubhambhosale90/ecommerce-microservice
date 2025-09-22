package com.smasher.ecommerce.order_service.controller;

import com.smasher.ecommerce.order_service.clients.InventoryOpenFeignClient;
import com.smasher.ecommerce.order_service.dto.OrderRequestDto;
import com.smasher.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/helloOrders")
    public String helloOrders(@RequestHeader(name = "X-User-Id") Long userId) {
        return "Hello from Order Service, \n user id is : "+userId;
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders() {
        List<OrderRequestDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id) {
        OrderRequestDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderRequestDto orderRequestDto1 = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderRequestDto1);
    }
}
