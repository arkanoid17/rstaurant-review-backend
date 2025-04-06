package com.arka.restaurantreview.mapper;

import com.arka.restaurantreview.domain.RestaurantCreateUpdateRequest;
import com.arka.restaurantreview.domain.dto.GeoPointDto;
import com.arka.restaurantreview.domain.dto.RestaurantCreateUpdateRequestDto;
import com.arka.restaurantreview.domain.dto.RestaurantDto;
import com.arka.restaurantreview.domain.dto.RestaurantSummaryDto;
import com.arka.restaurantreview.domain.entities.Restaurant;
import com.arka.restaurantreview.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {


    RestaurantCreateUpdateRequest toRestaurantCreateUpdateRequestDto(RestaurantCreateUpdateRequestDto request);

    @Mapping(source = "reviews",target = "totalReviews",qualifiedByName = "populateTotalReviews")
    RestaurantDto restaurantDto(Restaurant restaurant);

    @Mapping(target = "latitude",expression = "java(geoPoint.getLat())")
    @Mapping(target = "longitude",expression = "java(geoPoint.getLon())")
    GeoPointDto geoPointDto(GeoPoint geoPoint);

    @Mapping(source = "reviews",target = "totalReviews",qualifiedByName = "populateTotalReviews")
    RestaurantSummaryDto toSummaryDto(Restaurant restaurant);

    @Named("populateTotalReviews")
    default Integer populateTotalReviews(List<Review> reviews){
        return reviews.size();
    }
}
