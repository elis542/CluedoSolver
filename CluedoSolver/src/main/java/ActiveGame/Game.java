package ActiveGame;

import java.util.ArrayList;

public class Game {
    ArrayList<String> weapons = new ArrayList<>();
    ArrayList<String> characters = new ArrayList<>();
    ArrayList<String> rooms = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();

    private Player selectedPlayerAsk;
    private Player selectedPlayerAnswer;
    private String selectedCharacter;
    private String selectedRoom;
    private String selectedWeapon;

    public Game() {

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
                    players.add(new Player(item));
                    break;
        }
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
                players.removeIf(player -> player.getName().equals("item"));
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
}
