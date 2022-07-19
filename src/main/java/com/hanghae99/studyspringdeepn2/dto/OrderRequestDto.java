package com.hanghae99.studyspringdeepn2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class OrderRequestDto {
    private Long restaurantId;
    private List<OrderFoodsRequestDto> foods;
}
