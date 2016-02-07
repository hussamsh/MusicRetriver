package com.github.hussamsh.musicretriever.MusicClasses;

import java.util.ArrayList;

public class Playlist  {

    public static final int IDENTIFIER = 3;

    private String name ;
    private long playlistID ;
    private long dateAdded ;
    private long duration ;
    private ArrayList<Long> albumIDs ;
    private int songsCount;

    public Playlist(String name , long playlistID , int songsCount , long duration, long dateAdded , ArrayList<Long> albumIDs) {
        this.name = name;
        this.songsCount = songsCount;
        this.duration = duration;
        this.playlistID = playlistID;
        this.dateAdded = dateAdded;
        this.albumIDs = albumIDs;
    }

    public String getName() {
        return name;
    }

    public long getPlaylistID() {
        return playlistID;
    }

    public long getDuration() {
        return duration;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public ArrayList<Long> getAlbumIDs() {
        return albumIDs;
    }

    public int getSongsCount() {
        return songsCount;
    }
}
