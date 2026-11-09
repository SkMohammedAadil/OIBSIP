import java.util.Random;
import java.util.Scanner;

/**
 * Oasis Infobyte Java Development - Task 2
 * Number Guessing Game
 */
public class NumberGuessingGame {
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100;
    private static final int MAX_ATTEMPTS = 7;

    private final Scanner scanner;
    private final Random random;
    private int totalScore;
    private int roundNumber;

    public NumberGuessingGame() {
        scanner = new Scanner(System.in);
        random = new Random();
        totalScore = 0;
        roundNumber = 1;
    }

    public static void main(String[] args) {
        new NumberGuessingGame().start();
    }

    private void start() {
        System.out.println("====================================");
        System.out.println("        NUMBER GUESSING GAME");
        System.out.println("====================================");

        boolean playAgain;
        do {
            playRound();
            playAgain = askToPlayAgain();
        } while (playAgain);

        System.out.println("\nThanks for playing!");
        System.out.println("Rounds played: " + (roundNumber - 1));
        System.out.println("Final score: " + totalScore);
        scanner.close();
    }

    private void playRound() {
        int secretNumber = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
        int attemptsUsed = 0;
        boolean won = false;

        System.out.println("\n--- Round " + roundNumber + " ---");
        System.out.println("I have selected a number from " + MIN_NUMBER + " to " + MAX_NUMBER + ".");
        System.out.println("You have " + MAX_ATTEMPTS + " valid attempts.");

        while (attemptsUsed < MAX_ATTEMPTS) {
            System.out.print("Enter your guess: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a whole number.");
                scanner.next();
                continue;
            }

            int guess = scanner.nextInt();
            if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
                System.out.println("Please enter a number between " + MIN_NUMBER + " and " + MAX_NUMBER + ".");
                continue;
            }

            attemptsUsed++;

            if (guess == secretNumber) {
                won = true;
                int pointsEarned = (MAX_ATTEMPTS - attemptsUsed + 1) * 10;
                totalScore += pointsEarned;
                System.out.println("Correct! You guessed the number.");
                System.out.println("Attempts used: " + attemptsUsed);
                System.out.println("Points earned: " + pointsEarned);
                break;
            } else if (guess > secretNumber) {
                System.out.println("Too High!");
            } else {
                System.out.println("Too Low!");
            }

            System.out.println("Attempts remaining: " + (MAX_ATTEMPTS - attemptsUsed));
        }

        if (!won) {
            System.out.println("You Lost! The correct number was " + secretNumber + ".");
        }

        System.out.println("Current total score: " + totalScore);
        roundNumber++;
    }

    private boolean askToPlayAgain() {
        while (true) {
            System.out.print("\nWould you like to play again? (yes/no): ");
            String answer = scanner.next().trim();
            if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                return true;
            }
            if (answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Please enter yes or no.");
        }
    }
}
