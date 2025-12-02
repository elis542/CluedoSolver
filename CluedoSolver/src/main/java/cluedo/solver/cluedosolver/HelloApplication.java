package cluedo.solver.cluedosolver;

import ActiveGame.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private final double width = 750;
    private final double height = 750;

    @Override
    public void start(Stage stage) throws IOException {
        Game newGame = new Game();

        Scene scene = new Scene(new StartWindow(width, height, newGame), width, height);
        stage.setTitle("Cluedo Solver Alpha");
        stage.setScene(scene);
        stage.show();
    }
}
