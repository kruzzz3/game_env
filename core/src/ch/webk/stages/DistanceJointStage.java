package ch.webk.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.actors.combined.Circle;
import ch.webk.actors.combined.Fps;
import ch.webk.actors.screen.hud.Hud;
import ch.webk.utils.Constants;
import ch.webk.utils.actor.ActorGenerator;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.manager.GameManager;
import ch.webk.utils.manager.WorldManager;

public class DistanceJointStage extends GameStage {

    private Logger l = new Logger("DistanceJointStage", true);

    private Actor fps;
    private Circle circleStatic1;
    private Circle circleStatic2;
    private Circle circleStatic3;
    private Circle circleStatic4;
    private Circle circleStatic5;
    private Circle circleDynamic1;
    private Circle circleDynamic2;
    private Circle circleDynamic3;
    private Circle circleDynamic4;
    private Circle circleDynamic5;
    private Body defaultBody;

    public DistanceJointStage() {
        super();
        l.i("DistanceJointStage()");
        setUpFPS();
        setUpCircleStatic();
        setUpCircleDynamic();
        GameManager.getWorld().setGravity(new Vector2(0,-10));
        hud = new Hud();
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.VOLUME_UP) {
            circleDynamic1.getBody().applyLinearImpulse(new Vector2(-10,0),circleDynamic1.getBody().getWorldCenter(),true);
            return true;
        }
        if (keyCode == Input.Keys.VOLUME_DOWN) {
            circleDynamic5.getBody().applyLinearImpulse(new Vector2(10,0),circleDynamic5.getBody().getWorldCenter(),true);
            return true;
        }
        return super.keyDown(keyCode);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return super.touchDown(x, y, pointer, button);
    }

    private void setUpFPS() {
        fps = new Fps();
        GameManager.addActor(fps);
    }

    @Override
    public void act(float delta) {
        fps.setZIndex(fps.getZIndex()+5);
        super.act(delta);
    }

    private void setUpCircleStatic() {
        l.i("setUpCircleStatic()");
        float tol = 0.05f;
        float r = 1;
        float x = (Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN) / 2;
        float y = (Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN) - (r * 5);
        circleStatic1 = ActorGenerator.createCircle(x - (4 * r) - tol,y,r, BodyDef.BodyType.StaticBody);
        circleStatic2 = ActorGenerator.createCircle(x - (2 * r) - tol,y,r, BodyDef.BodyType.StaticBody);
        circleStatic3 = ActorGenerator.createCircle(x,y,r, BodyDef.BodyType.StaticBody);
        circleStatic4 = ActorGenerator.createCircle(x + (2 * r) + tol,y,r, BodyDef.BodyType.StaticBody);
        circleStatic5 = ActorGenerator.createCircle(x + (4 * r) + tol,y,r, BodyDef.BodyType.StaticBody);
    }

    private void setUpCircleDynamic() {
        l.i("setUpCircleDynamic()");
        float tol = 0.05f;
        float r = 1;
        float x = (Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN) / 2;
        float y = (r * 5);
        circleDynamic1 = ActorGenerator.createCircle(x - (4 * r) - tol,y,r, BodyDef.BodyType.DynamicBody);
        circleDynamic2 = ActorGenerator.createCircle(x - (2 * r) - tol,y,r, BodyDef.BodyType.DynamicBody);
        circleDynamic3 = ActorGenerator.createCircle(x,y,r, BodyDef.BodyType.DynamicBody);
        circleDynamic4 = ActorGenerator.createCircle(x + (2 * r) + tol,y,r, BodyDef.BodyType.DynamicBody);
        circleDynamic5 = ActorGenerator.createCircle(x + (4 * r) + tol,y,r, BodyDef.BodyType.DynamicBody);

        WorldManager.createDistanceJoint(circleStatic1.getBody(), Vector2.Zero, circleDynamic1.getBody(), Vector2.Zero, true);
        WorldManager.createDistanceJoint(circleStatic2.getBody(), Vector2.Zero, circleDynamic2.getBody(), Vector2.Zero, true);
        WorldManager.createDistanceJoint(circleStatic3.getBody(), Vector2.Zero, circleDynamic3.getBody(), Vector2.Zero, true);
        WorldManager.createDistanceJoint(circleStatic4.getBody(), Vector2.Zero, circleDynamic4.getBody(), Vector2.Zero, true);
        WorldManager.createDistanceJoint(circleStatic5.getBody(), Vector2.Zero, circleDynamic5.getBody(), Vector2.Zero, true);
    }

}
