package vn.brine.spotifymusicchannel.spotifyapi;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by HaiLeader on 12-May-16.
 */
public abstract class SpotifyCallback<T> implements Callback<T> {
    public abstract void failure(SpotifyError error);

    @Override
    public void failure(RetrofitError error) {
        failure(SpotifyError.fromRetrofitError(error));
    }
}
