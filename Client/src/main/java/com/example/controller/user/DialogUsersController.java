package com.example.controller.user;

import com.example.controller.BaseController;
import com.example.controller.ShowAlert;
import com.example.domain.util.builder.impl.UserBuilderImpl;
import com.example.domain.util.validator.UserValidator;
import entity.User;
import entity.UserStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.example.controller.ShowAlert.showErrorAlert;

public class DialogUsersController implements BaseController {

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
    @FXML
    private ChoiceBox<UserStatus> choiceStatus;
    private static final ObservableList<UserStatus> STATUSES = FXCollections.observableList(
            Arrays.stream(UserStatus.values()).collect(Collectors.toList()));

    private User user;


    public void initialize() {
        textPhone.setPromptText("+375291111111");
        choiceStatus.setVisible(true);

        choiceStatus.setItems(STATUSES);
    }

    public void setNullEntity() {
        this.user = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null) {
            return;
        }
        textLogin.setEditable(false);
        this.user = user;
        //
        textLogin.setText(user.getLogin());
        textPassword.setText(user.getPassword());
        textName.setText(user.getName());
        textSurname.setText(user.getSurname());
        textPhone.setText(user.getPhone());
        //
        this.choiceStatus.setValue(
                STATUSES.stream().filter(e -> e.getId() == user.getUserStatus().getId())
                        .findFirst()
                        .get()
        );
    }

    public void actionSave(ActionEvent actionEvent) {
        String login = textLogin.getText();
        String password = textPassword.getText();
        String name = textName.getText();
        String surname = textSurname.getText();
        String phone = textPhone.getText();


        if (!(
                password.equals("")
                        && name.equals("")
                        && surname.equals("")
                        && phone.equals(""))) {


            //
            if (UserValidator.validatePhone(phone)) {

                if (!choiceStatus.getSelectionModel().isEmpty()) {

                    if (this.user != null) {
                        this.user.setLogin(login);
                        this.user.setPassword(password);
                        this.user.setName(name);
                        this.user.setSurname(surname);
                        this.user.setPhone(phone);
                        this.user.setUserStatus(choiceStatus.getValue());
                    } else {
                        this.user = new UserBuilderImpl()
                                .withLogin(login)
                                .withPassword(password)
                                .withName(name)
                                .withSurname(surname)
                                .withPhone(phone)
                                .withUserStatus(choiceStatus.getValue())
                                .build();
                    }
                    //
                    actionClose(actionEvent);
                } else {
                    showErrorAlert("Выберите роль!");
                }
            } else {
                showErrorAlert("Введите корретный телефон!");
            }
        } else {
            ShowAlert.showErrorAlert("Заполните все поля");
        }

    }


    public void actionClose(ActionEvent actionEvent) {
        makeClearFields();

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    private void makeClearFields() {
        textLogin.setEditable(true);
        textLogin.clear();
        textPassword.clear();
        textName.clear();
        textSurname.clear();
        textPhone.clear();
    }
}
