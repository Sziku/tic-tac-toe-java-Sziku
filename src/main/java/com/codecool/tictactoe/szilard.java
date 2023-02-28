package com.codecool.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class szilard {
    public static void main(String[] args) {
      /*  int currentPlayer = 1; // 0: Player 1 ("X"), 1: Player 2 ("O")
        ArrayList<String> moveSetList = new ArrayList<>();
        moveSetList.add("1");
        String[] currentMove = new String[1];
        currentMove[0] = "";
        String[][] currentGameDtatus = {{".", ".", "."}, {".", ".", "."}, {".", ".", "."}};
        String test1 = null;
        String test2 = "";
        String test3 = " ";
        String test4 = "AbC";
        String test5 = "123";
        String test6 = "A2";
        String test7 = "H6";
        String test8 = "k8";
        String test9 = "bc";
        String test10 = "14";*/

        //System.out.println(getHumanMove(moveSetList,currentPlayer,test1));
        //System.out.println(getHumanMove(moveSetList,currentPlayer,test2));
        //System.out.println(getHumanMove(moveSetList,currentPlayer,test3));
        //System.out.println(getHumanMove(moveSetList,currentPlayer,test4));
        //System.out.println(getHumanMove(moveSetList,currentPlayer,test5));
        //System.out.println(getHumanMove(moveSetList,currentPlayer,test6));
        //System.out.println(getHumanMove(moveSetList,currentPlayer,test7));
        //System.out.println(getHumanMove(moveSetList,currentPlayer,test8));
        // System.out.println(getHumanMove(moveSetList,currentPlayer,test9));
        // System.out.println(getHumanMove(moveSetList,currentPlayer,test10));
        //System.out.println(getHumanMove(moveSetList,currentPlayer,test8));
        //currentMove[0]=getHumanMove(moveSetList,currentPlayer);
        //System.out.println(currentMove[0]);

        //System.out.println(Arrays.deepToString(currentGameDtatus));
        // currentGameDtatus=mark(currentGameDtatus,currentPlayer, currentMove);
        //System.out.println(Arrays.deepToString(currentGameDtatus));

        ticTacToe();

    }


    public static void ticTacToe() {
        int gameMode = 0; // 0:PvP, 1:PvAI, 2:AIvAI
        int gameEnd = 0; //0:win, 1:tie, 2:quit
        int currentPlayer = 0; // 0: Player 1 ("X"), 1: Player 2 ("O")
        ArrayList<String> moveSetList = new ArrayList<>();
        moveSetList.add("1");
        String[] currentMove = new String[1];
        currentMove[0] = "";
        String[][] currentGameDtatus =  {{".", ".", "."}, {".", ".", "."}, {".", ".", "."}};
        boolean stop = true;

        enum State {
            INI, PRINT, MOVE, MARK, WON, FULL, END
        }

        State activeState = State.INI;
        while (stop) {
            switch (activeState) {
                case INI -> {
                    activeState = State.PRINT;
                }
                case PRINT -> {
                    printBoard(currentGameDtatus);
                    activeState = State.MOVE;
                }
                case MOVE -> {
                    currentMove[0] = getHumanMove(moveSetList, currentPlayer);
                    //System.out.println(currentMove[0]);
                    //System.out.println(moveSetList);
                    if (currentMove[0].equals("quit")) {
                        gameEnd = 2;
                        activeState = State.END;
                    } else if (currentMove[0].equals("used")) {
                        System.out.println("Used move, please give a new one");
                        activeState = State.MOVE;
                    } else if (currentMove[0].equals("wrong")) {
                        System.out.println("Wrong format. Correct format: Letter+Number");
                        activeState = State.MOVE;
                    } else {
                        activeState = State.MARK;
                    }
                }
                case MARK -> {
                    currentGameDtatus = mark(currentGameDtatus, currentPlayer, currentMove);
                    if (currentGameDtatus[0][0].equals("bound")) {
                        System.out.println("The move is out of bounds please give a new one");
                        currentGameDtatus[0][0]=".";
                        activeState = State.MOVE;
                    } else {
                        activeState = State.WON;
                    }
                }
                case WON -> {
                    activeState = State.FULL;
                }


                case FULL -> {

                    if (currentPlayer==0){
                        currentPlayer=1;
                    }else {
                        currentPlayer=0;
                    }

                    activeState = State.PRINT;
                }
                case END -> {
                    System.out.println("New game?");
                    Scanner newgame = new Scanner(System.in);
                    currentMove[0] = newgame.nextLine();
                    if (currentMove[0].equals("yes")) {
                        activeState = State.INI;
                    } else if (currentMove[0].equals("no")) {
                        stop = false;
                    } else {
                        System.out.println("Wrong input, please give a new one");
                        activeState = State.END;
                    }
                }
            }
        }
    }

            // Returns the coordinates of a valid move for player on board.
            public static String getHumanMove (ArrayList < String > moveSetList,int currentPlayer){
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
                            return "wrong";
                        } else {
                            return "wrong";
                        }
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
            public static String[][] mark (String[][]currentGameStatus,int currentPlayer, String[] currentMove){
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
                        return currentGameStatus;
                    }
                } else {
                    currentGameStatus[0][0] = "bound";
                    return currentGameStatus;
                }
                return currentGameStatus;
            }

    public static void printBoard(String[][] currentGameStatus) {

        StringBuilder sb = new StringBuilder();
        String rowLines = "|-----------------|";
        String columnLines = "  |  ";
        String frameColumn = "|  ";

        for (int i = 0; i < currentGameStatus.length; i++) {

            sb.append(rowLines);
            sb.append("\n");
            sb.append(frameColumn);
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



}
