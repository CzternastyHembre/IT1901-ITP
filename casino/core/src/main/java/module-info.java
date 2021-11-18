module core {
    requires com.google.gson;
    opens roulette;
    opens user;
    opens slots;
    opens validators;
    opens savehandler;
    exports roulette;
    exports user;
    exports slots;
    exports validators;
    exports savehandler;
    exports blackjack;
}
