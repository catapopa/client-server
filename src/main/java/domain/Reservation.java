package domain;


import java.io.Serializable;

public class Reservation implements Serializable {
    private int id;
    private String clientName;
    private int nrSeats;
    private int courseId;

    public Reservation(String clientName, int nrSeats, int courseId) {
        this.clientName = clientName;
        this.nrSeats = nrSeats;
        this.courseId = courseId;
    }

    public Reservation(int id, String clientName, int nrSeats, int courseId) {
        this.id = id;
        this.clientName = clientName;
        this.nrSeats = nrSeats;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getNrSeats() {
        return nrSeats;
    }

    public void setNrSeats(int nrSeats) {
        this.nrSeats = nrSeats;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
