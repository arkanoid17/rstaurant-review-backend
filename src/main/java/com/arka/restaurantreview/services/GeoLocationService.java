package com.arka.restaurantreview.services;


import com.arka.restaurantreview.domain.GeoLocation;
import com.arka.restaurantreview.domain.entities.Address;

public interface GeoLocationService {
    GeoLocation geoLocation(Address address);
}
