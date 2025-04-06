package com.arka.restaurantreview.controller;

import com.arka.restaurantreview.domain.dto.ReviewCreateUpdateRequest;
import com.arka.restaurantreview.domain.dto.ReviewCreateUpdateRequestDto;
import com.arka.restaurantreview.domain.dto.ReviewDto;
import com.arka.restaurantreview.domain.entities.User;
import com.arka.restaurantreview.mapper.ReviewMapper;
import com.arka.restaurantreview.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants/{restaurantId}/reviews")
public class ReviewController {

    private final ReviewMapper reviewMapper;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@PathVariable String restaurantId, @Valid @RequestBody ReviewCreateUpdateRequestDto requestDto, @AuthenticationPrincipal Jwt jwt){

        ReviewCreateUpdateRequest request = reviewMapper.toReReviewCreateUpdateRequest(requestDto);
        User user = jwtToUser(jwt);
        ;

        return ResponseEntity.ok(reviewMapper.toDto(reviewService.createReview(user,restaurantId,request)));

    }

    private User jwtToUser(Jwt jwt){
        return User
                .builder()
                .id(jwt.getSubject())
                .username(jwt.getClaimAsString("preferred_username"))
                .firstName(jwt.getClaimAsString("first_name"))
                .lastName(jwt.getClaimAsString("last_name"))
                .build();
    }

    @GetMapping
    public Page<ReviewDto>  listReviews(
            @PathVariable String restaurantId,
            @PageableDefault(
                    size = 20,
                    page = 0,
                    sort = "datePosted",
                    direction = Sort.Direction.ASC) Pageable pageable
            )
    {

        return reviewService
                .listReviews(restaurantId,pageable)
                .map(reviewMapper::toDto);

    }
}
