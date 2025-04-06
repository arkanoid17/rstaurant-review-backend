package com.arka.restaurantreview.controller;


import com.arka.restaurantreview.domain.RestaurantCreateUpdateRequest;
import com.arka.restaurantreview.domain.dto.RestaurantCreateUpdateRequestDto;
import com.arka.restaurantreview.domain.dto.RestaurantDto;
import com.arka.restaurantreview.domain.dto.RestaurantSummaryDto;
import com.arka.restaurantreview.domain.entities.Restaurant;
import com.arka.restaurantreview.mapper.RestaurantMapper;
import com.arka.restaurantreview.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@Valid @RequestBody RestaurantCreateUpdateRequest request){

        Restaurant restaurant = restaurantService.createRestaurant(request);
        return ResponseEntity.ok(restaurantMapper.restaurantDto(restaurant));

    }

    @GetMapping
    public Page<RestaurantSummaryDto> searchRestaurants(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float latitude,
            @RequestParam(required = false) Float longitude,
            @RequestParam(required = false) Float radius,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<Restaurant> searchResult = restaurantService.searchRestaurants(
                q,
                minRating,
                latitude,
                longitude,
                radius,
                PageRequest.of(page- 1, size)
        );

        return searchResult.map(restaurantMapper::toSummaryDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable String id){
        return restaurantService
                .getRestaurant(id)
                .map(restaurant-> ResponseEntity.ok(restaurantMapper.restaurantDto(restaurant)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable String id, RestaurantCreateUpdateRequestDto requestDto){
        RestaurantCreateUpdateRequest request = restaurantMapper.toRestaurantCreateUpdateRequestDto(requestDto);

        return ResponseEntity.ok(restaurantMapper.restaurantDto(restaurantService.updateRestaurant(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable String id){
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok("Success!");
    }
}
