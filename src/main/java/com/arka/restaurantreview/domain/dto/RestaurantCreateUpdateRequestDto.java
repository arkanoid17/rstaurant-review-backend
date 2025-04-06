package com.arka.restaurantreview.domain.dto;

import com.arka.restaurantreview.domain.entities.Address;
import com.arka.restaurantreview.domain.entities.OperatingHours;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantCreateUpdateRequestDto {

    @NotBlank(message = "Restaurant name is required!")
    private String name;

    @NotBlank(message = "Restaurant cuisine type is required!")
    private String cuisineType;

    @NotBlank(message = "Restaurant contact information is required!")
    private String contactInformation;

    @Valid
    private AddressDto address;

    @Valid
    private OperatingHoursDto operatingHours;

    @Size(min=1,message = "At least 1 photo id required!")
    private List<String> photoIds;
}
