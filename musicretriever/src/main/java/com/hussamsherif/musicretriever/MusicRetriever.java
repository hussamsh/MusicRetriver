package com.hussamsherif.musicretriever;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.hussamsherif.musicretriever.MusicClasses.Album;
import com.hussamsherif.musicretriever.MusicClasses.Artist;
import com.hussamsherif.musicretriever.MusicClasses.MusicQuery;
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

    private static void setResolver(ContentResolver resolver) {
        MusicRetriever.resolver = resolver;
    }

    public static MusicRetriever with(ContentResolver resolver){
       if (retriever == null)
           retriever = new MusicRetriever();

        setResolver(resolver);
        return retriever;
    }

    public ArrayList<Song> getAllSongs(String orderBy) {
        if (!RowConstants.checkIfExists(orderBy))
            throw new IllegalArgumentException("Order by not recognized , Did you get it from RowConstants class");

        return parseSongs(resolver.query(musicUri, SONG_COLUMNS, null, null, null));
    }

    public ArrayList<Artist> getAllArtists() {
        return parseArtists(resolver.query(musicUri, ARTIST_COLUMNS, null, null, null));
    }


    public ArrayList<Album> getAllAlbums() {
        return parseAlbums(resolver.query(musicUri, ALBUM_COLUMNS, null, null, null));
    }

    public ArrayList<Song> getSongs(MusicQuery musicQuery){
        return parseSongs(resolver.query(musicUri, SONG_COLUMNS, musicQuery.getProjection(), musicQuery.getSelectionArguments(), musicQuery.getSortBy()));
    }

    public ArrayList<Album> getAlbums(MusicQuery musicQuery ){
        return parseAlbums(resolver.query(musicUri, ALBUM_COLUMNS, musicQuery.getProjection() + " ) GROUP BY ( " + RowConstants.ALBUM_ID
                , musicQuery.getSelectionArguments(), musicQuery.getSortBy()));
    }

    public ArrayList<Artist> getArtists(MusicQuery musicQuery){
        return parseArtists(resolver.query(musicUri , ARTIST_COLUMNS , musicQuery.getProjection() + " ) GROUP BY ( " + RowConstants.ARTIST_ID ,
                musicQuery.getSelectionArguments() , musicQuery.getSortBy()));
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
                final AlbumArtColor albumArtColor = new AlbumArtColor();
//                Glide.with(resolver).load(musicUri).asBitmap().into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        albumArtColor.set(loadAlbumColor(resource));
//                    }
//                });
                albums.add(new Album(albumName, artistName, albumId, artistId, numberOfSongs, albumArtColor));
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
            int artistNameColumn = musicCursor.getColumnIndex(RowConstants.ARTIST_NAME);
            int albumNameColumn = musicCursor.getColumnIndex(RowConstants.ALBUM_NAME);
            int songDurationColumn = musicCursor.getColumnIndex(RowConstants.SONG_DURATION);
            int dateAddedColumn = musicCursor.getColumnIndex(RowConstants.DATE_ADDED);
            do {
                long songID = musicCursor.getLong(songIDColumn);
                long albumID = musicCursor.getLong(albumIDColumn);
                long artistId = musicCursor.getLong(artistIDColumn);
                String title = musicCursor.getString(titleColumn);
                String artist = musicCursor.getString(artistNameColumn);
                String album = musicCursor.getString(albumNameColumn);
                long songDuration = musicCursor.getLong(songDurationColumn);
                long dateAdded = musicCursor.getLong(dateAddedColumn);
                songs.add(new Song(title, artist, album, songID, albumID, artistId, songDuration, dateAdded));
            } while (musicCursor.moveToNext());
            musicCursor.close();
        }

        if (songs == null)
            songs = new ArrayList<>();
        return songs;
    }

    public ArrayList<String> queryNames(String nameColumn, String where, String[] whereVal) {
        Cursor cursor = resolver
                .query(musicUri, new String[]{nameColumn}, where == null ? MediaStore.Audio.Media.IS_MUSIC : where + "=?", whereVal, null);
        ArrayList<String> names = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(nameColumn);
            do {
                String string = cursor.getString(columnIndex);
                if (!names.contains(string))
                    names.add(string);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return names;
    }

    private ArrayList<Long> queryIDs(String columnID, String where, String[] whereVal) {
        Cursor cursor = resolver
                .query(musicUri, new String[]{columnID}, where == null ? MediaStore.Audio.Media.IS_MUSIC: where + "=?", whereVal, null);
        ArrayList<Long> values = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            values = new ArrayList<>(cursor.getCount());
            int idColumn = cursor.getColumnIndex(columnID);
            do {
                long id = cursor.getLong(idColumn);
                if (!values.contains(id))
                    values.add(id);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return values;
    }
//
//    //TODO: Ugly code
//    private AlbumArtColor loadAlbumColor(final Bitmap bitmap) {
//        AlbumArtColor albumArtColor = new AlbumArtColor();
//        if (bitmap != null){
//            Palette palette = Palette.from(bitmap).generate();
//            if (palette.getVibrantSwatch() != null)
//                albumArtColor.setVibrantColor(palette.getVibrantSwatch().getRgb());
//            if (palette.getLightVibrantSwatch() != null)
//                albumArtColor.setLightVibrantColor(palette.getLightVibrantSwatch().getRgb());
//            if (palette.getDarkVibrantSwatch() != null)
//                albumArtColor.setDarkVibrantColor(palette.getDarkVibrantSwatch().getRgb());
//            if (palette.getMutedSwatch() != null)
//                albumArtColor.setMutedColor(palette.getMutedSwatch().getRgb());
//            if (palette.getLightMutedSwatch() != null)
//                albumArtColor.setLightMutedColor(palette.getLightMutedSwatch().getRgb());
//            if (palette.getDarkMutedSwatch() != null)
//                albumArtColor.setDarkMutedColor(palette.getDarkMutedSwatch().getRgb());
//        }
//        return albumArtColor;
//    }
}

