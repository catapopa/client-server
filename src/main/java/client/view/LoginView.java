package client.view;

import client.controller.CourseController;
import client.controller.ReservationController;
import client.controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginView {
    private UserController userController;
    private CourseController courseController;
    private ReservationController reservationController;
    private Stage stage;
    private ReservationView reservationView;

    public LoginView(Stage stage, UserController userController,
                     CourseController courseController, ReservationController reservationController) {
        this.stage = stage;
        this.userController = userController;
        this.courseController = courseController;
        this.reservationController = reservationController;
        this.setLogin();
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("login failed");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    // LOGIN
    public void setLogin() {
        Label usernameL = new Label("username");
        Label passwordL = new Label("password");
        TextField usernameTF = new TextField();
        TextField passwordTF = new TextField();
        Button loginButton = new Button("login");

        loginButton.setOnAction(e -> {
            try {
                if (userController.login(usernameTF.getText(), passwordTF.getText()) != null) {
                    this.reservationView = new ReservationView(stage, courseController, reservationController, this);
                    reservationView.setApp();
                } else {
                    showError();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        stage.setOnCloseRequest(e -> {
            try {
                this.userController.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20, 30, 20, 20));
        layout.getChildren().addAll(usernameL, usernameTF, passwordL, passwordTF, loginButton);

        Scene choiceScene = new Scene(layout, 300, 300);
        stage.setScene(choiceScene);
        stage.show();
    }
}
