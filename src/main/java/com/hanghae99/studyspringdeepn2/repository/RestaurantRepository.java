package com.hanghae99.studyspringdeepn2.repository;

import com.hanghae99.studyspringdeepn2.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
