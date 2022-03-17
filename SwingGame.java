import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

public class SwingGame {

    Player playerOne;
    Player playerTwo;
    Player playerThree;
    ArrayList<Player> playerInGame = new ArrayList<>();
    
    JFrame frame = new SwingMain();
    JPanel boxPanel = new JPanel(); // main panel
    JPanel bottomPanel = new JPanel(); // panel for buttons

    public void homePage() {
        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // clean previous frame
        frame.getContentPane().removeAll();

        c.gridy = 0;
        JLabel welcome = new JLabel("WELCOME!");
        SwingMain.customTitle(welcome);
        gridBagPanel.add(welcome, c);

        JButton exit = new JButton("EXIT"); // exit game button to exit from the program
        SwingMain.customButton(exit);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  
            }
        });

        JButton start = new JButton("START"); // start game button
        SwingMain.customButton(start);
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterName(); // proceed to next page to enter player name
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exit);
        buttonPanel.add(start);

        c.gridy = 1;
        gridBagPanel.add(buttonPanel, c);

        frame.add(gridBagPanel);
        frame.setVisible(true);
    }

    public void enterName() {
        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // clean previous frame
        frame.getContentPane().removeAll();

        c.gridy = 0; // y-index
        c.gridwidth = 2; // grid width
        JLabel enterName = new JLabel("Enter Player Name");
        SwingMain.customTitle(enterName);
        gridBagPanel.add(enterName, c);

        c.gridx = 0; // x-index
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(20, 0, 0, 0); // top padding
        JLabel player1Name = new JLabel("Player 1");
        SwingMain.customText(player1Name);
        gridBagPanel.add(player1Name, c);

        c.gridx = 1;
        c.insets = new Insets(20, 0, 0, 40); // top and right padding
        JTextField player1 = new JTextField(15);
        SwingMain.customTextField(player1);
        gridBagPanel.add(player1, c);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(10, 0, 0, 0); // top padding
        JLabel player2Name = new JLabel("Player 2");
        SwingMain.customText(player2Name);
        gridBagPanel.add(player2Name, c);

        c.gridx = 1;
        c.insets = new Insets(10, 0, 0, 40); // top and right padding
        JTextField player2 = new JTextField(15);
        SwingMain.customTextField(player2);
        gridBagPanel.add(player2, c);

        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(10, 0, 0, 0); // top  padding
        JLabel player3Name = new JLabel("Player 3");
        SwingMain.customText(player3Name);
        gridBagPanel.add(player3Name, c);

        c.gridx = 1;
        c.insets = new Insets(10, 0, 0, 40); // top and right padding
        JTextField player3 = new JTextField(15);
        SwingMain.customTextField(player3);
        gridBagPanel.add(player3, c);

        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(20, 70, 0, 0); // top and left padding
        JButton cancel = new JButton("Cancel"); // press cancel button to back to homepage
        SwingMain.customButton(cancel);
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                homePage();
            }
        });
        gridBagPanel.add(cancel, c);

        c.gridx = 1;
        c.insets = new Insets(20, 0, 0, 60); // top and right padding
        JButton confirm = new JButton("Confirm"); // press confirm button to start game
        SwingMain.customButton(confirm);
        confirm.addActionListener(new ActionListener() { // once confirm button is pressed
            public void actionPerformed(ActionEvent e) {
                playerOne = new Player(player1.getText()); // get player name form the text field
                playerInGame.add(playerOne);

                playerTwo = new Player(player2.getText());
                playerInGame.add(playerTwo);

                playerThree = new Player(player3.getText());
                playerInGame.add(playerThree);

                try {
                    startGame(1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        gridBagPanel.add(confirm, c);

        frame.add(gridBagPanel);
        frame.setVisible(true);
    }

    // when start the game from beginning
    public void startGame(int stageWhat) throws IOException {
        // clean previous panel and frame
        boxPanel.removeAll();
        boxPanel.revalidate();
        boxPanel.repaint();
        frame.getContentPane().removeAll();

        // use BoxLayout for vertical alignment
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));

        JPanel phase = new JPanel(new FlowLayout(FlowLayout.CENTER));
        if (stageWhat == 1) { // phase 1
            JLabel numOfPlayer = new JLabel("3-PLAYER PHASE");
            SwingMain.customTitle(numOfPlayer);
            phase.add(numOfPlayer);
            boxPanel.add(phase);

            int count = 0;
            // create a queue(card on hand) for each player
            for (Player thisPlayer : playerInGame) {
                if (count == 0) {
                    LinkedList<Card> listThisPlayer = new LinkedList<>(Card.distributeCards(18));
                    Queue<String> queueThisPlayer = stringCardsInQueue(listThisPlayer, 18); // put 5 cards into 1 String
                                                                                            // and add to queue
                    thisPlayer.assignCards(queueThisPlayer); // assign to the player's card on hand
                } 
                else {
                    LinkedList<Card> listThisPlayer = new LinkedList<>(Card.distributeCards(17));
                    Queue<String> queueThisPlayer = stringCardsInQueue(listThisPlayer, 17); // put 5 cards into 1 String
                                                                                            // and add to queue
                    thisPlayer.assignCards(queueThisPlayer); // assign to the player's card on hand
                }
                count++;
            }
        }
        else if (stageWhat == 2) { // phase 2
            JLabel numOfPlayer = new JLabel("2-PLAYER PHASE");
            SwingMain.customTitle(numOfPlayer);
            phase.add(numOfPlayer);
            boxPanel.add(phase);

            // create a queue(card on hand) for each player
            for (Player thisPlayer : playerInGame) {
                LinkedList<Card> listThisPlayer = new LinkedList<>(Card.distributeCards(26));
                Queue<String> queueThisPlayer = stringCardsInQueue(listThisPlayer, 26); // put 5 cards into 1 String and
                // add to queue
                thisPlayer.assignCards(queueThisPlayer); // assign to the player's card on hand
            }
        }

        // display every players' card on hand
        JPanel availableCard = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel availCard = new JLabel("Available Cards");
        SwingMain.customText(availCard);
        availableCard.add(availCard);
        boxPanel.add(availableCard);

        for (Player thisPlayer : playerInGame) {
            thisPlayer.setNewScore(0); // reset new score to 0

            JPanel playerName = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel playerNameLabel = new JLabel(thisPlayer.getName());
            SwingMain.customText(playerNameLabel);
            playerName.add(playerNameLabel);

            // convert String type into Card type
            Queue<Card> availableCardQueue = new LinkedList<>();
            availableCardQueue = toCard(thisPlayer.getCardsOnHand());

            JPanel playerCard = new JPanel(new FlowLayout(FlowLayout.LEFT));
            playerCard.add(Box.createHorizontalStrut(-15));
            JPanel playerCardNdRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            playerCardNdRow.add(Box.createHorizontalStrut(135));
            
            if (stageWhat == 1) {
                for (int j = 0; j < availableCardQueue.size(); j++) {
                    // get card image
                    JLabel imageLabel = new JLabel(new ImageIcon(((LinkedList<Card>) availableCardQueue).get(j).getCardImage().getScaledInstance(70, 100, 20)));
                    
                    if (j % 5 == 0)  // seperate the cards into 5 per group
                        playerCard.add(Box.createHorizontalStrut(45));
                    else 
                        playerCard.add(Box.createHorizontalStrut(-30)); // to display image over image
                    
                    playerCard.add(imageLabel);
                }
            }
            else if (stageWhat == 2) {
                for (int j = 0; j < availableCardQueue.size(); j++) {
                    JLabel imageLabel = new JLabel(new ImageIcon(((LinkedList<Card>) availableCardQueue).get(j).getCardImage().getScaledInstance(70, 100, 20)));
                    
                    if (j % 5 == 0) { // seperate the cards into 5 per group
                        playerCard.add(Box.createHorizontalStrut(45));
                        playerCardNdRow.add(Box.createHorizontalStrut(45));
                    } 
                    else { 
                        playerCard.add(Box.createHorizontalStrut(-30)); // to display image over image
                        playerCardNdRow.add(Box.createHorizontalStrut(-30));
                    }

                    if (j >= 15) // if first row have more than 15 cards
                        playerCardNdRow.add(imageLabel); // continue to add card in second row
                    else
                        playerCard.add(imageLabel);
                }
            }
            boxPanel.add(playerName);
            boxPanel.add(playerCard);
            boxPanel.add(playerCardNdRow);
        }
        askForShuffle(stageWhat);

        boxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(boxPanel, BorderLayout.PAGE_START);
        frame.setVisible(true);
    }

    public void askForShuffle(int stageWhat) {
        // clean bottom panel
        bottomPanel.removeAll();
        bottomPanel.revalidate();
        bottomPanel.repaint();

        JButton shuffle = new JButton("Shuffle"); // shuffle card button
        SwingMain.customButton(shuffle);
        shuffle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Card.shuffleCards();
                try {
                    reassign(stageWhat);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        bottomPanel.add(shuffle);

        JButton start = new JButton("Start"); // start game button
        SwingMain.customButton(start);
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (stageWhat == 1) {
                    try {
                        runRound(1);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } 
                else if (stageWhat == 2) {
                    try {
                        runRound(2);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(start);
        frame.add(bottomPanel, BorderLayout.PAGE_END);
        frame.setVisible(true);
    }

    // use this after shuffle
    public void reassign(int stageWhat) throws IOException {
        if (stageWhat == 1)
            startGame(1);
        else if (stageWhat == 2)
            startGame(2);
    }

    int round = 1;
    public void runRound(int stageWhat) throws IOException {
        GridBagConstraints c = new GridBagConstraints();

        // clean previous panel
        boxPanel.removeAll();
        boxPanel.revalidate();
        boxPanel.repaint();
        bottomPanel.removeAll();
        bottomPanel.revalidate();
        bottomPanel.repaint();

        int stopGame;

        if (stageWhat == 1)
            stopGame = 3; // phase 1 total have 3 rounds of game
        else
            stopGame = 4; // phase 2 total have 4 rounds of game

        if (round < stopGame + 1) {
            JPanel roundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel roundNum = new JLabel("ROUND " + round);
            SwingMain.customTitle(roundNum);
            roundPanel.add(roundNum);
            boxPanel.add(roundPanel);

            // put all player's card into an arraylist to loop (before sort)
            ArrayList<String> arrayCardsForEachRound = new ArrayList<>();
            for (Player thisPlayer : playerInGame)
                arrayCardsForEachRound.add(thisPlayer.cardsForEachRound());

            // find the highest score
            int highest = findWinner1();

            // put all player's card into an arraylist to loop (after sort)
            Queue<Card> arrayCardsForEachRoundCard = new LinkedList<>();
            arrayCardsForEachRoundCard = sortCards(arrayCardsForEachRound);

            JPanel handCards = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel handCardsLabel = new JLabel("Cards at Hand");
            SwingMain.customText(handCardsLabel);
            handCards.add(handCardsLabel);
            boxPanel.add(handCards);

            JPanel handCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            // print cards and points for a player
            for (int i = 0; i < playerInGame.size(); i++) {
                JPanel cardsAtHandPanel = new JPanel(new GridBagLayout()); // create a single border for every players
                Border playerBorder = BorderFactory.createLineBorder(Color.black);
                Border winnerBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED); // red colour border for winner 

                JPanel cardImage = new JPanel();
                for (int j = 0; j < 5; j++) {
                    Card temp = arrayCardsForEachRoundCard.poll();
                    cardImage.add(new JLabel(new ImageIcon(temp.getCardImage().getScaledInstance(70, 100, 20))));
                }

                c.gridx = i;
                c.gridy = 1;
                c.insets = new Insets(5, 0, 0, 0);
                cardsAtHandPanel.add(cardImage, c); // add card image

                c.gridy = 2;
                if (playerInGame.get(i).getOldScore() == highest) {
                    cardsAtHandPanel.add(new JLabel("Point: " + playerInGame.get(i).getOldScore() + " WIN!"), c); // add point
                    TitledBorder title = BorderFactory.createTitledBorder(winnerBorder, playerInGame.get(i).getName());
                    title.setTitleJustification(TitledBorder.CENTER);
                    cardsAtHandPanel.setBorder(title);
                }
                else {
                    cardsAtHandPanel.add(new JLabel("Point: " + playerInGame.get(i).getOldScore() + ""), c); // add point
                    TitledBorder title = BorderFactory.createTitledBorder(playerBorder, playerInGame.get(i).getName());
                    title.setTitleJustification(TitledBorder.CENTER);
                    cardsAtHandPanel.setBorder(title);
                }

                if (playerInGame.get(i).getOldScore() == highest) {
                    playerInGame.get(i).setNewScore(playerInGame.get(i).getNewScore() + highest); // only players with highest score get points
                }
                else {
                    playerInGame.get(i).setNewScore(playerInGame.get(i).getNewScore());
                }

                c.gridy = 3;
                c.insets = new Insets(5, 0, 10, 0);
                cardsAtHandPanel.add(new JLabel("Score: " + playerInGame.get(i).getNewScore()), c);

                handCardsPanel.add(cardsAtHandPanel);
            }
            boxPanel.add(handCardsPanel, BorderLayout.CENTER);
            
            // display every players' card on hand
            JPanel availableCard = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel availableCardLabel = new JLabel("Available Cards");
            SwingMain.customText(availableCardLabel);
            availableCard.add(availableCardLabel);
            boxPanel.add(availableCard);

            for (Player thisPlayer : playerInGame) {
                JPanel playerName = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel playerNameLabel = new JLabel(thisPlayer.getName());
                SwingMain.customText(playerNameLabel);
                playerName.add(playerNameLabel);
    
                // convert String type into Card type
                Queue<Card> availableCardQueue = new LinkedList<>();
                availableCardQueue = toCard(thisPlayer.getCardsOnHand());
    
                JPanel playerCard = new JPanel(new FlowLayout(FlowLayout.LEFT));

                for (int j = 0; j < availableCardQueue.size(); j++) {
                    JLabel imageLabel = new JLabel(new ImageIcon(((LinkedList<Card>) availableCardQueue).get(j).getCardImage().getScaledInstance(50, 80, 20)));
                    
                    if (j % 5 == 0)  // seperate the cards into 5 per group
                        playerCard.add(Box.createHorizontalStrut(45));
                    else 
                        playerCard.add(Box.createHorizontalStrut(-30)); // to display image over image
                    
                    playerCard.add(imageLabel);
                }
                boxPanel.add(playerName);
                boxPanel.add(playerCard);
            }

            JButton nextRound = new JButton("Next Round"); // next round button
            SwingMain.customButton(nextRound);
            bottomPanel.add(nextRound);
            nextRound.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (round != stopGame) { // if not the last round
                        round += 1;
                        try {
                            runRound(stageWhat); // proceed to next round
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    else { // if is the last round
                        round = 1;
                        if (stageWhat == 1) // if in three-player phase
                            winnerWith3Ppl(); // find two winners
                        else // if in two-player phase
                            winnerWith2Ppl(); // find final winner
                    }
                }
            });
        }
        boxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        frame.add(bottomPanel, BorderLayout.PAGE_END);
        frame.setVisible(true);
    }

    // find final winner from two-player phase
    public void winnerWith2Ppl() {
        // clean previous panel
        boxPanel.removeAll();
        boxPanel.revalidate();
        boxPanel.repaint();
        bottomPanel.removeAll();

        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        int lowest = findWinner2();
        if (lowest == -1) { // if two players have same point
            c.gridy = 0;
            JLabel label = new JLabel("It's a tie! Would you like to play again?");
            SwingMain.customEndText(label);
            gridBagPanel.add(label, c);
        }
        else {
            Player loser = playerInGame.get(0);
            for (Player thisPlayer : playerInGame) // find the loser
                if (thisPlayer.getNewScore() == lowest)
                    loser = thisPlayer;

            playerInGame.remove(loser); // remove the loser from game

            c.gridy = 0;
            JLabel label = new JLabel(playerInGame.get(0).getName() + " is the WINNER!");
            SwingMain.customEndText(label);
            gridBagPanel.add(label, c);
        }

        JButton exit = new JButton("Exit"); // exit game button
        SwingMain.customButton(exit);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);;
            }
        });

        JButton newGame = new JButton("Start a New Game"); // new game button
        SwingMain.customButton(newGame);
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                SwingGame newGame = new SwingGame();
                newGame.homePage();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exit);
        buttonPanel.add(newGame);

        c.gridy = 1;
        gridBagPanel.add(Box.createVerticalStrut(45));
        gridBagPanel.add(buttonPanel, c);

        frame.add(gridBagPanel);
        frame.setVisible(true);
    }

    // find two winners from three-player phase
    public void winnerWith3Ppl() {
        // clean previous panel
        boxPanel.removeAll();
        boxPanel.revalidate();
        boxPanel.repaint();
        bottomPanel.removeAll();

        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        int lowest = findWinner2();
        if (lowest == -1) { // if 3 players have same point
            c.gridy = 0;
            JLabel label = new JLabel("It's a tie! Would you like to play again?");
            SwingMain.customEndText(label);
            gridBagPanel.add(label, c);
            gridBagPanel.add(Box.createVerticalStrut(45));

            JButton exit = new JButton("Exit"); // exit game button
            SwingMain.customButton(exit);
            exit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);;
                }
            });

            JButton newGame = new JButton("Yes"); // new game button
            SwingMain.customButton(newGame);
            newGame.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Card.shuffleCards();
                    try {
                        startGame(1);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(exit);
            buttonPanel.add(newGame);

            c.gridy = 1;
            gridBagPanel.add(buttonPanel, c);
        }
        else {
            Player loser = playerInGame.get(0);
            for (Player thisPlayer : playerInGame) // find the loser
                if (thisPlayer.getNewScore() == lowest)
                    loser = thisPlayer;

            playerInGame.remove(loser); // remove the loser from game

            c.gridy = 0;
            JLabel label = new JLabel(playerInGame.get(0).getName() + " and " + playerInGame.get(1).getName() + " proceed to 2-Player phase.");
            SwingMain.customEndText(label);
            gridBagPanel.add(label, c);
            gridBagPanel.add(Box.createVerticalStrut(50));

            c.gridy = 1;
            JButton button = new JButton("Continue");
            SwingMain.customButton(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Card.shuffleCards();
                    try {
                        startGame(2);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            gridBagPanel.add(button, c);
        }
        frame.add(gridBagPanel);
        frame.setVisible(true);
    }

    // to find winner for each round
    private int findWinner1() {
        int largestScore = playerInGame.get(0).getOldScore();

        for (int i = 1; i < playerInGame.size(); i++)
            if (playerInGame.get(i).getOldScore() > largestScore)
                largestScore = playerInGame.get(i).getOldScore();

        return largestScore;
    }

    // to find winner for each game(phase)
    private int findWinner2() {
        int tie = 0;
        int lowestScore = playerInGame.get(0).getNewScore();

        for (int i = 1; i < playerInGame.size(); i++)
            if (playerInGame.get(i).getNewScore() < lowestScore)
                lowestScore = playerInGame.get(i).getNewScore();

        // make sure not tie (tie > 1 means tie)
        for (Player thisPlayer : playerInGame)
            if (thisPlayer.getNewScore() == lowestScore)
                tie += 1;

        if (tie > 1)
            lowestScore = -1; //When tie return -1

        return lowestScore;
    }

    // put into queue which has been separated equally(all cards become string)
    public static LinkedList<String> stringCardsInQueue (LinkedList<Card> cards, int numberOfCards) {
        LinkedList<String> temp = new LinkedList<>();
        int i = 0;
        while (i < numberOfCards) {
            int count = 0;
            String cardsForARound = ""; // combine 5 cards into a String
            for (int j = i; j < numberOfCards; j++) {
                if (count == 0)
                    cardsForARound = cards.get(j).toString();
                else
                    cardsForARound = cardsForARound + " " + cards.get(j).toString();
                count++;
                i++;
                if (count == 5 || i == numberOfCards) break; // limit of a String in each queue
            }
            temp.add(cardsForARound);
        }
        cards.clear();
        return temp;
    }

    // before sort the cards we need to change String back to Card, after that return String
    public Queue<Card> sortCards(ArrayList<String> cardsBeforeSorting) throws IOException {
        Queue<Card> sortedCards = new LinkedList<>();

        for (String card : cardsBeforeSorting) { // for each player's cards
            ArrayList<Card> temp = new ArrayList<>();
            String[] split = card.split(" "); // split those 5 String
            for (String s : split) { // convert 5 String to Card
                char stringSuit = s.charAt(0);
                char stringFace = s.charAt(1);
                // to find card's photo
                for (int i = 0; i < Card.getAllCardsWithoutShuffle().size(); i++) {
                    String myCard = Card.getAllCardsWithoutShuffle().get(i).getSuit() + Card.getAllCardsWithoutShuffle().get(i).getFace();
                    if (s.equals(myCard)) {
                        Card newCard = new Card(String.valueOf(stringSuit), String.valueOf(stringFace), ImageIO.read(new File("cards/" + i + ".png")));
                        temp.add(newCard);
                        break;
                    }
                }
            }
            Collections.sort(temp); // sort it using compareTo from Card class
            for (Card aCard : temp) {
                sortedCards.add(aCard);
            }
        }
        return sortedCards;
    }

    public Queue<Card> toCard(Queue<String> cardString) throws IOException {
        Queue<Card> convertedCard = new LinkedList<>();

        for (String card : cardString) { // for each player's cards
            ArrayList<Card> temp = new ArrayList<>();
            String[] split = card.split(" "); // split those 5 String
            for (String s : split) { // convert 5 String to Card
                char stringSuit = s.charAt(0);
                char stringFace = s.charAt(1);
                // to find card's photo
                for (int i = 0; i < Card.getAllCardsWithoutShuffle().size(); i++) {
                    String myCard = Card.getAllCardsWithoutShuffle().get(i).getSuit() + Card.getAllCardsWithoutShuffle().get(i).getFace();
                    if (s.equals(myCard)) {
                        Card newCard = new Card(String.valueOf(stringSuit), String.valueOf(stringFace), ImageIO.read(new File("cards/" + i + ".png")));
                        temp.add(newCard);
                        break;
                    }
                }
            }
            for (Card aCard: temp) {
                convertedCard.add(aCard);
            }
        }
        return convertedCard;
    }
}