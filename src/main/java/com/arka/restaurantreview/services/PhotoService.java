package com.arka.restaurantreview.services;

import com.arka.restaurantreview.domain.entities.Photo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PhotoService {

    public Photo uploadPhoto(MultipartFile file);
    public Optional<Resource> getPhotoAsResource(String id);
}
