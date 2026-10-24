package com.tourmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class  TourResponseDto {
    private Long id;
    private String name;
    private String startPosition;
    private LocalDateTime startTime;
    private int participationCost;
    private int duration;
}
