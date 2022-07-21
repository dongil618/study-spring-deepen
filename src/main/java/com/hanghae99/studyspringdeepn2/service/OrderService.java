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
        // Restaurant ID를 통해 음식점 정보 가져오기
        Restaurant restaurant = restaurantRepository.findById(orderRequestDto.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("음식점 정보를 찾을 수 없습니다."));

        List<OrderFoodsRequestDto> foods = orderRequestDto.getFoods();

        Long totalPrice = getTotalPrice(foods,restaurant);

        minOrderPriceValidation(restaurant, totalPrice);

        Order order = new Order(restaurant, totalPrice);

        // 저장 후에 orderId 값 가져오기(OrderDetail 테이블 저장을 위함)
        Long orderId = orderRepository.save(order).getId();
        saveOrderDetail(foods, orderId);

        // response로 넘겨줄 foods 배열 만들기
        List<OrderFoodsResponseDto> foodList = getFoodList(order);

        return new OrderResponseDto(restaurant,foodList, order);
    }

    public List<OrderResponseDto> getOrders() {
        List<OrderResponseDto> successOrderList = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();
        for(Order order:orders){
            List<OrderFoodsResponseDto> foodList = new ArrayList<>();
            Restaurant restaurant = restaurantRepository.findById(order.getRestaurant().getId())
                    .orElseThrow(() -> new IllegalArgumentException("음식점 정보가 없습니다."));
            List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrder(order);
            for (OrderDetail od: orderDetailList) {
                OrderFoodsResponseDto orderFoodsResponseDto = new OrderFoodsResponseDto(od);
                foodList.add(orderFoodsResponseDto);
            }
            successOrderList.add(new OrderResponseDto(restaurant,foodList, order));
        }
        return successOrderList;
    }

    public void foodQuantityValidation(Integer foodQuantity){
        if(foodQuantity < 1){
            throw new IllegalArgumentException("허용된 값이 아닙니다.");
        }
        if(foodQuantity > 100){
            throw new IllegalArgumentException("허용된 값이 아닙니다.");
        }
    }

    public void minOrderPriceValidation(Restaurant restaurant, Long totalPrice){
        // 주문 음식가격의 총합 = 최종 결제금액 - 배달비용
        if(restaurant.getMinOrderPrice() > totalPrice - restaurant.getDeliveryFee()){
            throw new IllegalArgumentException("주문 음식 가격의 총 합이 최소 주문 금액을 넘지 못했습니다.");
        }
    }

    public Long getTotalPrice(List<OrderFoodsRequestDto> foods, Restaurant restaurant){
        Long totalPrice = 0L;

        for (OrderFoodsRequestDto orderFoodsRequestDto : foods) {
            Long foodId = orderFoodsRequestDto.getId();
            Integer foodPrice = foodRepository.findById(foodId)
                    .orElseThrow(() -> new IllegalArgumentException("음식정보를 찾을 수 없습니다."))
                    .getPrice();
            Integer foodQuantity = orderFoodsRequestDto.getQuantity();
            foodQuantityValidation(foodQuantity);
            totalPrice += Long.valueOf(foodPrice) * Long.valueOf(foodQuantity);
        }

        // 마지막에 배달비까지 더하기
        totalPrice += Long.valueOf(restaurant.getDeliveryFee());
        return  totalPrice;
    }

    public void saveOrderDetail(List<OrderFoodsRequestDto> foods, Long orderId){
        // 주문 저장하기
        for (OrderFoodsRequestDto orderFoodsRequestDto : foods) {
            Long foodId = orderFoodsRequestDto.getId();
            Food food = foodRepository.findById(foodId)
                    .orElseThrow(() -> new IllegalArgumentException("음식점보를 찾을 수 없습니다."));
            Order orderInOrderDetail = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("주문정보를 찾을 수 없습니다."));
            Integer quantity = orderFoodsRequestDto.getQuantity();
            OrderDetail orderDetail = new OrderDetail(food, orderInOrderDetail, quantity);
            orderDetailRepository.save(orderDetail);
        }
    }
    public List<OrderFoodsResponseDto> getFoodList(Order order){
        List<OrderFoodsResponseDto> foodList = new ArrayList<>();
        // 주문 객체를 통해 해당 주문의 주문 상세 내역 리스트 불러오기
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrder(order);
        for (OrderDetail od: orderDetailList) {
            OrderFoodsResponseDto orderFoodsResponseDto = new OrderFoodsResponseDto(od);
            foodList.add(orderFoodsResponseDto);
        }
        return foodList;
    }
}