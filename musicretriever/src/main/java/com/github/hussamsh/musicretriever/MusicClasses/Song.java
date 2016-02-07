package com.github.hussamsh.musicretriever.MusicClasses;

import com.github.hussamsh.musicretriever.Utils;

public class Song {

    public static final int IDENTIFIER = 0 ;

    private long songId;
    private String title ;
    private String artist ;
    private String album ;
    private long songDuration ;
    private long albumId ;
    private long artistId ;
    private long dateAdded ;

    public Song(String title, String artist , String album
            , long songId , long albumId , long artistId
            , long songDuration , long dateAdded ) {

        this.title = title;
        this.artist = artist;
        this.album = album ;
        this.songId = songId;
        this.albumId = albumId;
        this.artistId = artistId;
        this.songDuration = songDuration;
        this.dateAdded = dateAdded ;
    }

    public long getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public long getSongDuration() {
        return songDuration;
    }

    public long getAlbumId() {
        return albumId;
    }

    public String getAlbum() {
        return album;
    }

    public long getArtistId() {
        return artistId;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public String getReadableSongDuration(){
        return Utils.convertToReadableTime(this.getSongDuration());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Song))
            return false;

        Song that = (Song) o ;
        return this.songId == that.songId  && this.title.equals(that.title) &&
                this.albumId == that.albumId && this.artistId == that.artistId;
    }
}
