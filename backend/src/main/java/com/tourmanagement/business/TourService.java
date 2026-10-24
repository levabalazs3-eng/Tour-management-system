package com.tourmanagement.business;

import com.tourmanagement.business.exception.EntityNotFoundException;
import com.tourmanagement.model.Tour;

import java.util.Collection;

public interface TourService {

    Tour createTour(Tour tour);

    Tour findTourById(long id) throws EntityNotFoundException;

    void deleteTour(long id) throws EntityNotFoundException;

    Collection<Tour> findAllTour();

    void updateTour(Tour tour) throws EntityNotFoundException;

}
