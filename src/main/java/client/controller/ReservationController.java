package client.controller;

import com.sun.corba.se.spi.ior.ObjectKey;
import domain.Pair;
import domain.Reservation;
import utils.Request;
import utils.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReservationController {

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ReservationController(ObjectOutputStream out, ObjectInputStream in) {
        this.out = out;
        this.in = in;
    }

    public List<Pair> manageSeats(String destination, String date) throws IOException {

        List<Pair> result = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        data.add(destination);
        data.add(date);
        Request request = new Request(Request.Type.SEARCH_RESERVATIONS, data);

        out.writeObject(request);
        out.flush();

        try {
            Response response = (Response) in.readObject();
            result = (List<Pair>) response.getData();
        } catch (ClassNotFoundException e) {
            System.out.println("Error deserialize " + e);
        }
        return result;
    }

    public Reservation create(String client, int nrSeats, int courseIdValue) throws IOException {
        Reservation result = null;
        List<String> data = new ArrayList<>();
        data.add(client);
        data.add(String.valueOf(nrSeats));
        data.add(String.valueOf(courseIdValue));

        Request request = new Request(Request.Type.MAKE_RESERVATION, data);

        out.writeObject(request);
        out.flush();

        try {
            Response response = (Response) in.readObject();
            result = (Reservation) response.getData();
        } catch (ClassNotFoundException e) {
            System.out.println("Error deserialize " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
