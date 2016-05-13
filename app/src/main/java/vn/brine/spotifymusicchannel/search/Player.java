package vn.brine.spotifymusicchannel.search;

import android.support.annotation.Nullable;

/**
 * Created by HaiLeader on 12-May-16.
 */
public interface Player {

    void play(String url);

    void pause();

    void resume();

    boolean isPlaying();

    @Nullable
    String getCurrentTrack();

    void release();
}