package musiccollection.jaked.musiccollection.database;

// Defines the cloumns of the SQLite database
public class MusicDbSchema {

    public static final class MusicTable{
        public static final String NAME = "music";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String ARTIST = "artist";
            public static final String YEAR = "year";
            public static final String OFFICIAL = "official";
            public static final String RATING = "rating";
        }
    }
}
