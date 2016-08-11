package codepath.com.mediaplayerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by sshah on 8/8/16.
 */
public class BaseActivity  extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_audio_playback:
                startActivity(new Intent(this, AudioPlaybackActivity.class));
                return true;
            case R.id.menu_item_video_playback:
                startActivity(new Intent(this, VideoPlaybackActivity.class));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
