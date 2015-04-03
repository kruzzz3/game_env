package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;

import ch.webk.box2d.BallUserData;
import ch.webk.box2d.IBeforeDestroyListener;
import ch.webk.box2d.ICollisionListener;
import ch.webk.lights.box2dLight.PointLight;
import ch.webk.utils.helper.BreakableTimer;
import ch.webk.utils.manager.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.GameMath;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.helper.UDM;
import ch.webk.utils.manager.LightManager;

public class Ball extends GameCombinedActor {

    private Logger l = new Logger("Ball", true);

    private Animation animation;
    private PointLight light;

    private boolean remove;
    private Timer.Task task;

    public Ball(Body body) {
        super(body);
        l.i("Ball");
        animation = ActorManager.getAnimation(Constants.BALL, 0.2f);

        float a = GameMath.getRandomFloat(0,20) - GameMath.getRandomFloat(0,20);
        float b = GameMath.getRandomFloat(0,20) - GameMath.getRandomFloat(0,20);

        body.applyLinearImpulse(new Vector2(a, b), body.getWorldCenter(), true);

        setIsTouchable(true);
        createLight();

        UDM.getUserData(body).setCollisionListener(new ICollisionListener() {

            @Override
            public void beginContact(Body body) {

                task = BreakableTimer.addTask(new Timer.Task() {
                    @Override
                    public void run() {
                        getUserData().setDestroy(true);
                    }
                }, 5000);
            }

            @Override
            public void endContact(Body body) {
                l.i("endContact");
            }
        });

        UDM.getUserData(body).setBeforeDestroyListener(new IBeforeDestroyListener() {
            @Override
            public void beforeDestroy() {
                dispose();
            }
        });

    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    private void createLight() {
        light = new PointLight(LightManager.getRayHandler(), 36, LightManager.getRandColor(), UDM.getUserData(body).getHeight() * 3, 0, 0);
        light.attachToBody(body,0,0);
    }

    @Override
    public BallUserData getUserData() {
        return (BallUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawLoopAnimation(batch, animation);
    }

    @Override
    public void dispose() {
        if (task != null) {
            task.cancel();
        }
        if (light != null) {
            light.setActive(false);
            light.remove();
            light = null;
        }
    }

    @Override
    public void resume() {}

}
