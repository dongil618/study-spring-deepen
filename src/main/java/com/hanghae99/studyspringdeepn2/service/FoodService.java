package com.hanghae99.studyspringdeepn2.service;

import com.hanghae99.studyspringdeepn2.dto.FoodRequestDto;
import com.hanghae99.studyspringdeepn2.dto.FoodResponseDto;
import com.hanghae99.studyspringdeepn2.model.Food;
import com.hanghae99.studyspringdeepn2.model.Restaurant;
import com.hanghae99.studyspringdeepn2.repository.FoodRepository;
import com.hanghae99.studyspringdeepn2.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public void registerFoods(Long restaurantId, @Valid List<FoodRequestDto> requestDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("음식점 정보가 없습니다."));
        saveFoods(requestDto, restaurant);
    }


    public List<FoodResponseDto> getFoods(Long restaurantId) {
        // 음식점 정보 가져오기
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new IllegalArgumentException("음식점 정보가 없습니다."));

        // foodResponseDtoList 생성 후 리턴
        return getFoodResponseDtoList(restaurant);
    }

    private void saveFoods(@Valid List<FoodRequestDto> requestDto, Restaurant restaurant) {
        for (FoodRequestDto foodRequestDto : requestDto) {
            String name = foodRequestDto.getName();
            Integer price = foodRequestDto.getPrice();
            priceValidation(price);
            foodValidation(restaurant, name);
            Food food = new Food(restaurant, name, price);
            foodRepository.save(food);
        }
    }

    public void priceValidation(Integer price){
        if (price < 100) {
            throw new IllegalArgumentException("허용된 값이 아닙니다.");
        }
        if (price > 1000000) {
            throw new IllegalArgumentException("허용된 값이 아닙니다.");
        }
        // minOrderPrice는 100원 단위
        if (price % priceUnit != 0) {
            throw new IllegalArgumentException(priceUnit + "단위만 입력가능합니다.");
        }
    }

    public void foodValidation(Restaurant restaurant, String name){
        Optional<Food> foodsInRestaurant = foodRepository.findFoodByRestaurantAndName(restaurant, name);
        if (foodsInRestaurant.isPresent()) {
            throw new IllegalArgumentException("중복된 메뉴입니다.");
        }
    }

    public List<FoodResponseDto> getFoodResponseDtoList(Restaurant restaurant){
        List<FoodResponseDto> foodResponseDtoList = new ArrayList<>();
        List<Food> foodList = foodRepository.findAllByRestaurant(restaurant);
        for(Food food : foodList){
            FoodResponseDto foodResponseDto = new FoodResponseDto(food);
            foodResponseDtoList.add(foodResponseDto);
        }
        return foodResponseDtoList;
    }
}
