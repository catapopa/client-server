package domain;

import java.io.Serializable;

public class Course implements Serializable {

    private int id;
    private String destination;
    private String date;
    private int seats; //max 18

    public Course(String destination, String date) {
        this.destination = destination;
        this.date = date;
        this.seats = 18;
    }

    public Course(int id, String destination, String date, int seats) {
        this.id = id;
        this.destination = destination;
        this.date = date;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
