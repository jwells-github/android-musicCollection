package musiccollection.jaked.musiccollection;

public class Album {

    private String mTitle;
    private String mArtistName;
    private String mReleaseYear;
    private String mTrackCount;
    private Boolean mBootleg;

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

    public String getTrackCount() {
        return mTrackCount;
    }

    public void setTrackCount(String trackCount) {
        mTrackCount = trackCount;
    }

    public Boolean getBootleg() {
        return mBootleg;
    }

    public void setBootleg(Boolean bootleg) {
        mBootleg = bootleg;
    }
}
