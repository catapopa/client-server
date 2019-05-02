package server.repository;

import domain.Reservation;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

public class ReservationRepositoryTest {

    private ReservationRepository reservationRepository = new ReservationRepository();

    @Test
    public void getAll() {
        List<Reservation> reservations = reservationRepository.getAll();
        Assert.assertNotEquals(reservations.size(), null);
    }

    @Test
    public void save() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);

        String clientName = new String(array, Charset.forName("UTF-8"));
        int nrSeats = 3;
        int courseId = 2;
        Reservation reservation = new Reservation(clientName, nrSeats, courseId);
        Assert.assertEquals(reservationRepository.create(reservation), reservation);
    }
}