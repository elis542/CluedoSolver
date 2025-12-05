package cluedo.solver.cluedosolver;

import ActiveGame.Game;
import Scenes.StartWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private final double width = 750;
    private final double height = 600;

    @Override
    public void start(Stage stage) throws IOException {
        StageController.setStage(stage);
        Game newGame = new Game();
        Scene scene = new Scene(new StartWindow(width, height, newGame), width, height);

        stage.setTitle("Cluedo Solver Alpha");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
