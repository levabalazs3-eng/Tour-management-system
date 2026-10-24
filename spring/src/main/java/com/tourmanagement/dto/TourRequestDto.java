package com.tourmanagement.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TourRequestDto {
    @NotBlank(message = "A nev mezo nem lehet ures")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Add meg az indulasi helyet")
    private String startPosition;

    @NotNull(message = "A kezdesi idopont megadasa kotelezo")
    @Future(message = "A datum egyu jovobeli datum kell legyen")
    private LocalDateTime startTime;

    @Positive(message = "A benevezesi dij nagyobb mint 0")
    private int participationCost;

    @Positive(message = "A tura hossza pozitiv szam kell legyen")
    private int duration;
}
