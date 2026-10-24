package com.tourmanagement.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourmanagement.business.exception.RepositoryException;
import com.tourmanagement.config.AppConfig;
import com.tourmanagement.repo.jdbc.JdbcDaoFactory;
import com.tourmanagement.repo.mem.MemDaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public abstract class DaoFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoFactory.class);
    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                InputStream is = DaoFactory.class.getResourceAsStream("/config.json");

                if (is == null) {
                    throw new RepositoryException("config.json nem talalhato");
                }

                AppConfig config = mapper.readValue(is, AppConfig.class);
                String activeProfile = config.getActiveProfile();
                AppConfig.ProfileConfig profileData = config.getProfiles().get(activeProfile);
                if ("JDBC".equalsIgnoreCase(profileData.getDaoType())) {
                    instance = new JdbcDaoFactory(profileData);
                } else {
                    instance = new MemDaoFactory();
                }



            } catch (IOException e) {
                LOGGER.error("Hiba a configuralasnal mikor dolgoza fel a DaoFactory", e);
                throw new RepositoryException("Hiba az inicializalaskor", e);
            }
        }
        return instance;
    }

    public abstract TourDao getTourDao();
}
