package ActiveGame;

import java.util.ArrayList;

public class Player {
    private final String name;
    private final int numOfCards;

    private final ArrayList<String> doesNotHaveList = new ArrayList<String>();
    private final ArrayList<String> doesHaveList = new ArrayList<>();
    private final ArrayList<ArrayList<String>> guessesAnswered = new ArrayList<>();

    public Player(String name, int cards) {
        this.name = name;
        numOfCards = cards;
    }

    public void logicUpdate() {
        for (ArrayList<String> list : guessesAnswered) {
            if (list.size() == 1) {
                doesHaveList.add(list.getFirst());
            }
        }
        guessesAnswered.removeIf(list -> list.isEmpty());
    }

    public void addDoeshave(String item) {
        if (item == null) { return; }
        doesHaveList.add(item);
        for (ArrayList<String> totalList : guessesAnswered) {
            if (totalList.contains(item)) {
                guessesAnswered.remove(totalList);
            }
        }
    }

    public void addDoesNotHave(String item) {
        doesNotHaveList.add(item);
        for (ArrayList<String> totalList : guessesAnswered) {
            totalList.remove(item);
        }
    }

    public void addGuessAnswered(ArrayList<String> guess) {
        guess.removeIf(item -> doesNotHaveList.contains(item));
        if (guess.size() == 1) {
            addDoeshave(guess.getFirst());
            return;
        }
        guessesAnswered.add(guess);
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getDoesHaveList() {
        return doesHaveList;
    }

    public ArrayList<String> getDoesNotHaveList() {
        return doesNotHaveList;
    }

    public ArrayList<String> getGuessesAnswered() {
        ArrayList<String> allItems = new ArrayList<>();
        for (ArrayList<String> totalList : guessesAnswered) {
                allItems.addAll(totalList);
        }
        return allItems;
    }
}
