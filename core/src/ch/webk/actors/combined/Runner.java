package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

import ch.webk.box2d.RunnerUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class Runner extends GameCombinedActor {

    private Logger l = new Logger("Runner", true);

    private final TextureRegion textureRegion;

    private float moveToX = 0;
    private float moveToY = 0;

    private float maxVel = 1;

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
    public void touchDown() {
        l.i("touchDown()");
    }

    @Override
    public void touchUp() {
        l.i("touchUp()");
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float x = body.getWorldCenter().x;
        float y = body.getWorldCenter().y;

        float turnX = 1;
        if (moveToX < x) {
            turnX = -1;
        }
        float turnY = 1;
        if (moveToY < y) {
            turnY = -1;
        }
        x = turnX * Math.abs(moveToX - x);
        y = turnY * Math.abs(moveToY - y);
        Vector2 v1 = new Vector2(x,y);
        Vector2 v2 = new Vector2(moveToX - x,moveToY - y);


        l.i("act x="+v1.x+", y="+v1.y);
        l.i("act x="+v2.x+", y="+v2.y);
        v1.setLength(1);

        body.setLinearVelocity(v1);


    }

    public void moveTo(float x, float y) {
        l.i("moveTo x="+x+", y="+y);
        moveToX = x;
        moveToY = y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float rotDegree = (float) Math.toDegrees(body.getAngle());
        drawTextureRegion(batch, textureRegion, rotDegree);
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
        MouseJoint mj = (MouseJoint) WorldUtils.getWorld().createJoint(mjd);
        return mj;
    }

}