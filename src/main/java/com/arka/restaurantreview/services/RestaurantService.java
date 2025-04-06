package com.arka.restaurantreview.services;

import com.arka.restaurantreview.domain.RestaurantCreateUpdateRequest;
import com.arka.restaurantreview.domain.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestaurantService {

    public Restaurant createRestaurant(RestaurantCreateUpdateRequest request);

    public Page<Restaurant> searchRestaurants(
            String query,
            Float minRating,
            Float latitude,
            Float longitude,
            Float radius,
            Pageable pageable
    );

    public Optional<Restaurant> getRestaurant(String id);

    Restaurant updateRestaurant(String id, RestaurantCreateUpdateRequest request);

    void deleteRestaurant(String id);

}
