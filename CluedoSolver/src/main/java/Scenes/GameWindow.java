package Scenes;

import ActiveGame.Game;
import ActiveGame.Player;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.zip.ZipEntry;

public class GameWindow extends VBox {
    Game game;

    public GameWindow(double width, double height, Game game) {
        setWidth(width);
        setHeight(height);
        this.game = game;

        initializeWindow();
    }

    private void initializeWindow() {
        VBox playerAskAndAnswer = createPlayerAskAndAnswer();
        HBox murderToolSelect = createMurderToolSelect();
        HBox topOfMenu = new HBox(playerAskAndAnswer, murderToolSelect);
        topOfMenu.setSpacing(65);

        getChildren().add(topOfMenu);
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

        HBox returnBox = new HBox(weaponButton, characterButton, roomButton);
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
}
