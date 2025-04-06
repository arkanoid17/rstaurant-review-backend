package com.arka.restaurantreview.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantSummaryDto {
    private String id;
    private String name;
    private String cuisineType;
    private Integer totalReviews;
    private AddressDto address;
    private List<PhotoDto> photos;
    private Float averageRating;


}
