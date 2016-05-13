package vn.brine.spotifymusicchannel.spotifyapi.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HaiLeader on 12-May-16.
 */
public class Result implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Result() {
    }

    protected Result(Parcel in) {
    }

    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
