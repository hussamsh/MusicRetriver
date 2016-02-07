package com.hussamsherif.musicretriever.MusicClasses;

public class Album  {

    private String name ;
    private String artistName;
    private long albumId;
    private long artistId ;
    private int numberOfSongs ;

    public Album(String name, String artist ,long albumId, long artistId , int numberOfSongs) {
        this.name = name;
        this.artistName = artist ;
        this.albumId = albumId;
        this.artistId = artistId ;
        this.numberOfSongs = numberOfSongs;
    }

    /**
     * @return The name of the album, if any
     */
    public String getName() {
        return name;
    }
    /**
     * @return The unique id of the album
     */
    public long getAlbumId() {
        return albumId;
    }
    /**
     * @return Number of songs contained in the album
     */
    public int getNumberOfSongs() {
        return numberOfSongs;
    }
    /**
     * @return Artist Name who created the album
     */
    public String getArtistName() {
        return artistName;
    }
    /**
     * @return The unique id of the artist who created the albuum
     */
    public long getArtistId(){
        return artistId ;
    }
}
