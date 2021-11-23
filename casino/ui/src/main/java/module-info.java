module ui {
    requires core;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.net.http;
    requires com.google.gson;

    opens ui;
    opens ui.MenuItem to javafx.fxml, javafx.graphics;
}
