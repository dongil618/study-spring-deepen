package com.hanghae99.studyspringdeepn2.service;

import com.hanghae99.studyspringdeepn2.dto.FoodRequestDto;
import com.hanghae99.studyspringdeepn2.model.Food;
import com.hanghae99.studyspringdeepn2.model.Restaurant;
import com.hanghae99.studyspringdeepn2.repository.FoodRepository;
import com.hanghae99.studyspringdeepn2.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    private Integer priceUnit = 100;  // 100원 단위

    public List<Food> registerFoods(Long restaurantId, @Valid List<FoodRequestDto> requestDto){
        List<Food> foods = new ArrayList<>();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        for(int i =0; i < requestDto.size(); i++){
            String name = requestDto.get(i).getName();
            Integer price = requestDto.get(i).getPrice();
            Food food = new Food(restaurant, name, price);
            if(price < 0){
                throw new IllegalArgumentException("허용된 값이 아닙니다.");
            }
            if(price > 1000000){
                throw new IllegalArgumentException("허용된 값이 아닙니다.");
            }
            // minOrderPrice는 100원 단위
            if(price % priceUnit != 0){
                throw new IllegalArgumentException(priceUnit + "단위만 입력가능합니다.");
            }
            Optional<Food> foodsInRestaurant = foodRepository.findFoodByRestaurantAndName(restaurant, name);
            if(foodsInRestaurant.isPresent()){
                throw new IllegalArgumentException("중복된 메뉴입니다.");
            }
            foods.add(food);
            System.out.println("name : " + requestDto.get(i).getName());
            System.out.println("price : " + requestDto.get(i).getPrice());
        }
        return foodRepository.saveAll(foods);
    }

    public List<Food> getFoods(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        return foodRepository.findAllByRestaurant(restaurant);
    }
}
