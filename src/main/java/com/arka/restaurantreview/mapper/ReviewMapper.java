package com.arka.restaurantreview.mapper;

import com.arka.restaurantreview.domain.dto.ReviewCreateUpdateRequest;
import com.arka.restaurantreview.domain.dto.ReviewCreateUpdateRequestDto;
import com.arka.restaurantreview.domain.dto.ReviewDto;
import com.arka.restaurantreview.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    ReviewCreateUpdateRequest toReReviewCreateUpdateRequest(ReviewCreateUpdateRequestDto requestDto);

    ReviewDto toDto(Review review);
}
