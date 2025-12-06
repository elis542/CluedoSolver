package Scenes;

import ActiveGame.Game;
import cluedo.solver.cluedosolver.StageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartWindow extends HBox {
    Game game;

    public StartWindow(double width, double height, Game game) {
        setWidth(width);
        setHeight(height);
        initializeWindow();
        this.game = game;
    }

    private void initializeWindow() {
        VBox weaponVBox = new VBox();
        vBoxInitializer("Weapon", weaponVBox);

        VBox characterVBox = new VBox();
        vBoxInitializer("Character", characterVBox);

        VBox roomVBox = new VBox();
        vBoxInitializer("Room", roomVBox);

        VBox playerVBox = new VBox();
        vBoxInitializer("Players", playerVBox);

        getChildren().add(weaponVBox);
        getChildren().add(characterVBox);
        getChildren().add(roomVBox);
        getChildren().add(playerVBox);
    }

    private void vBoxInitializer(String textType, VBox inputBox) {
        ObservableList<String> listItems = FXCollections.observableArrayList();
        inputBox.setSpacing(15);
        inputBox.setPadding(new Insets(15, 15, 15, 15));

        TextField addedText = new TextField();
        addedText.setPromptText(textType);

        ListView<String> selectedItems = new ListView<>();
        selectedItems.setMaxHeight(275);

        Button addButton = new Button("add");
        addButton.setOnAction((action) -> {
            addButtonAction(textType, addedText.getText(), listItems, selectedItems);
            addedText.setText("");
        });

        Button removeButton = new Button("remove");
        removeButton.setOnAction((action) -> {
            removeButtonAction(textType, selectedItems.getSelectionModel().getSelectedItem(), listItems, selectedItems);
        });

        inputBox.getChildren().addAll(addedText, addButton, selectedItems, removeButton);

        //If it's the last row we want to add more buttons. Should probably be its own VBox...
        if (textType.equals("Players")) {
            HBox loadAndSave = new HBox();
            loadAndSave.setSpacing(5);

            Button loadButton = new Button("Load");
            loadButton.setOnAction((event) -> {
                loadButtonAction();
            });
            loadButton.setDisable(true); //REMOVE WHEN FEATURE WORKING

            Button saveButton = new Button("Save");
            saveButton.setOnAction((event) -> {
                saveButtonAction();
            });
            saveButton.setDisable(true); //REMOVE WHEN FEATURE IS WORKING

            loadAndSave.getChildren().addAll(loadButton, saveButton);

            Button startButton = new Button("start");
            startButton.setOnAction((event) -> {
                startButtonAction();
            });

            inputBox.getChildren().add(loadAndSave); //Make this into a addAll
            inputBox.getChildren().add(startButton);
        }
    }

    private void addButtonAction(String textType, String addedText, ObservableList<String> listItems, ListView<String> displayList) {
        listItems.add(addedText);
        displayList.setItems(listItems);
        game.addItem(addedText, textType);
    }

    private void removeButtonAction(String textType, String addedText, ObservableList<String> listItems, ListView<String> displayList) {
        listItems.remove(addedText);
        displayList.setItems(listItems);
        game.removeItem(addedText, textType);
    }

    private void saveButtonAction() {

    }

    private void loadButtonAction() {

    }

    private void startButtonAction() {
        StageController.getStage().setScene(new Scene(new GameWindow(getWidth(), getHeight(), game), getWidth(), getHeight()));
    }
}
