package com.manager.manager.decorators;

import com.manager.manager.Authentication.Register;
import com.manager.manager.Interfaces.Authentication;
import com.manager.manager.abstraction.databaseConnection;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDecorator extends AuthenticationDecorator {

    public RegistrationDecorator(Authentication wrapped) {
        super(wrapped);
    }

    private boolean ellenorizJogosultsag() {
        String username = wrapped.getUsername();
        try (Connection connection = DriverManager.getConnection(databaseConnection.URL, databaseConnection.USERNAME, databaseConnection.PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM felhasznalok WHERE username = ?")) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Regisztráció Hiba");
                    alert.setHeaderText(null);
                    alert.setContentText("Ez a felhasználónév már foglalt!");
                    alert.showAndWait();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void execute() {
        if (ellenorizJogosultsag()) {
            super.execute();
        } else {
            System.out.println("A regisztráció sikertelen, a felhasználónév már foglalt!");
        }
    }

    @Override
    public String getUsername() {
        return wrapped.getUsername();
    }
}
