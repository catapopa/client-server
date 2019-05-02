package server.service;

import domain.Course;
import domain.Pair;
import domain.Reservation;
import server.repository.CourseRepository;
import server.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private ReservationRepository reservationRepository;
    private CourseRepository courseRepository;

    public ReservationService(ReservationRepository reservationRepository, CourseRepository courseRepository) {
        this.reservationRepository = reservationRepository;
        this.courseRepository = courseRepository;
    }

    // create pairs: client - seat nr
    public List<Pair> manageSeats(String destination, String date) {
        Course course = courseRepository.findByDestinationDate(destination, date);
        if(course == null){
            return null;
        }
        List<Reservation> reservations = reservationRepository.findAll(course.getId());
        List<Pair> result = new ArrayList<>();
        Pair pair;
        int seat = 18;

        for (Reservation reservation : reservations) {
            String clientName = reservation.getClientName();
            int nrSeats = reservation.getNrSeats();
            for (int j = 0; j < nrSeats; j++) {
                pair = new Pair(clientName, seat);
                result.add(pair);
                seat--;
            }
        }
        while (seat > 0) {
            pair = new Pair("-", seat);
            result.add(pair);
            seat--;
        }
        return result;
    }

    // create reservation
    public Reservation create(String client, int nrSeats, int idCourse) {
        Course course = courseRepository.findOne(idCourse);
        course.setSeats(course.getSeats() - nrSeats);

        if (nrSeats > 18 || nrSeats < 0) {
            return null;
        }
        Reservation reservation = new Reservation(client, nrSeats, idCourse);
        reservationRepository.create(reservation);
        courseRepository.update(course, course.getId());
        return reservation;
    }

    public List<Reservation> getAll() {
        return reservationRepository.getAll();
    }
}
