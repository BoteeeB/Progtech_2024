package com.manager.manager.customercommands;

import com.manager.manager.Interfaces.CustomerFactory;
import com.manager.manager.abstraction.databaseConnection;
import com.manager.manager.user.UserSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateUserMoney extends databaseConnection implements CustomerFactory {

    private int newBalance;
    public UpdateUserMoney(int newBalance){
        this.newBalance = newBalance;
    }
    @Override
    public void execute() {
        int loggedInUserId = UserSession.getLoggedInUserId();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE felhasznalok SET money = ? WHERE id = ?")) {
            statement.setInt(1, newBalance);
            statement.setInt(2, loggedInUserId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int returnExecute() {
        return 0;
    }
}
