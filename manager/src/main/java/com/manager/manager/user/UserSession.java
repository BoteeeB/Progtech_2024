package com.manager.manager.user;

import com.manager.manager.abstraction.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSession extends databaseConnection {
    private static int loggedInUserId;
    private static int userType;

    public static void setLoggedInUserId(int userId) {
        loggedInUserId = userId;
        loadUserType(); // Betöltjük a felhasználó típusát
    }

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }

    public static int getUserType() {
        return userType;
    }

    public static void setUserType(int type) {
        userType = type;
    }

    private static void loadUserType() {
        // Az adatbázisból betöltjük a felhasználó típusát
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT user_type FROM felhasznalok WHERE id = ?")) {

            statement.setInt(1, loggedInUserId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userType = resultSet.getInt("user_type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
