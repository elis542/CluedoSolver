package ActiveGame;

import java.util.ArrayList;

public class Game {
    ArrayList<String> weapons = new ArrayList<>();
    ArrayList<String> characters = new ArrayList<>();
    ArrayList<String> rooms = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();

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
                    //TEMP NEEDS FIXING
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
                //TEMP NEEDS FIXING
                break;
        }
    }
}
