package com.hanghae99.studyspringdeepn2.controller;

import com.hanghae99.studyspringdeepn2.dto.FoodRequestDto;
import com.hanghae99.studyspringdeepn2.dto.FoodResponseDto;
import com.hanghae99.studyspringdeepn2.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant/{restaurantId}")
public class FoodController {
    private final FoodService foodService;

    @PostMapping("/food/register")
    public void registerFoods(@PathVariable Long restaurantId, @RequestBody List<FoodRequestDto> requestDto){
        foodService.registerFoods(restaurantId, requestDto);
    }

    @GetMapping("/foods")
    public List<FoodResponseDto> getFoods(@PathVariable Long restaurantId){
        return foodService.getFoods(restaurantId);
    }

}
