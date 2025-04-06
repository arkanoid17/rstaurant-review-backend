package com.arka.restaurantreview.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    @NotBlank(message = "Street number is required!")
    private String streetNumber;

    @NotBlank(message = "Street name is required!")
    private String streetName;

    private String unit;

    @NotBlank(message = "Street city is required!")
    private String city;

    @NotBlank(message = "Street state is required!")
    private String state;

    @NotBlank(message = "Street zipcode is required!")
    private String zipCode;

    @NotBlank(message = "Street country is required!")
    private String country;
}
