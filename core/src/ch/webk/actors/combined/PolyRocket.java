package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer.Task;

import ch.webk.actors.screen.Explosion;
import ch.webk.box2d.ICollisionListener;
import ch.webk.box2d.PolyRocketUserData;
import ch.webk.box2d.UserData;
import ch.webk.utils.manager.ActorManager;
import ch.webk.utils.manager.GameManager;
import ch.webk.utils.manipulator.Box2dManipulator;
import ch.webk.utils.helper.BreakableTimer;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.helper.UDM;

public class PolyRocket extends GameCombinedActor {

    private Logger l = new Logger("PolyRocket", true);

    private final TextureRegion textureRegion;

    private float maxAngularVelocity = 7;
    private float angularAcceleration = 0.01f;

    private float maxVelocity = 20;
    private float currentVelocity = 0;
    private float acceleration = 1f;
    private float inertia = 0.2f;
    private Task task;

    private static Target target;

    public PolyRocket(Body body, Target target) {
        super(body);

        textureRegion = ActorManager.getTextureRegion(Constants.POLY_ROCKET);
        getUserData().setRotationFixDegree(body, 90);
        this.target = target;

        getUserData().setCollisionListener(new ICollisionListener() {
            @Override
            public void beginContact(Body body) {
                l.i("beginContact");
                if (UDM.bodyIsTarget(body)) {
                    explode();
                    getUserData().setDestroy(true);
                }
            }

            @Override
            public void endContact(Body body) {

            }
        });

        createTask(5000);
    }

    private void createTask(float milli) {
        task = BreakableTimer.addTask(new Task() {
            @Override
            public void run() {
                getUserData().setDestroy(true);
                explode();
            }
        }, milli);
    }

    private void explode() {
        task.cancel();
        float width = ((UserData)body.getUserData()).getWidth() * 1.5f;
        GameManager.addActor(new Explosion(body.getWorldCenter().x, body.getWorldCenter().y, width, width, 0));
    }

    @Override
    public PolyRocketUserData getUserData() {
        return (PolyRocketUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawTextureRegion(batch, textureRegion);
    }

    @Override
    public void act(float delta) {
        super.act(delta);


        l.i("M1="+task.getExecuteTimeMillis());
        l.i("M2="+System.currentTimeMillis() % 1000);
        l.i("M3="+((System.currentTimeMillis() % 1000) - task.getExecuteTimeMillis()));

        try {
            Box2dManipulator.steerAt(body, target.getPosition(), angularAcceleration, maxAngularVelocity, 0);
        } catch (Exception e) {}

        currentVelocity += acceleration;
        float tempCurrentSpeed = currentVelocity;

        if (tempCurrentSpeed > maxVelocity) {
            //tempCurrentSpeed -= Math.abs(body.getAngularVelocity() / 2);;
        }

        if (tempCurrentSpeed > maxVelocity) {
            tempCurrentSpeed = maxVelocity;
            currentVelocity = maxVelocity;
        } else if (tempCurrentSpeed < 0) {
            tempCurrentSpeed = acceleration;
            currentVelocity = acceleration;
        }

        Vector2 v1 = body.getLinearVelocity().cpy();
        v1.setLength(inertia);

        Vector2 v2 = Box2dManipulator.getLookDirection(body, 0);
        v2.add(v1);
        v2.setLength(tempCurrentSpeed);

        body.setLinearVelocity(v2);
    }

    @Override
    public void dispose() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public void resume() {
        createTask(BreakableTimer.getTimeLeft(task));
    }
}
