package com.hanghae99.studyspringdeepn2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "ORDER_DETAIL_ID")
//    private OrderDetail orderDetail;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @Column(nullable = false)
    private Long totalPrice;

    public Order(Restaurant restaurant, Long totalPrice){
        this.restaurant = restaurant;
        this.totalPrice = totalPrice;
    }

}
