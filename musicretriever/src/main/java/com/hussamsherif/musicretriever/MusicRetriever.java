package com.hussamsherif.musicretriever;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.hussamsherif.musicretriever.MusicClasses.Album;
import com.hussamsherif.musicretriever.MusicClasses.Artist;
import com.hussamsherif.musicretriever.MusicClasses.Song;

import java.util.ArrayList;

/**
 * Class for querying All information regarding the music on the device
 */

//TODO: Initialize all arrayLists with a size instead of leaving it dynamically (saves memory)
public class MusicRetriever {

    private Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    private final String[] SONG_COLUMNS = {RowConstants.SONG_ID, RowConstants.TITLE, RowConstants.ALBUM_ID, RowConstants.ALBUM_NAME, RowConstants.ARTIST_ID,
            RowConstants.ALBUM_NAME, RowConstants.SONG_DURATION, RowConstants.DATE_ADDED};
    private final String[] ALBUM_COLUMNS = {RowConstants.ALBUM_NAME, RowConstants.ALBUM_ID, RowConstants.ARTIST_NAME, RowConstants.ARTIST_ID};
    private final String[] ARTIST_COLUMNS = {RowConstants.ARTIST_NAME, RowConstants.ARTIST_ID, RowConstants.ALBUM_ID};

    private static ContentResolver resolver ;
    private static MusicRetriever retriever ;

    public static MusicRetriever with(ContentResolver resolver){
       if (retriever == null)
           retriever = new MusicRetriever();

        setResolver(resolver);
        return retriever;
    }

    /**
     * @param orderBy Order by which return the songs
     * @return Alll songs on the device
     */
    public ArrayList<Song> getAllSongs(String orderBy) {
        if (!RowConstants.checkIfExists(orderBy))
            throw new IllegalArgumentException("Order by not recognized , Did you get it from RowConstants class");

        return parseSongs(resolver.query(musicUri, SONG_COLUMNS, null, null, orderBy));
    }

    /**
     * @param orderBy Order by which return the songs
     * @return All artists on the device
     */
    public ArrayList<Artist> getAllArtists(String orderBy) {
        if (!RowConstants.checkIfExists(orderBy))
            throw new IllegalArgumentException("Order by not recognized , Did you get it from RowConstants class");

        return parseArtists(resolver.query(musicUri, ARTIST_COLUMNS, null, null, orderBy));
    }

    /**
     * @param orderBy Order by which return the songs
     * @return All albums on the device
     */

    public ArrayList<Album> getAllAlbums(String orderBy) {
        return parseAlbums(resolver.query(musicUri, ALBUM_COLUMNS, null, null, orderBy));
    }

    public ArrayList<Song> getSongs(MusicQuery musicQuery){
        return parseSongs(resolver.query(musicUri, SONG_COLUMNS, musicQuery.getProjection(), musicQuery.getSelectionArguments(), musicQuery.getSortBy()));
    }

    public ArrayList<Album> getAlbums(MusicQuery musicQuery ){
        return parseAlbums(resolver.query(musicUri, ALBUM_COLUMNS, musicQuery.getProjection() + " ) GROUP BY ( " + RowConstants.ALBUM_ID
                , musicQuery.getSelectionArguments(), musicQuery.getSortBy()));
    }

    public ArrayList<Artist> getArtists(MusicQuery musicQuery){
        return parseArtists(resolver.query(musicUri, ARTIST_COLUMNS, musicQuery.getProjection() + " ) GROUP BY ( " + RowConstants.ARTIST_ID,
                musicQuery.getSelectionArguments(), musicQuery.getSortBy()));
    }

    private ArrayList<Artist> parseArtists(Cursor artistCursor) {
        ArrayList<Artist> artists = new ArrayList<>();
        if (artistCursor != null && artistCursor.moveToFirst()) {
            int artistNameColumn = artistCursor.getColumnIndex(RowConstants.ARTIST_NAME);
            int artistIdColumn = artistCursor.getColumnIndex(RowConstants.ARTIST_ID);
            do {
                String artistName = artistCursor.getString(artistNameColumn);
                long artistId = artistCursor.getLong(artistIdColumn);

                Cursor numberOfSongsCursor = resolver.query(musicUri, new String[]{RowConstants.SONG_ID}, RowConstants.ARTIST_ID + "=?", new String[]{String.valueOf(artistId)}, null);
                int numberOfSongs = 0;
                if (numberOfSongsCursor != null) {
                    numberOfSongs = numberOfSongsCursor.getCount();
                    numberOfSongsCursor.close();
                }

                Cursor numberOfAlbumsCursor = resolver.query(musicUri, new String[]{RowConstants.ALBUM_ID}, RowConstants.ARTIST_ID + "=?" + " ) GROUP BY ( " + RowConstants.ALBUM_ID,
                        new String[]{String.valueOf(artistId)}, null);
                int numberOfAlbums = 0;
                if (numberOfAlbumsCursor != null) {
                    numberOfAlbums = numberOfAlbumsCursor.getCount();
                    numberOfAlbumsCursor.close();
                }

                artists.add(new Artist(artistName, artistId, numberOfSongs, numberOfAlbums));
            } while (artistCursor.moveToNext());
            artistCursor.close();
        }
        return artists;
    }

    private ArrayList<Album> parseAlbums(Cursor albumsCursor) {
        ArrayList<Album> albums = new ArrayList<>();
        if (albumsCursor != null && albumsCursor.moveToFirst()) {
            int albumNameColumn = albumsCursor.getColumnIndex(RowConstants.ALBUM_NAME);
            int artistNameColumn = albumsCursor.getColumnIndex(RowConstants.ARTIST_NAME);
            int artistIdColumn = albumsCursor.getColumnIndex(RowConstants.ARTIST_ID);
            int albumIdColumn = albumsCursor.getColumnIndex(RowConstants.ALBUM_ID);
            do {
                String albumName = albumsCursor.getString(albumNameColumn);
                String artistName = albumsCursor.getString(artistNameColumn);
                long artistId = albumsCursor.getLong(artistIdColumn);
                final long albumId = albumsCursor.getLong(albumIdColumn);
                int numberOfSongs = 0;
                Cursor cursor = resolver.query(musicUri, new String[]{RowConstants.SONG_ID}, RowConstants.ALBUM_ID + "=?", new String[]{String.valueOf(albumId)}, null);
                if (cursor != null) {
                    numberOfSongs = cursor.getCount();
                    cursor.close();
                }
                albums.add(new Album(albumName, artistName, albumId, artistId, numberOfSongs));
            } while (albumsCursor.moveToNext());
            albumsCursor.close();
        }
        return albums;
    }

    private ArrayList<Song> parseSongs(Cursor musicCursor) {
        ArrayList<Song> songs = null;
        if (musicCursor != null && musicCursor.moveToFirst()) {
            songs = new ArrayList<>(musicCursor.getCount());
            int songIDColumn = musicCursor.getColumnIndex(RowConstants.SONG_ID);
            int albumIDColumn = musicCursor.getColumnIndex(RowConstants.ALBUM_ID);
            int artistIDColumn = musicCursor.getColumnIndex(RowConstants.ARTIST_ID);
            int titleColumn = musicCursor.getColumnIndex(RowConstants.TITLE);
            int composerColumn = musicCursor.getColumnIndex(RowConstants.COMPOSER);
            int artistNameColumn = musicCursor.getColumnIndex(RowConstants.ARTIST_NAME);
            int albumNameColumn = musicCursor.getColumnIndex(RowConstants.ALBUM_NAME);
            int songDurationColumn = musicCursor.getColumnIndex(RowConstants.SONG_DURATION);
            int dateAddedColumn = musicCursor.getColumnIndex(RowConstants.DATE_ADDED);
            int fileSizeColumn = musicCursor.getColumnIndex(RowConstants.SIZE);
            int yearCreatedColumn = musicCursor.getColumnIndex(RowConstants.YEAR);
            do {
                long songId = musicCursor.getLong(songIDColumn);
                long albumId = musicCursor.getLong(albumIDColumn);
                long artistId = musicCursor.getLong(artistIDColumn);
                String songTitle = musicCursor.getString(titleColumn);
                String artistName = musicCursor.getString(artistNameColumn);
                String albumName = musicCursor.getString(albumNameColumn);
                String composerName = musicCursor.getString(composerColumn);
                long songDuration = musicCursor.getLong(songDurationColumn);
                long dateAdded = musicCursor.getLong(dateAddedColumn);
                long fileSize = musicCursor.getLong(fileSizeColumn);
                int yearCreated = musicCursor.getInt(yearCreatedColumn);
                songs.add(new Song(songId , songTitle , artistName , albumName , composerName ,
                        songDuration , albumId , artistId , dateAdded , yearCreated , fileSize));
            } while (musicCursor.moveToNext());
            musicCursor.close();
        }

        if (songs == null)
            songs = new ArrayList<>();
        return songs;
    }

    private static void setResolver(ContentResolver resolver) {
        MusicRetriever.resolver = resolver;
    }
}

