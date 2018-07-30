package musiccollection.jaked.musiccollection.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import musiccollection.jaked.musiccollection.Album;
import musiccollection.jaked.musiccollection.database.MusicDbSchema.MusicTable;

public class AlbumCursorWrapper extends CursorWrapper {

    public AlbumCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public static final String UUID = "uuid";
    public static final String TITLE = "title";
    public static final String ARTIST = "artist";
    public static final String YEAR = "year";
    public static final String OFFICIAL = "official";
    public static final String RATING = "rating";

    public Album getAlbum(){
        String uuidString = getString(getColumnIndex(MusicTable.Cols.UUID));
        String title = getString(getColumnIndex(MusicTable.Cols.TITLE));
        String artist = getString(getColumnIndex(MusicTable.Cols.ARTIST));
        String year = getString(getColumnIndex(MusicTable.Cols.YEAR));
        int official = getInt(getColumnIndex(MusicTable.Cols.OFFICIAL));
        int rating = getInt(getColumnIndex(MusicTable.Cols.RATING));

        Boolean officialBool = (official != 0);
        Album album = new Album(title, artist, year, officialBool);
        album.setRating(rating);
        album.setUUID(java.util.UUID.fromString(uuidString  ));
        return album;
    }
}
