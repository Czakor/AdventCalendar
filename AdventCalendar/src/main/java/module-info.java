module AdventCalendar {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;

    exports adventCalendar.main to javafx.graphics;
    opens adventCalendar.controller to javafx.fxml;
}