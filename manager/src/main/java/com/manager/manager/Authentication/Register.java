package com.manager.manager.Authentication;

import com.manager.manager.Interfaces.Authentication;
import com.manager.manager.abstraction.databaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends databaseConnection implements Authentication {

    private TextField usernameField;
    private PasswordField passwordField;

    public Register(TextField usernameField, PasswordField passwordField){
        this.usernameField = usernameField;
        this.passwordField = passwordField;
    }

    @Override
    public void execute() {
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
