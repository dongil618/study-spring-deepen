package com.hanghae99.studyspringdeepn2.model;

import com.hanghae99.studyspringdeepn2.dto.FoodRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOOD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;


    public Food(FoodRequestDto requestDto) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
    }

    public Food(Restaurant restaurant, String name, Integer price) {
        this.restaurant = restaurant;
        this.name = name;
        this.price = price;
    }
}
