package musiccollection.jaked.musiccollection.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import musiccollection.jaked.musiccollection.Album;
import musiccollection.jaked.musiccollection.database.MusicDbSchema.MusicTable;

public class DatabaseReader {
    SQLiteDatabase mDatabase;


    public ArrayList<Album> DatabaseReader(Context context){
        mDatabase = new MusicBaseHelper(context).getWritableDatabase();
        ArrayList<Album> albums = new ArrayList<Album>();

        AlbumCursorWrapper cursorWrapper = queryAlbums(null,null);

        try{
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                albums.add(cursorWrapper.getAlbum());
                cursorWrapper.moveToNext();
            }
        }
        finally {
            cursorWrapper.close();
        }
        return albums;
    }

    private AlbumCursorWrapper queryAlbums(String whereClause,
                                           String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MusicTable.NAME,
                null, // columns - null selects allcolumns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new AlbumCursorWrapper(cursor);
    }
}
