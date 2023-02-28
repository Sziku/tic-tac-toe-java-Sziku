package com.codecool.tictactoe;

public class roland {
    public static void main(String[] args) {

    }

    // Prints a 3-by-3 board on the screen with borders.
    public static void printBoard(String[][] currentGameStatus) {

        StringBuilder sb = new StringBuilder();
        String rowLines = "  |-----------------|";
        String columnLines = "  |  ";
        String letters = "     A     B     C  ";

        System.out.println(letters);
        for (int i = 0; i < currentGameStatus.length; i++) {

            sb.append(rowLines);
            sb.append("\n");
            sb.append(i + " |  ");
            for (int j = 0; j < currentGameStatus[i].length; j++) {
                sb.append(currentGameStatus[i][j] + columnLines);
                if (j == 2) {
                    sb.append("\n");
                }
            }
        }
        sb.append(rowLines);
        System.out.println(sb);
    }
    // Returns True if player has won the game.
    public static boolean hasWon(String[][] currentGameStatus, int currentPlayer) {
        boolean hasWon = false;
        String playerMark;

        if (currentPlayer == 0) {
            playerMark = "X";
        } else {
            playerMark = "O";
        }

        for (int i = 0; i < currentGameStatus.length; i++) {
            if (currentGameStatus[i][0].equals(playerMark) && currentGameStatus[i][1].equals(playerMark) && currentGameStatus[i][2].equals(playerMark)) {
                hasWon = true;
                break;
            } else if (currentGameStatus[0][0].equals(playerMark) && currentGameStatus[1][1].equals(playerMark) && currentGameStatus[2][2].equals(playerMark)) {
                hasWon = true;
                break;
            } else if (currentGameStatus[0][2].equals(playerMark) && currentGameStatus[1][1].equals(playerMark) && currentGameStatus[2][0].equals(playerMark)) {
                hasWon = true;
                break;
            } else if (currentGameStatus[0][i].equals(playerMark) && currentGameStatus[1][i].equals(playerMark) && currentGameStatus[2][i].equals(playerMark)) {
                hasWon = true;
                break;
            } else {
                hasWon = false;
            }
        }
        return hasWon;
    }

    // Returns True if board is full.
    // Returns True if board is full.
    public static boolean isFull(String[][] currentGameStatus) {
        String dot = ".";
        boolean isFull = true;
        for (int i = 0; i < currentGameStatus.length; i++) {
            for (int j = 0; j < currentGameStatus[i].length; j++) {
                if (currentGameStatus[i][j].equals(dot)) {
                    isFull = false;
                }
            }
        }
        return isFull;
    }

}
