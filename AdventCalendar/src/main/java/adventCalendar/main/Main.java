package adventCalendar.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane mainPanel = FXMLLoader.load(getClass().getResource("/fxml/mainPanel.fxml"));
        Scene scene = new Scene(mainPanel);
        stage.setScene(scene);
        stage.setTitle("Generuj kalendarz adwentowy");
        stage.show();
    }
}