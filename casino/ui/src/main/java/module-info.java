module ui {
    requires core;
    requires storage;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens ui to javafx.graphics, javafx.fxml;
}
