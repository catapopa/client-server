package server.repository;

import utils.JdbcUtil;
import domain.Reservation;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository implements IReservationRepository {

    private JdbcUtil jdbcUtil;
    private static final Logger LOGGER = LogManager.getLogger(User.class);

    public ReservationRepository() {
        jdbcUtil = new JdbcUtil();
    }

    @Override
    public List<Reservation> getAll() {
        String selectString = "select * from Reservations";
        List<Reservation> reservations = new ArrayList<>();

        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(selectString)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String clientName = resultSet.getString("clientName");
                    int nrSeats = resultSet.getInt("nrSeats");
                    int courseId = resultSet.getInt("courseId");
                    Reservation reservation = new Reservation(id, clientName, nrSeats, courseId);
                    reservations.add(reservation);
                }
                return reservations;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Reservation create(Reservation reservation) {
        String insertString = "insert into Reservations (clientName,nrSeats,courseId) values(?,?,?);";

        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(insertString)) {
            stm.setString(1, reservation.getClientName());
            stm.setInt(2, reservation.getNrSeats());
            stm.setInt(3, reservation.getCourseId());
            stm.executeUpdate();
            LOGGER.debug("reservation save - successful");
            return reservation;
        } catch (SQLException e) {
            LOGGER.debug("reservation save - fail");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Reservation delete(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reservation update(Reservation entity, Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reservation findOne(Integer integer) {
        throw new UnsupportedOperationException();
    }

    public List<Reservation> findAll(Integer courseId) {
        String selectString = "select * from Reservations where courseId=?;";
        List<Reservation> resultList = new ArrayList<>();

        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(selectString)) {
            stm.setInt(1, courseId);
            try (ResultSet result = stm.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String clientName = result.getString("clientName");
                    int nrSeats = result.getInt("nrSeats");
                    Reservation found = new Reservation(id, clientName, nrSeats, courseId);
                    resultList.add(found);
                    LOGGER.debug("reservation found");
                }
                return resultList;
            }
        } catch (SQLException e) {
            LOGGER.debug("reservation not found");
            e.printStackTrace();
        }
        return null;
    }
}
