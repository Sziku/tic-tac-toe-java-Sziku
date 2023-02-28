package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println(gameMode());
    }

    public static int gameMode(){
        System.out.println("Welcome to Tictactoe game!");
        System.out.println("Choose gamemode:");
        System.out.println("1: PvP");
        System.out.println("2: Player vs AI");
        System.out.println("3: AI vs AI");
        int gameMode;
        do{
            gameMode = readMode();
        }while(gameMode != 1 && gameMode != 2 && gameMode != 3);
        return gameMode;

    }

    public static int readMode(){
        Scanner scanner = new Scanner(System.in);
        int result;
        try{
           do {
               result = Integer.parseInt(scanner.nextLine());
               if(result != 1 && result != 2 && result != 3){
                   System.out.println("Invalid data, try again!");
               }
           }while(result != 1 && result != 2 && result != 3);


        }catch(Exception e){
            System.out.println("Invalid datatype, try again!" + e);
            result = -1;
        }
        return result;

    }
}
