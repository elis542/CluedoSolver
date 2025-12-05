package cluedo.solver.cluedosolver;

import javafx.stage.Stage;

public class StageController {
    private static Stage stage = null;

    private StageController() {}

    public static void setStage(Stage newStage) {
        stage = newStage;
    }

    public static Stage getStage() {
        if (stage != null) {
            return stage;
        }
        System.err.println("Getting stage, but stage is NULL");
        System.exit(0);
        return stage;
    }
}
