package server;

import domain.User;
import server.controller.CourseController;
import server.controller.ReservationController;
import server.controller.UserController;
import utils.Request;
import utils.Response;

import java.net.Socket;
import java.io.*;
import java.util.List;


public class SerialConcurrentServer extends AbstractServer {

    private UserController userController;
    private CourseController courseController;
    private ReservationController reservationController;

    SerialConcurrentServer(int port, UserController userController, CourseController courseController, ReservationController reservationController) {
        super(port);
        this.userController = userController;
        this.courseController = courseController;
        this.reservationController = reservationController;
    }

    @Override
    protected Thread createWorker(Socket client) {
        Worker worker = new Worker(client, userController, courseController, reservationController);
        return new Thread(worker);
    }

    class Worker implements Runnable {
        private Socket client;
        private UserController userController;
        private CourseController courseController;
        private ReservationController reservationController;

        Worker(Socket client, UserController userController, CourseController courseController, ReservationController reservationController) {
            this.client = client;
            this.userController = userController;
            this.courseController = courseController;
            this.reservationController = reservationController;
        }

        public void run() {
            boolean statusClient = true;
            try {
                // opening streams  - mandatory to open first the output and flush, and then the input
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                out.flush();
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());

                // process request
                while (statusClient) {
                    try {
                        Request request = (Request) in.readObject();
                        Request.Type requestType = request.getType();

                        switch (requestType) {
                            case LOGIN:
                                User user = (User) request.getData();
                                Response response_user = this.userController.login(user.getUsername(), user.getPassword());
                                out.writeObject(response_user);
                                break;
                            case GET_COURSES:
                                Response response_courses = this.courseController.getAll();
                                out.writeObject(response_courses);
                                break;
                            case SEARCH_RESERVATIONS:
                                // TODO CHANGE: STRING -> OBJECT!!!
                                List<String> data_course = (List<String>) request.getData();
                                Response response_reservations = this.reservationController.manageSeats(data_course.get(0), data_course.get(1));
                                out.writeObject(response_reservations);
                                break;
                            case MAKE_RESERVATION:
                                // TODO CHANGE: STRING -> OBJECT!!!
                                List<String> data_reservation = (List<String>) request.getData();
                                Response response_reservation = this.reservationController.create(data_reservation.get(0), data_reservation.get(1), data_reservation.get(2));
                                out.writeObject(response_reservation);
                                break;
                            case CLOSE:
                                statusClient = false;
                                System.out.println("------------------ client closed");
                                break;
                        }
                        out.flush();

                    } catch (ClassNotFoundException e) {
                        System.out.println("Error deserialize " + e);
                    }
                }

                //close streams
                in.close();
                out.close();
                //close connection
                client.close();
            } catch (IOException e) {
                System.out.println("Error in processing client request " + e);
            }
        }
    }
}
