package com.tourmanagement.repo.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import com.tourmanagement.config.AppConfig;
import com.tourmanagement.repo.DaoFactory;
import com.tourmanagement.repo.TourDao;

public class JdbcDaoFactory extends DaoFactory {

    private final TourDao tourDao;

    public JdbcDaoFactory(AppConfig.ProfileConfig config) {
        super();
        HikariDataSource dataSource = DataSourceFactory.getDataSource(config);
        this.tourDao = new TourJdbcDao(dataSource);
    }

    @Override
    public TourDao getTourDao() {
        return tourDao;
    }

}
