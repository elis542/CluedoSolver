package ActiveGame;

import java.util.ArrayList;
import java.util.HashSet;

public class Player {
    private final String name;
    private final int numOfCards;
    private final Game game;

    private final HashSet<String> doesHaveList = new HashSet<>();
    private final ArrayList<String> doesNotHaveList = new ArrayList<String>();
    private final ArrayList<ArrayList<String>> guessesAnswered = new ArrayList<>();
    private final ArrayList<String> cardsNoAnswer = new ArrayList<>();


    public Player(String name, int cards, Game game) {
        this.name = name;
        numOfCards = cards;
        this.game = game;
    }

    public void logicUpdate() {
        for (ArrayList<String> list : guessesAnswered) {
            if (list.size() == 1) {
                doesHaveList.add(list.getFirst());
            }
        }
        guessesAnswered.removeIf(list -> list.isEmpty());

        if (doesHaveList.size() == numOfCards && !cardsNoAnswer.isEmpty()) {
            for (String card : cardsNoAnswer) {
                game.addRightItem(card);
            }
        }
    }

    public void addDoeshave(String item) {
        if (item == null) { return; }
        doesHaveList.add(item);
        guessesAnswered.removeIf(totalList -> totalList.contains(item));
    }

    public void addDoesNotHave(String item) {
        if (doesHaveList.contains(item)) {
            return;
        }
        doesNotHaveList.add(item);
        for (ArrayList<String> totalList : guessesAnswered) {
            totalList.remove(item);
        }
    }

    public void addGuessAnswered(ArrayList<String> guess) {
        guess.removeIf(item -> doesNotHaveList.contains(item));
        guess.removeIf(item -> game.getFoundItems().contains(item));

        if (guess.size() == 1) {
            addDoeshave(guess.getFirst());
            return;
        }
        guessesAnswered.add(guess);
        logicUpdate();
    }

    public void addCardsNoAnswer(ArrayList<String> cards) {
        cardsNoAnswer.addAll(cards);
    }

    public String getName() {
        return name;
    }

    public HashSet<String> getDoesHaveList() {
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

    public int getCards() {
        return numOfCards;
    }
}
