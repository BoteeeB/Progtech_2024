package com.manager.manager.customercommands;

import com.manager.manager.Interfaces.CustomerFactory;
import com.manager.manager.abstraction.databaseConnection;
import com.manager.manager.user.UserSession;

import java.sql.*;

public class GetUserMoney extends databaseConnection implements CustomerFactory {

    public GetUserMoney(){}
    @Override
    public void execute() {

    }

    @Override
    public int returnExecute() {
        int loggedInUserId = UserSession.getLoggedInUserId();
        int money = 0;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT money FROM felhasznalok WHERE id = ?")) {
            statement.setInt(1, loggedInUserId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                money = resultSet.getInt("money");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return money;
    }
}
