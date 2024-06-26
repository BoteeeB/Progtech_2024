package com.manager.manager.Authentication;

import com.manager.manager.Interfaces.Authentication;
import com.manager.manager.user.UserSession;
import com.manager.manager.abstraction.databaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Login extends databaseConnection implements Authentication {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private static final Logger logger = LogManager.getLogger(Login.class);


    public Login(TextField UsernameField, PasswordField PasswordField){
        this.usernameField = UsernameField;
        this.passwordField = PasswordField;
    }

    @Override
    public String getUsername() {
        return usernameField.getText().trim();
    }

    @Override
    public void execute() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM felhasznalok WHERE username = ? AND password = ?")) {

                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int loggedInUserId = resultSet.getInt("id");
                    UserSession.setLoggedInUserId(loggedInUserId);

                    logger.info("Sikeres bejelentkezés: " + username);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Bejelentkezés");
                    alert.setHeaderText(null);
                    alert.setContentText("Sikeres bejelentkezés!");
                    alert.showAndWait();

                    int userType = resultSet.getInt("user_type");
                    UserSession.setUserType(userType);
                    String fxmlFile = userType == 1 ? "Nyilvantarto.fxml" : "Vasarlo.fxml";

                    FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/manager/manager/" + fxmlFile));
                    Scene mainScene = new Scene(mainLoader.load());
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(mainScene);
                    stage.show();
                } else {
                    logger.error("Hibás felhasználónév vagy jelszó: " + username);

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hiba");
                    alert.setHeaderText(null);
                    alert.setContentText("Hibás felhasználónév vagy jelszó!");
                    alert.showAndWait();
                }

            } catch (SQLException | IOException e) {
                logger.error("Hiba történt a bejelentkezés során.", e);
            }

        }
    }
}
