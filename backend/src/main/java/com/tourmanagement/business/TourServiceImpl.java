package com.tourmanagement.business;

import com.tourmanagement.business.exception.EntityNotFoundException;
import com.tourmanagement.model.Tour;
import com.tourmanagement.repo.DaoFactory;
import com.tourmanagement.repo.TourDao;

import java.time.LocalDateTime;
import java.util.Collection;

public class TourServiceImpl implements TourService {

    private final TourDao dao = DaoFactory.getInstance().getTourDao();

    @Override
    public Tour createTour(Tour tour) {
        validateTourStrings(tour);
        dao.create(tour);
        return tour;
    }

    @Override
    public Tour findTourById(long id) throws EntityNotFoundException {
        return dao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nincs ilyen ID-ju tura: " + id));
    }

    private void validateTourStrings(Tour tour) {
        if (tour.getName() == null || tour.getName().isBlank()) {
            throw new IllegalArgumentException("A név mező nem lehet üres");
        }
        if (tour.getStartPosition() == null || tour.getStartPosition().isBlank()) {
            throw new IllegalArgumentException("Add meg az indulási helyet");
        }
        validateTourNumbers(tour);
    }

    private void validateTourNumbers(Tour tour) {
        if (tour.getStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A dátum egy jövőbeli dátum kell legyen");
        }
        if (tour.getParticipationCost() <= 0) {
            throw new IllegalArgumentException("A benvezesi díj nagyobb kell legyen mint 0");
        }
        if (tour.getDuration() <= 0) {
            throw new IllegalArgumentException("A tura hossza pozitiv szám kell");
        }
    }

    @Override
    public void deleteTour(long id) throws EntityNotFoundException {
        dao.findById(id).orElseThrow(() -> new EntityNotFoundException("Nincs ilyen ID-jü tura"));
        dao.delete(id);
    }

    @Override
    public void updateTour(Tour tour) throws EntityNotFoundException {
        dao.findById(tour.getId()).orElseThrow(() -> new EntityNotFoundException("Nincs ilyen ID-jü tura"));
        dao.update(tour);
    }

    @Override
    public Collection<Tour> findAllTour() {
        return dao.findAll();
    }

}
