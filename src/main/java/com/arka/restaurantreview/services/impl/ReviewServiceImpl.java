package com.arka.restaurantreview.services.impl;

import com.arka.restaurantreview.domain.dto.ReviewCreateUpdateRequest;
import com.arka.restaurantreview.domain.entities.Photo;
import com.arka.restaurantreview.domain.entities.Restaurant;
import com.arka.restaurantreview.domain.entities.Review;
import com.arka.restaurantreview.domain.entities.User;
import com.arka.restaurantreview.exceptions.BaseExceptions;
import com.arka.restaurantreview.exceptions.RestaurantNotFoundException;
import com.arka.restaurantreview.exceptions.ReviewNotAllowedException;
import com.arka.restaurantreview.repositories.RestaurantRepository;
import com.arka.restaurantreview.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found!"));
        boolean existingReview = restaurant.getReviews().stream().anyMatch(r-> Objects.equals(r.getWrittenBy().getId(), author.getId()));

        if(existingReview){
            throw new ReviewNotAllowedException("User already reviewed restaurant");
        }

        String reviewId = UUID.randomUUID().toString();

        List<Photo> photos = review.getPhotoIds().stream()
                .map(url-> Photo
                        .builder()
                        .uploadDate(LocalDateTime.now())
                        .build()).toList();

        Review rev = Review
                .builder()
                .id(reviewId)
                .content(review.getContent())
                .datePosted(LocalDateTime.now())
                .photos(photos)
                .rating(review.getRating())
                .lastEdited(LocalDateTime.now())
                .writtenBy(author)
                .build();

        List<Review> revs = restaurant.getReviews();
        revs.add(rev);
        restaurant.setReviews(revs);
        restaurant.setAverageRating(getRestaurantAverageRating(revs));

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);



        return savedRestaurant.getReviews().stream().filter(r->r.getId().equals(reviewId)).findFirst().orElseThrow(()->new RuntimeException("Error retrieving review that created"));
    }

    @Override
    public Page<Review> listReviews(String restaurantId, Pageable pageable) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);

        List<Review> reviews = restaurant.getReviews();

        Sort sort = pageable.getSort();

        if(sort.isSorted()){
            Sort.Order order = sort.iterator().next();
            String property = order.getProperty();
            boolean isAscending = order.getDirection().isAscending();
            Comparator comparator =switch (property){
                case "datePosted" -> Comparator.comparing(Review::getDatePosted);
                case "rating" -> Comparator.comparing(Review::getRating);
                default -> Comparator.comparing(Review::getDatePosted);
            };

            reviews.sort(isAscending?comparator:comparator.reversed());

        }
        else{
            reviews.sort(Comparator.comparing(Review::getDatePosted).reversed());
        }

        int start = (int) pageable.getOffset();
        if(start>=reviews.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, reviews.size());
        }
        int end = Math.min(start+pageable.getPageSize(), reviews.size());

        return new PageImpl<>(reviews.subList(start,end),pageable,reviews.size());
    }

    private Float getRestaurantAverageRating(List<Review> reviews){
        if(reviews.isEmpty()){
            return 0f;
        }else{
            double averageRating = reviews.stream().mapToDouble(
                    Review::getRating
            ).average().orElse(0.0);
            return (float)averageRating;
        }
    }

    private Restaurant getRestaurantOrThrow(String id){
        return restaurantRepository
                .findById(id)
                .orElseThrow(()->new RestaurantNotFoundException("No restaurants found with matching id!"));
    }
}
