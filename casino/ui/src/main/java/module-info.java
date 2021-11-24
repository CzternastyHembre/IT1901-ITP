module ui {
    requires core;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.net.http;
    requires com.google.gson;

    opens ui to javafx.fxml, javafx.graphics;
    opens ui.menuItem;
}
