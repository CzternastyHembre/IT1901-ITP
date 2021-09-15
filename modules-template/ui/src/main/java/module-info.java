module calc.ui {
    requires calc.core;
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens ui to javafx.graphics, javafx.fxml;
}
