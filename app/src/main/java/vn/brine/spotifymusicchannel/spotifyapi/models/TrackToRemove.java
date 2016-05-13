package vn.brine.spotifymusicchannel.spotifyapi.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HaiLeader on 12-May-16.
 */
public class TrackToRemove implements Parcelable {
    public String uri;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uri);
    }

    public TrackToRemove() {
    }

    protected TrackToRemove(Parcel in) {
        this.uri = in.readString();
    }

    public static final Parcelable.Creator<TrackToRemove> CREATOR = new Parcelable.Creator<TrackToRemove>() {
        public TrackToRemove createFromParcel(Parcel source) {
            return new TrackToRemove(source);
        }

        public TrackToRemove[] newArray(int size) {
            return new TrackToRemove[size];
        }
    };
}

