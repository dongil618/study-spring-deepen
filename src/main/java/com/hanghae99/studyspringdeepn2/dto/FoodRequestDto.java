package com.hanghae99.studyspringdeepn2.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class FoodRequestDto {

    private String name;

    @Max(value = 1000000, message = "허용된 값이 아닙니다.")
    @Min(value = 100, message = "허용된 값이 아닙니다.")
    private Integer price;

    @Valid
    private List<FoodRequestDto> request;

    public FoodRequestDto(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
