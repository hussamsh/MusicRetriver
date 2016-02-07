package com.github.hussamsh.musicretriever;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.github.hussamsh.musicretriever.MusicClasses.Album;
import com.github.hussamsh.musicretriever.MusicClasses.Artist;
import com.github.hussamsh.musicretriever.MusicClasses.MusicQuery;
import com.github.hussamsh.musicretriever.MusicClasses.Song;

import java.util.ArrayList;

/**
 * Class for querying All information regarding the music on the device
 */

//TODO: Initialize all arrayLists with a size instead of leaving it dynamically (saves memory)
public class MusicRetriever {

    private Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    private static final String ALBUM_NAME = MediaStore.Audio.Media.ALBUM;
    private static final String ARTIST_NAME = MediaStore.Audio.Media.ARTIST;
    public static final String TITLE = MediaStore.Audio.Media.TITLE;
    private static final String ALBUM_ID = MediaStore.Audio.Media.ALBUM_ID;
    private static final String SONG_ID = MediaStore.Audio.Media._ID;
    private static final String ARTIST_ID = MediaStore.Audio.Media.ARTIST_ID;
    private static final String SONG_DURATION = MediaStore.Audio.Media.DURATION;
    private static final String ALL = MediaStore.Audio.Media.IS_MUSIC;
    public static final String DATE_ADDED = MediaStore.Audio.Media.DATE_ADDED;

    private final String[] SONG_COLUMNS = {SONG_ID, TITLE, ALBUM_ID, ALBUM_NAME, ARTIST_ID, ARTIST_NAME, SONG_DURATION, DATE_ADDED};
    private final String[] ALBUM_COLUMNS = {ALBUM_NAME, ALBUM_ID, ARTIST_NAME, ARTIST_ID};
    private final String[] ARTIST_COLUMNS = {ARTIST_NAME, ARTIST_ID, ALBUM_ID};

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
        return parseSongs(resolver.query(musicUri , SONG_COLUMNS , null , null , null));
    }

    public ArrayList<Artist> getAllArtists() {
        return parseArtists(resolver.query(musicUri, ARTIST_COLUMNS, null, null, null));
    }

    public ArrayList<Album> getAllAlbums() {
        return parseAlbums(resolver.query(musicUri, ALBUM_COLUMNS, null, null, null));
    }

    public long getArtistID(String artistName) {
        ArrayList<Long> IDs = queryIDs(ARTIST_ID, ARTIST_NAME, new String[]{artistName});
        if (IDs.isEmpty()) {
            IDs = queryIDs(ARTIST_ID, null, null);
            for (int i = 0; i < IDs.size(); i++) {
                if (i != IDs.get(i))
                    return i;
            }
        } else
            return IDs.get(0);
        return IDs.get(IDs.size() - 1) + 1;
    }

    public long getAlbumID(String albumName) {
        ArrayList<Long> IDs = queryIDs(ALBUM_ID, ALBUM_NAME, new String[]{albumName});
        if (IDs.isEmpty()) {
            IDs = queryIDs(SONG_ID, null, null);
            for (int i = 0; i < IDs.size(); i++) {
                if (i != IDs.get(i))
                    return i;
            }
        } else
            return IDs.get(0);

        return IDs.get(IDs.size() - 1) + 1;
    }

    public ArrayList<Album> searchForAlbumsByName(String parameter) {
        return parseAlbums(resolver.query(musicUri, ALBUM_COLUMNS, ALBUM_NAME + " LIKE ? " + " ) GROUP BY ( " + ALBUM_ID, new String[]{"%" + parameter + "%"}, null));
    }

    public ArrayList<Artist> searchForArtistsByName(String parameter) {
        return parseArtists(resolver
                .query(musicUri, ARTIST_COLUMNS, ARTIST_NAME +
                        " LIKE ? " + " ) GROUP BY ( " + ARTIST_ID, new String[]{"%" + parameter + "%"}, null));
    }

    public ArrayList<String> getAllAlbumsNames() {
        ArrayList<String> result = queryNames(ALBUM_NAME, null, null);
        return !result.isEmpty() ? result : null;
    }

    public ArrayList<String> getAllArtistNames() {
        ArrayList<String> result = queryNames(ARTIST_NAME, null, null);
        return !result.isEmpty() ? result : null;
    }

    public ArrayList<Song> getSongs(MusicQuery musicQuery){
        return parseSongs(resolver.query(musicUri , SONG_COLUMNS , musicQuery.getProjection() , musicQuery.getSelectionArguments() , musicQuery.getSortBy()));
    }

    public ArrayList<Album> getAlbums(MusicQuery musicQuery){
        return parseAlbums(resolver.query(musicUri , ALBUM_COLUMNS , musicQuery.getProjection() , musicQuery.getSelectionArguments() , musicQuery.getSortBy()));
    }

    public ArrayList<Artist> getArtists(MusicQuery musicQuery){
        return parseArtists(resolver.query(musicUri , ARTIST_COLUMNS , musicQuery.getProjection() , musicQuery.getSelectionArguments() , musicQuery.getSortBy()));
    }

    private ArrayList<Artist> parseArtists(Cursor artistCursor) {
        ArrayList<Artist> artists = new ArrayList<>();
        if (artistCursor != null && artistCursor.moveToFirst()) {
            int artistNameColumn = artistCursor.getColumnIndex(ARTIST_NAME);
            int artistIdColumn = artistCursor.getColumnIndex(ARTIST_ID);
            do {
                String artistName = artistCursor.getString(artistNameColumn);
                long artistId = artistCursor.getLong(artistIdColumn);

                Cursor numberOfSongsCursor = resolver.query(musicUri, new String[]{SONG_ID}, ARTIST_ID + "=?", new String[]{String.valueOf(artistId)}, null);
                int numberOfSongs = 0;
                if (numberOfSongsCursor != null) {
                    numberOfSongs = numberOfSongsCursor.getCount();
                    numberOfSongsCursor.close();
                }

                Cursor numberOfAlbumsCursor = resolver.query(musicUri, new String[]{ALBUM_ID}, ARTIST_ID + "=?" + " ) GROUP BY ( " + ALBUM_ID,
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

    private Artist parseArtist(Cursor artistCursor) {
        if (artistCursor != null && artistCursor.moveToFirst()) {
            String artistName = artistCursor.getString(artistCursor.getColumnIndex(ARTIST_NAME));
            long artistId = artistCursor.getLong(artistCursor.getColumnIndex(ARTIST_ID));

            Cursor numberOfSongsCursor = resolver.query(musicUri,
                    new String[]{SONG_ID}, ARTIST_ID + "=?", new String[]{String.valueOf(artistId)}, null);
            int numberOfSongs = 0;
            if (numberOfSongsCursor != null) {
                numberOfSongs = numberOfSongsCursor.getCount();
                numberOfSongsCursor.close();
            }

            Cursor numberOfAlbumsCursor = resolver.query(musicUri, new String[]{ALBUM_ID}, ARTIST_ID + "=?", new String[]{String.valueOf(artistId)}, null);
            int numberOfAlbums = 0;
            if (numberOfAlbumsCursor != null) {
                numberOfAlbums = numberOfAlbumsCursor.getCount();
                numberOfAlbumsCursor.close();
            }

            artistCursor.close();
            return new Artist(artistName, artistId, numberOfSongs, numberOfAlbums);
        }
        return null;
    }

    private ArrayList<Album> parseAlbums(Cursor albumsCursor) {
        ArrayList<Album> albums = new ArrayList<>();
        if (albumsCursor != null && albumsCursor.moveToFirst()) {
            int albumNameColumn = albumsCursor.getColumnIndex(ALBUM_NAME);
            int artistNameColumn = albumsCursor.getColumnIndex(ARTIST_NAME);
            int artistIdColumn = albumsCursor.getColumnIndex(ARTIST_ID);
            int albumIdColumn = albumsCursor.getColumnIndex(ALBUM_ID);
            do {
                String albumName = albumsCursor.getString(albumNameColumn);
                String artistName = albumsCursor.getString(artistNameColumn);
                long artistId = albumsCursor.getLong(artistIdColumn);
                final long albumId = albumsCursor.getLong(albumIdColumn);
                int numberOfSongs = 0;
                Cursor cursor = resolver.query(musicUri, new String[]{SONG_ID}, ALBUM_ID + "=?", new String[]{String.valueOf(albumId)}, null);
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


    private Album parseAlbum(Cursor albumCursor) {
        if (albumCursor != null && albumCursor.moveToFirst()) {
            String albumName = albumCursor.getString(albumCursor.getColumnIndex(ALBUM_NAME));
            String artistName = albumCursor.getString(albumCursor.getColumnIndex(ARTIST_NAME));
            long artistId = albumCursor.getLong(albumCursor.getColumnIndex(ARTIST_ID));
            final long albumId = albumCursor.getLong(albumCursor.getColumnIndex(ALBUM_ID));
            Cursor cursor = resolver.query(musicUri, new String[]{SONG_ID},
                    ALBUM_ID + "=?", new String[]{String.valueOf(albumId)}, null);
            int numberOfSongs = 0;
            if (cursor != null) {
                numberOfSongs = cursor.getCount();
                cursor.close();
            }
            albumCursor.close();
            final AlbumArtColor albumArtColor = new AlbumArtColor();
//            Glide.with(resolver).load(musicUri).asBitmap().into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    albumArtColor.set(loadAlbumColor(resource));
//                }
//            });
            return new Album(albumName, artistName, albumId, artistId, numberOfSongs
                    , albumArtColor);
        }
        return null;
    }

    private ArrayList<Song> parseSongs(Cursor musicCursor) {
        ArrayList<Song> songs = null;
        if (musicCursor != null && musicCursor.moveToFirst()) {
            songs = new ArrayList<>(musicCursor.getCount());
            int songIDColumn = musicCursor.getColumnIndex(SONG_ID);
            int albumIDColumn = musicCursor.getColumnIndex(ALBUM_ID);
            int artistIDColumn = musicCursor.getColumnIndex(ARTIST_ID);
            int titleColumn = musicCursor.getColumnIndex(TITLE);
            int artistNameColumn = musicCursor.getColumnIndex(ARTIST_NAME);
            int albumNameColumn = musicCursor.getColumnIndex(ALBUM_NAME);
            int songDurationColumn = musicCursor.getColumnIndex(SONG_DURATION);
            int dateAddedColumn = musicCursor.getColumnIndex(DATE_ADDED);
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
                .query(musicUri, new String[]{nameColumn}, where == null ? ALL : where + "=?", whereVal, null);
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
                .query(musicUri, new String[]{columnID}, where == null ? ALL : where + "=?", whereVal, null);
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

