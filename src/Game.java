// VERSION WITH UPDATED GENERATE HANDS

import java.util.*;

class Game {

    // the cards you are initially dealt
    private static final int START_HAND_COUNT = 2;
    private static final double LOAD_FACTOR = .2;

    private List<Card> allCards; // stores the cards that haven't been played
    private Hand myHand; // the hand of this object
    private long pairCount;
    private long tripleCount;
    private long twoPairCount;
    private long straightCount;
    private int handCount;
    private List<List<Hand>> possibleHands; // stores the potential hands of opponents
    private int currentLoad;
    private int maxCollisions;


    // post: the main method that controls high level game operation
    public Game() {
        this.myHand = new Hand();
        Scanner input = new Scanner(System.in);
        // setupGamePrompt(input);
        for (int i = 0; i < START_HAND_COUNT; i++) { // loop gives first 2 cards
            promptNextCard(input);
        }
        System.out.println("---------------");
        generateHands();
        printResults();
        resetCounts();
        while (!this.myHand.isFull()) { // loop gives rest of the cards
            promptNextCard(input);
            System.out.println("---------------");
            if (this.myHand.getHand().size() >= 2) {
                generateHands();
                printResults();
                resetCounts();
            }
        }
    }

    // post: returns the next prime number after the parameter one
    private static int nextPrime(int prevPrime) {
        int nextPrime = prevPrime;
        boolean found = false;
        while (!found) {
            nextPrime++;
            found = true;
            for (int i = nextPrime - 1; i >= 2; i--) {
                if (nextPrime % i == 0) {
                    found = false;
                }
            }
        }
        return nextPrime;
    }

    // asks the user for the next card
    private void promptNextCard(Scanner input) {
        String val = "x"; // dummy variable
        char suit = ' '; // dummy variable
        while (!isValidValue(val) || !isValidSuit(suit)) {
            String label;
            if (this.myHand.getSize() < 2) {
                label = "your hand:";
            } else {
                label = "on table:";
            }
            System.out.print(label + " card #" + (this.myHand.getSize() + 1) + ": ");
            String valSuit = input.next().toUpperCase();
            val = valSuit.substring(0, valSuit.length() - 1);
            suit = valSuit.charAt(valSuit.length() - 1);
        }
        Card temp = new Card((cardValue(val)), suitValue(suit));
        if (!this.myHand.containsCard(temp)) {
            this.myHand.add(temp);
            // removeFromCardList(temp); // gets the card out of possibilities
            System.out.println("current hand: " + this.myHand.toString());
        } else {
            System.out.println("that card is in your hand lol"); // put this back later
            promptNextCard(input);
        }
    }

    // asks the user how many people are playing and prints a welcome message
    private void setupGamePrompt(Scanner input) {
        System.out.println("Rate My Hand");
        System.out.println();
        int players = 0; // fencepost
        while (players < 1) { // change back to <= 1
            System.out.print("How many players? ");
            players = input.nextInt();
        }
        //int totalPlayers = players;
    }

    // sets the cards up for the game, not including cards in hand
    private void initializeAllCards() {
        this.allCards = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) { // controls the suit
            for (int j = 0; j < 13; j++) { // controls the value
                this.allCards.add(new Card(j + 2, i));
            }
        }
        for (Card cur : this.myHand.getHand()) {
            for (int i = 0; i < this.allCards.size(); i++) {
                Card other = this.allCards.get(i);
                if (cur.equals(other)) {
                    this.allCards.remove(i);
                    i--;
                }
            }
        }
    }

    // post: updates the state to contain all possible hands
    private void generateHands() {
        int maxTableSize = Hand.maxFormula() + 1;
        this.possibleHands = new ArrayList<List<Hand>>(maxTableSize);
        // loop gives the hashtable the appropriate size
        for (int i = 0; i <= maxTableSize; i++) {
            this.possibleHands.add(null);
        }
        List<Card> curCards = this.myHand.getHand();
        initializeAllCards();
        generateHands(curCards, new LinkedList<Card>(this.allCards), this.myHand.getSize());
    }

    // post: helper method to generate all possible hands
    private void generateHands(List<Card> curHand, List<Card> remainingCards, int curIndex) {
        if (curHand.size() == Hand.MAX_SIZE) {
            addToTable(new Hand(curHand));
        } else {
            while (!remainingCards.isEmpty()) {
                // take card from deck, add to hand
                Card curCard = remainingCards.remove(0);
                curHand.add(curIndex, curCard);
                generateHands(curHand, new LinkedList<Card>(remainingCards), curIndex + 1);
                curHand.remove(curIndex);
            }
        }
    }

    // post: gets chances of card combos
    private void analyzeHand(Hand hand) {
        this.handCount++;
        if (hand.hasPair()) {
            this.pairCount++;
            if (hand.hasTriple()) {
                this.tripleCount++;
            }
            if (hand.hasTwoPair()) {
                this.twoPairCount++;
            }
        }
        if (hand.hasStraight()) {
            this.straightCount++;
        }
    }

    // returns true if the parameter char is a valid suit, else returns false
    private boolean isValidSuit(char suit) {
        return (suit == 'H' || suit == 'D' || suit == 'S' || suit == 'C');
    }

    // post: returns true if the String parameter is a valid card value, else returns false
    private boolean isValidValue(String val) {
        for (int i = 2; i <= 10; i++) {
            if (val.equals(i + "")) {
                return true;
            }
        }
        return (val.equals("J") || val.equals("Q") || val.equals("K") || val.equals("A"));
    }

    // pre:  parameter char must be a valid suit, else throws new IllegalArgumentException.
    // post: returns the int value of the parameter char.
    private int suitValue(char suit) {
        if (suit == 'C') {
            return 0;
        } else if (suit == 'D') {
            return 1;
        } else if (suit == 'H') {
            return 2;
        } else {  // 'S'
            return 3;
        }
    }

    // post: returns the int of the String parameter passed.
    private int cardValue(String value) {
        int n = 10;
        if (value.length() == 1) {
            char test = value.charAt(0);
            switch (test) {
                case 'J':
                    n = 11;
                    break;
                case 'Q':
                    n = 12;
                    break;
                case 'K':
                    n = 13;
                    break;
                case 'A':
                    n = 14;
                    break;
                default:
                    n = Integer.parseInt(test + "");
                    break;
            }
        }
        return n;
    }

    // post: adds the value to the hash table
    private void addToTable(Hand hand) {
        int code = hand.hashCode();
        if (this.possibleHands.get(code) == null) {
            List<Hand> tempList = new LinkedList<Hand>();
            this.possibleHands.set(code, tempList);
        }
        if (!isInTable(hand)) {
            analyzeHand(hand);
            this.possibleHands.get(code).add(0, hand);

            trackCollisions(code); // debugging

            this.currentLoad++;
           // System.out.println(this.currentLoad + " || " + this.maxCollisions); // debugging
        }
    }

    // post: keeps track of the number of collisions in the hash table
    private void trackCollisions(int code) {
        int size = this.possibleHands.get(code).size();
        if (size > this.maxCollisions) {
            this.maxCollisions = size;
        }
    }

    // post: returns true if the hand is already in the hashtable, false otherwise
    private boolean isInTable(Hand hand) {
        List<Hand> list = this.possibleHands.get(hand.hashCode());
        if (list != null) {
            for (Hand curHand : list) {
                if (curHand.equals(hand)) {
                    return true;
                }
            }
        }
        return false;
    }

    // post: prints the results of the current hand
    private void printResults() {
        System.out.println("2: " + 100 * ((double) this.pairCount / this.handCount));
        System.out.println("2p: " + 100 * ((double) this.twoPairCount / this.handCount));
        System.out.println("3: " + 100 * ((double) this.tripleCount / this.handCount));
        System.out.println("straight: " + 100 * ((double) this.straightCount / this.handCount));
        System.out.println("remaining possibilities: " + this.handCount);
    }

    // post: resets the counts of all card combinations
    private void resetCounts() {
        this.pairCount = 0;
        this.twoPairCount = 0;
        this.tripleCount = 0;
        this.straightCount = 0;
        this.currentLoad = 0;
        this.handCount = 0;
        this.maxCollisions = 0; // debugging
        this.currentLoad = 0; // debugging
    }

}
/*      no index:
        2: 77.67146821725916
        2p: 25.235137533274177
        3: 6.83569635069569
        straight: 0.9666031074779587
        remaining possibilities: 2118760

        2: 77.67146821725916
        2p: 25.235137533274177
        3: 6.83569635069569
        straight: 0.9666031074779587
        remaining possibilities: 2118760
        */