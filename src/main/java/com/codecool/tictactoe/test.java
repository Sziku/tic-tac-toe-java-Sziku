package com.codecool.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        List<String> movesetlist = new ArrayList<>(){{
            add("a1");
            add("a0");
            add("c2");
            add("b1");
        }};

        moveAi(movesetlist);

    }

    public static String moveAi (List<String> moveSet){
        List<String> allMoveSet = new ArrayList<>(){{
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

        List<String> avaibeleMoveSet =new ArrayList<>();

        for (int i = 0; i < allMoveSet.size(); i++) {
            for (int j = 0; j < moveSet.size(); j++) {
                if(allMoveSet.get(i).equalsIgnoreCase(moveSet.get(j))){
                    break;
                }
                else if(j== moveSet.size()-1){
                    avaibeleMoveSet.add(allMoveSet.get(i));
                }
            }
        }

        System.out.println(avaibeleMoveSet);

        int max = allMoveSet.size();;
        int min = 1;
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;

        return avaibeleMoveSet.get(rand);


    }
}
