package vn.brine.spotifymusicchannel.search;

import java.util.List;

import vn.brine.spotifymusicchannel.spotifyapi.models.Track;

/**
 * Created by HaiLeader on 12-May-16.
 */
public class Search {

    public interface View {
        void reset();

        void addData(List<Track> items);
    }

    public interface ActionListener {

        void init(String token);

        String getCurrentQuery();

        void search(String searchQuery);

        void loadMoreResults();

        void selectTrack(Track item);

        void resume();

        void pause();

        void destroy();

    }
}