package com.tourmanagement.repo.jdbc;

import com.tourmanagement.business.exception.RepositoryException;
import com.tourmanagement.model.Tour;
import com.tourmanagement.repo.TourDao;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


public class TourJdbcDao implements TourDao {
    private final DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(TourJdbcDao.class);

    public TourJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Collection<Tour> findAll() {
        LOGGER.info("Osszes tura lekerdezese az adatbazisbol");
        Collection<Tour> tours = new ArrayList<>();
        String sql = "SELECT * FROM tours";

        try (Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tour tour = new Tour();
                tour.setId(rs.getLong("id"));
                tour.setName(rs.getString("name"));
                tour.setStartPosition(rs.getString("start_position"));
                Timestamp ts = rs.getTimestamp("start_time");
                if (ts != null) {
                    tour.setStartTime(ts.toLocalDateTime());
                }

                tour.setParticipationCost(rs.getInt("participation_cost"));
                tour.setDuration(rs.getInt("duration"));

                tours.add(tour);
            }

        } catch (SQLException e) {
            LOGGER.error("Hiba tortent a turak listazasakor", e);
            throw new RepositoryException("Hiba a listazaskor", e);
        }

        return tours;
    }

    @Override
    public Optional<Tour> findById(long id) {
        LOGGER.info("Turat kersunk ID alapjan ID: {}", id);
        String sql = "SELECT * FROM tours WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Tour tour = new Tour();
                    tour.setId(rs.getLong("id"));
                    tour.setName(rs.getString("name"));
                    tour.setStartPosition(rs.getString("start_position"));

                    Timestamp ts = rs.getTimestamp("start_time");
                    if (ts != null) {
                        tour.setStartTime(ts.toLocalDateTime());
                    }

                    tour.setParticipationCost(rs.getInt("participation_cost"));
                    tour.setDuration(rs.getInt("duration"));

                    return Optional.of(tour);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Hiba az id szerinti keresesnel ID: {}", id, e);
            throw new RepositoryException("Hiba az id szerinti keresesnel", e);
        }

        return Optional.empty();
    }

    @Override
    public void create(Tour tour) {
        LOGGER.info("Egy tura letrehozasa az adatbazisba");
        String sql = "INSERT INTO tours (name, start_position, start_time,"
                + " participation_cost, duration) VALUES (?, ?, ?, ?, ?)";
        try  (Connection conn = dataSource.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, tour.getName());
            pstmt.setString(2, tour.getStartPosition());

            if (tour.getStartTime() != null) {
                pstmt.setTimestamp(3, Timestamp.valueOf(tour.getStartTime()));
            } else {
                pstmt.setNull(3, Types.TIMESTAMP);
            }

            pstmt.setInt(4, tour.getParticipationCost());
            pstmt.setInt(5, tour.getDuration());

            pstmt.execute();

            try (ResultSet genKey = pstmt.getGeneratedKeys()) {
                if (genKey.next()) {
                    long genId = genKey.getLong(1);
                    tour.setId(genId);
                }

            }
            LOGGER.info("Turat sikerult letrehozni az ID -nel: {}", tour.getId());

        } catch (SQLException e) {
            LOGGER.error("Nem sikerult elmenteni a turat", e);
            throw new RepositoryException("Nem sikerult elmenteni a trat: " + tour.getName(), e);
        }
    }

    @Override
    public void delete(Long id) {
        LOGGER.warn("Tura torlese, ID: {}", id);
        String sql = "DELETE FROM tours WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            LOGGER.info("Tura torlese sikerult az ID -nel: {}", id);
        } catch (SQLException e) {
            LOGGER.error("Hiba a torlesnel ID: {}", id, e);
            throw new RepositoryException("Hiba a torlesnel, ID:" + id, e);
        }
    }

    @Override
    public void update(Tour tour) {
        LOGGER.info("Tura modositasa, ID: {}", tour.getId());
        String sql = "UPDATE tours SET name = ?, start_position = ?, start_time = ?,"
                + " participation_cost = ?, duration = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tour.getName());
            pstmt.setString(2, tour.getStartPosition());

            if (tour.getStartTime() != null) {
                pstmt.setTimestamp(3, Timestamp.valueOf(tour.getStartTime()));
            } else {
                pstmt.setNull(3, Types.TIMESTAMP);
            }

            pstmt.setInt(4, tour.getParticipationCost());
            pstmt.setInt(5, tour.getDuration());
            pstmt.setLong(6, tour.getId()); // Itt mondjuk meg, melyik ID-jú túrát frissítse

            pstmt.executeUpdate();
            LOGGER.info("Tura modositasa sikerult az ID -nel: {}", tour.getId());
        } catch (SQLException e) {
            LOGGER.error("Hiba a frissitesnel, ID: {}", tour.getId(), e);
            throw new RepositoryException("Hiba a frissitesnel, ID: " + tour.getId(), e);
        }
    }

    @Override
    public Optional<Tour> findByName(String name) {
        LOGGER.info("Kereses nev alapjan name: {}", name);
        String sql = "SELECT * FROM tours WHERE name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Tour tour = new Tour();
                    tour.setId(rs.getLong("id"));
                    tour.setName(rs.getString("name"));
                    tour.setStartPosition(rs.getString("start_position"));

                    Timestamp ts = rs.getTimestamp("start_time");
                    if (ts != null) {
                        tour.setStartTime(ts.toLocalDateTime());
                    }

                    tour.setParticipationCost(rs.getInt("participation_cost"));
                    tour.setDuration(rs.getInt("duration"));

                    return Optional.of(tour);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Hiba az nev szerinti keresesnel name: {}", name, e);
            throw new RepositoryException("Hiba az nev szerinti keresesnel", e);
        }

        return Optional.empty();
    }
}
