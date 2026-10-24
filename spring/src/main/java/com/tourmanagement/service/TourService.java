package com.tourmanagement.service;

import com.tourmanagement.model.Tour;

import java.util.Collection;

public interface TourService {

    Tour createTour(Tour tour);

    Tour findTourById(long id);

    Tour findTourByName(String name);

    void deleteTour(long id);

    Collection<Tour> findAllTour();

    void updateTour(Tour tour);

}
