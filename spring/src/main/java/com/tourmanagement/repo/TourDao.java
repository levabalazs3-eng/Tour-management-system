package com.tourmanagement.repo;

import com.tourmanagement.model.Tour;

import java.util.Optional;

public interface TourDao extends Dao<Tour> {

    Optional<Tour> findByName(String name);

}
