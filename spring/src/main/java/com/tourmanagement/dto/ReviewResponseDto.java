package com.tourmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDto {
    private Long id;
    private String commenterName;
    private int star;
    private String comment;
    private LocalDateTime createdAt;
}
