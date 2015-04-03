package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

import ch.webk.box2d.RunnerUserData;
import ch.webk.utils.manager.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.GameMath;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.manager.GameManager;

public class Runner extends GameCombinedActor {

    private Logger l = new Logger("Runner", true);

    private final TextureRegion textureRegion;

    private boolean move = false;
    private float moveToX = 0;
    private float moveToY = 0;
    private float tol = 0.1f;

    private float maxVelocity = 10;
    private float acceleration = 10f;
    private float inertia = 0.2f;

    public Runner(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.RUNNER);
        setIsTouchable(true);
    }

    @Override
    public RunnerUserData getUserData() {
        return (RunnerUserData) userData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (move) {
            float dst = body.getWorldCenter().dst(new Vector2(moveToX,moveToY));
            if (dst < tol) {
                body.setLinearVelocity(new Vector2(0,0));
                move = false;
            } else {
                Vector2 vForce = GameMath.relVector(body.getWorldCenter().x, body.getWorldCenter().y, moveToX, moveToY);
                vForce.setLength(acceleration);

                body.applyForce(vForce, body.getWorldCenter(), true);

                Vector2 v = body.getLinearVelocity();
                if (v.len() > maxVelocity) {
                    v.setLength(maxVelocity);
                }
                body.setLinearVelocity(v);
            }
        }
    }

    public void moveTo(float x, float y) {
        l.i("moveTo x="+x+", y="+y);
        moveToX = x;
        moveToY = y;
        move = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawTextureRegion(batch, textureRegion);
    }

    public MouseJoint mouse(Body defaultBody) {
        MouseJointDef mjd = new MouseJointDef();
        mjd.bodyA = defaultBody;
        mjd.bodyB = body;
        mjd.dampingRatio = 5;
        mjd.frequencyHz = 30;
        mjd.maxForce = (float) (2000.0f * body.getMass());
        mjd.collideConnected = true;
        mjd.target.set(body.getWorldCenter());
        MouseJoint mj = (MouseJoint) GameManager.getWorld().createJoint(mjd);
        return mj;
    }

    @Override
    public void dispose() {}

    @Override
    public void resume() {}

}