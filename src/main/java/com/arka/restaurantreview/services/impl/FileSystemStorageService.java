package com.arka.restaurantreview.services.impl;

import com.arka.restaurantreview.exceptions.StorageException;
import com.arka.restaurantreview.services.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    @Value("${app.storage.location:uploads}")
    private String storageLocation;

    private Path rootLocation;

    @PostConstruct
    public void init(){
        rootLocation = Paths.get(storageLocation);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location!",e);
        }
    }

    @Override
    public String store(MultipartFile file, String fileName) {
        if(file.isEmpty()){
            throw new StorageException("Cannot save an empty file!");
        }

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        String finalFileName = fileName+"."+extension;
        Path destinationPath = rootLocation
                .resolve(Paths.get(finalFileName))
                .normalize()
                .toAbsolutePath();

        if(!destinationPath.getParent().equals(rootLocation.toAbsolutePath())){
            throw new StorageException("Can not store file outside specific directory!");
        }

        try{
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream,destinationPath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new StorageException("Failed to save the file!",e);
        }

        return finalFileName;
    }

    @Override
    public Optional<Resource> loadAsResource(String fileName) {
        try{
            Path file = rootLocation.resolve(fileName);
            UrlResource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return Optional.of(resource);
            }
        } catch (MalformedURLException e) {
            log.warn("Could not read file: %s".formatted(fileName),e);
        }

        return Optional.empty();
    }
}
