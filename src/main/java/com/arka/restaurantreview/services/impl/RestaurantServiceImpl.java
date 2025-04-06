package com.arka.restaurantreview.services.impl;

import com.arka.restaurantreview.domain.GeoLocation;
import com.arka.restaurantreview.domain.RestaurantCreateUpdateRequest;
import com.arka.restaurantreview.domain.entities.Address;
import com.arka.restaurantreview.domain.entities.Photo;
import com.arka.restaurantreview.domain.entities.Restaurant;
import com.arka.restaurantreview.exceptions.RestaurantNotFoundException;
import com.arka.restaurantreview.repositories.RestaurantRepository;
import com.arka.restaurantreview.services.GeoLocationService;
import com.arka.restaurantreview.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final GeoLocationService geoLocationService;

    @Override
    public Restaurant createRestaurant(RestaurantCreateUpdateRequest request) {

        Address address = request.getAddress();
        GeoLocation location = geoLocationService.geoLocation(address);
        GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());

        List<String> photoIds = request.getPhotoIds();
        List<Photo> photos = photoIds.stream().map(url -> Photo.builder().url(url).uploadDate(LocalDateTime.now()).build()).toList();

        Restaurant restaurant = Restaurant
                .builder()
                .name(request.getName())
                .cuisineType(request.getCuisineType())
                .contactNo(request.getContactInformation())
                .address(request.getAddress())
                .geoLocation(point)
                .photos(photos)
                .operatingHours(request.getOperatingHours())
                .averageRating(0f)
                .build();

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Page<Restaurant> searchRestaurants(String query, Float minRating, Float latitude, Float longitude, Float radius, Pageable pageable) {

        if(null!=minRating && (null==query || query.isEmpty())){
            return restaurantRepository.findByAverageRatingGreaterThanEqual(minRating,pageable);
        }

        Float searchMinRating = null==minRating?0f:minRating;

        if(null!=query && !query.trim().isEmpty()){
            return restaurantRepository.findByQueryAndMinRating(
                    query,
                    searchMinRating,
                    pageable
            );
        }

        if(null!=latitude && null!=longitude && null!=radius){
            return restaurantRepository.findByLocationNear(latitude,longitude,radius,pageable);
        }

        return restaurantRepository.findAll(pageable);
    }

    @Override
    public Optional<Restaurant> getRestaurant(String id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Restaurant updateRestaurant(String id, RestaurantCreateUpdateRequest request) {

        Restaurant res = getRestaurant(id).orElseThrow(()->new RestaurantNotFoundException("Restaurant does not exist"));
        GeoLocation newLocation = geoLocationService.geoLocation(request.getAddress());
        GeoPoint newPoint = new GeoPoint(newLocation.getLatitude(),newLocation.getLongitude());
        List<String> photoIds = request.getPhotoIds();
        List<Photo> photos = photoIds.stream().map(url -> Photo.builder().url(url).uploadDate(LocalDateTime.now()).build()).toList();

        res.setName(request.getName());
        res.setCuisineType(request.getCuisineType());
        res.setAddress(request.getAddress());
        res.setContactNo(request.getContactInformation());
        res.setGeoLocation(newPoint);
        res.setOperatingHours(request.getOperatingHours());
        res.setPhotos(photos);

        return restaurantRepository.save(res);
    }

    @Override
    public void deleteRestaurant(String id) {
        restaurantRepository.deleteById(id);
    }
}
