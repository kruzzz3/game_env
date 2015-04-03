package ch.webk.android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import ch.webk.IUIListener;
import ch.webk.MyGame;
import ch.webk.enums.Action;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.manager.GameManager;

public class AndroidLauncher extends AndroidApplication implements SensorEventListener {

    private Logger l = new Logger("AL", true);

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private static AndroidLauncher self;

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

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // senGyroscope = senSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_GAME);
        //senSensorManager.registerListener(this, senGyroscope , SensorManager.SENSOR_DELAY_GAME);


        config = new AndroidApplicationConfiguration();
        game = new MyGame(Constants.STAGE_ROCKET);
		initialize(game, config);
        self = this;
        createUIActionListener();
	}

    @Override
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        //senSensorManager.registerListener(this, senGyroscope , SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Constants.STAGE_ROCKET, Menu.NONE, "Rocket");
        menu.add(Menu.NONE, Constants.STAGE_POLY, Menu.NONE, "Poly");
        menu.add(Menu.NONE, Constants.STAGE_MOVE, Menu.NONE, "Move");
        menu.add(Menu.NONE, Constants.STAGE_DISTANCE_JOINT, Menu.NONE, "Distance");
        menu.add(Menu.NONE, Constants.STAGE_REVOLUTE_JOINT, Menu.NONE, "Revolute");
        menu.add(Menu.NONE, Constants.STAGE_ROPE_JOINT, Menu.NONE, "Rope");
        menu.add(Menu.NONE, Constants.STAGE_CHAIN, Menu.NONE, "Chain");
        menu.add(Menu.NONE, Constants.STAGE_EXPLOSION, Menu.NONE, "Explosion");
        menu.add(Menu.NONE, Constants.STAGE_MONSTER, Menu.NONE, "Monsters");
        menu.add(Menu.NONE, Constants.STAGE_SHADOW, Menu.NONE, "Shadow");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Constants.STAGE_ROCKET:
                game.stop();
                game = new MyGame(Constants.STAGE_ROCKET);
                initialize(game, config);
                createUIActionListener();
                return true;
            case Constants.STAGE_POLY:
                game.stop();
                game = new MyGame(Constants.STAGE_POLY);
                initialize(game, config);
                createUIActionListener();
                return true;
            case Constants.STAGE_MOVE:
                game.stop();
                game = new MyGame(Constants.STAGE_MOVE);
                initialize(game, config);
                createUIActionListener();
                return true;
            case Constants.STAGE_DISTANCE_JOINT:
                game.stop();
                game = new MyGame(Constants.STAGE_DISTANCE_JOINT);
                initialize(game, config);
                createUIActionListener();
                return true;
            case Constants.STAGE_REVOLUTE_JOINT:
                game.stop();
                game = new MyGame(Constants.STAGE_REVOLUTE_JOINT);
                initialize(game, config);
                createUIActionListener();
                return true;
            case Constants.STAGE_ROPE_JOINT:
                game.stop();
                game = new MyGame(Constants.STAGE_ROPE_JOINT);
                initialize(game, config);
                createUIActionListener();
                return true;
            case Constants.STAGE_CHAIN:
                game.stop();
                game = new MyGame(Constants.STAGE_CHAIN);
                initialize(game, config);
                createUIActionListener();
                return true;
            case Constants.STAGE_EXPLOSION:
                game.stop();
                game = new MyGame(Constants.STAGE_EXPLOSION);
                initialize(game, config);
                createUIActionListener();
                return true;
            case Constants.STAGE_MONSTER:
                game.stop();
                game = new MyGame(Constants.STAGE_MONSTER);
                initialize(game, config);
                createUIActionListener();
                return true;
            case Constants.STAGE_SHADOW:
                game.stop();
                game = new MyGame(Constants.STAGE_SHADOW);
                initialize(game, config);
                createUIActionListener();
                return true;
            default:
                return false;
        }
    }

    private void createUIActionListener() {
        l.i("createUIActionListener");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    GameManager.getStage().setUIListener(new IUIListener() {
                        @Override
                        public void sendUIAction(final Action action) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (action == Action.OPEN_MENU) {
                                        l.i("sendUIAction as");
                                        self.actionOpenMenu();
                                    }
                                }
                            });
                            l.i("sendUIAction");

                        }
                    });
                } catch (Exception e) {}
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Constants.accYDegree = (int) (-event.values[0] * 10);
            Constants.accXDegree = (int) (event.values[1] * 10);
        }
    }

    public void actionOpenMenu() {
        openOptionsMenu();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
