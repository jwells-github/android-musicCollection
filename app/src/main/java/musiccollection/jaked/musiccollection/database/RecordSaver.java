package musiccollection.jaked.musiccollection.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import musiccollection.jaked.musiccollection.database.MusicDbSchema.MusicTable;
import musiccollection.jaked.musiccollection.Album;

public class RecordSaver {


    public void addRecord(Album album, Context context){
        SQLiteDatabase database = new MusicBaseHelper(context).getWritableDatabase();
        ContentValues values = getContentValues(album);
        database.insert(MusicTable.NAME, null, values);
    }


    public void updateRecord(Album album, Context context){
        String uuidString = album.getUUID().toString();
        ContentValues values = getContentValues(album);
        SQLiteDatabase database = new MusicBaseHelper(context).getWritableDatabase();
        database.update(MusicTable.NAME, values, MusicTable.Cols.UUID + " = ?", new String[] { uuidString });

    }


    private static ContentValues getContentValues(Album album){
        ContentValues values = new ContentValues();

        values.put(MusicTable.Cols.UUID, album.getUUID().toString());
        values.put(MusicTable.Cols.TITLE, album.getTitle());
        values.put(MusicTable.Cols.ARTIST, album.getArtistName());
        values.put(MusicTable.Cols.YEAR, album.getReleaseYear());
        values.put(MusicTable.Cols.OFFICIAL, album.isOfficial() ? 1 : 0);
        values.put(MusicTable.Cols.RATING, album.getRating());

        return values;
    }
}
