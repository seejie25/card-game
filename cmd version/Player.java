import java.util.*;

public class Player {

    private final String name;
    private int oldScore;
    private int newScore;
    private Queue<String> cardsOnHand;

    //Create a player when start a game
    Player(String name){
        this.name = name;
        oldScore = 0;
        newScore = 0;
    }

    //Assign cards to each player
    public void assignCards(Queue<String> assignedCards){
        cardsOnHand = assignedCards;
    }

    public Queue<String> getCardsOnHand() {
        return cardsOnHand;
    }

    public String getName() {
        return name;
    }

    public int getOldScore() {
        return oldScore;
    }

    public int getNewScore() {
        return newScore;
    }

    public void setNewScore(int newScore) {
        this.newScore = newScore;
    }

    //Cards for each round
    public String cardsForEachRound(){
        String cardsForThisRound = cardsOnHand.poll();
        calScore1(cardsForThisRound);
        return cardsForThisRound;
    }

    //To calculate score for each suit and find largest
    private void calScore1(String cards){
        String[] splitCards = cards.split(" ");
        Stack<String> suitOfd = new Stack<>();
        int scoreOfd = 0;
        Stack<String> suitOfc = new Stack<>();
        int scoreOfc = 0;
        Stack<String> suitOfh = new Stack<>();
        int scoreOfh = 0;
        Stack<String> suitOfs = new Stack<>();
        int scoreOfs = 0;

        //To separate 5 cards into 1 card and add to stack according to the type of suit
        for (String thisCard : splitCards) {
            char charSuit = thisCard.charAt(0);
            String thisFace = String.valueOf(thisCard.charAt(1));
            switch (charSuit){
                case 'd': suitOfd.push(thisFace); break;
                case 'c': suitOfc.push(thisFace); break;
                case 'h': suitOfh.push(thisFace); break;
                case 's': suitOfs.push(thisFace); break;
            }
        }

        scoreOfd += calScore2(suitOfd);
        scoreOfc += calScore2(suitOfc);
        scoreOfh += calScore2(suitOfh);
        scoreOfs += calScore2(suitOfs);

        int[] allSuitScore = new int[]{scoreOfd, scoreOfc, scoreOfh, scoreOfs};
        oldScore = findLargest(allSuitScore);

    }

    //To get value using key
    private int calScore2(Stack<String> calThisStack){
        int scoreOfThisStack = 0;

        for (String key: calThisStack) {
            int thisScore = Card.getArrayFace().get(key);
            scoreOfThisStack += thisScore;
        }

        return scoreOfThisStack;
    }

    //To find largest combination
    private static int findLargest(int[] allSuitScore)
    {
        int largestScore = allSuitScore[0];

        for (int i = 1; i < allSuitScore.length; i++)
            if (allSuitScore[i] > largestScore)
                largestScore = allSuitScore[i];

        return largestScore;
    }
}
