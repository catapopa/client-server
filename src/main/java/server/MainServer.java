package server;

import server.controller.CourseController;
import server.controller.ReservationController;
import server.controller.UserController;
import server.repository.CourseRepository;
import server.repository.ReservationRepository;
import server.repository.UserRepository;
import server.service.CourseService;
import server.service.ReservationService;
import server.service.UserService;

import java.rmi.ServerException;

public class MainServer {

    public static void  main(String[] args) {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);

        CourseRepository courseRepository = new CourseRepository();
        CourseService courseService = new CourseService(courseRepository);
        CourseController courseController = new CourseController(courseService);

        ReservationRepository reservationRepository = new ReservationRepository();
        ReservationService reservationService = new ReservationService(reservationRepository, courseRepository);
        ReservationController reservationController = new ReservationController(reservationService);

        int port = 3333;
        AbstractServer server = new SerialConcurrentServer(port, userController, courseController, reservationController);
        try {
            server.start();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }
}