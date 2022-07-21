package com.hanghae99.studyspringdeepn2.dto;

import com.hanghae99.studyspringdeepn2.model.Food;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class FoodResponseDto {
    private Long id;
    private String name;
    private Integer price;

    public FoodResponseDto(Food food) {
        this.id = food.getId();
        this.name = food.getName();
        this.price = food.getPrice();
    }
}
