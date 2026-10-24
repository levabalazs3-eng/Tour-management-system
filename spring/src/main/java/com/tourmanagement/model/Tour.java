package com.tourmanagement.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tours")
public class Tour extends BaseEntity {

    private String name;
    private String startPosition;
    private LocalDateTime startTime;
    private int participationCost;
    private int duration;

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Tour(Long id, String name, String startPosition, LocalDateTime startTime, int partCost, int duration) {
        super(id);
        this.name = name;
        this.startPosition = startPosition;
        this.startTime = startTime;
        this.participationCost = partCost;
        this.duration = duration;
    }

    public Tour() {
        super();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getParticipationCost() {
        return participationCost;
    }

    public void setParticipationCost(int participationCost) {
        this.participationCost = participationCost;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
