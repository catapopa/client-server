package domain;

import java.io.Serializable;

public class Pair implements Serializable {
    private String client;
    private int seat;

    public Pair(String client, int seat) {
        this.client = client;
        this.seat = seat;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }
}
