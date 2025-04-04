package com.arka.restaurantreview.repositories;

import com.arka.restaurantreview.domain.entities.Restaurant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends ElasticsearchRepository<Restaurant,String> {
}
