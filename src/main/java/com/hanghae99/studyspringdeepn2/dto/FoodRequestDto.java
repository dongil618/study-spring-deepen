package com.hanghae99.studyspringdeepn2.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodRequestDto {

    private String name;
    private Integer price;

}
