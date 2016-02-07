package com.github.hussamsh.musicretriever;

import android.provider.MediaStore;

public class RowConstants {

    public static final String ALBUM_NAME = MediaStore.Audio.Media.ALBUM;
    public static final String ARTIST_NAME = MediaStore.Audio.Media.ARTIST;
    public static final String TITLE = MediaStore.Audio.Media.TITLE;
    public static final String ALBUM_ID = MediaStore.Audio.Media.ALBUM_ID;
    public static final String SONG_ID = MediaStore.Audio.Media._ID;
    public static final String ARTIST_ID = MediaStore.Audio.Media.ARTIST_ID;
    public static final String SONG_DURATION = MediaStore.Audio.Media.DURATION;
    public static final String DATE_ADDED = MediaStore.Audio.Media.DATE_ADDED;

    public static final String[] SONG_COLUMNS = {SONG_ID, TITLE, ALBUM_ID, ALBUM_NAME, ARTIST_ID, ARTIST_NAME, SONG_DURATION, DATE_ADDED};
    public static final String[] ALBUM_COLUMNS = {ALBUM_NAME, ALBUM_ID, ARTIST_NAME, ARTIST_ID};
    public static final String[] ARTIST_COLUMNS = {ARTIST_NAME, ARTIST_ID, ALBUM_ID};

    public static boolean checkIfExists(String argument){
        return argument.equals(ALBUM_NAME) || argument.equals(ARTIST_NAME) || argument.equals(TITLE)
                || argument.equals(ALBUM_ID) || argument.equals(SONG_ID) || argument.equals(ARTIST_ID)
                || argument.equals(SONG_DURATION) || argument.equals(DATE_ADDED);
    }
}
