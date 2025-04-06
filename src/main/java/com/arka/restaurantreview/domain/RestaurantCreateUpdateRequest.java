package com.arka.restaurantreview.domain;


import com.arka.restaurantreview.domain.entities.Address;
import com.arka.restaurantreview.domain.entities.OperatingHours;
import com.arka.restaurantreview.domain.entities.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantCreateUpdateRequest {
    private String name;
    private String cuisineType;
    private String contactInformation;
    private Address address;
    private OperatingHours operatingHours;
    private List<String> photoIds;
}
