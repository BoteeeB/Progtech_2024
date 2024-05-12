package com.manager.manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NyilvantartoApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Betöltjük a bejelentkezési/regisztrációs felületet
        FXMLLoader loginLoader = new FXMLLoader(NyilvantartoApp.class.getResource("Login_Register.fxml"));
        Scene loginScene = new Scene(loginLoader.load());

        // Létrehozunk egy új ablakot a bejelentkezési/regisztrációs felületnek
        Stage loginStage = new Stage();
        loginStage.setTitle("Nyilvántartó rendszer");
        loginStage.setScene(loginScene);
        loginStage.showAndWait();  // Megjelenítjük az ablakot és várunk, amíg a felhasználó be nem zárja

        // Miután a felhasználó bejelentkezett/regisztrált, betöltjük a fő alkalmazás felületét
        FXMLLoader mainLoader = new FXMLLoader(NyilvantartoApp.class.getResource("Nyilvantarto.fxml"));
        Scene mainScene = new Scene(mainLoader.load(), 1000, 500);
        primaryStage.setTitle("Nyilvántartó rendszer");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}