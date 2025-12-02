package cluedo.solver.cluedosolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane mainPane = new BorderPane();

        Scene scene = new Scene(mainPane, 750, 750);
        stage.setTitle("Cluedo Solver Alpha");
        stage.setScene(scene);
        stage.show();
    }
}
