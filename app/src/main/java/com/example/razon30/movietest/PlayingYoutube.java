package com.example.razon30.movietest;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayingYoutube extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener  {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    FloatingActionButton fab;

    // YouTube player view
    private YouTubePlayerView youTubeView;
    public String YOUTUBE_VIDEO_CODE = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_playing_youtube);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        Intent intent = getIntent();
        YOUTUBE_VIDEO_CODE = intent.getStringExtra("trailer");


        fab = (FloatingActionButton) findViewById(R.id.action_a);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.ssyoutube.com/watch?v=" +YOUTUBE_VIDEO_CODE ));
                startActivity(intent);
            }
        });


        // Initializing video player with developer key
        youTubeView.initialize(Config.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "Error", errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(YOUTUBE_VIDEO_CODE);

            // Hiding player controls
            player.setPlayerStyle(PlayerStyle.MINIMAL);

            player.getFullscreenControlFlags();
            player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

            player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                @Override
                public void onLoading() {

                    message("loading");
                    fab.setVisibility(fab.GONE);

                }

                @Override
                public void onLoaded(String s) {
                    fab.setVisibility(fab.GONE);

                }

                @Override
                public void onAdStarted() {

                }

                @Override
                public void onVideoStarted() {
                    message("started");
                    fab.setVisibility(fab.GONE);
                }

                @Override
                public void onVideoEnded() {
                    message("ended");
                    fab.setVisibility(fab.VISIBLE);

                }

                @Override
                public void onError(YouTubePlayer.ErrorReason errorReason) {


                }
            });



            player.setShowFullscreenButton(true);
            player.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                @Override
                public void onPlaying() {
                    message("Playing");
                    fab.setVisibility(fab.GONE);
                }

                @Override
                public void onPaused() {
                    message("Paused");
                    fab.setVisibility(fab.VISIBLE);
                }

                @Override
                public void onStopped() {
                    message("Stopped");
                    fab.setVisibility(fab.VISIBLE);
                }

                @Override
                public void onBuffering(boolean b) {
                    fab.setVisibility(fab.GONE);
                }

                @Override
                public void onSeekTo(int i) {
                    fab.setVisibility(fab.GONE);
                }
            });


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    public void message(String string){
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

}