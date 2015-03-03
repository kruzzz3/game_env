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
import ch.webk.stages.TestStage;
import ch.webk.utils.ActorManager;
import ch.webk.utils.BodyUtils;
import ch.webk.utils.Box2dManipulator;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class PolyRocket extends GameCombinedActor {

    private Logger l = new Logger("PolyRocket", true);

    private final TextureRegion textureRegion;

    private float maxAngularVelocity = 5;
    private float angularAcceleration = 0.1f;

    private float maxSpeed = 20;
    private float currentSpeed = 0;
    private float acceleration = 0.5f;
    private float inertia = 0.5f;
    private Task task;

    public PolyRocket(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.POLY_ROCKET);
        getUserData().setRotationFixDegree(body, 90);
        setIsTouchable(true);
        getUserData().setCollisionListener(new CollisionListener() {
            @Override
            public void beginContact(Body body) {
                if (BodyUtils.bodyIsTarget(body)) {
                    explode();
                }
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
        Box2dManipulator.steerAt(body, TestStage.target.getPosition(), angularAcceleration, maxAngularVelocity);

        currentSpeed += acceleration;
        if (currentSpeed > maxSpeed) {
            currentSpeed = maxSpeed;
        }

        Vector2 v1 = body.getLinearVelocity().cpy();
        v1.setLength(inertia);

        Vector2 v2 = Box2dManipulator.getLookDirection(body);
        v2.add(v1);
        v2.setLength(currentSpeed);


        body.setLinearVelocity(v2);
    }

}
