package com.hanghae99.studyspringdeepn2.repository;

import com.hanghae99.studyspringdeepn2.model.Order;
import com.hanghae99.studyspringdeepn2.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrder(Order order);
}
