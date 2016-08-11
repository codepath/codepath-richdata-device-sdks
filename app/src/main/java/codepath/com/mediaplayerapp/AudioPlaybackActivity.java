package codepath.com.mediaplayerapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.io.IOException;

public class AudioPlaybackActivity extends BaseActivity {

    MediaPlayer mMediaPlayer;

    public static final String AUDIO_URL = "https://dl.dropboxusercontent.com/u/10281242/sample_audio.mp3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_playback);

        playAudio();
    }

    private void playAudio(){
        mMediaPlayer = new MediaPlayer();

        // Set type to streaming
        // Different stream types have different Audio Focus (priority)
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // Listen for if the audio file can't be prepared
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // ... react appropriately ...
                // The MediaPlayer has moved to the Error state, must be reset!
                return false;
            }
        });

        // Attach to when audio file is prepared for playing
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer.start();
            }

        });

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mMediaPlayer.start();
            }
        });

        // Set the data source to the remote URL
        try {
            mMediaPlayer.setDataSource(AUDIO_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Trigger an async preparation which will fire listener when completed
        mMediaPlayer.prepareAsync();
    }

    @Override
    protected void onPause() {
        // Important to release sys resources to allow for
        // other activities/apps to use them properly

        mMediaPlayer.release();
        super.onPause();
    }
}
