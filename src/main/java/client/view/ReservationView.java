package client.view;

import client.controller.CourseController;
import client.controller.ReservationController;
import client.controller.UserController;
import domain.Course;
import domain.Pair;
import domain.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ReservationView {

    private LoginView loginView;
    private CourseController courseController;
    private ReservationController reservationController;
    private TableView<Course> coursesTable;
    private TableView<Pair> seatsTable;
    private TextField destinationTF;
    private TextField dateTF;
    private TextField clientTF;
    private TextField nrSeatsTF;
    private TextField courseIdTF;
    private Button searchB;
    private Button createB;
    private Button logoutB;
    private int courseIdValue;
    private Stage stage;

    public ReservationView(Stage stage, CourseController courseController, ReservationController reservationController, LoginView loginView) {
        this.courseController = courseController;
        this.reservationController = reservationController;
        this.loginView = loginView;
        coursesTable = new TableView<>();
        seatsTable = new TableView<>();
        destinationTF = new TextField();
        dateTF = new TextField();
        clientTF = new TextField();
        nrSeatsTF = new TextField();
        courseIdTF = new TextField();
        searchB = new Button("search reservations");
        createB = new Button("create reservation");
        logoutB = new Button("logout");
        courseIdTF.setDisable(true);
        this.stage = stage;
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("error");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    // APP
    public void setApp() throws IOException {
        setTableCourses();
        setTableSeats();
        getCourses();
        setButtons();

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(coursesTable, destinationTF, dateTF, searchB,
                seatsTable, clientTF, nrSeatsTF, courseIdTF, createB, logoutB);

        Scene appScene = new Scene(layout, 550, 800);
        stage.setScene(appScene);
        stage.show();
    }

    // set/create tables with columns
    private void setTableCourses() {
        TableColumn<Course, Integer> idColumn = new TableColumn<>("id");
        TableColumn<Course, String> destinationColumn = new TableColumn<>("destination");
        TableColumn<Course, String> dateColumn = new TableColumn<>("date");
        TableColumn<Course, Integer> seatsColumn = new TableColumn<>("seats");
        idColumn.setMinWidth(50);
        destinationColumn.setMinWidth(200);
        dateColumn.setMinWidth(200);
        seatsColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));

        coursesTable.getColumns().addAll(Arrays.asList(idColumn, destinationColumn, dateColumn, seatsColumn));
    }

    private void setTableSeats() {
        TableColumn<Pair, String> clientColumn = new TableColumn<>("client");
        TableColumn<Pair, Integer> seatColumn = new TableColumn<>("seat");

        clientColumn.setMinWidth(100);
        seatColumn.setMinWidth(50);
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seat"));
        seatsTable.getColumns().addAll(Arrays.asList(clientColumn, seatColumn));
    }

    // get table data
    private void getCourses() throws IOException {
        List<Course> courses = courseController.getAll();
        ObservableList<Course> courseObservableList = FXCollections.observableArrayList();
        courseObservableList.addAll(courses);
        coursesTable.setItems(courseObservableList);
    }

    // pairs: client - seat nr
    private void getSeats() throws IOException {
        String destination = destinationTF.getText();
        String date = dateTF.getText();
        List<Pair> pairList = reservationController.manageSeats(destination, date);
        if (pairList != null) {
            ObservableList<Pair> seatsObservableList = FXCollections.observableArrayList();
            seatsObservableList.addAll(pairList);
            seatsTable.setItems(seatsObservableList);
        } else {
            showError();
        }
    }

    // buttons + events
    private void setButtons() {
        coursesTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                Course course = coursesTable.getSelectionModel().getSelectedItem();
                courseIdValue = course.getId();
                courseIdTF.setText(String.valueOf(courseIdValue));
                destinationTF.setText(course.getDestination());
                dateTF.setText(course.getDate());
                try {
                    refresh();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        searchB.setOnAction(e -> {
            try {
                getSeats();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        createB.setOnAction(e -> {
            try {
                createReservation();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        logoutB.setOnAction(e -> loginView.setLogin());
    }

    private void createReservation() throws IOException {
        String client = clientTF.getText();
        int nrSeats = Integer.parseInt(nrSeatsTF.getText());
        Reservation reservation = reservationController.create(client, nrSeats, courseIdValue);
        if (reservation == null) {
            showError();
        }
        refresh();
    }

    private void refresh() throws IOException {
        getCourses();
        getSeats();
    }
}
