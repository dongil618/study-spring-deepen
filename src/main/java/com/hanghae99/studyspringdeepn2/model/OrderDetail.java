package com.hanghae99.studyspringdeepn2.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_DETAIL_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FOOD_ID")
    private Food food;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

//    @ManyToOne
//    @JoinColumn(name = "RESTAURANT_ID")
//    private Restaurant restaurant;

    @Column(nullable = false)
    private Integer quantity;

    public OrderDetail(Food food, Order order, Integer quantity) {
        this.food = food;
        this.order = order;
        this.quantity = quantity;
    }
}
