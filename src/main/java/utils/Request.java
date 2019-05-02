package utils;

import java.io.Serializable;

public class Request implements Serializable {

    public enum Type {
        LOGIN,
        GET_COURSES,
        SEARCH_RESERVATIONS,
        MAKE_RESERVATION,
        CLOSE
    }

    private Type type;
    private Object data;

    public Request(Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
