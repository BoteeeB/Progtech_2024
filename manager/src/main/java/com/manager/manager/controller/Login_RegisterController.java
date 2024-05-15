package com.manager.manager.controller;

import com.manager.manager.Authentication.Login;
import com.manager.manager.Authentication.Register;
import com.manager.manager.abstraction.databaseConnection;
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

public class Login_RegisterController{

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        Login newLogin = new Login(usernameField, passwordField);
        newLogin.execute();
    }


    @FXML
    private void handleRegister() {
        Register newRegister = new Register(usernameField,passwordField);
        newRegister.execute();
    }
}
