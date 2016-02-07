package com.github.hussamsh.musicretriever;

public class QueryCondition {

    String whereCondition ;
    String argument;

    public QueryCondition(String whereCondition ,String argument){
        this.whereCondition = whereCondition;
        this.argument = argument;
    }


}