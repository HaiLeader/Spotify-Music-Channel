package vn.brine.spotifymusicchannel.spotifyapi.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HaiLeader on 12-May-16.
 */
public class SavedAlbum implements Parcelable {
    public String added_at;
    public Album album;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.added_at);
        dest.writeParcelable(this.album, 0);
    }

    public SavedAlbum() {
    }

    protected SavedAlbum(Parcel in) {
        this.added_at = in.readString();
        this.album = in.readParcelable(Album.class.getClassLoader());
    }

    public static final Parcelable.Creator<SavedAlbum> CREATOR = new Parcelable.Creator<SavedAlbum>() {
        public SavedAlbum createFromParcel(Parcel source) {
            return new SavedAlbum(source);
        }

        public SavedAlbum[] newArray(int size) {
            return new SavedAlbum[size];
        }
    };
}
