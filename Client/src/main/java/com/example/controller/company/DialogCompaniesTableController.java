package com.example.controller.company;

import com.example.controller.BaseController;
import com.example.controller.ShowAlert;
import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.command.factory.CommandFactory;
import com.example.domain.message.Message;
import com.example.domain.util.builder.impl.CompanyBuilderImpl;
import com.example.starter.ControllerManager;
import entity.Company;
import entity.ProductMarkStatus;
import entity.Segment;
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

public class DialogCompaniesTableController implements BaseController {


    @FXML
    private ChoiceBox<Segment> choiceSegment = new ChoiceBox<>();


    private static final String[] STATUS_NAMES =
            {"Выгодно для инвестиций",
                    "Невыгодно для инвестиций",
                    "Затруднился оценить",
                    "Не оцененно"};
    private static final ObservableList<String> MARK_STATUSES =
            FXCollections.observableList(
                    Arrays.asList("Выгодно для инвестиций",
                            "Невыгодно для инвестиций",
                            "Затруднился оценить",
                            "Не оцененно"));
    @FXML
    private ChoiceBox<String> choiceMark = new ChoiceBox<>(MARK_STATUSES);

    @FXML
    private TextField textName;
    @FXML
    private TextField textCountry;
    @FXML
    private TextField textSuccor;
    @FXML
    private TextField textIncomeTax;
    @FXML
    private TextField textFinancialIncome;
    @FXML
    private TextField textDepreciation;
    @FXML
    private TextField textEbitda;


    private Company company;


    public void initialize() {
        textEbitda.setText("0");
        textEbitda.setEditable(false);
        //
        choiceSegment.setItems(getSegments());
        //
        //
        choiceMark.setItems(MARK_STATUSES);
        //
        setPromt();
        initTextListenersForNumber();
        //
        if (ControllerManager.getUser().getUserStatus().equals(UserStatus.USER)) {
            this.choiceMark.setDisable(true);
        }
        //
        choiceMark.setValue("не оценен");
        //
    }

    private void setPromt() {
        textName.setPromptText("введите название");
        textCountry.setPromptText("ввведите страну");
        textSuccor.setPromptText("введите выручку");
        textIncomeTax.setPromptText("введите расходы по налогу на прибыль(цифры)");
        textFinancialIncome.setPromptText("введите финансовую прибыль(цифры)");
        textDepreciation.setPromptText("введите сумму амортизаций(цифры)");
        textEbitda.setPromptText("");
    }

    private void initTextListenersForNumber() {

        //////
        textSuccor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(FOR_MATCH)) {
                textSuccor.setText(newValue.replaceAll(FOR_REPLACE, CLEAR_TEXT));
            }
        });
        textIncomeTax.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(FOR_MATCH)) {
                textIncomeTax.setText(newValue.replaceAll(FOR_REPLACE, CLEAR_TEXT));
            }
        });
        textFinancialIncome.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(FOR_MATCH)) {
                textFinancialIncome.setText(
                        newValue.replaceAll(FOR_REPLACE, CLEAR_TEXT));
            }
        });
        textDepreciation.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(FOR_MATCH)) {
                textDepreciation.setText(
                        newValue.replaceAll(FOR_REPLACE, CLEAR_TEXT));
            }
        });
    }

    void setNullEntity() {
        this.company = null;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
        if (company == null) {
            return;
        }

        //
        this.textName.setText(company.getName());
        this.textCountry.setText(company.getCountry());
        this.textSuccor.setText(String.valueOf(company.getSuccor()));
        this.textIncomeTax.setText(String.valueOf(company.getIncomeTax()));
        this.textFinancialIncome.setText(String.valueOf(company.getFinancialIncome()));
        this.textDepreciation.setText(String.valueOf(company.getDepreciation()));
        this.textEbitda.setText(String.valueOf(company.getEbitda()));
        //
        this.choiceSegment.setValue(company.getSegment());
        this.choiceMark.setValue(STATUS_NAMES[company.getStatus().getId() - 1]);

        if (ControllerManager.getUser().getUserStatus().equals(UserStatus.USER)) {
            this.choiceMark.setDisable(true);
            choiceMark.setValue("не оценен");
        } else if (ControllerManager.getUser().getUserStatus().equals(UserStatus.VALUER)) {
            //
            setCompanyEditableFalse();
        }

        if (company.getStatus().getId() != 4) {
            setNotEditableProduct();
        }
    }

    private void setCompanyEditableFalse() {
        this.textName.setEditable(false);
        this.textCountry.setEditable(false);
        this.textSuccor.setEditable(false);
        this.textIncomeTax.setEditable(false);
        this.textFinancialIncome.setEditable(false);
        this.textDepreciation.setEditable(false);
        //
        this.choiceSegment.setDisable(true);
    }

    private void setNotEditableProduct() {
        setCompanyEditableFalse();
        this.choiceMark.setDisable(true);
    }

    public void save(ActionEvent actionEvent) {

        String name = textName.getText();
        String country = textCountry.getText();
        try {
            double succor = Double.parseDouble(textSuccor.getText());
            double incomeTax = Double.parseDouble(textIncomeTax.getText());
            double financialIncome = Double.parseDouble(textFinancialIncome.getText());
            double depreciation = Double.parseDouble(textDepreciation.getText());
            double ebitda = succor + incomeTax + financialIncome + depreciation;

            if (succor == 0 || incomeTax == 0 || financialIncome == 0 || depreciation == 0) {
                ShowAlert.showErrorAlert("Денежные поля не могут равняться нулю!");
            } else {
                if (!name.equals("")
                        && !country.equals("")
                        && !choiceSegment.getSelectionModel().isEmpty()) {


                    Segment segment = choiceSegment.getSelectionModel().getSelectedItem();
                    //
                    if (ControllerManager.getUser().getUserStatus().equals(UserStatus.VALUER)
                            && choiceMark.getSelectionModel().isEmpty()) {
                        ShowAlert.showErrorAlert("Оцените продукт!");
                    } else {
                        //
                        ProductMarkStatus status;
                        if (ControllerManager.getUser().getUserStatus().equals(UserStatus.USER) ||
                                choiceMark.getSelectionModel().getSelectedItem().isEmpty()) {
                            status = ProductMarkStatus.values()[3];
                        } else {
                            status = ProductMarkStatus.values()[choiceMark.getSelectionModel().getSelectedIndex()];
                        }
                        //
                        if (this.company != null) {
                            company.setName(name);
                            company.setCountry(country);
                            company.setSegment(segment);
                            company.setSuccor(succor);
                            company.setFinancialIncome(financialIncome);
                            company.setDepreciation(depreciation);
                            company.setEbitda(ebitda);
                            company.setIncomeTax(incomeTax);
                            company.setStatus(status);
                        } else {
                            company = new CompanyBuilderImpl()
                                    .withSegment(segment)
                                    .withName(name)
                                    .withCountry(country)
                                    .withSuccor(succor)
                                    .withFinancialIncome(financialIncome)
                                    .withDepreciation(depreciation)
                                    .withEbitda(ebitda)
                                    .withIncomeTax(incomeTax)
                                    .withProductStatus(status)
                                    .build();
                        }
                        ///
                        actionClose(actionEvent);
                    }
                } else {
                    ShowAlert.showErrorAlert("Заполните все поля");
                }
            }
        } catch (NumberFormatException ex) {
            ShowAlert.showErrorAlert("Заполните все поля!Точка должна быть одна");
        }
    }

    public void actionClose(ActionEvent actionEvent) {
        makeClearFields();

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    private void makeClearFields() {
        this.textName.clear();
        this.textCountry.clear();
        this.textSuccor.clear();
        this.textIncomeTax.clear();
        this.textFinancialIncome.clear();
        this.textDepreciation.clear();
        this.textEbitda.clear();
        //
        choiceSegment.getSelectionModel().clearSelection();
        //
        this.textName.setEditable(true);
        this.textCountry.setEditable(true);
        this.textSuccor.setEditable(true);
        this.textIncomeTax.setEditable(true);
        this.textFinancialIncome.setEditable(true);
        this.textDepreciation.setEditable(true);
        //
        this.choiceSegment.setDisable(false);
        //
        this.choiceMark.setDisable(false);
        if (ControllerManager.getUser().getUserStatus().equals(UserStatus.USER)) {
            this.choiceMark.setDisable(true);
        }
        this.choiceMark.setValue(STATUS_NAMES[3]);
        //
    }


    //
    private ObservableList<Segment> getSegments() {
        Message response = getMessage(Commands.GET_ALL_SEGMENT);
        return (ObservableList<Segment>) response.getByKey("segments");
    }


    private Message getMessage(Commands action) {
        Command command = CommandFactory.getInstance().createCommand(action);
        Message message = new Message();
        return command.execute(message);
    }


    private static final String CLEAR_TEXT = "";
    private static final String FOR_MATCH = "(?=.)([+-]?(?=[\\d\\.])(\\d*)(\\.(\\d+))?)";
    private static final String FOR_REPLACE = "[^(?=.)([+-]?(?=[\\d\\.])(\\d*)(\\.(\\d+))?)]";

}
