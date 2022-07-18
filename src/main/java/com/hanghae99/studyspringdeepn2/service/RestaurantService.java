package com.hanghae99.studyspringdeepn2.service;

import com.hanghae99.studyspringdeepn2.dto.RestaurantRequestDto;
import com.hanghae99.studyspringdeepn2.model.Restaurant;
import com.hanghae99.studyspringdeepn2.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private Integer minOrderPriceUnit = 100;  // 100원 단위
    private Integer deliveryFeeUnit = 500; // 500원 단위


    @Transactional
    public Restaurant registerRestaurant(RestaurantRequestDto requestDto){
        Integer minOrderPrice = requestDto.getMinOrderPrice();
        Integer deliveryFee = requestDto.getDeliveryFee();

        // minOrderPrice는 100원 단위
        if(minOrderPrice % minOrderPriceUnit != 0){
            throw new IllegalArgumentException(minOrderPriceUnit + "단위만 입력가능합니다.");
        }
        // deliveryFee는 500원 단위
        if(deliveryFee % deliveryFeeUnit != 0){
            throw new IllegalArgumentException(deliveryFeeUnit + "단위만 입력가능합니다.");
        }
        Restaurant restaurant = new Restaurant(requestDto);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurant(){
        return restaurantRepository.findAll();
    }

}
