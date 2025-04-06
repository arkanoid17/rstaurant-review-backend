package com.arka.restaurantreview.controller;


import com.arka.restaurantreview.domain.dto.ErrorDto;
import com.arka.restaurantreview.exceptions.BaseExceptions;
import com.arka.restaurantreview.exceptions.RestaurantNotFoundException;
import com.arka.restaurantreview.exceptions.ReviewNotAllowedException;
import com.arka.restaurantreview.exceptions.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorDto> handleRestaurantNotFound(RestaurantNotFoundException ex){
        log.error("Caught error ",ex);
        ErrorDto error = ErrorDto.builder().status(HttpStatus.NOT_FOUND.value()).message("Restaurant was not found").build();
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgNotValid(MethodArgumentNotValidException ex){
        log.error("Caught exception ",ex);
        String message = ex.getBindingResult().getFieldErrors().stream().map(err-> err.getField()+" : "+err.getDefaultMessage()).collect(Collectors.joining(", "));
        ErrorDto error = ErrorDto.builder().status(HttpStatus.BAD_REQUEST.value()).message(message).build();

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ErrorDto> handleStorageException(StorageException exception){
        log.error("Storage exception "+exception);

        ErrorDto errorDto = ErrorDto
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Unable to store or retrieve message at this point!")
                .build();

        return new ResponseEntity<>(errorDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BaseExceptions.class)
    public ResponseEntity<ErrorDto> handleBaseException(BaseExceptions exception){
        log.error("Storage exception "+exception);

        ErrorDto errorDto = ErrorDto
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred!")
                .build();

        return new ResponseEntity<>(errorDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ReviewNotAllowedException.class)
    public ResponseEntity<ErrorDto> reviewNotAllowedException(ReviewNotAllowedException ex){
        log.error("caught error ",ex);
        ErrorDto error = ErrorDto
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Review cannot be created or updated!")
                .build();
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
