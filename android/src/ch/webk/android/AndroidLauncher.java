package ch.webk.android;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import ch.webk.MyGame;
import ch.webk.ch.webk.screens.GameScreen;
import ch.webk.stages.RocketStage;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;

public class AndroidLauncher extends AndroidApplication {

    private Logger l = new Logger("AndroidLauncher", true);

    private static final int STAGE_ROCKET = 1;
    private static final int STAGE_POLY = 1;
    private static final int STAGE_FOLLOW = 1;

    private AndroidApplicationConfiguration config;

    private MyGame game;

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

		config = new AndroidApplicationConfiguration();
        game = new MyGame(Constants.STAGE_ROCKET);
		initialize(game, config);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, STAGE_ROCKET, Menu.NONE, "Rocket");
        menu.add(Menu.NONE, STAGE_POLY, Menu.NONE, "Poly");
        menu.add(Menu.NONE, STAGE_FOLLOW, Menu.NONE, "sdsf");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case STAGE_ROCKET:
                game.stop();
                game = new MyGame(Constants.STAGE_ROCKET);
                initialize(game, config);
                return true;
            default:
                return false;
        }
    }
}
