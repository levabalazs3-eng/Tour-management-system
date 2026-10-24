package com.tourmanagement.repo.mem;

import com.tourmanagement.model.Tour;
import com.tourmanagement.repo.TourDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Profile("dev")
public class TourMemDao extends MemDao<Tour> implements TourDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourMemDao.class);

    @Override
    public Optional<Tour> findByName(String name) {
        LOGGER.info("Tura keresese nev alapjan a memoriaban: {}", name);
        for (Tour tour : storage.values()) {
            if (tour.getName().equalsIgnoreCase(name)) {
                return Optional.of(tour);
            }
        }
        return Optional.empty();
    }
}
