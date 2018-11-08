package games.tictactoe;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class TicTacToe { //TODO: Possibly implement an interface for scores and GridInput
    private char OXChoice;
    private boolean firstTurn;
    private char board[][];
    /**
     * @see TicTacToe#gameEnded()
     **/
    private char winnerChar = ' ';
    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static void launch() {
        do {
            TicTacToe obj = new TicTacToe();
            String tempInput;
            //TODO: Decide design for startup of game
            System.out.println("What do you want to play as, O or X?\n" +
                    "Enter choice: ");
            for (boolean invalidInput = true; invalidInput; ) {
                obj.OXChoice = Character.toUpperCase(scanner.next().charAt(0));
                if (obj.OXChoice == 'O' || obj.OXChoice == 'X')
                    invalidInput = false;
                else
                    System.out.println("Only O or X allowed for input!!\nEnter choice: ");
            }
            System.out.println("Do you want to play first?");
            tempInput = scanner.next().toUpperCase();
            obj.firstTurn = tempInput.equals("YES") || tempInput.equals("Y");
            obj.startGame();
            System.out.println("Enter 'y' to play again, any other key will exit!"); //TODO: UX
        } while (scanner.next().equals("y"));
    }

    private void startGame() {
        do {
            if (firstTurn) getPlayerInput();
            else getComputerInput();

            if (gameEnded()) break;

            if (firstTurn) getComputerInput();
            else getPlayerInput();
        } while (!gameEnded());
        System.out.println("Game Ended");
        showBoard();
        if (winnerChar == ' ')
            System.out.println("It's a tie!");
        else if (winnerChar == OXChoice)
            System.out.println("Player won");
        else
            System.out.println("Computer won");
        //TODO: End Game Scoring
    }

    private boolean gameEnded() {
        winnerChar = ' ';   //No winner decided (TIE condition)
        for (int i = 0; i < 3; i++) {
            if (!Character.isDigit(board[i][0]) && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                winnerChar = board[i][0];
                return true;
            }
            if (!Character.isDigit(board[0][i]) && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                winnerChar = board[0][i];
                return true;
            }
        }

        if (!Character.isDigit(board[0][0]) && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            winnerChar = board[0][0];
            return true;
        }

        if (!Character.isDigit(board[0][2]) && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            winnerChar = board[0][2];
            return true;
        }

        return boardFilled();
    }

    private void getPlayerInput() {
        showBoard();
        boolean inputIssue;
        int index;
        do {
            inputIssue = false;
            System.out.println("Enter position: ");
            index = scanner.nextInt() - 1;
            if (index < 0 || index > 8) {
                System.out.println("Unknown Choice");
                inputIssue = true;
            } else if (!Character.isDigit(board[(index) / 3][index % 3])) {
                System.out.println("Already filled!!");
                inputIssue = true;
            }
        } while (inputIssue);
        board[index / 3][index % 3] = OXChoice;
    }

    private void getComputerInput() { //TODO: Better logic
        int index;
        System.out.println("Computer playing move..");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        do {
            index = ThreadLocalRandom.current().nextInt(0, 9);
        } while (!Character.isDigit(board[index / 3][index % 3]));
        board[index / 3][index % 3] = OXChoice == 'O' ? 'X' : 'O';
    }

    private void showBoard() {
        //TODO: Proper design
        System.out.print(" \u2554\u2550\u2550\u2550\u2566\u2550\u2550\u2550\u2566\u2550\u2550\u2550\u2557 ");
        for (char i[] : board) {
            System.out.println("\n \u2551   \u2551   \u2551   \u2551"); //FIRST LINE OF A ROW

            System.out.print(" \u2551");
            for (char j : i)
                System.out.print(" " + j + " \u2551");//TODO: Parentheses for each

            System.out.println("\n \u2551   \u2551   \u2551   \u2551"); //THIRD LINE OF A ROW
            System.out.print(" \u2560\u2550\u2550\u2550\u256C\u2550\u2550\u2550\u256C\u2550\u2550\u2550\u2563 ");
        }
        System.out.println("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b \u255A\u2550\u2550\u2550\u2569\u2550\u2550\u2550\u2569\u2550\u2550\u2550\u255D");
    }

    private boolean boardFilled() {
        for (char i[] : board)
            for (char j : i)
                if (Character.isDigit(j))
                    return false;
        return true;
    }

    private TicTacToe() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = (char) (i * 3 + j + 49);
    }
}
