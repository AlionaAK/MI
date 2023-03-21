package com.example.controller.user;

import com.example.controller.ShowAlert;
import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.command.factory.CommandFactory;
import com.example.domain.configuration.Pages;
import com.example.domain.message.Message;
import com.example.starter.ControllerManager;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

import static com.example.controller.ShowAlert.showErrorAlert;
import static com.example.controller.ShowAlert.showMessageAlert;


public class UpdatePasswordController {

    @FXML
    private PasswordField textCurPass;
    @FXML
    private PasswordField textNewPass;
    @FXML
    private PasswordField textNewConfPass;

    private User user = ControllerManager.getUser();


    public void initialize() {

    }


    public void update(ActionEvent actionEvent) {

        String curPass = textCurPass.getText();
        String newPass = textNewPass.getText();
        String newPassConf = textNewConfPass.getText();

        if (!curPass.equals("")
                && !newPass.equals("")
                && !newPassConf.equals("")) {

            if (newPass.equals(newPassConf)) {


                Message message = new Message();

                message.add("userId", user.getId());
                message.add("curPass", curPass);
                message.add("newPass", newPass);
                message.add("newPassConf", newPassConf);

                Command command = CommandFactory.getInstance().createCommand(Commands.UPDATE_USER_PASSWORD);

                Message response = command.execute(message);
                String exception = (String) response.getByKey("ex");
                if (exception != null) {
                    showErrorAlert(exception);
                    clearFields();
                } else {
                    showMessageAlert("Пароль сменен!");
                    clearFields();
                }
            } else {
                ShowAlert.showErrorAlert("новый пароль не совпал с подтверждённым!");
                clearFields();
            }

        } else {
            ShowAlert.showErrorAlert("Заполните все поля");
        }

    }

    private void clearFields() {
        this.textCurPass.setText("");
        this.textNewPass.setText("");
        this.textNewConfPass.setText("");
    }

    public void back(ActionEvent actionEvent) {
        ControllerManager.changeScene(Pages.USER_PERSONAL_CABINET_PAGE);
    }
}
