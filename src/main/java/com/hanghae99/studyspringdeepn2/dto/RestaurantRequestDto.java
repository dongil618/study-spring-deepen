package com.hanghae99.studyspringdeepn2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
public class RestaurantRequestDto {
    private String name;

    @Max(value = 100000, message = "허용된 값이 아닙니다.")
    @Min(value = 1000, message = "허용된 값이 아닙니다.")
    private Integer minOrderPrice;
    @Max(value = 10000, message = "허용된 값이 아닙니다.")
    @Min(value = 0, message = "허용된 값이 아닙니다.")
    private Integer deliveryFee;

    public RestaurantRequestDto(String name, Integer minOrderPrice, Integer deliveryFee) {
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
    }
}
