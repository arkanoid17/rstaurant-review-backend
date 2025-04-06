package com.arka.restaurantreview.manual;


import com.arka.restaurantreview.domain.RestaurantCreateUpdateRequest;
import com.arka.restaurantreview.domain.entities.Address;
import com.arka.restaurantreview.domain.entities.OperatingHours;
import com.arka.restaurantreview.domain.entities.TimeRange;
import com.arka.restaurantreview.mapper.RestaurantMapper;
import com.arka.restaurantreview.services.PhotoService;
import com.arka.restaurantreview.services.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import com.arka.restaurantreview.domain.dto.*;
import com.arka.restaurantreview.domain.entities.Restaurant;
import com.arka.restaurantreview.services.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RestaurantDataLoaderTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Test
    void shouldCreateFiveDistinctRestaurants() {
        // Restaurant 1
        RestaurantCreateUpdateRequest restaurant1 = RestaurantCreateUpdateRequest.builder()
                .name("Pasta Paradise")
                .cuisineType("Italian")
                .contactInformation("contact@pastaparadise.com")
                .address(buildAddress("12", "Olive Street", "A1", "Rome", "Lazio", "00100", "Italy"))
                .operatingHours(buildOperatingHours("10:00", "22:00"))
                .photoIds(List.of("restaurant1.jpg"))
                .build();

        // Restaurant 2
        RestaurantCreateUpdateRequest restaurant2 = RestaurantCreateUpdateRequest.builder()
                .name("Sushi Central")
                .cuisineType("Japanese")
                .contactInformation("contact@sushicentral.jp")
                .address(buildAddress("88", "Sakura Avenue", "2F", "Tokyo", "Tokyo", "100-0001", "Japan"))
                .operatingHours(buildOperatingHours("11:00", "23:00"))
                .photoIds(List.of("restaurant2.jpg"))
                .build();

        // Restaurant 3
        RestaurantCreateUpdateRequest restaurant3 = RestaurantCreateUpdateRequest.builder()
                .name("Taco Town")
                .cuisineType("Mexican")
                .contactInformation("info@tacotown.mx")
                .address(buildAddress("45", "Guadalupe St", "", "Mexico City", "CDMX", "01000", "Mexico"))
                .operatingHours(buildOperatingHours("09:00", "21:00"))
                .photoIds(List.of("restaurant3.jpg"))
                .build();

        // Restaurant 4
        RestaurantCreateUpdateRequest restaurant4 = RestaurantCreateUpdateRequest.builder()
                .name("Curry Corner")
                .cuisineType("Indian")
                .contactInformation("hello@currycorner.in")
                .address(buildAddress("101", "Spice Road", "", "Mumbai", "Maharashtra", "400001", "India"))
                .operatingHours(buildOperatingHours("08:00", "22:00"))
                .photoIds(List.of("restaurant4.jpg"))
                .build();

        // Restaurant 5
        RestaurantCreateUpdateRequest restaurant5 = RestaurantCreateUpdateRequest.builder()
                .name("Burger Barn")
                .cuisineType("American")
                .contactInformation("contact@burgerbarn.us")
                .address(buildAddress("500", "Freedom Blvd", "Suite 10", "New York", "NY", "10001", "USA"))
                .operatingHours(buildOperatingHours("10:00", "20:00"))
                .photoIds(List.of("restaurant5.jpg"))
                .build();

        // Save and assert
        List<RestaurantCreateUpdateRequest> restaurants = List.of(restaurant1, restaurant2, restaurant3, restaurant4, restaurant5);

        for (RestaurantCreateUpdateRequest request : restaurants) {


            Restaurant created = restaurantService.createRestaurant(request);

            assertThat(created).isNotNull();
            assertThat(created.getId()).isNotNull();
            assertThat(created.getName()).isEqualTo(request.getName());
            assertThat(created.getCuisineType()).isEqualTo(request.getCuisineType());
        }
    }

    private Address buildAddress(String streetNumber, String streetName, String unit, String city, String state, String zipCode, String country) {
        return Address.builder()
                .streetNumber(streetNumber)
                .streetName(streetName)
                .unit(unit)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .country(country)
                .build();
    }

    private OperatingHours buildOperatingHours(String openTime, String closeTime) {
        TimeRange timeRange = TimeRange.builder()
                .openTime(openTime)
                .closeTime(closeTime)
                .build();

        return OperatingHours.builder()
                .monday(timeRange)
                .tuesday(timeRange)
                .wednesday(timeRange)
                .thursday(timeRange)
                .friday(timeRange)
                .saturday(timeRange)
                .sunday(timeRange)
                .build();
    }
}
