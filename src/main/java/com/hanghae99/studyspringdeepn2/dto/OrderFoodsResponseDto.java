package com.hanghae99.studyspringdeepn2.dto;

import com.hanghae99.studyspringdeepn2.model.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class OrderFoodsResponseDto {
    private String name;
    private Integer quantity;
    private Integer price;

    public OrderFoodsResponseDto(OrderDetail orderDetail){
        this.name = orderDetail.getFood().getName();
        this.quantity = orderDetail.getQuantity();
        this.price = orderDetail.getQuantity() * orderDetail.getFood().getPrice();
    }
}
