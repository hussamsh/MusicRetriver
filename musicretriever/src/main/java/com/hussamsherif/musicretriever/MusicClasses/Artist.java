package com.hussamsherif.musicretriever.MusicClasses;

public class Artist  {

    private String name ;
    private long artistID ;
    private int numberOfSongs ;
    private int numberOfAlbums ;

    public Artist(String name, long artistID , int numberOfSongs, int numberOfAlbums) {
        this.name = name;
        this.artistID = artistID ;
        this.numberOfSongs = numberOfSongs;
        this.numberOfAlbums = numberOfAlbums;
    }

    /**
     * @return The name of the artist, if any
     */
    public String getName() {
        return name;
    }
    /**
     * @return Number of songs created by this artist
     */
    public int getNumberOfSongs() {
        return numberOfSongs;
    }
    /**
     * @return Number of albums this artist has created
     */
    public int getNumberOfAlbums() {
        return numberOfAlbums;
    }
    /**
     * @return The unique id of the artist
     */
    public long getArtistID() {
        return artistID;
    }

}
