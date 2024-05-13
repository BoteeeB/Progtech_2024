package com.manager.manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login_RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private static final String URL = "jdbc:mysql://localhost:3306/nyilvantartas";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM felhasznalok WHERE username = ? AND password = ?")) {

                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // A felhasználó sikeresen bejelentkezett
                    int loggedInUserId = resultSet.getInt("id");
                    UserSession.setLoggedInUserId(loggedInUserId);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Bejelentkezés");
                    alert.setHeaderText(null);
                    alert.setContentText("Sikeres bejelentkezés!");
                    alert.showAndWait();

                    // Check user_type and load the appropriate FXML file
                    int userType = resultSet.getInt("user_type");
                    String fxmlFile = userType == 1 ? "Nyilvantarto.fxml" : "Vasarlo.fxml"; // Replace "Other.fxml" with your other FXML file

                    FXMLLoader mainLoader = new FXMLLoader(getClass().getResource(fxmlFile));
                    Scene mainScene = new Scene(mainLoader.load());
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(mainScene);
                    stage.show();
                } else {
                    // A bejelentkezési adatok nem helyesek
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hiba");
                    alert.setHeaderText(null);
                    alert.setContentText("Hibás felhasználónév vagy jelszó!");
                    alert.showAndWait();
                }

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }


        }
    }


    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO felhasznalok (username, password, user_type, money) VALUES (?, ?, ?, ?)")) {

                statement.setString(1, username);
                statement.setString(2, password);
                statement.setInt(3, 0); // user_type is 0 for a simple user
                statement.setInt(4, 20000); // money is 20000 for every new user
                statement.executeUpdate();

                // A felhasználó sikeresen regisztrált
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Regisztráció");
                alert.setHeaderText(null);
                alert.setContentText("Sikeres regisztráció!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
