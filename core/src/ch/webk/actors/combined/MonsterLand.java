package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

import ch.webk.actors.sensor.Sensor;
import ch.webk.box2d.BallUserData;
import ch.webk.box2d.ICollisionListener;
import ch.webk.box2d.MonsterLandUserData;
import ch.webk.box2d.UserData;
import ch.webk.utils.ActorGenerator;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.UDM;
import ch.webk.utils.WorldUtils;

public class MonsterLand extends GameCombinedActor {

    private Logger l = new Logger("MonsterLand", true);

    private Animation animation;
    private Vector2 walk;
    private Sensor sLeft;
    private Sensor sRight;
    private Sensor sTop;
    private boolean flipX = false;
    private boolean destroy = false;

    public MonsterLand(final Body body, String key) {
        super(body);
        animation = ActorManager.getAnimation(key, 0.2f);
        screenRectangle.setOffsetYWorld(-0.1f);
        body.setFixedRotation(true);
        setIsTouchable(true);
        float thickness = 0.1f;
        float width = UDM.getUserData(body).getWidth() * 0.75f;
        float height = UDM.getUserData(body).getHeight() * 0.75f;

        Vector2 posTempL = GameMath.rotateVector2Radians(new Vector2(-UDM.getUserData(body).getWidth() / 2, 0), body.getAngle());
        Vector2 posL = new Vector2(body.getWorldCenter().x + posTempL.x, body.getWorldCenter().y + posTempL.y);
        sLeft = ActorGenerator.createSensor(posL.x, posL.y,thickness,height);
        ((UserData) sLeft.getBody().getUserData()).setCollisionListener(new ICollisionListener() {
            @Override
            public void beginContact(Body body) {
                l.i("sLeft B");
                walkRight();
            }
            @Override
            public void endContact(Body body) { l.i("sLeft E");}
        });
        WorldUtils.createRevoluteJoint(sLeft.getBody(), Vector2.Zero, body, posTempL, false);

        Vector2 posTempR = GameMath.rotateVector2Radians(new Vector2(UDM.getUserData(body).getWidth() / 2, 0), body.getAngle());
        Vector2 posR = new Vector2(body.getWorldCenter().x + posTempR.x, body.getWorldCenter().y + posTempR.y);
        sRight = ActorGenerator.createSensor(posR.x, posR.y,thickness,height);
        ((UserData) sRight.getBody().getUserData()).setCollisionListener(new ICollisionListener() {
            @Override
            public void beginContact(Body body) {
                l.i("sRight B");
                walkLeft();
            }
            @Override
            public void endContact(Body body) { l.i("sRight E");}
        });
        WorldUtils.createRevoluteJoint(sRight.getBody(), Vector2.Zero, body, posTempR, false);

        Vector2 posTempT = GameMath.rotateVector2Radians(new Vector2(0, UDM.getUserData(body).getHeight() / 2), body.getAngle());
        Vector2 posT = new Vector2(body.getWorldCenter().x + posTempT.x, body.getWorldCenter().y + posTempT.y);
        sTop = ActorGenerator.createSensor(posT.x, posT.y,width,thickness);
        ((UserData) sTop.getBody().getUserData()).setCollisionListener(new ICollisionListener() {
            @Override
            public void beginContact(Body body) {
                l.i("sTop B");
                destroy = true;
            }
            @Override
            public void endContact(Body body) { l.i("sTop E");}
        });
        WorldUtils.createRevoluteJoint(sTop.getBody(), Vector2.Zero, body, posTempT, false);

        walkLeft();
    }

    @Override
    public MonsterLandUserData getUserData() {
        return (MonsterLandUserData) userData;
    }

    private void walkLeft() {
        l.i("walkLeft");
        walk = new Vector2(-1.5f,0);
        flipX = false;
    }

    private void walkRight() {
        l.i("walkRight");
        walk = new Vector2(1.5f,0);
        flipX = true;
    }

    private void flipDir() {
        walk = new Vector2(-1*walk.x,0);
        if (flipX) {
            flipX = false;
        } else {
            flipX = true;
        }
    }

    @Override
    public boolean touchDown() {
        l.i("touchDown");
        flipDir();
        return super.touchDown();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(walk.x,body.getLinearVelocity().y);
        if (destroy) {
            UDM.setDestroy(sLeft.getBody());
            UDM.setDestroy(sRight.getBody());
            UDM.setDestroy(sTop.getBody());
            UDM.setDestroy(body);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawLoopAnimation(batch, animation, flipX);
    }

    @Override
    public void dispose() {}

    @Override
    public void resume() {}

}
