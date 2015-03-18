package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import ch.webk.actors.screen.Explosion;
import ch.webk.box2d.CollisionListener;
import ch.webk.box2d.PolyRocketUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.BodyUtils;
import ch.webk.utils.Box2dManipulator;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

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

        getUserData().setCollisionListener(new CollisionListener() {
            @Override
            public void beginContact(Body body) {
                l.i("beginContact");
                if (BodyUtils.bodyIsTarget(body)) {
                    getUserData().setDestroy(true);
                    explode();
                }
            }

            @Override
            public void endContact(Body body) {

            }
        });

        task = Timer.schedule(new Task() {
            @Override
            public void run() {
            getUserData().setDestroy(true);
            explode();
            }
        }, 10);
    }

    private void explode() {
        task.cancel();
        WorldUtils.addActor(new Explosion(screenRectangle.x, screenRectangle.y, screenRectangle.width * 1.5f, screenRectangle.width * 1.5f, 0));
    }

    @Override
    public PolyRocketUserData getUserData() {
        return (PolyRocketUserData) userData;
    }

    @Override
    public void touchDown() {
        l.i("touchDown()");
    }

    @Override
    public void touchUp() {
        l.i("touchUp()");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float rotDegree = (float) Math.toDegrees(body.getAngle());
        drawTextureRegion(batch, textureRegion, rotDegree);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

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
        super.dispose();
        task.cancel();
    }
}
