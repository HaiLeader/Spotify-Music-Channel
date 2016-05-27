package vn.brine.spotifymusicchannel.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;

import java.util.concurrent.TimeUnit;

import vn.brine.spotifymusicchannel.ConfigSpotify;
import vn.brine.spotifymusicchannel.R;
import vn.brine.spotifymusicchannel.search.CredentialsHandler;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @AfterViews
    void checkLogined(){
        String token = CredentialsHandler.getToken(this);
        if(token != null){
            startMainActivity(token);
        }
    }

    @Click(R.id.loginButton)
    void onClick(){
        final AuthenticationRequest request = new AuthenticationRequest.Builder(ConfigSpotify.CLIENT_ID, AuthenticationResponse.Type.TOKEN, ConfigSpotify.REDIRECT_URI)
                .setScopes(new String[]{"playlist-read"})
                .build();

        AuthenticationClient.openLoginActivity(this, ConfigSpotify.REQUEST_CODE, request);
    }

    @OnActivityResult(ConfigSpotify.REQUEST_CODE)
    void onResult(int resultCode, Intent intent){
        AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
        switch (response.getType()) {
            // Response was successful and contains auth token
            case TOKEN:
                logMessage("Got token: " + response.getAccessToken());
                CredentialsHandler.setToken(this, response.getAccessToken(), response.getExpiresIn(), TimeUnit.SECONDS);
                startMainActivity(response.getAccessToken());
                break;

            // Auth flow returned an error
            case ERROR:
                logError("Auth error: " + response.getError());
                break;

            // Most likely auth flow was cancelled
            default:
                logError("Auth result: " + response.getType());
        }
    }

    private void startMainActivity(String token) {
        Intent intent = MainActivity_.createIntent(this);
        intent.putExtra(MainActivity_.EXTRA_TOKEN, token);
        startActivity(intent);
        finish();
    }

    private void logError(String msg) {
        Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        Log.e(TAG, msg);
    }

    private void logMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }
}
