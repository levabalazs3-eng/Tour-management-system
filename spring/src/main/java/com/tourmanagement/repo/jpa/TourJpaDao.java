package com.tourmanagement.repo.jpa;

import com.tourmanagement.model.Tour;
import com.tourmanagement.repo.TourDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Repository
@Profile("jpa")
@RequiredArgsConstructor
public class TourJpaDao implements TourDao {
    private final TourRepo tourRepo;

    @Override
    public Collection<Tour> findAll() {
        log.info("Osszes tura lekerdezes Spring Data JPA-val");
        return tourRepo.findAll();
    }

    @Override
    public Optional<Tour> findById(long id) {
        log.info("Tura keresese ID alapjan JPA-val, ID: {}", id);
        return tourRepo.findById(id);
    }

    @Override
    public void create(Tour entity) {
        log.info("Tura mentese az adatbazisba JPA-val");
        Tour saved = tourRepo.save(entity);
        entity.setId(saved.getId());
    }

    @Override
    public void update(Tour entity) {
        log.info("Tura frissitese JPA-val, ID: {}", entity);
        tourRepo.save(entity);
    }

    @Override
    public void delete(Long id) {
        log.warn("Tura torles JPA-val, ID: {}", id);
        tourRepo.deleteById(id);
    }

    @Override
    public Optional<Tour> findByName(String name) {
        log.info("Tura keresese nev alapjan JPA-val, name: {}", name);
        return tourRepo.findByName(name);
    }


}
