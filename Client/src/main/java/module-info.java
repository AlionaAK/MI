module client {
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires Entity;

	opens com.example.controller.company to javafx.fxml;
	exports com.example.controller.company to javafx.fxml;
	opens com.example.controller.user to javafx.fxml;
	opens com.example.starter to javafx.graphics;
	opens com.example.controller to javafx.fxml;
	opens com.example.controller.segment to javafx.fxml;
}