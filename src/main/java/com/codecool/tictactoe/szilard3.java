package com.codecool.tictactoe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class szilard3 {
    public static void main(String[] args) {
        ticTacToe();
    }

    public static void ticTacToe() {
        int gameMode = 0; // 0:PvP, 1:PvAI, 2:AIvAI
        int gameEnd = 3; //0:win, 1:tie, 2:quit
        int currentPlayer = 0; // 0: Player 1 ("X"), 1: Player 2 ("O")
        ArrayList<String> moveSetList = new ArrayList<>();
        moveSetList.add("1");
        String[] currentMove = new String[1];
        currentMove[0] = "";
        String[][] currentGameDtatus = new String[3][3];
        boolean stop = true;
        int pvsAi = 0;
        String filePath = "C:\\Users\\sziku\\OneDrive\\Documents\\CodeCool\\projects\\tic-tac-toe-java-Sziku\\src\\main\\java\\com\\codecool\\tictactoe\\firstmove.txt";
        String filePath2 = "C:\\Users\\sziku\\OneDrive\\Documents\\CodeCool\\projects\\tic-tac-toe-java-Sziku\\src\\main\\java\\com\\codecool\\tictactoe\\aiwon.txt";
        System.out.println("Hello"/*+", world!"*/);

        enum State {
            INI, PRINT, MOVE, MARK, WON, FULL, END, AI
        }

        State activeState = State.INI;
        while (stop) {
            switch (activeState) {
                case INI -> {
                    currentMove[0] = menu();
                    if (currentMove[0].equals("quit")) {
                        gameEnd = 2;
                        activeState = State.END;
                    } else if (currentMove[0].equals("wrong")) {
                        System.out.println("Wrong game mode please give a new one");
                    } else {
                        gameMode = Integer.parseInt(currentMove[0]);
                        initBoard(currentGameDtatus);
                        currentPlayer = 0;
                        pvsAi = 0;
                        activeState = State.PRINT;
                    }

                }
                case PRINT -> {
                    printBoard(currentGameDtatus);

                    if (gameEnd == 0 || gameEnd == 1) {
                        activeState = State.END;
                    } else if (gameMode == 1) {
                        if (pvsAi == 0) {
                            pvsAi = 1;
                            activeState = State.MOVE;
                        } else {
                            pvsAi = 0;
                            activeState = State.AI;
                        }
                    } else if (gameMode == 2) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        activeState = State.AI;
                    } else {
                        activeState = State.MOVE;
                    }
                }
                case MOVE -> {
                    currentMove[0] = getHumanMove(moveSetList, currentPlayer);

                    switch (currentMove[0]) {
                        case "quit" -> {
                            gameEnd = 2;
                            activeState = State.END;
                        }
                        case "used" -> {
                            System.out.println("Used move, please give a new one");
                        }
                        case "wrong" -> {
                            System.out.println("Wrong format. Correct format: Letter+Number");
                        }
                        default -> activeState = State.MARK;
                    }
                }
                case MARK -> {
                    String temp = currentGameDtatus[0][0];
                    mark(currentGameDtatus, currentPlayer, currentMove);
                    if (currentGameDtatus[0][0].equals("bound")) {
                        int lostMove = moveSetList.size();
                        moveSetList.remove(lostMove - 1);
                        System.out.println("The move is out of bounds please give a new one");
                        currentGameDtatus[0][0] = temp;
                        activeState = State.MOVE;
                    } else {
                        activeState = State.WON;
                    }
                }
                case WON -> {
                    if (hasWon(currentGameDtatus, currentPlayer)) {
                        gameEnd = 0;
                        activeState = State.PRINT;
                    } else {
                        activeState = State.FULL;
                    }
                }
                case FULL -> {
                    if (isFull(currentGameDtatus)) {
                        gameEnd = 1;
                    } else {
                        if (currentPlayer == 0) {
                            currentPlayer = 1;
                        } else {
                            currentPlayer = 0;
                        }
                    }
                    activeState = State.PRINT;
                }
                case END -> {
                    printResult(currentPlayer, gameEnd);
                    writeToFile(filePath, moveSetList.get(1));
                    writeToFile(filePath, "\n");
                    if(gameEnd==0){
                        writeToFile(filePath2, String.valueOf(currentPlayer));
                        writeToFile(filePath2, "\n");
                    } else if (gameEnd==1) {
                        writeToFile(filePath2, "tie");
                        writeToFile(filePath2, "\n");
                    }

                    System.out.println("New game?\n (yes or no)");
                    Scanner newgame = new Scanner(System.in);
                    currentMove[0] = newgame.nextLine();

                    if (currentMove[0].equals("y")) {
                        gameEnd = 3;
                        moveSetList.clear();
                        moveSetList.add("1");
                        activeState = State.INI;
                    } else if (currentMove[0].equals("no")) {
                        System.out.println("Bye bye");
                        stop = false;
                    } else {
                        System.out.println("Wrong input, please give a new one");
                    }
                }
                case AI -> {
                    currentMove[0] = moveAi(moveSetList);
                    activeState = State.MARK;

                }
            }
        }
    }

    // Returns the coordinates of a valid move for player on board.
    public static String getHumanMove(ArrayList<String> moveSetList, int currentPlayer) {
        String name = "";

        if (currentPlayer == 0) {
            name += " Player 1";
        } else {
            name += "Player 2";
        }

        System.out.printf("Please give a move %s\n", name);
        Scanner scanner = new Scanner(System.in);
        String playerMove = scanner.next();

        if (playerMove != null && !playerMove.equals("")) {
            if (playerMove.length() == 2) {
                char[] chararray = playerMove.toCharArray();
                if ((chararray[0] >= 'a' && chararray[0] <= 'z') || (chararray[0] >= 'A' && chararray[0] <= 'Z')) {
                    if (chararray[1] >= '0' && chararray[1] <= '9') {
                        for (String x : moveSetList) {
                            if (x.equalsIgnoreCase(playerMove)) {
                                return "used";
                            }
                        }
                        moveSetList.add(playerMove);
                        return playerMove;
                    }
                }
                return "wrong";
            } else if (playerMove.equals("quit")) {
                return "quit";
            } else {
                return "wrong";
            }
        } else {
            return "wrong";
        }
    }

    // Marks the element at row & col on the board for player.
    public static void mark(String[][] currentGameStatus, int currentPlayer, String[] currentMove) {
        char[] chararray = currentMove[0].toCharArray();
        int row = 10;
        int collum = 10;
        if ((chararray[0] >= 'a' && chararray[0] <= 'c') || (chararray[0] >= 'A' && chararray[0] <= 'C')) {
            if (chararray[1] >= '0' && chararray[1] <= '2') {
                if (chararray[0] == 'a' || chararray[0] == 'A') {
                    row = 0;
                } else if (chararray[0] == 'b' || chararray[0] == 'B') {
                    row = 1;
                } else {
                    row = 2;
                }

                collum = Character.getNumericValue(chararray[1]);

                if (currentPlayer == 0) {
                    currentGameStatus[row][collum] = "X";
                } else {
                    currentGameStatus[row][collum] = "O";
                }
            } else {
                currentGameStatus[0][0] = "bound";
            }
        } else {
            currentGameStatus[0][0] = "bound";
        }
    }

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

    private static String menu() {
        System.out.println("Welcome to the game!\n Please choose game moode:\n 0:Player vs. Player, 1:Player vs. AI, 2:AI vs. AI \n Or write quit to exit the game");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        char c = answer.charAt(0);
        if (answer.equals("quit")) {
            return "quit";
        } else if (c >= '0' && c <= '2') {
            return answer;
        } else {
            return "wrong";
        }

    }


    // Returns an empty 3-by-3 board (with ".").
    public static void initBoard(String[][] currentGameStatus) {

        for (int i = 0; i < currentGameStatus.length; i++) {
            for (int j = 0; j < currentGameStatus.length; j++) {
                currentGameStatus[i][j] = ".";
            }
        }
    }

    public static boolean isFull(String[][] currentGameStatus) {
        for (String[] gameStatus : currentGameStatus) {
            for (int j = 0; j < currentGameStatus.length; j++) {
                if (gameStatus[j].equals(".")) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printResult(int currentPlayer, int gameEnd) {
        String name = "";
        if (currentPlayer == 0) {
            name += "Player 1";
        } else {
            name += "Player 2";
        }

        if (gameEnd == 2) {
            System.out.println("Sorry! Maybe next time.\n");
        } else if (gameEnd == 0) {
            System.out.printf("%s is win the game!\n", name);
        } else if (gameEnd == 1) {
            System.out.println("It is a tie!\n");
        } else {
            System.out.println("Wrong ending\n");
        }
    }

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

    public static String moveAi(List<String> moveSet) {
        System.out.println("The AI is moved");
        List<String> allMoveSet = new ArrayList<>() {{
            add("a0");
            add("a1");
            add("a2");
            add("b0");
            add("b1");
            add("b2");
            add("c0");
            add("c1");
            add("c2");
        }};

        List<String> avaibeleMoveSet = new ArrayList<>();

        for (String s : allMoveSet) {
            for (int j = 0; j < moveSet.size(); j++) {
                if (s.equalsIgnoreCase(moveSet.get(j))) {
                    break;
                } else if (j == moveSet.size() - 1) {
                    avaibeleMoveSet.add(s);
                }
            }
        }
        int max = avaibeleMoveSet.size();
        int min = 1;
        int range = max - min + 1;
        int rand = (int) (Math.random() * range) + min;
        String aimove = "";
        aimove = avaibeleMoveSet.get(rand - 1);
        moveSet.add(aimove);
        return aimove;
    }
    private static void writeToFile(String filePath, String content){
        try {
            Files.write(Path.of(filePath), content.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
