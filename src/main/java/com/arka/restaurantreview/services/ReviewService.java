package com.arka.restaurantreview.services;

import com.arka.restaurantreview.domain.dto.ReviewCreateUpdateRequest;
import com.arka.restaurantreview.domain.entities.Review;
import com.arka.restaurantreview.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Review createReview(
            User author,
            String restaurantId,
            ReviewCreateUpdateRequest review
    );

    Page<Review> listReviews(String restaurantId, Pageable pageable);
}
