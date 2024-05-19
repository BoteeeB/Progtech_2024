package com.manager.manager.Authentication;

import com.manager.manager.Interfaces.Authentication;
import com.manager.manager.abstraction.databaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends databaseConnection implements Authentication {

    private static final Logger logger = LogManager.getLogger(Register.class);

    private TextField usernameField;
    private PasswordField passwordField;

    public Register(TextField usernameField, PasswordField passwordField){
        this.usernameField = usernameField;
        this.passwordField = passwordField;
    }

    @Override
    public String getUsername() {
        return usernameField.getText().trim();
    }

    @Override
    public void execute() {
        String username = getUsername();
        String password = passwordField.getText().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO felhasznalok (username, password, user_type, money) VALUES (?, ?, ?, ?)")) {

                statement.setString(1, username);
                statement.setString(2, password);
                statement.setInt(3, 0);
                statement.setInt(4, 20000);
                statement.executeUpdate();

                logger.info("Sikeres regisztráció: " + username);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Regisztráció");
                alert.setHeaderText(null);
                alert.setContentText("Sikeres regisztráció!");
                alert.showAndWait();
            } catch (SQLException e) {
                logger.error("Hiba történt a regisztráció során.", e);
            }
        }
    }
}
