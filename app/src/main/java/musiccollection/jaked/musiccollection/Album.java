package musiccollection.jaked.musiccollection;

public class Album {

    private String mTitle;
    private String mArtistName;
    private String mReleaseYear;
    private Boolean mOfficial;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String artistName) {
        mArtistName = artistName;
    }

    public String getReleaseYear() {
        return mReleaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        mReleaseYear = releaseYear;
    }

    public Boolean isOfficial() {
        return mOfficial;
    }

    public void setOfficial(Boolean official) {
        mOfficial = official;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Album) {
            return ((Album) obj).getTitle() == mTitle;
        }
        return false;
    }

    @Override
    public int hashCode() {

        return this.mTitle.hashCode();
    }
}
