package server.controller;

import domain.Pair;
import domain.Reservation;
import server.service.ReservationService;
import utils.Response;

import java.util.List;

public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public Response manageSeats(String destination, String date) {
        List<Pair> reservations = reservationService.manageSeats(destination, date);
        return new Response(Response.Type.SEARCH_RESERVATIONS, reservations);
    }

    public Response create(String client, String nrSeats, String idCourse) {
        Reservation reservation = reservationService.create(client, Integer.parseInt(nrSeats), Integer.parseInt(idCourse));
        return new Response(Response.Type.MAKE_RESERVATION, reservation);
    }

    public List<Reservation> getAll() {
        return reservationService.getAll();
    }
}
