package com.example.controller.user;

import com.example.controller.ShowAlert;
import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.command.factory.CommandFactory;
import com.example.domain.configuration.Pages;
import com.example.domain.message.Message;
import com.example.starter.ControllerManager;
import com.example.starter.ShowDialogController;
import entity.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.stream.Collectors;

import static com.example.controller.SendInfoToServer.sendInfoToServer;
import static com.example.domain.configuration.Pages.USER_PERSONAL_CABINET_PAGE;


public class UsersTableController {
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> columnLogin;
    @FXML
    private TableColumn<User, String> columnName;
    @FXML
    private TableColumn<User, String> columnSurname;
    @FXML
    private TableColumn<User, String> columnPhone;
    @FXML
    private TableColumn<User, String> columnStatus;
    //////////////
    @FXML
    private Button btnAddNew;
    @FXML
    private Button btnChange;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnFind;
    @FXML
    private TextField textFind;

    private DialogUsersController dialogUsersController;
    private ShowDialogController showDialog = new ShowDialogController();

    public void initialize() {
        dialogUsersController = (DialogUsersController) showDialog
                .getDialogController(dialogUsersController, Pages.DIALOG_USERS_TABLE_PAGE);
        prepareAndSetDataToTable();
        initListeners();
        textFind.setPromptText("введите фамилию");
    }

    private void prepareAndSetDataToTable() {
        initColumns();
        initList();
        table.refresh();
    }

    private void initListeners() {
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                viewChangePatient(table.getSelectionModel().getSelectedItem());
            }
        });

    }

    private void initList() {
        Command command = CommandFactory.getInstance().createCommand(Commands.GET_ALL_USERS);
        Message message = new Message();
        Message response = command.execute(message);
        ObservableList<User> users = (ObservableList<User>) response.getByKey("users");
        table.setItems(users);
    }


    private void initColumns() {
        columnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnStatus.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getUserStatus().toString())
        );
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }
        User selectedItem = table.getSelectionModel().getSelectedItem();
        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "btnAddNew": {
                showDialog();
                if (dialogUsersController.getUser() != null) {

                    sendInfoToServer(Commands.ADD_NEW_USER
                            , "user", dialogUsersController.getUser());
                    dialogUsersController.setNullEntity();
                    prepareAndSetDataToTable();
                }
            }
            break;
            case "btnChange": {
                if (selectedItem != null) {
                    viewChangePatient(selectedItem);
                } else {
                    ShowAlert.showErrorAlert("Для изменения выберите запись из таблицы!");
                }
            }
            break;
            case "btnDelete": {
                if (selectedItem != null) {
                    if(selectedItem.getId().equals(ControllerManager.getUser().getId())){
                        ShowAlert.showErrorAlert("Вы не можете удалить сами себя!");
                    }else {
                        sendInfoToServer(Commands.DELETE_USER, "userId", selectedItem.getId());
                        prepareAndSetDataToTable();
                    }
                } else {
                    ShowAlert.showErrorAlert("Для изменения выберите запись из таблицы!");
                }
            }
            break;
        }

    }

    private void viewChangePatient(User selected) {
        if (selected != null) {
            dialogUsersController.setUser(selected);
            showDialog.showDialog();
            //
            sendInfoToServer(Commands.UPDATE_USER, "user", dialogUsersController.getUser());
            dialogUsersController.setNullEntity();
            prepareAndSetDataToTable();
        }
    }


    private void showDialog() {
        showDialog.showDialog();
    }


    public void actionSearch(ActionEvent actionEvent) {
        String userSurname = textFind.getText().trim();
        if (userSurname.equals("")) {
            ShowAlert.showErrorAlert("Для поиска нужно что-нибудь ввести!");
        } else {
            ObservableList<User> newList =
                    FXCollections.observableList(
                            table.getItems()
                                    .stream()
                                    .filter(e -> e.getSurname().equalsIgnoreCase(userSurname))
                                    .collect(Collectors.toList()));

            table.setItems(newList);
        }
    }


    public void ShowAll(ActionEvent actionEvent) {
        prepareAndSetDataToTable();
    }


    public void goBack(ActionEvent actionEvent) {
        dialogUsersController = null;
        ControllerManager.changeScene(USER_PERSONAL_CABINET_PAGE);
    }
}
