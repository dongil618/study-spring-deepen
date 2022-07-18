package com.hanghae99.studyspringdeepn2.service;

import com.hanghae99.studyspringdeepn2.dto.FoodRequestDto;
import com.hanghae99.studyspringdeepn2.model.Food;
import com.hanghae99.studyspringdeepn2.model.Restaurant;
import com.hanghae99.studyspringdeepn2.repository.FoodRepository;
import com.hanghae99.studyspringdeepn2.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    public List<Food> registerFoods(Long restaurantId, FoodRequestDto requestDto){
        List<Food> foods = new ArrayList<>();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        for(int i =0; i < requestDto.getRequest().size(); i++){
            String name = requestDto.getRequest().get(i).getName();
            Integer price = requestDto.getRequest().get(i).getPrice();
            Food food = new Food(restaurant, name, price);
            foods.add(food);
//                System.out.println("name : " + requestDto.getFoodList().get(i).getName());
//                System.out.println("price : " + requestDto.getFoodList().get(i).getPrice());
        }
        return foodRepository.saveAll(foods);
    }

    public List<Food> getFoods(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        return foodRepository.findAllByRestaurant(restaurant);
    }
}
