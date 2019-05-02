package client;

import client.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;
import client.controller.CourseController;
import client.controller.ReservationController;
import client.controller.UserController;

import java.net.Socket;
import java.io.*;


public class MainClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Socket client = new Socket("localhost", 3333);
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        //flushes all the streams of data, executes them and gives a new space to new streams in buffer
        out.flush();
        ObjectInputStream in = new ObjectInputStream(client.getInputStream());

        UserController userController = new UserController(out, in);
        CourseController courseController = new CourseController(out, in);
        ReservationController reservationController = new ReservationController(out, in);

        Stage stage1 = new Stage();
        Stage stage2 = new Stage();
        LoginView loginView = new LoginView(stage1, userController, courseController, reservationController);
        LoginView loginView2 = new LoginView(stage2, userController, courseController, reservationController);
    }
}