package musiccollection.jaked.musiccollection;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

// Albums needs to be created parcelable so that it can be passed between views as an Extra
public class Album implements  Parcelable{

    private String mAlbumTitle;
    private String mArtistName;
    private String mReleaseYear;
    private Boolean mOfficial;
    private float mRating;
    private UUID mUUID;

    public Album(String albumTitle, String artistName, String releaseYear, boolean official) {
        mAlbumTitle = albumTitle;
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

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }

    public String getAlbumTitle() {
        return mAlbumTitle;
    }

    public void setAlbumTitle(String title) {
        mAlbumTitle = title;
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
            return ((Album) obj).getAlbumTitle() == mAlbumTitle;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.mAlbumTitle.hashCode();
    }

    //
    public Album(Parcel in){
        String[] data = new String[6];
        in.readStringArray(data);
        this.mAlbumTitle = data[0];
        this.mArtistName = data[1];
        this.mReleaseYear = data[2];

        if (data[3].equals("true")){
            mOfficial = true;
        }
        else {
            mOfficial = false;
        }
        this.mRating = Float.parseFloat(data[4]);
        this.mUUID = UUID.fromString(data[5]);
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
        dest.writeStringArray(new String[] {this.mAlbumTitle,
                this.mArtistName,
                this.mReleaseYear,
                official,
                String.valueOf(this.mRating),
                this.mUUID.toString()});
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
