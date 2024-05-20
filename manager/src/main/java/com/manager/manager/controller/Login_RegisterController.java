package com.manager.manager.controller;

import com.manager.manager.Authentication.Login;
import com.manager.manager.Authentication.Register;
import com.manager.manager.decorators.AuthorizationDecorator;
import com.manager.manager.decorators.LoggingDecorator;
import com.manager.manager.decorators.RegistrationDecorator;
import com.manager.manager.Interfaces.Authentication;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login_RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        Authentication login = new Login(usernameField, passwordField);

        login = new LoggingDecorator(login);
        login = new AuthorizationDecorator(login, false);

        login.execute();
    }

    @FXML
    private void handleRegister() {
        Authentication register = new Register(usernameField, passwordField);

        register = new LoggingDecorator(register);
        register = new RegistrationDecorator(register);

        register.execute();
    }
}
