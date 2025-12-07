package Scenes;

import ActiveGame.Game;
import cluedo.solver.cluedosolver.StageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartWindow extends HBox {
    private Game game;
    private boolean testScriptOn = true;

    public StartWindow(double width, double height, Game game) {
        this.game = game;
        setWidth(width);
        setHeight(height);
        initializeWindow();
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

        getChildren().addAll(weaponVBox, characterVBox, roomVBox, playerVBox);

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

        if (textType.equals("Players")) {
            MenuButton cardNum = new MenuButton();
            cardNum.setText("cards");

            for (int x = 1; x < 11; x++) {
                String num = String.valueOf(x);
                MenuItem item = new MenuItem(num);
                item.setOnAction((event) -> {
                    cardNum.setText(num);
                });
                cardNum.getItems().add(item);
            }

            addButton.setOnAction((event) -> {
                addButtonActionPlayer(textType, addedText.getText(), listItems, selectedItems, Integer.parseInt(cardNum.getText()));
                addedText.setText("");
            });

            inputBox.getChildren().addAll(addedText, cardNum, addButton, selectedItems, removeButton);
            vBoxInitializerPlayerAddon(inputBox);
        } else {
            inputBox.getChildren().addAll(addedText, addButton, selectedItems, removeButton);
        }

        if (testScriptOn) {
            testScript(listItems, selectedItems, textType);
        }
    }

    private void vBoxInitializerPlayerAddon(VBox inputBox) {
        HBox loadAndSave = new HBox();
        loadAndSave.setSpacing(5);

        Button loadButton = new Button("Load");
        loadButton.setOnAction((event) -> {
            loadButtonAction();
        });
        loadButton.setDisable(true); //TODO: REMOVE WHEN FEATURE WORKING

        Button saveButton = new Button("Save");
        saveButton.setOnAction((event) -> {
            saveButtonAction();
        });
        saveButton.setDisable(true); //TODO: REMOVE WHEN FEATURE IS WORKING

        loadAndSave.getChildren().addAll(loadButton, saveButton);

        Button startButton = new Button("start");
        startButton.setOnAction((event) -> {
            startButtonAction();
        });

        inputBox.getChildren().add(loadAndSave); //TODO: Make this into a addAll
        inputBox.getChildren().add(startButton);
    }

    private void addButtonAction(String textType, String addedText, ObservableList<String> listItems, ListView<String> displayList) {
        listItems.add(addedText);
        displayList.setItems(listItems);
        game.addItem(addedText, textType);
    }

    private void addButtonActionPlayer(String textType, String addedText, ObservableList<String> listItems, ListView<String> displayList, int cards) {
        listItems.add(addedText);
        displayList.setItems(listItems);
        game.addItem(addedText, cards);
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

    private void testScript(ObservableList<String> listItems, ListView<String> selectedItems, String textType) {
        if (textType.equals("Players")) {
            addButtonActionPlayer(textType, textType + "1", listItems, selectedItems, 3);
            addButtonActionPlayer(textType, textType + "2", listItems, selectedItems, 3);
            addButtonActionPlayer(textType, textType + "3", listItems, selectedItems, 3);
            addButtonActionPlayer(textType, textType + "4", listItems, selectedItems, 3);
            addButtonActionPlayer(textType, textType + "5", listItems, selectedItems, 3);
            addButtonActionPlayer(textType, textType + "6", listItems, selectedItems, 3);
            addButtonActionPlayer(textType, textType + "7", listItems, selectedItems, 3);
        } else {
            addButtonAction(textType, textType + "1", listItems, selectedItems);
            addButtonAction(textType, textType + "2", listItems, selectedItems);
            addButtonAction(textType, textType + "3", listItems, selectedItems);
            addButtonAction(textType, textType + "4", listItems, selectedItems);
            addButtonAction(textType, textType + "5", listItems, selectedItems);
            addButtonAction(textType, textType + "6", listItems, selectedItems);
            addButtonAction(textType, textType + "7", listItems, selectedItems);
        }
    }
}
