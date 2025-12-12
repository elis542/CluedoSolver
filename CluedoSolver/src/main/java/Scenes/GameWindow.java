package Scenes;

import ActiveGame.Game;
import ActiveGame.Player;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


//TODO: Add error text, so you can display errors when something that is not possible happens [DONE]
/*        Examples of impossible stuff:
    1. Someone answering on a question when we know that someone else has all the cards they asked for
    2. You making a guess and not selecting one of each weapon, character, room
    3. Someone not answering a question when they should have one of the cards
    4. Also add errors to the appropriate functions
 */

public class GameWindow extends VBox {
    private final Game game;
    private ListView<String> solutionView;
    private ListView<String> playerSolutionView;
    private Player playerViewSelect = null;

    private Text errorText;

    public GameWindow(double width, double height, Game game) {
        setWidth(width);
        setHeight(height);
        this.game = game;
        game.setGameWindow(this);

        initializeWindow();
    }

    private void initializeWindow() {
        VBox playerAskAndAnswer = createPlayerAskAndAnswer();
        HBox murderToolSelect = createMurderToolSelect();
        HBox topOfMenu = new HBox(playerAskAndAnswer, murderToolSelect);
        topOfMenu.setSpacing(65);

        solutionView = createSolutionView();
        VBox playerSelectBox = selectPlayerViewButtonCreator(game.getPlayers());
        playerSolutionView = createPlayerSolutionView();

        HBox middleBox = new HBox(solutionView, playerSelectBox, playerSolutionView);
        middleBox.setPadding(new Insets(50));
        middleBox.setSpacing(25);

        errorText = new Text();
        HBox errorMessage = new HBox(errorText); //TODO: fix this
        errorMessage.setMaxHeight(50);
        errorMessage.setMinHeight(50);
        errorMessage.setPadding(new Insets(0, 0, 0, 25));

        getChildren().addAll(topOfMenu, middleBox, errorMessage);
    }

    private VBox selectPlayerViewButtonCreator(ArrayList<Player> list) {
        MenuButton button = new MenuButton("Select player");
        Text numberOfCardsInt = new Text("cards: ");
        for (Player playerObj : list) {
            String player = playerObj.getName();

            MenuItem item = new MenuItem(player);
            item.setOnAction((event) -> {
                numberOfCardsInt.setText("cards: " + playerObj.getCards());
                button.setText(player);
                playerViewSelect = playerObj;
                updatePlayerSolutionView();
            });
            button.getItems().add(item);
        }

        return new VBox(button, numberOfCardsInt);
    }

    private ListView<String> createPlayerSolutionView() {
        ListView<String> returnList = new ListView<>();
        returnList.getItems().addAll(game.getWeapons());
        returnList.getItems().addAll(game.getCharacters());
        returnList.getItems().addAll(game.getRooms());

        returnList.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || playerViewSelect == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (game.playerHas(item, playerViewSelect)) {
                        setStyle("-fx-text-fill: green;");
                    } else if (game.playerMaybeHas(item, playerViewSelect)) {
                        setStyle("-fx-text-fill: yellow;");
                    } else if (game.playerDoesNotHave(item, playerViewSelect)) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("-fx-text-fill: black;");
                    }
                }
            }
        });
        returnList.setFocusTraversable(false);
        return returnList;
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
                        setStyle("-fx-text-fill: red;");
                    } else if (game.containsRightItem(item)) {
                        setStyle("-fx-text-fill: green;");
                    } else {
                        setStyle("-fx-text-fill: black;");
                    }
                }
            }
        });
        returnList.setFocusTraversable(false);
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

        VBox buttonBox = createGuessKnowBox();

        HBox returnBox = new HBox(weaponBox, characterBox, roomBox, buttonBox);
        returnBox.setSpacing(35);
        return returnBox;
    }

    private VBox createGuessKnowBox() {
        Button guessButton = new Button("Guess");
        guessButton.setOnAction((event) -> {
            guessButtonAction();
        });
        guessButton.setMinWidth(100);

        Button knowButton = new Button("Know");
        knowButton.setOnAction((event) -> {
            Player player = game.getSelectedPlayerAsk();
            player.addDoeshave(game.getSelectedWeapon());
            player.addDoeshave(game.getSelectedCharacter());
            player.addDoeshave(game.getSelectedRoom());

            game.logicUpdate();
            updateView();
        });
        knowButton.setMinWidth(100);

        VBox guessBox = new VBox(guessButton, knowButton);
        guessBox.setPadding(new Insets(13));
        guessBox.setSpacing(10);
        return guessBox;
    }

    private void guessButtonAction() {
        ArrayList<String> guessList = new ArrayList<>();

        if (game.getSelectedWeapon() != null) guessList.add(game.getSelectedWeapon());
        if (game.getSelectedCharacter() != null) guessList.add(game.getSelectedCharacter());
        if (game.getSelectedRoom() != null) guessList.add(game.getSelectedRoom());
        if (guessList.size() != 3) return;

        game.guessMade(guessList);
        updateView();
    }

    //fills the MenuButtons with relevant choices
    private void murderToolButtonBuilder(MenuButton button, ArrayList<String> list, String type) {
        MenuItem voidItem = new MenuItem("----");
        voidItem.setOnAction((event) -> {
            button.setText("----");
            game.selectItem(null, type);
        });
        button.getItems().add(voidItem);

        for (String tool : list) {
            MenuItem item = new MenuItem(tool);
            item.setOnAction((event) -> {
                button.setText(tool);
                if (tool.equals("----")) {
                    game.selectItem(null, type);
                } else {
                    game.selectItem(tool, type);
                }

            });
            button.getItems().add(item);
        }
    }

    private VBox createPlayerAskAndAnswer() {
        VBox returnBox = new VBox();
        ArrayList<Player> players = game.getPlayers();

        Text askText = new Text("Asks/Has");
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
        if (player.equals("----")) {
            game.selectItem(null, "PlayersAsk");
            return;
        }
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

    public void sendError(String error) {
        errorText.setFill(Color.RED);
        errorText.setText(error);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                errorText.setText("");
            }
        }, 6500);
    }

    public void updateView() {
        solutionView.refresh();
        playerSolutionView.refresh();
    }

    public void updateSolutionView() {
        solutionView.refresh();
    }
    
    public void updatePlayerSolutionView() {
        playerSolutionView.refresh();
    }
}
