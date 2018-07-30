package musiccollection.jaked.musiccollection;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Album implements  Parcelable{

    private String mTitle;
    private String mArtistName;
    private String mReleaseYear;
    private Boolean mOfficial;
    private int mRating;
    private UUID mUUID;

    public Album(String title, String artistName, String releaseYear, boolean official) {
        mTitle = title;
        mArtistName = artistName;
        mReleaseYear = releaseYear;
        mOfficial = official;
        mRating = 0;
        mUUID = UUID.randomUUID();
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

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

    public Album(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.mTitle = data[0];
        this.mArtistName = data[1];
        this.mReleaseYear = data[2];

        if (data[3].equals("true")){
            mOfficial = true;
        }
        else {
            mOfficial = false;
        }
    }


    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String official;

        if(mOfficial){
            official = "true";
        }
        else{
            official = "false";
        }
        dest.writeStringArray(new String[] {this.mTitle,
                this.mArtistName,
                this.mReleaseYear,official});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

}
