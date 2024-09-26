package com.rimdome;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple Tic Tac Toe game.
 */
public class TicTacToe {
    private static final Logger logger = Logger.getLogger(TicTacToe.class.getName());
    private static final char EMPTY = ' ';
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private static final int SIZE = 3;
    private char[][] board;
    private char currentPlayer;

    /**
     * Custom exception for invalid moves.
     */
    public static class InvalidMoveException extends Exception {
        public InvalidMoveException(String message) {
            super(message);
        }
    }

    /**
     * Initializes the game board and sets the starting player.
     */
    public TicTacToe() {
        board = new char[SIZE][SIZE];
        currentPlayer = PLAYER_X;
        initializeBoard();
    }

    /**
     * Initializes the game board with empty spaces.
     */
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    /**
     * Prints the current state of the game board.
     */
    private void printBoard() {
        System.out.println("Current board:");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]);
                if (j < SIZE - 1) System.out.print("|");
            }
            System.out.println();
            if (i < SIZE - 1) System.out.println("-----");
        }
    }

    /**
     * Checks if the game board is full.
     * @return true if the board is full, false otherwise.
     */
    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the current player has won the game.
     * @return true if the current player has won, false otherwise.
     */
    private boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                    (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }
        // Check diagonals
        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)) {
            return true;
        }
        return false;
    }

    /**
     * Switches the current player.
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
    }

    /**
     * Makes a move on the game board.
     * @param row the row index.
     * @param col the column index.
     * @throws InvalidMoveException if the move is invalid.
     */
    private void makeMove(int row, int col) throws InvalidMoveException {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || board[row][col] != EMPTY) {
            throw new InvalidMoveException("Invalid move. Try again.");
        }
        board[row][col] = currentPlayer;
    }

    /**
     * Runs the Tic Tac Toe game.
     */
    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameRunning = true;

        while (gameRunning) {
            printBoard();
            System.out.println("Player " + currentPlayer + ", enter your move (row and column) or 'q' to quit:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                logger.info("Game quit by player.");
                System.out.println("Game quit. Thanks for playing!");
                break;
            }

            try {
                String[] parts = input.split(" ");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                makeMove(row, col);

                if (checkWin()) {
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    logger.info("Player " + currentPlayer + " wins the game.");
                    gameRunning = false;
                } else if (isBoardFull()) {
                    printBoard();
                    System.out.println("The game is a tie!");
                    logger.info("The game ended in a tie.");
                    gameRunning = false;
                } else {
                    switchPlayer();
                }
            } catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
                logger.warning(e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter row and column numbers.");
                logger.warning("Invalid input: " + e.getMessage());
            }
        }
        scanner.close();
    }

    /**
     * Main method to start the game.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.play();
    }
}
