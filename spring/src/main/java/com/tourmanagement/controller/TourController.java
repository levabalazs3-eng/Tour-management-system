package com.tourmanagement.controller;

import com.tourmanagement.dto.TourRequestDto;
import com.tourmanagement.dto.TourResponseDto;
import com.tourmanagement.mapper.TourMapper;
import com.tourmanagement.model.Tour;
import com.tourmanagement.service.TourService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/tours")
@RequiredArgsConstructor // privte final adattagoknak automatikusan letrehoz egy konstruuktort
public class TourController {
    private final TourService tourService;
    private final TourMapper tourMapper;

    @GetMapping
    public Collection<TourResponseDto> getAllTours(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            Collection<TourResponseDto> jaa = new ArrayList<>();
            TourResponseDto tourRespDto = tourMapper.toDto(tourService.findTourByName(name));
            jaa.add(tourRespDto);
            return jaa;
        }
        return tourMapper.toDtoCollection(tourService.findAllTour());
    }

    @GetMapping("/{id}")
    public TourResponseDto getTourById(@PathVariable long id) {

        return tourMapper.toDto(tourService.findTourById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TourResponseDto create(@Valid @RequestBody TourRequestDto dto) {
        Tour tourEntity = tourMapper.toEntity(dto);
        Tour savedTour = tourService.createTour(tourEntity);
        return tourMapper.toDto(savedTour);
    }

    @PutMapping("/{id}")
    public TourResponseDto updateTour(@PathVariable long id, @Valid @RequestBody TourRequestDto dto) {
        Tour tour = tourMapper.toEntity(dto);
        tour.setId(id);
        tourService.updateTour(tour);
        return tourMapper.toDto(tour);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTour(@PathVariable long id) {
        tourService.deleteTour(id);
    }
}
