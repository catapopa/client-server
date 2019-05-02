package server.repository;

import domain.Reservation;

import java.util.List;

public interface IReservationRepository extends ICrudRepository<Integer, Reservation> {
    List<Reservation> findAll(Integer courseId);
}
