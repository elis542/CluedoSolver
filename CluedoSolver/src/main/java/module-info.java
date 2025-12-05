module cluedo.solver.cluedosolver {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens cluedo.solver.cluedosolver to javafx.fxml;
    exports cluedo.solver.cluedosolver;
    exports ActiveGame;
    opens ActiveGame to javafx.fxml;
    exports Scenes;
    opens Scenes to javafx.fxml;
}