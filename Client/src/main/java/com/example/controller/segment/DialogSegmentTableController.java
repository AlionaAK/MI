package com.example.controller.segment;

import com.example.controller.BaseController;
import com.example.controller.ShowAlert;
import com.example.domain.util.builder.impl.SegmentBuilderImpl;
import entity.Segment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class DialogSegmentTableController implements BaseController {

    private Segment segment;

    @FXML
    private TextField textName;

    public void initialize() {

    }

    public void setNullEntity() {
        this.segment = null;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        if (segment == null) {
            return;
        }
        this.segment = segment;
        //
        textName.setText(segment.getName());
    }

    public void actionSave(ActionEvent actionEvent) {
        String name = textName.getText();
        if (!(name.equals(""))) {
            if (this.segment != null) {
                this.segment.setName(name);
            } else {
                this.segment = new SegmentBuilderImpl()
                        .withName(name)
                        .build();
            }

            actionClose(actionEvent);
        } else {
            ShowAlert.showErrorAlert("Заполните все поля!");
        }

    }

    public void actionClose(ActionEvent actionEvent) {
        makeClearFields();

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    private void makeClearFields() {
        textName.clear();
    }
}
