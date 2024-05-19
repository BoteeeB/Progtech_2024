package com.manager.manager.customercommands;

import com.manager.manager.Interfaces.CustomerFactory;
import javafx.scene.control.Alert;

public class ShowAlert implements CustomerFactory {
    private String message;

    public ShowAlert(String message){
        this.message = message;
    }
    @Override
    public void execute() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public int returnExecute() {
        return 0;
    }
}
