package com.tourmanagement.dto;

import lombok.Data;

@Data
public class ReviewRequestDto {
    private String commenterName;
    private int star;
    private String comment;
}
