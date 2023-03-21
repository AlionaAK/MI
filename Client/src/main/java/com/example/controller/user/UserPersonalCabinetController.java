package com.example.controller.user;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.command.factory.CommandFactory;
import com.example.domain.configuration.Pages;
import com.example.domain.message.Message;
import com.example.domain.util.validator.UserValidator;
import com.example.starter.ControllerManager;
import entity.User;
import entity.UserStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static com.example.controller.ShowAlert.showErrorAlert;
import static com.example.domain.configuration.Pages.SIGN_IN_PAGE;

public class UserPersonalCabinetController {

    @FXML
    private Button btnUsersTable;
    @FXML
    private Button btnSegmentsTable;
    @FXML
    private Button btnCompaniesTable;
    @FXML
    private TextField textLogin;
    @FXML
    private TextField textName;
    @FXML
    private TextField textSurname;
    @FXML
    private TextField textPhone;

    ///

    private User user = ControllerManager.getUser();


    public void initialize() {
        //
        this.textLogin.setText(user.getLogin());
        this.textLogin.setEditable(false);
        this.textName.setText(user.getName());
        this.textSurname.setText(user.getSurname());
        this.textPhone.setText(user.getPhone());
        //
        if (!user.getUserStatus().equals(UserStatus.ADMIN)) {
            btnSegmentsTable.setVisible(false);
            btnUsersTable.setVisible(false);
        }
    }

    public void exit(ActionEvent actionEvent) {
        ControllerManager.changeScene(SIGN_IN_PAGE);
    }


    public void update(ActionEvent actionEvent) {
        final String login = textLogin.getText();
        final String name = textName.getText();
        final String surname = textSurname.getText();
        final String phone = textPhone.getText();

        if (!login.equals("")
                && !name.equals("")
                && !surname.equals("")
                && !phone.equals("")) {

            if (UserValidator.validatePhone(phone)) {
                Message request = new Message();

                user.setLogin(login);
                user.setName(name);
                user.setSurname(surname);
                user.setPhone(phone);

                //
                request.add("user", user);
                //
                Command command = CommandFactory.getInstance().createCommand(Commands.UPDATE_USER);
                Message response = command.execute(request);
                String exception = (String) response.getByKey("ex");
                if (exception != null) {
                    showErrorAlert(exception);
                }
            } else {
                showErrorAlert("Введите корретный телефон!");
            }
        } else {
            showErrorAlert("Заполните все поля!");
        }
    }


    public void updatePassword(ActionEvent actionEvent) {
        ControllerManager.changeScene(Pages.UPDATE_PASSWORD_PAGE);
    }

    public void showCompaniesTable(ActionEvent actionEvent) {
        ControllerManager.changeScene(Pages.COMPANY_TABLE_PAGE);
    }

    ///////////////////////////////////////////////////////////////////////

    public void showUsersStable(ActionEvent actionEvent) {
        ControllerManager.changeScene(Pages.USERS_TABLE_PAGE);
    }


    public void showSegmentsTable(ActionEvent actionEvent) {
        ControllerManager.changeScene(Pages.SEGMENT_TABLE_PAGE);
    }

}


