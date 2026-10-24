package com.tourmanagement.service;

import com.tourmanagement.exception.EntityNotFoundException;
import com.tourmanagement.model.Tour;
import com.tourmanagement.repo.TourDao;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class TourServiceImp implements TourService {
    private final TourDao dao;

    public TourServiceImp(TourDao  dao) {
        this.dao = dao;
    }

    @Override
    public Tour createTour(Tour tour) {
        dao.create(tour);
        return tour;
    }

    @Override
    public Tour findTourById(long id) {
        return dao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nincs ilyen ID-ju tura: " + id));
    }

    @Override
    public Tour findTourByName(String name) {
        return dao.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Nincs ilyen Nevu-ju tura: " + name));
    }

    @Override
    public void deleteTour(long id) {
        dao.findById(id).orElseThrow(() -> new EntityNotFoundException("Nincs ilyen ID-jü tura"));
        dao.delete(id);
    }

    @Override
    public void updateTour(Tour tour) {
        dao.findById(tour.getId()).orElseThrow(() -> new EntityNotFoundException("Nincs ilyen ID-jü tura"));
        dao.update(tour);
    }

    @Override
    public Collection<Tour> findAllTour() {
        return dao.findAll();
    }

}
