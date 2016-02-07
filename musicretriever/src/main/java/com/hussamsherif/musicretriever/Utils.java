package com.hussamsherif.musicretriever;


public class Utils {
    public static String convertToReadableTime(long milliSeconds){
        double time = (double) milliSeconds /1000/60 ;
        int minutes = (int)time ;
        double tmp = (time - minutes) * 60 ;
        int seconds = (int) tmp;

        if (seconds < 10)
            return minutes + ":0" + seconds ;
        return minutes + ":" + seconds ;
    }
}
