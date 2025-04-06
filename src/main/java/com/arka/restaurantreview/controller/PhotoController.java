package com.arka.restaurantreview.controller;


import com.arka.restaurantreview.domain.dto.PhotoDto;
import com.arka.restaurantreview.domain.entities.Photo;
import com.arka.restaurantreview.mapper.PhotoMapper;
import com.arka.restaurantreview.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos")
@CrossOrigin(origins = "*")
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    @PostMapping
    public PhotoDto photoDto(@RequestParam("file") MultipartFile file){
        Photo photo = photoService.uploadPhoto(file);
        return photoMapper.toDto(photo);
    }

    @GetMapping(path = "/{id:.+}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String id){
        return photoService.getPhotoAsResource(id).map(
                photo->
                        ResponseEntity.ok()
                                .contentType(MediaTypeFactory.getMediaType(photo).orElse(MediaType.APPLICATION_OCTET_STREAM))
                                .header(HttpHeaders.CONTENT_DISPOSITION,"inline")
                                .body(photo)
        ).orElse(ResponseEntity.notFound().build());
    }

}
