package com.tourmanagement.mapper;

import com.tourmanagement.dto.TourRequestDto;
import com.tourmanagement.dto.TourResponseDto;
import com.tourmanagement.model.Tour;
import org.mapstruct.Mapper;
import java.util.Collection;

@Mapper(componentModel = "spring")
public interface TourMapper {

    Tour toEntity(TourRequestDto dto);

    TourResponseDto toDto(Tour tour);

    Collection<TourResponseDto> toDtoCollection(Collection<Tour> entities);
}
