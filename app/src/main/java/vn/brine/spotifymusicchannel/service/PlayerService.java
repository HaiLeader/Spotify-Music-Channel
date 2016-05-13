package vn.brine.spotifymusicchannel.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import vn.brine.spotifymusicchannel.search.Player;
import vn.brine.spotifymusicchannel.search.PreviewPlayer;

/**
 * Created by HaiLeader on 12-May-16.
 */
public class PlayerService extends Service {

    private final IBinder mBinder = new PlayerBinder();
    private PreviewPlayer mPlayer = new PreviewPlayer();

    public static Intent getIntent(Context context) {
        return new Intent(context, PlayerService.class);
    }

    public class PlayerBinder extends Binder {
        public Player getService() {
            return mPlayer;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mPlayer.release();
        super.onDestroy();
    }
}