package com.hussamsherif.musicretriever.MusicClasses;

public class Artist  {

    public static final int IDENTIFIER = 2 ;

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

    public String getName() {
        return name;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public int getNumberOfAlbums() {
        return numberOfAlbums;
    }

    public long getArtistID() {
        return artistID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Artist))
            return false;

        Artist that = (Artist) o ;
        return this.artistID == that.artistID && this.name.matches(that.name) &&
                this.numberOfAlbums == that.numberOfAlbums && this.numberOfSongs == that.numberOfSongs;
    }
}
