package com.tourmanagement.repo.mem;

import com.tourmanagement.model.BaseEntity;
import com.tourmanagement.repo.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class MemDao<T extends BaseEntity> implements Dao<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemDao.class);
    protected final Map<Long, T> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1L);

    @Override
    public Collection<T> findAll() {
        LOGGER.info("Osszes elem lekerese a memoriabol.");
        return storage.values();
    }

    @Override
    public Optional<T> findById(long id) {
        LOGGER.info("Elem keresese ID alapjan a memoriaban: {}", id);
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void create(T entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.getAndIncrement());
        }
        storage.put(entity.getId(), entity);
        LOGGER.info("Uj elem letrehozva a memoriaban, ID: {}", entity.getId());
    }

    @Override
    public void delete(Long id) {
        if (storage.containsKey(id)) {
            storage.remove(id);
            LOGGER.warn("Elem torolve a memoriabol, ID: {}", id);
        } else {
            LOGGER.warn("Torles sikertelen: ID {} nem talalhato a memoriaban.", id);
        }
    }

    @Override
    public void update(T entity) {
        if (entity.getId() != null) {
            storage.put(entity.getId(), entity);
            LOGGER.info("Elem frissitve a memoriaban, ID: {}", entity.getId());
        } else {
            LOGGER.error("Frissites sikertelen: az entitasnak nincs ID-ja!");
        }
    }
}
