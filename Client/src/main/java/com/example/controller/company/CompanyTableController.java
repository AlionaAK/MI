package com.example.controller.company;

import com.example.controller.ShowAlert;
import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.command.factory.CommandFactory;
import com.example.domain.configuration.Pages;
import com.example.domain.message.Message;
import com.example.starter.ControllerManager;
import com.example.starter.ShowDialogController;
import entity.Company;
import entity.UserStatus;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static com.example.controller.SendInfoToServer.sendInfoToServer;
import static com.example.domain.command.Commands.*;
import static com.example.domain.configuration.Pages.USER_PERSONAL_CABINET_PAGE;

public class CompanyTableController {
	@FXML
	private TableView<Company> table;
	@FXML
	private TableColumn<Company, String> columnSegment;
	@FXML
	private TableColumn<Company, String> columnName;
	@FXML
	private TableColumn<Company, String> columnCountry;
	@FXML
	private TableColumn<Company, String> columnSuccor;
	@FXML
	private TableColumn<Company, String> columnIncomeTax;
	@FXML
	private TableColumn<Company, String> columnFinancialIncome;
	@FXML
	private TableColumn<Company, String> columnDepreciation;
	@FXML
	private TableColumn<Company, String> columnEbitda;
	@FXML
	private TableColumn<Company, String> columnStatus;
	//////////////
	@FXML
	private Button btnAddNew;
	@FXML
	private Button btnChange;
	@FXML
	private Button btnDelete;

	private static final String[] STATUS_NAMES =
			{"Выгодно для инвестиций", "Невыгодно для инвестиций", "Затруднился оценить", "Не оцененно"};

	private DialogCompaniesTableController dialogCompaniesTableController;
	private ShowDialogController showDialog = new ShowDialogController();

	public void initialize() { //javafx init controller
		dialogCompaniesTableController = (DialogCompaniesTableController) showDialog
				.getDialogController(dialogCompaniesTableController, Pages.DIALOG_COMPANY_TABLE_PAGE);
		prepareAndSetDataToTable(GET_ALL_COMPANIES);
		initListeners();
		//
		if (ControllerManager.getUser().getUserStatus().equals(UserStatus.VALUER)) {
			this.btnAddNew.setVisible(false);
			this.btnDelete.setVisible(false);
			btnChange.setText("Оценить");
		}
	}

	private void prepareAndSetDataToTable(Commands action) {
		initColumns();
		initList(action);
		table.refresh();
	}

	private void initListeners() {
		table.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				viewChangePatient(table.getSelectionModel().getSelectedItem());
			}
		});

	}

	private void initList(Commands action) {
		Command command = CommandFactory.getInstance().createCommand(action);
		Message message = new Message();
		Message response = command.execute(message);
		ObservableList<Company> companies = (ObservableList<Company>) response.getByKey("companies");
		table.setItems(companies);
	}


	private void initColumns() {
		columnSegment.setCellValueFactory(cell ->
				new SimpleStringProperty(
						cell.getValue().getSegment().getName())
		);
		columnName.setCellValueFactory(cell ->
				new SimpleStringProperty(
						cell.getValue().getName())
		);
		columnCountry.setCellValueFactory(cell ->
				new SimpleStringProperty(
						cell.getValue().getCountry())
		);

		columnSuccor.setCellValueFactory(cell ->
				new SimpleStringProperty(
						String.valueOf(cell.getValue().getSuccor()))
		);
		columnIncomeTax.setCellValueFactory(cell ->
				new SimpleStringProperty(
						String.valueOf(cell.getValue().getIncomeTax()))
		);
		columnFinancialIncome.setCellValueFactory(cell ->
				new SimpleStringProperty(
						String.valueOf(cell.getValue().getFinancialIncome()))
		);
		columnDepreciation.setCellValueFactory(cell ->
				new SimpleStringProperty(
						String.valueOf(cell.getValue().getDepreciation()))
		);
		columnEbitda.setCellValueFactory(cell ->
				new SimpleStringProperty(
						String.valueOf(cell.getValue().getEbitda()))
		);
		columnStatus.setCellValueFactory(cell ->
				new SimpleStringProperty(
						STATUS_NAMES[cell.getValue().getStatus().getId() - 1])
		);

	}

	public void actionButtonPressed(ActionEvent actionEvent) {
		Object source = actionEvent.getSource();

		if (!(source instanceof Button)) {
			return;
		}
		Company selectedItem = table.getSelectionModel().getSelectedItem();
		Button clickedButton = (Button) source;

		switch (clickedButton.getId()) {
			case "btnAddNew": {
				showDialog();
				if (dialogCompaniesTableController.getCompany() != null) {

					sendInfoToServer(Commands.ADD_NEW_COMPANY
							, "company", dialogCompaniesTableController.getCompany());
					dialogCompaniesTableController.setNullEntity();
					prepareAndSetDataToTable(GET_ALL_COMPANIES);
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
					if (selectedItem.getStatus().getId() == 4) {
						sendInfoToServer(Commands.DELETE_COMPANY, "companyId", selectedItem.getId());
						prepareAndSetDataToTable(GET_ALL_COMPANIES);
					} else {
						ShowAlert.showErrorAlert("Нельзя удалить оцененный продукт!");
					}
				} else {
					ShowAlert.showErrorAlert("Для изменения выберите запись из таблицы!");
				}
			}
			break;
		}

	}

	private void viewChangePatient(Company selected) {
		if (selected != null) {
			dialogCompaniesTableController.setCompany(selected);
			showDialog.showDialog();
			//
			sendInfoToServer(Commands.UPDATE_COMPANY, "company", dialogCompaniesTableController.getCompany());
			dialogCompaniesTableController.setNullEntity();
			prepareAndSetDataToTable(GET_ALL_COMPANIES);
		}
	}


	private void showDialog() {
		showDialog.showDialog();
	}


	///////////////////////////////////////////////////////////
	public void showAllMarked(ActionEvent actionEvent) {
		prepareAndSetDataToTable(GET_ALL_MARKED_COMPANIES);
	}

	public void showAllNonMarked(ActionEvent actionEvent) {
		prepareAndSetDataToTable(GET_ALL_NON_MARKED_COMPANIES);
	}

	public void actionClose(ActionEvent actionEvent) {
		dialogCompaniesTableController = null;
		ControllerManager.changeScene(USER_PERSONAL_CABINET_PAGE);
	}


	public void showAll(ActionEvent actionEvent) {
		prepareAndSetDataToTable(GET_ALL_COMPANIES);
	}
}
