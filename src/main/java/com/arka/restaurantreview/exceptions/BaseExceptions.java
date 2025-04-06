package com.arka.restaurantreview.exceptions;

public class BaseExceptions extends RuntimeException{
    public BaseExceptions() {
    }

    public BaseExceptions(String message) {
        super(message);
    }

    public BaseExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseExceptions(Throwable cause) {
        super(cause);
    }
}
