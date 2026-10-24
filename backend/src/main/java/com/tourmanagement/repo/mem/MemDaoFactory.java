package com.tourmanagement.repo.mem;

import com.tourmanagement.repo.DaoFactory;
import com.tourmanagement.repo.TourDao;

public class MemDaoFactory extends DaoFactory {
    private final TourDao tourDao = new TourMemDao();

    @Override
    public TourDao getTourDao() {
        return tourDao;
    }

}
