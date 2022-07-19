package com.hanghae99.studyspringdeepn2.repository;

import com.hanghae99.studyspringdeepn2.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
