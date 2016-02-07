package com.hussamsherif.musicretriever.MusicClasses;

import com.hussamsherif.musicretriever.AlbumArtColor;

public class Album  {

    public static final int IDENTIFIER = 1 ;

    private String name ;
    private String artistName;
    private long albumId;
    private long artistId ;
    private int numberOfSongs ;
    private AlbumArtColor albumArtColor ;


    public Album(String name, String artist ,long albumId, long artistId , int numberOfSongs , AlbumArtColor albumArtColor) {
        this.name = name;
        this.artistName = artist ;
        this.albumId = albumId;
        this.artistId = artistId ;
        this.numberOfSongs = numberOfSongs;
        this.albumArtColor = albumArtColor;
    }

    public String getName() {
        return name;
    }

    public long getAlbumId() {
        return albumId;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public String getArtistName() {
        return artistName;
    }

    public long getArtistId(){
        return artistId ;
    }

    public AlbumArtColor getAlbumArtColor() {
        return albumArtColor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Album))
            return false;

        Album that = (Album) o ;
        return this.albumId == that.albumId && this.name.matches(that.name) && this.artistId == that.artistId;
    }
}
