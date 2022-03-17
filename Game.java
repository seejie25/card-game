import java.util.*;

public class Game {

    Player playerOne;
    Player playerTwo;
    Player playerThree;
    ArrayList<Player> playerInGame = new ArrayList<>();

    public void enterName(){
        Scanner input = new Scanner(System.in);

        System.out.print("Enter player 1 name: ");
        playerOne = new Player(input.next());
        playerInGame.add(playerOne);

        System.out.print("Enter player 2 name: ");
        playerTwo = new Player(input.next());
        playerInGame.add(playerTwo);

        System.out.print("Enter player 3 name: ");
        playerThree = new Player(input.next());
        playerInGame.add(playerThree);
    }

    //When start the game from beginning
    public void startGame(int stageWhat){

        if (stageWhat == 1){ //Phase 1
            System.out.println("\n**** 3-Player Phase ****");
            int count = 0;
            //Create a queue(card on hand) for each player
            for (Player thisPlayer: playerInGame) {
                if(count == 0){
                    LinkedList<Card> listThisPlayer = new LinkedList<>(Card.distributeCards(18));
                    Queue<String> queueThisPlayer = stringCardsInQueue(listThisPlayer, 18); //Put 5 cards into 1 String and add to queue
                    thisPlayer.assignCards(queueThisPlayer); //Assign to the player's card on hand
                }
                else{
                    LinkedList<Card> listThisPlayer = new LinkedList<>(Card.distributeCards(17));
                    Queue<String> queueThisPlayer = stringCardsInQueue(listThisPlayer, 17); //Put 5 cards into 1 String and add to queue
                    thisPlayer.assignCards(queueThisPlayer); //Assign to the player's card on hand
                }
                count ++;
            }
        }

        else if (stageWhat == 2){ //Phase 2
            System.out.println("\n**** 2-Player Phase ****");
            //Create a queue(card on hand) for each player
            for (Player thisPlayer: playerInGame) {
                LinkedList<Card> listThisPlayer = new LinkedList<>(Card.distributeCards(26));
                Queue<String> queueThisPlayer = stringCardsInQueue(listThisPlayer, 26); //Put 5 cards into 1 String and add to queue
                thisPlayer.assignCards(queueThisPlayer); //Assign to the player's card on hand
            }
        }

        //Display every players' card on hand
        System.out.println("\nAvailable Cards: ");
        for (Player thisPlayer: playerInGame) {
            thisPlayer.setNewScore(0); //reset new score to 0
            System.out.println(thisPlayer.getName() + ": " + thisPlayer.getCardsOnHand());
        }

        askForShuffle(stageWhat);
    }

    public void askForShuffle(int stageWhat){
        System.out.println("Press S to Shuffle or Press Enter to Start.");
        Scanner input = new Scanner(System.in);
        String userChoice = input.nextLine();
        if (userChoice.equals("S")  || userChoice.equals("s")){ //Yes, shuffle
            System.out.println("<S is pressed>");
            Card.shuffleCards();
            reassign(stageWhat);
        }
        else{ //No, start game
            if(stageWhat == 1){
                System.out.println("<Enter is pressed>");
                runRound(1);
            }
            else if(stageWhat == 2){
                System.out.println("<Enter is pressed>");
                runRound(2);
            }
        }
    }

    //Use this after shuffle
    public void reassign(int stageWhat){
        if(stageWhat == 1)
            startGame(1);
        else if(stageWhat == 2)
            startGame(2);
    }

    public void runRound(int stageWhat){
        int round = 1;
        int stopGame;

        if(stageWhat == 1)
            stopGame = 3; //Phase 1
        else
            stopGame = 4; //Phase 2

        while(round < stopGame + 1){
            System.out.println("\n*** ROUND " + round + " ***\n");

            //Put all player's card into an arraylist to loop (before sort)
            ArrayList<String> arrayCardsForEachRound = new ArrayList<>();
            for (Player thisPlayer: playerInGame)
                arrayCardsForEachRound.add(thisPlayer.cardsForEachRound());

            //Find the highest score
            int highest = findWinner1();

            //Put all player's card into an arraylist to loop (after sort)
            arrayCardsForEachRound = sortCards(arrayCardsForEachRound);

            System.out.println("Cards at Hand: ");
            for (int i = 0; i < playerInGame.size(); i++){
                if (playerInGame.get(i).getOldScore() == highest)
                    System.out.println(playerInGame.get(i).getName() + ": " + arrayCardsForEachRound.get(i) + " | Point = " + playerInGame.get(i).getOldScore() + " | Win");
                else
                    System.out.println(playerInGame.get(i).getName() + ": " + arrayCardsForEachRound.get(i) + " | Point = " + playerInGame.get(i).getOldScore());
            }

            System.out.println("\nScore: "); //Only players with highest score get points

            for(Player thisPlayer : playerInGame) {
                if (thisPlayer.getOldScore() == highest)
                    thisPlayer.setNewScore(thisPlayer.getNewScore() + highest);
                else
                    thisPlayer.setNewScore(thisPlayer.getNewScore());
                System.out.println(thisPlayer.getName() + "= " + thisPlayer.getNewScore());
            }

            //Display every players' card on hand
            System.out.println("\nAvailable Cards: ");
            for (Player thisPlayer: playerInGame)
                System.out.println(thisPlayer.getName() + ": " + thisPlayer.getCardsOnHand());


            if(round != stopGame) //To find the winners
                promptEnter("Press ENTER to next round.");
            else{
                if(stageWhat == 1)
                    winnerWith3Ppl();
                else
                    winnerWith2Ppl();
                }

            round += 1;
        }
    }

    public void winnerWith2Ppl(){
        int lowest = findWinner2();
        if(lowest == -1){ //If no loser, play again
            promptEnter("We don't have any loser, let's play again!\nPlease Shuffle the cards before starting the new round.");
            startGame(2);
        }
        else {
            Player loser = playerInGame.get(0);
            for (Player thisPlayer : playerInGame) //Find the loser
                if (thisPlayer.getNewScore() == lowest)
                    loser = thisPlayer;

            playerInGame.remove(loser); //Remove the loser from game

            System.out.println("\n***** " + playerInGame.get(0).getName() + " is the WINNER! *****\n");
        }
    }

    public void winnerWith3Ppl(){
        int lowest = findWinner2();
        if(lowest == -1){ //If no loser, play again
            promptEnter("We don't have any loser, let's play again!\nPlease Shuffle the cards before starting the new round.");
            startGame(1);
        }
        else {
            Player loser = playerInGame.get(0);
            for (Player thisPlayer : playerInGame) //Find the loser
                if (thisPlayer.getNewScore() == lowest)
                    loser = thisPlayer;

            playerInGame.remove(loser); //Remove the loser from game

            System.out.println("\n***** " + playerInGame.get(0).getName() + " and " + playerInGame.get(1).getName() + " proceed to 2-Player phase *****\n");
            promptEnter("Press Enter to continue, Please Shuffle the cards before starting the new round.");
            startGame(2);
        }
    }

    //To find winner for each round
    private int findWinner1() {
        int largestScore = playerInGame.get(0).getOldScore();

        for (int i = 1; i < playerInGame.size(); i++)
            if (playerInGame.get(i).getOldScore() > largestScore)
                largestScore = playerInGame.get(i).getOldScore();

        return largestScore;
    }

    //To find winner for each game(phase)
    private int findWinner2() {
        int tie = 0;
        int lowestScore = playerInGame.get(0).getNewScore();

        for (int i = 1; i < playerInGame.size(); i++)
            if (playerInGame.get(i).getNewScore() < lowestScore)
                lowestScore = playerInGame.get(i).getNewScore();

        //Make sure not tie (tie > 1 means tie)
        for (Player thisPlayer : playerInGame)
            if (thisPlayer.getNewScore() == lowestScore)
                tie += 1;

        if(tie > 1)
            lowestScore = -1; //When tie return -1

        return lowestScore;
    }

    //Put into queue which has been separated equally(all cards become string)
    public static LinkedList<String> stringCardsInQueue (LinkedList<Card> cards, int numberOfCards){
        LinkedList<String> temp = new LinkedList<>();
        int i = 0;
        while(i < numberOfCards){
            int count = 0;
            String cardsForARound = ""; //Combine 5 cards into a String
            for (int j = i; j < numberOfCards; j++) {
                if (count == 0)
                    cardsForARound = cards.get(j).toString();
                else
                    cardsForARound = cardsForARound + " " + cards.get(j).toString();
                count++;
                i++;
                if(count == 5 || i == numberOfCards) break; //Limit of a String in each queue
            }
            temp.add(cardsForARound);
        }
        cards.clear();
        return temp;
    }

    //Before sort the cards we need to change String back to Card, after that return String
    public ArrayList<String> sortCards(ArrayList<String> cardsBeforeSorting){
        ArrayList<String> sortedCards = new ArrayList<>();
        for(String card: cardsBeforeSorting){ //For each player's cards
            ArrayList<Card> temp = new ArrayList<>();
            String[] split = card.split(" "); //Split those 5 String
            for (String s : split) { //Convert 5 String to Card
                char stringSuit = s.charAt(0);
                char stringFace = s.charAt(1);
                Card newCard = new Card(String.valueOf(stringSuit), String.valueOf(stringFace), null);
                temp.add(newCard);
            }
            Collections.sort(temp); //Sort it using compareTo from Card class
            String cardsForARound = ""; //Change back to String
            for (int j = 0; j < temp.size(); j++) {
                if (j == 0)
                    cardsForARound = temp.get(j).toString();
                else
                    cardsForARound = cardsForARound + " " + temp.get(j).toString();
            }
            sortedCards.add(cardsForARound);
        }
        return sortedCards;
    }

    //Pause the program
    public void promptEnter(String message){
        System.out.println(message);
        Scanner input = new Scanner(System.in);
        input.nextLine();
    }
}
