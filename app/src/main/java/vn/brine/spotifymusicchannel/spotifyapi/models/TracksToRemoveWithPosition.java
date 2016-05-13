package vn.brine.spotifymusicchannel.spotifyapi.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HaiLeader on 12-May-16.
 */
public class TracksToRemoveWithPosition implements Parcelable {
    public List<TrackToRemoveWithPosition> tracks;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.tracks);
    }

    public TracksToRemoveWithPosition() {
    }

    protected TracksToRemoveWithPosition(Parcel in) {
        this.tracks = new ArrayList<TrackToRemoveWithPosition>();
        in.readList(this.tracks, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<TracksToRemoveWithPosition> CREATOR = new Parcelable.Creator<TracksToRemoveWithPosition>() {
        public TracksToRemoveWithPosition createFromParcel(Parcel source) {
            return new TracksToRemoveWithPosition(source);
        }

        public TracksToRemoveWithPosition[] newArray(int size) {
            return new TracksToRemoveWithPosition[size];
        }
    };
}
