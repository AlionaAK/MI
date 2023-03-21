package com.example.controller;

import javafx.scene.control.Alert;

public final class ShowAlert {

    private ShowAlert() {

    }


    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }


    public static void showMessageAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }
}
