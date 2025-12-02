package cluedo.solver.cluedosolver;

import ActiveGame.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
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
        vBoxInitializer("Characters", characterVBox);

        VBox roomVBox = new VBox();
        vBoxInitializer("Rooms", roomVBox);

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

        if (textType.equals("Players")) {
            HBox loadAndSave = new HBox();
            loadAndSave.setSpacing(5);
            Button load = new Button("Load");
            Button save = new Button("Save");
            loadAndSave.getChildren().addAll(load, save);
            inputBox.getChildren().add(loadAndSave); //Place into addAll later...


            Button startButton = new Button("start");

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
}
