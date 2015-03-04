package ch.webk.android;

import android.os.Bundle;
import android.util.DisplayMetrics;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import ch.webk.MyGame;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;

public class AndroidLauncher extends AndroidApplication {

    private Logger l = new Logger("AndroidLauncher", true);

    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float sX = metrics.widthPixels;
        float sY = metrics.heightPixels;


        float scaleX = sX / Constants.APP_WIDTH;
        float scaleY = sY / Constants.APP_HEIGHT;

        float scale = 1;
        if (scaleX <= scaleY) {
            scale = scaleX;
        } else {
            scale = scaleY;
        }
        l.i("scale="+scale);

        Constants.APP_WIDTH = Math.round(Constants.APP_WIDTH * scale);
        Constants.APP_HEIGHT = Math.round(Constants.APP_HEIGHT * scale);
        Constants.WORLD_TO_SCREEN = Constants.WORLD_TO_SCREEN * scale;

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyGame("config"), config);
	}
}
