package client.controller;

import domain.Course;
import utils.Request;
import utils.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CourseController {

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public CourseController(ObjectOutputStream out, ObjectInputStream in) {
        this.out = out;
        this.in = in;
    }

    public List<Course> getAll() throws IOException {

        List<Course> result = new ArrayList<>();
        Request request = new Request(Request.Type.GET_COURSES, null);

        out.writeObject(request);
        out.flush();

        try {
            Response response = (Response) in.readObject();
            result = (List<Course>) response.getData();
        } catch (ClassNotFoundException e) {
            System.out.println("Error deserialize " + e);
        }
        return result;
    }
}
