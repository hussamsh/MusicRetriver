package com.hussamsherif.musicretriever.MusicClasses;

public class Song {

    private long songId ;
    private String songTitle;
    private String artistName;
    private String albumName;
    private String composerName;
    private long songDuration ;
    private long albumId ;
    private long artistId ;
    private long dateAdded ;
    private int yearCreated ;
    private long sizeOnDisk;

    public Song(long songId, String title, String artistName, String albumName,
                String composerName, long songDuration, long albumId,
                long artistId, long dateAdded, int yearCreated,
                long sizeOnDisk) {
        this.songId = songId;
        this.songTitle = title;
        this.artistName = artistName;
        this.albumName = albumName;
        this.composerName = composerName;
        this.songDuration = songDuration;
        this.albumId = albumId;
        this.artistId = artistId;
        this.dateAdded = dateAdded;
        this.yearCreated = yearCreated;
        this.sizeOnDisk = sizeOnDisk;
    }

    /**
     * @return A unique id for a the  audio file
     */
    public long getSongId() {
        return songId;
    }
    /**
     * @return The songTitle of the audio file, if any
     */
    public String getSongTitle() {
        return songTitle;
    }
    /**
     * @return The album  the audio file is from, if any
     */
    public String getArtistName() {
        return artistName;
    }
    /**
     * @return The duration of the audio file, in ms
     */
    public long getSongDuration() {
        return songDuration;
    }
    /**
     * @return The id of the album of the audio file is from, if any
     */
    public long getAlbumId() {
        return albumId;
    }
    /**
     * @return The artist who created the audio file, if any
     */
    public String getAlbumName() {
        return albumName;
    }
    /**
     * @return The id of the artist who created the audio file, if any
     */
    public long getArtistId() {
        return artistId;
    }
    /**
     * @return The time the file was added to the media provider Units are seconds since 1970.
     */
    public long getDateAdded() {
        return dateAdded;
    }
    /**
     * @return The size of the audio file on disk in bytes
     */
    public long getSizeOnDisk() {
        return sizeOnDisk;
    }
    /**
     * @return The composer of the audio file, if any
     */
    public String getComposerName() {
        return composerName;
    }
    /**
     * @return The year the audio file was recorded, if any
     */
    public int getYearCreated() {
        return yearCreated;
    }
}
