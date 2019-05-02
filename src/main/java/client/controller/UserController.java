package client.controller;

import domain.User;
import utils.Request;
import utils.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserController {

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public UserController(ObjectOutputStream out, ObjectInputStream in){
        this.out = out;
        this.in = in;
    }

    public User login(String username, String password) throws IOException {

        User result = null;
        User person = new User(username, password);
        Request request = new Request(Request.Type.LOGIN, person);

        out.writeObject(request);
        out.flush();

        try {
            Response response = (Response) in.readObject();
            result = (User) response.getData();
        } catch (ClassNotFoundException e) {
            System.out.println("Error deserialize " + e);
        }
        return result;
    }

    public void close() throws IOException {
        Request request = new Request(Request.Type.CLOSE, null);
        out.writeObject(request);
        out.flush();
    }
}
