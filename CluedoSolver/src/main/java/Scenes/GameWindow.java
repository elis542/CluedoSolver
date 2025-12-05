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
        getChildren().add(playerAskAndAnswer);
    }

    private VBox createPlayerAskAndAnswer() {
        VBox returnBox = new VBox();
        ArrayList<Player> players = game.getPlayers();

        Text askText = new Text("Asks");
        Text answerText = new Text("Answers");

        MenuButton askButton = new MenuButton("Asks");
        askButton.setMinWidth(55);
        MenuButton answerButton = new MenuButton("Answers");
        answerButton.setMinWidth(55);

        //Creates the dropdown menus
        ArrayList<MenuItem> allButtonsAsk = new ArrayList<>();
        ArrayList<MenuItem> allButtonsAnswer = new ArrayList<>();

        for (Player player : players) {
            MenuItem item = new MenuItem(player.getName());
            allButtonsAsk.add(item);
            askButtonListBuilder(players, askButton, allButtonsAsk);
            item.setOnAction((e) -> {
                askButtonAction(player, askButton);
                answerButtonListBuilder(players, answerButton, allButtonsAnswer);
            });
        }

        for (Player player : players) {
            MenuItem item = new MenuItem(player.getName());
            allButtonsAnswer.add(item);
            answerButtonListBuilder(players, answerButton, allButtonsAnswer);
            item.setOnAction((e) -> {
                answerButtonAction(player, answerButton);
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

    private void askButtonAction(Player player, MenuButton button) {
        button.setText(player.getName());
        game.selectItem(player.getName(), "PlayersAsk");
    }

    private void answerButtonAction(Player player, MenuButton button) {
        button.setText(player.getName());
        game.selectItem(player.getName(), "PlayersAnswer");
    }
}
