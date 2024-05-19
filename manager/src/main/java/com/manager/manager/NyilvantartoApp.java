package com.manager.manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NyilvantartoApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Login_Register.fxml"));
        Scene loginScene = new Scene(loginLoader.load());

        Stage loginStage = new Stage();
        loginStage.setTitle("Nyilvántartó rendszer");
        loginStage.setScene(loginScene);
        loginStage.showAndWait();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
