package com.tourmanagement.repo.jpa;

import com.tourmanagement.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {

}
