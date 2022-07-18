package com.hanghae99.studyspringdeepn2.repository;

import com.hanghae99.studyspringdeepn2.model.Food;
import com.hanghae99.studyspringdeepn2.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByRestaurant(Restaurant restaurant);
}
