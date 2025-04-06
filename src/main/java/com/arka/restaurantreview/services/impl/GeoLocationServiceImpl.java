package com.arka.restaurantreview.services.impl;

import com.arka.restaurantreview.domain.GeoLocation;
import com.arka.restaurantreview.domain.entities.Address;
import com.arka.restaurantreview.services.GeoLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class GeoLocationServiceImpl implements GeoLocationService {

    private static  final float MIN_LATITUDE=51.28f;
    private static  final float MAX_LATITUDE=51.68f;

    private static  final float MIN_LONGITUDE=-0.489f;
    private static  final float MAX_LONGITUDE=-0.236f;

    @Override
    public GeoLocation geoLocation(Address address) {

        Random random = new Random();

        double latitude = MIN_LATITUDE + random.nextDouble() * (MAX_LATITUDE-MIN_LATITUDE);
        double longitude = MIN_LONGITUDE + random.nextDouble() * (MAX_LONGITUDE-MIN_LONGITUDE);



        return GeoLocation
                .builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
