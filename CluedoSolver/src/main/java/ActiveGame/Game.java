package ActiveGame;

import Scenes.GameWindow;

import java.util.ArrayList;
import java.util.HashSet;

public class Game {
    ArrayList<String> weapons = new ArrayList<>();
    ArrayList<String> characters = new ArrayList<>();
    ArrayList<String> rooms = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    HashSet<String> foundItems = new HashSet<>();

    private Player selectedPlayerAsk;
    private Player selectedPlayerAnswer;
    private String selectedCharacter;
    private String selectedRoom;
    private String selectedWeapon;

    GameWindow gameWindow;

    public Game() {

    }

    public void logicUpdate() {
        for (Player player : players) {
            foundItems.addAll(player.getDoesHaveList());
        }

        for (Player player : players) {
            for (String item : foundItems) {
                player.addDoesNotHave(item);
            }
        }

        for (Player player : players) {
            player.logicUpdate();
        }

        gameWindow.updateView();
    }

    public void guessMade(ArrayList<String> items) {
        Player asker = selectedPlayerAsk;
        Player answer = (selectedPlayerAnswer != null) ? selectedPlayerAnswer : selectedPlayerAsk;
        answer.addGuessAnswered(items);

        int diff = players.indexOf(asker) + 1;
        while (players.get(diff) != answer) {
            System.out.println("Iterating over: " + players.get(diff).getName());
            for (String item : items) {
                players.get(diff).addDoesNotHave(item);
                logicUpdate();
            }
            diff++;
            if (players.size() <= diff) {
                diff = 0;
            }
        }
    }

    public void addItem(String item, String type) {
        switch (type) {
                case "Weapon":
                    weapons.add(item);
                    break;

                case "Character":
                    characters.add(item);
                    break;

                case "Room":
                    rooms.add(item);
                    break;

                case "Players":
                    System.err.println("Use addItem(String, int) constructor for player");
                    break;
        }
    }

    public void addItem(String name, int cards) {
        players.add(new Player(name, cards));
    }

    public void removeItem(String item, String type) {
        switch (type) {
            case "Weapon":
                weapons.remove(item);
                break;

            case "Character":
                characters.remove(item);
                break;

            case "Room":
                rooms.remove(item);
                break;

            case "Players":
                players.removeIf(player -> player.getName().equals(item));
                break;
        }
    }

    public void selectItem(String item, String type) {
        switch (type) {
            case "Weapon":
                selectedWeapon = item;
                break;

            case "Character":
                selectedCharacter = item;
                break;

            case "Room":
                selectedRoom = item;
                break;

            case "PlayersAsk":
                for (Player player : players) {
                    if (player.getName().equals(item)) {
                        selectedPlayerAsk = player;
                    }
                }
                break;

            case "PlayersAnswer":
                for (Player player : players) {
                    if (player.getName().equals(item)) {
                        selectedPlayerAnswer = player;
                    }
                }
                break;
        }
    }

    public void addFoundItem(String s) {
        foundItems.add(s);
        gameWindow.updateSolutionView();
    }

    public boolean containsFoundItem(String s) {
        return foundItems.contains(s);
    }

    public boolean playerHas(String item, Player player) {
        return player.getDoesHaveList().contains(item);
    }

    public boolean playerDoesNotHave(String item, Player player) {
        return player.getDoesNotHaveList().contains(item);
    }

    public boolean playerMaybeHas(String item, Player player) {
        return player.getGuessesAnswered().contains(item);
    }

    public HashSet<String> getFoundItems() {
        return foundItems;
    }

    public ArrayList<String> getCharacters() {
        return characters;
    }

    public ArrayList<String> getWeapons() {
        return weapons;
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getSelectedPlayerAsk() {
        return selectedPlayerAsk;
    }

    public Player getSelectedPlayerAnswer() {
        return selectedPlayerAnswer;
    }

    public String getSelectedWeapon() {
        return selectedWeapon;
    }

    public String getSelectedCharacter() {
        return selectedCharacter;
    }

    public String getSelectedRoom() {
        return selectedRoom;
    }

    public void setGameWindow(GameWindow window) { gameWindow = window; }
}
