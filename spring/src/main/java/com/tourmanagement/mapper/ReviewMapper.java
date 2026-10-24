package com.tourmanagement.mapper;

import com.tourmanagement.dto.ReviewRequestDto;
import com.tourmanagement.dto.ReviewResponseDto;
import com.tourmanagement.model.Review;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toReview(ReviewRequestDto dto);

    ReviewResponseDto toReviewRespDto(Review review);

    Collection<ReviewResponseDto> toReviewRespDtoColl(Collection<Review> reviews);

}
