/*
 * This class represents a card with a suit and number value
 */

class Card implements Comparable<Card> {

    private final int suit;
    private final int value;

    // takes an int for the card value and a char for the card suit
    // post: constructs a new Card based on the parameters
    public Card(int value, int suit) {
        if (value < 2 || value > 14 || suit < 0 || suit > 3) {
            throw new IllegalArgumentException("invalid card");
        }
        this.value = value;
        this.suit = suit;
    }

    // post: returns an int representing the card suit if it is known. Else it returns -1
    public int getSuitInt() {
        return this.suit;
    }

    // post: returns the value of the card if it is known, else it returns -1
    public int getValue() {
        return this.value;
    }

    // post: returns a String representation of the Card object
    public String toString() {
        String temp = this.value + "";
        if (this.value == 11) {
            temp = "Jack";
        } else if (this.value == 12) {
            temp = "Queen";
        } else if (this.value == 13) {
            temp = "King";
        } else if (this.value == 14) {
            temp = "Ace";
        }
        return temp + " of " + getSuitName();
    }

    // post: takes a Card parameter, returns whether or not they are equal
    public int compareTo(Card other) {
        if (other == null) {
            return 1;
        } else {
            int valueComp = Integer.compare(this.value, other.value);
            if (valueComp == 0) {
                return Integer.compare(this.getSuitInt(), other.getSuitInt());
            } else {
                return valueComp;
            }
        }
    }

    // post: returns true if the cards are equal, false otherwise
    public boolean equals(Card other) {
        return this.compareTo(other) == 0;
    }

    // post: returns a String of the suit based on the int of the card
     private String getSuitName() {
        String result;
        if (this.suit == 0) {
            result = "Clubs";
        } else if (this.suit == 1) {
            result = "Diamonds";
        } else if (this.suit == 2) {
            result = "Hearts";
        } else {
            result = "Spades";
        }
        return result;
    }

    // returns true if the parameter char is a valid suit, else returns false
    private boolean isValidSuit(char suit) {
        return (suit != 'H' && suit != 'D' && suit != 'S' && suit != 'C');
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
}
