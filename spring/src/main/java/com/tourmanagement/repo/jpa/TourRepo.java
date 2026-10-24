package com.tourmanagement.repo.jpa;

import com.tourmanagement.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TourRepo extends JpaRepository<Tour, Long> {
    Optional<Tour> findByName(String name);
}
