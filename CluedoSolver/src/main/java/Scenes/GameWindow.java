package Scenes;

import ActiveGame.Game;
import ActiveGame.Player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


public class GameWindow extends VBox {
    Game game;
    ListView<String> solutionView;

    public GameWindow(double width, double height, Game game) {
        setWidth(width);
        setHeight(height);
        this.game = game;
        solutionView = createSolutionView();
        game.setGameWindow(this);

        //TODO: REMOVE THIS
        game.addFoundItem("kniv");

        initializeWindow();
    }

    private void initializeWindow() {
        VBox playerAskAndAnswer = createPlayerAskAndAnswer();
        HBox murderToolSelect = createMurderToolSelect();
        HBox topOfMenu = new HBox(playerAskAndAnswer, murderToolSelect);
        topOfMenu.setSpacing(65);

        //TODO: add selected player view to middlebox
        HBox middleBox = new HBox(solutionView);
        middleBox.setPadding(new Insets(50));

        getChildren().addAll(topOfMenu, middleBox);
    }

    private ListView<String> createSolutionView() {
        ListView<String> returnList = new ListView<>();
        returnList.getItems().addAll(game.getWeapons());
        returnList.getItems().addAll(game.getCharacters());
        returnList.getItems().addAll(game.getRooms());

        returnList.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (game.containsFoundItem(item)) {
                        setStyle("-fx-text-fill: green;");
                    }
                }
            }
        });
        return returnList;
    }

    private HBox createMurderToolSelect() {
        MenuButton weaponButton = new MenuButton("Weapon");
        MenuButton characterButton = new MenuButton("Character");
        MenuButton roomButton = new MenuButton("Room");

        setButtonWidth(weaponButton, 100);
        setButtonWidth(characterButton, 100);
        setButtonWidth(roomButton, 100);

        murderToolButtonBuilder(weaponButton, game.getWeapons(), "Weapon");
        murderToolButtonBuilder(characterButton, game.getCharacters(), "Character");
        murderToolButtonBuilder(roomButton, game.getRooms(), "Room");

        Text weaponText = new Text("Weapon");
        Text characterText = new Text("Character");
        Text roomText = new Text("Room");

        VBox weaponBox = new VBox(weaponText, weaponButton);
        VBox characterBox = new VBox(characterText, characterButton);
        VBox roomBox = new VBox(roomText, roomButton);

        //TODO: This button needs a .setOnAction
        Button guessButton = new Button("Guess");
        guessButton.setMinWidth(100);
        VBox guessBox = new VBox(guessButton);
        guessBox.setPadding(new Insets(13));

        HBox returnBox = new HBox(weaponBox, characterBox, roomBox, guessBox);
        returnBox.setSpacing(35);
        return returnBox;
    }

    //fills the MenuButtons with relevant choices
    private void murderToolButtonBuilder(MenuButton button, ArrayList<String> list, String type) {
        for (String tool : list) {
            MenuItem item = new MenuItem(tool);
            item.setOnAction((event) -> {
                button.setText(tool);
                game.selectItem(tool, type);
            });
            button.getItems().add(item);
        }
    }

    private VBox createPlayerAskAndAnswer() {
        VBox returnBox = new VBox();
        ArrayList<Player> players = game.getPlayers();

        Text askText = new Text("Asks");
        Text answerText = new Text("Answers");

        MenuButton askButton = new MenuButton("Asks");
        setButtonWidth(askButton, 100);
        MenuButton answerButton = new MenuButton("Answers");
        setButtonWidth(answerButton, 100);

        //Creates the dropdown menus, I have to make 2 lists because they have different setOnAction functions
        ArrayList<MenuItem> allButtonsAsk = new ArrayList<>();
        ArrayList<MenuItem> allButtonsAnswer = new ArrayList<>();

        for (Player player : players) {
            MenuItem item = new MenuItem(player.getName());
            allButtonsAsk.add(item);
            askButtonListBuilder(players, askButton, allButtonsAsk);
            item.setOnAction((e) -> {
                askButtonAction(player.getName(), askButton);
                answerButtonListBuilder(players, answerButton, allButtonsAnswer);
            });
        }

        //Adding this item in the case that noone has the cards asked for
        MenuItem noAnswer = new MenuItem("----");
        noAnswer.setOnAction((event) -> {
            answerButtonAction("---", answerButton);
            askButtonListBuilder(players, askButton, allButtonsAsk);
        });
        allButtonsAnswer.add(noAnswer);

        for (Player player : players) {
            MenuItem item = new MenuItem(player.getName());
            allButtonsAnswer.add(item);
            answerButtonListBuilder(players, answerButton, allButtonsAnswer);
            item.setOnAction((e) -> {
                answerButtonAction(player.getName(), answerButton);
                askButtonListBuilder(players, askButton, allButtonsAsk);
            });
        }

        returnBox.getChildren().addAll(askText, askButton, answerText, answerButton);
        return returnBox;
    }


    //These 2 functions make sure the lists doesn't contain a player that is selected by the other button
    private void answerButtonListBuilder(ArrayList<Player> players, MenuButton button, ArrayList<MenuItem> list) {
        button.getItems().clear();
        button.getItems().addAll(list);
        if (game.getSelectedPlayerAsk() != null) {
            button.getItems().removeIf(m -> m.getText().equals(game.getSelectedPlayerAsk().getName()));
        }
    }

    private void askButtonListBuilder(ArrayList<Player> players, MenuButton button, ArrayList<MenuItem> list) {
        button.getItems().clear();
        button.getItems().addAll(list);
        if (game.getSelectedPlayerAnswer() != null) {
            button.getItems().removeIf(m -> m.getText().equals(game.getSelectedPlayerAnswer().getName()));
        }
    }

    private void askButtonAction(String player, MenuButton button) {
        button.setText(player);
        game.selectItem(player, "PlayersAsk");
    }

    private void answerButtonAction(String player, MenuButton button) {
        button.setText(player);
        game.selectItem(player, "PlayersAnswer");
    }

    private void setButtonWidth(MenuButton button, double size) {
        button.setMaxWidth(size);
        button.setMinWidth(size);
    }

    public void updateSolutionView() {
        solutionView.refresh();
    }
}
