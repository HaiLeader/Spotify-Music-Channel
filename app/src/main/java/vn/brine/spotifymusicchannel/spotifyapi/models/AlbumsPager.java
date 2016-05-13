package vn.brine.spotifymusicchannel.spotifyapi.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HaiLeader on 12-May-16.
 */
public class AlbumsPager implements Parcelable {
    public Pager<AlbumSimple> albums;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.albums, 0);
    }

    public AlbumsPager() {
    }

    protected AlbumsPager(Parcel in) {
        this.albums = in.readParcelable(Pager.class.getClassLoader());
    }

    public static final Parcelable.Creator<AlbumsPager> CREATOR = new Parcelable.Creator<AlbumsPager>() {
        public AlbumsPager createFromParcel(Parcel source) {
            return new AlbumsPager(source);
        }

        public AlbumsPager[] newArray(int size) {
            return new AlbumsPager[size];
        }
    };
}
