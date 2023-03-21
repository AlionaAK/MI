package com.example.controller.user;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.command.factory.CommandFactory;
import com.example.domain.message.Message;
import com.example.domain.util.validator.UserValidator;
import com.example.starter.ControllerManager;
import entity.UserStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.example.controller.ShowAlert.showErrorAlert;
import static com.example.domain.configuration.Pages.SIGN_IN_PAGE;

public class SignUpController {

    @FXML
    private TextField textLogin;

    @FXML
    private TextField textPassword;

    @FXML
    private TextField textName;

    @FXML
    private TextField textSurname;

    @FXML
    private TextField textPhone;


    public void initialize() {
        textLogin.setPromptText("введите логин");
        textPassword.setPromptText("введите пароль");
        textName.setPromptText("введите имя");
        textSurname.setPromptText("введите фамилию");
        textPhone.setPromptText("телефон: +375291111111");
    }

    public void signUp(ActionEvent actionEvent) {
        final String login = textLogin.getText();
        final String password = textPassword.getText();
        final String name = textName.getText();
        final String surname = textSurname.getText();
        final String phone = textPhone.getText();

        if (!(
                login.equals("")
                        && password.equals("")
                        && name.equals("")
                        && surname.equals("")
                        && phone.equals(""))) {

            if (UserValidator.validatePhone(phone)) {
                Message message = new Message();


                message.add("login", login);
                message.add("password", password);
                message.add("name", name);
                message.add("surname", surname);
                message.add("phone", phone);
                message.add("userStatus", UserStatus.USER);

                Command command = CommandFactory.getInstance().createCommand(Commands.SIGN_UP);
                Message response = command.execute(message);
                String exception = (String) response.getByKey("ex");
                if (exception != null) {
                    showErrorAlert(exception);
                }
            } else {
                showErrorAlert("Введите корретный телефон!");
            }
        }else{
            showErrorAlert("Заполните все поля");
        }
    }


    public void goBack(ActionEvent actionEvent) {
        ControllerManager.changeScene(SIGN_IN_PAGE);
    }
}
