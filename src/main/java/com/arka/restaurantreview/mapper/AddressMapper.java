package com.arka.restaurantreview.mapper;

import com.arka.restaurantreview.domain.dto.AddressDto;
import com.arka.restaurantreview.domain.dto.PhotoDto;
import com.arka.restaurantreview.domain.entities.Address;
import com.arka.restaurantreview.domain.entities.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
}
