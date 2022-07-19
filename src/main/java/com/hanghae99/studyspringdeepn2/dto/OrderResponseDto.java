package com.hanghae99.studyspringdeepn2.dto;

import com.hanghae99.studyspringdeepn2.model.Order;
import com.hanghae99.studyspringdeepn2.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class OrderResponseDto {
    private String restaurantName;
    private List<OrderFoodsResponseDto> foods;
    private Integer deliveryFee;
    private Long totalPrice;

    public OrderResponseDto(Restaurant restaurant, List<OrderFoodsResponseDto> foods, Order order){
        this.restaurantName = restaurant.getName();
        this.foods = foods;
        this.deliveryFee = restaurant.getDeliveryFee();
        this.totalPrice = order.getTotalPrice();
    }
}
