package vn.brine.spotifymusicchannel.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.client.Response;
import vn.brine.spotifymusicchannel.spotifyapi.SpotifyCallback;
import vn.brine.spotifymusicchannel.spotifyapi.SpotifyError;
import vn.brine.spotifymusicchannel.spotifyapi.SpotifyService;
import vn.brine.spotifymusicchannel.spotifyapi.models.Track;
import vn.brine.spotifymusicchannel.spotifyapi.models.TracksPager;

/**
 * Created by HaiLeader on 12-May-16.
 */
public class SearchPager {

    private final SpotifyService mSpotifyApi;
    private int mCurrentOffset;
    private int mPageSize;
    private String mCurrentQuery;

    public interface CompleteListener {
        void onComplete(List<Track> items);
        void onError(Throwable error);
    }

    public SearchPager(SpotifyService spotifyApi) {
        mSpotifyApi = spotifyApi;
    }

    public void getFirstPage(String query, int pageSize, CompleteListener listener) {
        mCurrentOffset = 0;
        mPageSize = pageSize;
        mCurrentQuery = query;
        getData(query, 0, pageSize, listener);
    }

    public void getNextPage(CompleteListener listener) {
        mCurrentOffset += mPageSize;
        getData(mCurrentQuery, mCurrentOffset, mPageSize, listener);
    }

    private void getData(String query, int offset, final int limit, final CompleteListener listener) {

        Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.OFFSET, offset);
        options.put(SpotifyService.LIMIT, limit);

        mSpotifyApi.searchTracks(query, options, new SpotifyCallback<TracksPager>() {
            @Override
            public void success(TracksPager tracksPager, Response response) {
                listener.onComplete(tracksPager.tracks.items);
            }

            @Override
            public void failure(SpotifyError error) {
                listener.onError(error);
            }
        });
    }
}
