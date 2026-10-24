package com.tourmanagement.controller;

import com.tourmanagement.dto.ReviewRequestDto;
import com.tourmanagement.dto.ReviewResponseDto;
import com.tourmanagement.mapper.ReviewMapper;
import com.tourmanagement.model.Review;
import com.tourmanagement.model.Tour;
import com.tourmanagement.repo.TourDao;
import com.tourmanagement.repo.jpa.ReviewRepo;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@Profile("jpa")
@RequestMapping("/tours/{tourId}/reviews")
public class ReviewController  {
    private final TourDao tourDao;
    private final ReviewRepo reviewRepo;
    private final ReviewMapper reviewMapper;

    @GetMapping
    @Transactional(readOnly = true)//Biztositja a LAZY mukodeset session alatt
    public ResponseEntity<Collection<ReviewResponseDto>> getReviewsByTourId(
            @PathVariable Long tourId) {
        log.info("Reviews lekerese a a{} ID-ju turahoz", tourId);
        Optional<Tour> tourOptional = tourDao.findById(tourId);
        if (tourOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Collection<Review> reviews = tourOptional.get().getReviews();
        Collection<ReviewResponseDto> responseDtos = reviewMapper.toReviewRespDtoColl(reviews);
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ReviewResponseDto> addReviewToTour(
            @PathVariable Long tourId, @Valid @RequestBody ReviewRequestDto dto) {
        log.info("Uj review hozzaadasa a {} ID-ju turahoz", tourId);

        Optional<Tour> tourOptional = tourDao.findById(tourId);
        if (tourOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Tour tour = tourOptional.get();
        Review review = reviewMapper.toReview(dto);
        review.setCreatedAt(LocalDateTime.now());
        review.setTour(tour);

        Review savedReview = reviewRepo.save(review);
        ReviewResponseDto responseDto = reviewMapper.toReviewRespDto(savedReview);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @DeleteMapping("/{reviewId}")
    @Transactional
    public ResponseEntity<Void> deleteReviewFromTour(@PathVariable Long tourId, @PathVariable Long reviewId) {
        log.info("Review kitorlese a {} ID-ju turabol", tourId);
        Optional<Tour> tourOptional = tourDao.findById(tourId);
        if (tourOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Review> reviewOptional = reviewRepo.findById(reviewId);
        if (reviewOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reviewRepo.deleteById(reviewId);
        return ResponseEntity.noContent().build();
    }
}
