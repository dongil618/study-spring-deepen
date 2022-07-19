package com.hanghae99.studyspringdeepn2.controller;

import com.hanghae99.studyspringdeepn2.dto.OrderRequestDto;
import com.hanghae99.studyspringdeepn2.dto.OrderResponseDto;
import com.hanghae99.studyspringdeepn2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/request")
    public OrderResponseDto orderRequest(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.orderRequest(orderRequestDto);
    }

    @GetMapping("/orders")
    public List<OrderResponseDto> getOrders(){
        return orderService.getOrders();
    }
}
