module core {
    requires com.google.gson;
    exports roulette;
    exports user;
    exports slots;
    exports validators;
    exports savehandler;
    opens roulette;
    opens user;
    opens savehandler;
    opens validators;
    opens slots;
}
