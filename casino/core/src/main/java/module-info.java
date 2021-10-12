module core {
    requires javafx.fxml;
    opens roulette;
    opens user;
    opens slots;
    opens validators;

    exports roulette;
    exports user;
    exports slots;
    exports validators;
}
