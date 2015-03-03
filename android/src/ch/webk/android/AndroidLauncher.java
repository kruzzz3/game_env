package ch.webk.android;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

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

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        float sX = size.x;
        float sY = size.y;

        l.i("sx1="+sX);
        l.i("sx2="+Constants.APP_WIDTH);
        l.i("sY1="+sY);
        l.i("sY2="+Constants.APP_HEIGHT);

        float scaleX = sX / Constants.APP_WIDTH;
        l.i("scaleX="+scaleX);
        float scaleY = sY / Constants.APP_HEIGHT;
        l.i("scaleY="+scaleY);

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
