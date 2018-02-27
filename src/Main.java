// 1.1 :: Code cleanup

import java.util.*;

class Main {

    public static void main(String[] args) {
        // Scanner input = new Scanner(System.in);
        // String answer = introduceGame(input);
        // while (answer.toLowerCase().startsWith("y")) {
        Game game = new Game();
        //     System.out.print("Play again? y/n: ");
        //     answer = input.nextLine();
        // }
    }

    // post: this methods takes an input Scanner as a parameter and prints messages to the console
    private static String introduceGame(Scanner input) {
        System.out.println("Poker Assistant Thing");
        String answer = "dummy"; // dummy variable
        String sass = "";
        while (!answer.equals("y")) {
            printMenu(sass);
            answer = input.next().toLowerCase();
            if (answer.equals("x")) {
                System.out.println();
                printInstructions();
            }
        }
       // System.out.println();
        for (int i = 0; i < 75; i++) {
            System.out.print("-");
        }
        System.out.println();
        //System.out.println();
        return answer;
    }

    // prints the menu using the sassy parameter
    private static void printMenu(String sass) {
        System.out.println();
        System.out.println("MENU");
        System.out.println("---------------------------------------------------");
        System.out.println("- press 'y' then press 'enter' to start game");
        System.out.println("- press 'x' then press 'enter' to view instructions" + sass);
        System.out.println("---------------------------------------------------");
        System.out.print("your input: ");
    }

    private static void printInstructions() {
        System.out.println("INSTRUCTIONS");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("- when entering a card, format is (card value)(first letter of card suit)");
        System.out.println("- example: Ace of Spades = 'as', 2 of Clubs = '2c', 10 of Hearts = '10h'");
        System.out.println("---------------------------------------------------------------------------");
    }

    // prints stars to look pretty
    private static void printStars() {
        System.out.println();
        for (int i = 0; i < 4; i++) {
            System.out.println("*");
        }
        System.out.println();
    }

}
