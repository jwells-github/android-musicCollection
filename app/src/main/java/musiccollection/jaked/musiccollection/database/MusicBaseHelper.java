package musiccollection.jaked.musiccollection.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import musiccollection.jaked.musiccollection.database.MusicDbSchema.MusicTable;

public class MusicBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "recordBase.db";

    public MusicBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + MusicTable.NAME +
                "(" + " _id integer primary key autoincrement,"
                    + MusicTable.Cols.UUID + ", "
                    + MusicTable.Cols.TITLE + ", "
                    + MusicTable.Cols.ARTIST + ", "
                    + MusicTable.Cols.YEAR + ", "
                    + MusicTable.Cols.OFFICIAL + ", "
                    + MusicTable.Cols.RATING + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
