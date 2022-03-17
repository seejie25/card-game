import java.util.*;

public class Card implements Comparable<Card>{

    String suit;
    String face;

    Card(String suit, String face){
        this.suit = suit;
        this.face = face;
    }

    private static final String[] arraySuit = new String[]{"d","c","h","s"};
    private static final Map<String,Integer> arrayFace = new HashMap<>();
    private static final ArrayList<Card> allCards = new ArrayList<>();
    private static int index;

    //to initialise the number(Key) with their point(Value)
    static {
        arrayFace.put("A",1);
        arrayFace.put("2",2);
        arrayFace.put("3",3);
        arrayFace.put("4",4);
        arrayFace.put("5",5);
        arrayFace.put("6",6);
        arrayFace.put("7",7);
        arrayFace.put("8",8);
        arrayFace.put("9",9);
        arrayFace.put("X",10);
        arrayFace.put("J",10);
        arrayFace.put("Q",10);
        arrayFace.put("K",10);

        createAllNewCards();
    }

    //create a set of cards and shuffle it
    private static void createAllNewCards(){  
        for (String currentKey : arrayFace.keySet()) {   //to get all keys(number)
            for (String s : arraySuit) {   //to get all suits
                Card newCard = new Card(s, currentKey);
                allCards.add(newCard);   //add to arraylist
            }
        }
        shuffleCards();
    }

    //shuffle the all cards
    public static void shuffleCards(){
        Collections.shuffle(allCards);
    }

    //get the distributed cards
    //need to write exception handling for not exceed 52 cards
    public static ArrayList<Card> distributeCards(int numberOfCards){
        ArrayList<Card> distributedCards = new ArrayList<>();

        //assign the card to the player
        for (int i = index, j = 0 ; i < 52 && j < numberOfCards; j++, i++) {
            distributedCards.add(allCards.get(i));
            index++;

            //Reset the index
            if(index == 52){
                index = 0;
            }
        }
        return distributedCards;
    }

    public static Map<String, Integer> getArrayFace() {
        return arrayFace;
    }

    @Override
    public String toString() {
        return suit + face;
    }

    @Override
    public int compareTo(Card o) {
        int result = 0;
        result = this.suit.compareTo(o.suit);
        if(result == 0)
            result = arrayFace.get(this.face).compareTo(arrayFace.get(o.face)); //compare their point (value)
        return result;
    }
}
