import java.util.*;

public class Hand implements Comparable<Hand> {

    // class constants
    public static final int MAX_SIZE = 7; // change to 5
    private int size;
    private List<Card> inHand;

    // post: constructs a new Hand object
    public Hand() {
        this.size = 0;
        this.inHand = new ArrayList<Card>(7);
    }

    // copy constructor
    public Hand(List<Card> other) {
        this.inHand = new ArrayList<Card>(other);
        this.size = this.inHand.size();
    }

    public Hand(Hand other) {
        this(other.inHand);
    }

    // pre: if the hand already has 5 cards, throws IllegalArgumentException
    // post: adds the parameter Card to the Hand object
    public void add(Card card) {
        if (this.size == MAX_SIZE) {
            throw new IllegalArgumentException("hand is full");
        }
        this.size++;
        this.inHand.add(card);
    }

    // post: returns the number of cards in the hand
    public int getSize() {
        return this.size;
    }

    // returns a List of the Hand in this object. NEED TO ADD COPY FOR HANDS.
    public List<Card> getHand() {
        return new ArrayList<Card>(this.inHand);
    }

    // post: returns true if full, false otherwise
    public boolean isFull() {
        return this.size == MAX_SIZE;
    }

    // post: returns true if empty, false otherwise
    public boolean isEmpty() {
        return this.size == 0;
    }

    // post: removes the card from the hand if it is in the and,
    //       otherwise does nothing.
    public void remove(Card c) {
        if (this.inHand.remove(c)) {
            this.size--;
        }
    }

    // returns true if there is a pair
    public boolean hasPair() {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = i + 1; j < this.getSize(); j++) {

                if (this.inHand.get(i).getValue() == this.inHand.get(j).getValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    // returns true if there is a two pair
    public boolean hasTwoPair() {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = i + 1; j < this.getSize(); j++) {
                if (this.inHand.get(i).getValue() == this.inHand.get(j).getValue()) {
                    return hasTwoPair(this.inHand.get(i).getValue());
                }
            }
        }
        return false;
    }

    // returns true if there are two pairs in the hand
    private boolean hasTwoPair(int except) {
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = i + 1; j < MAX_SIZE; j++) {
                if (this.inHand.get(i).getValue() != except && this.inHand.get(i).getValue() ==
                        this.inHand.get(j).getValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    // returns true if there is a triple
    public boolean hasTriple() {
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = i + 1; j < MAX_SIZE; j++) {
                for (int k = j + 1; k < MAX_SIZE; k++) {
                    if (this.inHand.get(i).getValue() == this.inHand.get(j).getValue() &&
                            this.inHand.get(j).getValue() == this.inHand.get(k).getValue()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // returns true if there is a straight, FIX THIS
    public boolean hasStraight() {
        SortedSet<Card> temp = new TreeSet<Card>(this.inHand);
        Object[] values = temp.toArray(); //HERE NOW
        int streak = 0;
        for (int i = 1; i < values.length; i++) {
            int prev = ((Card) values[i - 1]).getValue();
            int cur = ((Card) values[i]).getValue();
            if (cur - prev == 1) {
                streak++;
                if (streak == 5) {
                    return true;
                }
            } else if (cur - prev != 0) {
                streak = 0;
            }
        }
        return false;
    }

    // compares two Hand objects based on the rules of Poker
    public int compareTo(Hand other) {
        if (this.getSize() != other.getSize()) {
            return 1;
        } else {
            for (Card thisCard : this.getHand()) {
                boolean found = false;
                for (Card otherCard : other.getHand()) {
                    if (thisCard.equals(otherCard)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return 1;
                }
            }
            return 0;
        }
    }

    // returns true if the values are the same, regardless of suit, and false otherwise
    public boolean equalsValuesOnly(Hand other) {
        if (other == null || this.getSize() != other.getSize()) {
            return false;
        } else {
            for (Card thisCard : this.getHand()) {
                boolean found = false;
                for (Card otherCard : other.getHand()) {
                    if (thisCard.getValue() == otherCard.getValue()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            for (Card thisCard : other.getHand()) {
                boolean found = false;
                for (Card otherCard : this.getHand()) {
                    if (thisCard.getValue() == otherCard.getValue()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            return true;
        }
    }

    // returns true if the hands are exactly equal, false otherwise
    public boolean equals(Hand other) {
        return this.compareTo(other) == 0;
    }

    // returns string representing the Hand
    public String toString() {
        return this.inHand.toString();
    }

    // returns true if the hand contains the card, false otherwise
    public boolean containsCard(Card other) {
        for (Card thisCard : this.getHand()) {
            if (other.equals(thisCard)) {
                return true;
            }
        }
        return false;
    }

    // returns the hashcode for a Hand
    public int hashCode() {
        int hashCode = 0;
        for (int i = 0; i < this.size; i++) {
            Card curCard = this.getHand().get(i);
            int val = curCard.getValue();
            int suit = curCard.getSuitInt() + 1;
            hashCode += hashCodeMath(val, suit);
        }
        return hashCode;
    }

    // returns the secret formula to my hashCode functions
    public static int maxFormula() {
        int result = 0;
        int suit = 4;
        int val = 14;
        for (int i = 0; i < MAX_SIZE; i++) {
            result += hashCodeMath(val, suit);
        }
        return result;
    }

    // post: returns an int based on a formula for the hashCode function to use
    private static int hashCodeMath(int val, int suit) {
        return ((val * 307) + (suit * 97) + (val * suit * 93) + ((val * 7) % suit * 43));
        //return ((val * 37) + (suit * 17) + (val * suit * 43) + ((val * 7) % suit * 23)); // debugging
    }
}
