package com.hussamsherif.musicretriever;

public class Operator {

    public static final int LIKE = 1;
    public static final int EQUALS = 2;

    public static boolean checkIfExists(int argument){
        return argument == LIKE || argument == EQUALS ;
    }

}
