package com.example.controller.user;

import com.example.controller.ShowAlert;
import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.command.factory.CommandFactory;
import com.example.domain.connector.Connector;
import com.example.domain.message.Message;
import com.example.starter.ControllerManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.controller.ShowAlert.showErrorAlert;
import static com.example.domain.configuration.Pages.SIGN_UP_PAGE;

public class SignInController {

    @FXML
    private TextField textPort;
    @FXML
    private TextField textLogin;
    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField textPassword;

    public void initialize() throws IOException {
        int port = Connector.getPort();
        if (port != 0) {
            btnSignUp.setVisible(true);
            //
            this.textPort.setText(String.valueOf(port));
        } else {
            btnSignUp.setVisible(false);
        }
        ///
        textPort.setPromptText("введите порт");
        textPort.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textPort.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        textLogin.setPromptText("введите логин");
        textPassword.setPromptText("введите пароль");
    }

    public void SignIn(ActionEvent actionEvent) {
        final String login = textLogin.getText();
        final String password = textPassword.getText();
        final String portString = textPort.getText();
        if (!Connector.getInstance().isConnectionCreated() ||
                (!portString.equals("") && Integer.parseInt(portString) != Connector.getPort())) {
            if (portString.length() > 5 || portString.equals("")) {
                showErrorAlert("Для установки порта введите число от 1 до 65535");
                textLogin.clear();
                textPassword.clear();
            } else {
                int port = Integer.parseInt(portString);
                if (port <= 0 || port > 65535) {
                    textLogin.clear();
                    textPassword.clear();
                    showErrorAlert("Введите число от 1 до 65535");
                } else {
                    try {
                        makeConnection(port);
                        //
                        btnSignUp.setVisible(true);
                    } catch (Exception ex) {
                        btnSignUp.setVisible(false);
                        //
                        textPort.clear();
                        textLogin.clear();
                        textPassword.clear();
                        showErrorAlert("Не удалось подключиться к серверу! попробуйте другой порт");
                    }
                }
            }
        } else {
            if (login.equals("") || password.equals("")) {
                showErrorAlert("Все поля должны быть заполнены!");
                textLogin.clear();
                textPassword.clear();
            } else {

                Command command = CommandFactory.getInstance().createCommand(Commands.SIGN_IN);
                Message message = new Message();
                message.add("login", login);
                message.add("password", password);
                Message response = command.execute(message);
                String exception = (String) response.getByKey("ex");

                if (exception != null) {
                    showErrorAlert(exception);
                    this.textPassword.setText("");
                }

            }
        }
    }

    private void makeConnection(int port) throws Exception {
        Connector.setPort(port);
        Connector.getInstance().doCreateConnection();
        ShowAlert.showMessageAlert("Подключение к серверу успешно!");
    }

    public void Exit(ActionEvent actionEvent) {
        ControllerManager.exit();
    }


    public void SignUp(ActionEvent actionEvent) {
        ControllerManager.changeScene(SIGN_UP_PAGE);
    }
}
