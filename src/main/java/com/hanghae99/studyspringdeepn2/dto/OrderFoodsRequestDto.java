package com.hanghae99.studyspringdeepn2.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class OrderFoodsRequestDto {
    private Long id;
    private Integer quantity;
}
