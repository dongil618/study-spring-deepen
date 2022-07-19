package com.hanghae99.studyspringdeepn2.service;

import com.hanghae99.studyspringdeepn2.dto.OrderFoodsRequestDto;
import com.hanghae99.studyspringdeepn2.dto.OrderFoodsResponseDto;
import com.hanghae99.studyspringdeepn2.dto.OrderRequestDto;
import com.hanghae99.studyspringdeepn2.dto.OrderResponseDto;
import com.hanghae99.studyspringdeepn2.model.Food;
import com.hanghae99.studyspringdeepn2.model.Order;
import com.hanghae99.studyspringdeepn2.model.OrderDetail;
import com.hanghae99.studyspringdeepn2.model.Restaurant;
import com.hanghae99.studyspringdeepn2.repository.FoodRepository;
import com.hanghae99.studyspringdeepn2.repository.OrderDetailRepository;
import com.hanghae99.studyspringdeepn2.repository.OrderRepository;
import com.hanghae99.studyspringdeepn2.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    public OrderResponseDto orderRequest(OrderRequestDto orderRequestDto) {
        Long restaurantId = orderRequestDto.getRestaurantId();
        List<OrderFoodsRequestDto> foods = orderRequestDto.getFoods();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        Long totalPrice = Long.valueOf(restaurant.getDeliveryFee());
        for (OrderFoodsRequestDto orderFoodsRequestDto : foods) {
            Long foodId = orderFoodsRequestDto.getId();
            Integer foodPrice = foodRepository.findById(foodId).get().getPrice();
            Integer foodQuantity = orderFoodsRequestDto.getQuantity();
            if(foodQuantity < 1){
                throw new IllegalArgumentException("허용된 값이 아닙니다.");
            }
            if(foodQuantity > 100){
                throw new IllegalArgumentException("허용된 값이 아닙니다.");
            }
//            System.out.println("foodPrice : " + foodPrice + " foodQuantity : " + foodQuantity);
            totalPrice += Long.valueOf(foodPrice) * Long.valueOf(foodQuantity);
        }
        // 주문 음식가격의 총합 = 최종 결제금액 - 배달비용
        if(restaurant.getMinOrderPrice() > totalPrice - restaurant.getDeliveryFee()){
            throw new IllegalArgumentException("주문 음식 가격의 총 합이 최소 주문 금액을 넘지 못했습니다.");
        }
//        System.out.println("----------------------------");
//        System.out.println("totalPrice : " + totalPrice);
        Order order = new Order(restaurant, totalPrice);
        Long orderId = orderRepository.save(order).getId();
//        System.out.println("orderId : " + orderId);
        for (int i = 0; i < foods.size(); i++) {
            Long foodId = foods.get(i).getId();
            Food food = foodRepository.findById(foodId).get();
            Order orderInOrderDetail = orderRepository.findById(orderId).get();
            Integer quantity = foods.get(i).getQuantity();
            OrderDetail orderDetail = new OrderDetail(food, orderInOrderDetail, quantity);
            orderDetailRepository.save(orderDetail);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrder(order);
//        System.out.print("orderDetailList 크기 : " + orderDetailList.size());\
        List<OrderFoodsResponseDto> foodList = new ArrayList<>();
        for (OrderDetail od: orderDetailList) {
            OrderFoodsResponseDto orderFoodsResponseDto = new OrderFoodsResponseDto(od);
//            System.out.println(orderFoodsResponseDto);
            foodList.add(orderFoodsResponseDto);
//            System.out.println(od.getFood().getName());
//            System.out.println(od.getFood().getPrice());
//            System.out.println(od.getQuantity());
        }
        //        System.out.println(orderResponseDto);
        // System.out.println("orderRequestDto = " + orderRequestDto.toString());
        return new OrderResponseDto(restaurant,foodList, order);
    }

    public List<OrderResponseDto> getOrders() {
        List<OrderResponseDto> successOrderList = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();
        for(Order order:orders){
            List<OrderFoodsResponseDto> foodList = new ArrayList<>();
            Restaurant restaurant = restaurantRepository.findById(order.getRestaurant().getId()).get();
            List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrder(order);
            for (OrderDetail od: orderDetailList) {
                OrderFoodsResponseDto orderFoodsResponseDto = new OrderFoodsResponseDto(od);
                foodList.add(orderFoodsResponseDto);
            }
            successOrderList.add(new OrderResponseDto(restaurant,foodList, order));
        }
        return successOrderList;
    }
}