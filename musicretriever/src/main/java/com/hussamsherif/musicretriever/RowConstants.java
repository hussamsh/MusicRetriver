package com.hussamsherif.musicretriever;

import android.provider.MediaStore;

public class RowConstants {

    public static final String ALBUM_NAME = MediaStore.Audio.Media.ALBUM;
    public static final String ARTIST_NAME = MediaStore.Audio.Media.ARTIST;
    public static final String TITLE = MediaStore.Audio.Media.TITLE;
    public static final String COMPOSER = MediaStore.Audio.Media.COMPOSER;
    public static final String ALBUM_ID = MediaStore.Audio.Media.ALBUM_ID;
    public static final String SONG_ID = MediaStore.Audio.Media._ID;
    public static final String ARTIST_ID = MediaStore.Audio.Media.ARTIST_ID;
    public static final String SONG_DURATION = MediaStore.Audio.Media.DURATION;
    public static final String DATE_ADDED = MediaStore.Audio.Media.DATE_ADDED;
    public static final String SIZE = MediaStore.Audio.Media.SIZE;
    public static final String YEAR = MediaStore.Audio.Media.YEAR;

    public static boolean checkIfExists(String argument){
        return argument.equals(ALBUM_NAME) || argument.equals(ARTIST_NAME) || argument.equals(TITLE)
                || argument.equals(ALBUM_ID) || argument.equals(SONG_ID) || argument.equals(ARTIST_ID)
                || argument.equals(SONG_DURATION) || argument.equals(DATE_ADDED) || argument.equals(SIZE)
                || argument.equals(YEAR) || argument.equals(COMPOSER);
    }
}
