package ch.webk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.actors.combined.Circle;
import ch.webk.actors.combined.Fps;
import ch.webk.actors.combined.Wall;
import ch.webk.utils.ActorGenerator;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class RopeJointStage extends GameStage {

    private Logger l = new Logger("RopeJointStage", true);

    private Wall wall;
    private Actor fps;
    private Circle circleStatic1;
    private Circle circleStatic2;
    private Circle circleStatic3;
    private Circle circleStatic4;

    private Circle circleDynamic1;
    private Circle circleDynamic2;
    private Circle circleDynamic3;
    private Circle circleDynamic4;

    private Body defaultBody;

    public RopeJointStage() {
        super();
        l.i("RopeJointStage()");
        setUpFPS();
        //setUpWall();
        setUpCircleStatic();
        setUpCircleDynamic();
        Gdx.input.setInputProcessor(this);
        startDebugRenderer();
        WorldUtils.getWorld().setGravity(new Vector2(0,-10));
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.VOLUME_UP) {
            circleDynamic1.getBody().applyLinearImpulse(new Vector2(-10,-10),circleDynamic1.getBody().getWorldCenter(),true);
            return true;
        }
        if (keyCode == Input.Keys.VOLUME_DOWN) {
            circleDynamic2.getBody().applyLinearImpulse(new Vector2(10,10),circleDynamic1.getBody().getWorldCenter(),true);
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
        WorldUtils.addActor(fps);
    }

    @Override
    public void act(float delta) {
        fps.setZIndex(fps.getZIndex()+5);
        super.act(delta);
    }

    private void setUpWall() {
        float width = GameMath.transformToWorld(Constants.APP_WIDTH);
        float height = GameMath.transformToWorld(Constants.APP_HEIGHT);
        float thickness = GameMath.transformToWorld(Constants.WORLD_TO_SCREEN / 4);

        wall = new Wall(WorldUtils.getWall(0f, height - thickness, width, thickness));
        WorldUtils.addActor(wall);

        wall = new Wall(WorldUtils.getWall(0f, 0f, thickness, height));
        WorldUtils.addActor(wall);

        wall = new Wall(WorldUtils.getWall(width - thickness, 0f, thickness, height));
        WorldUtils.addActor(wall);

        wall = new Wall(WorldUtils.getWall(0f, 0f, width, thickness));
        WorldUtils.addActor(wall);
    }

    private void setUpCircleStatic() {
        l.i("setUpCircleStatic()");
        float tol = 0.05f;
        float r = 1;
        float x = (Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN) / 2;
        float y = (Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN) - (r * 2);
        circleStatic1 = ActorGenerator.createCircle(x-3,y,r, BodyDef.BodyType.StaticBody);
        circleStatic2 = ActorGenerator.createCircle(x-1,y,r, BodyDef.BodyType.StaticBody);
        circleStatic3 = ActorGenerator.createCircle(x+1,y,r, BodyDef.BodyType.StaticBody);
        circleStatic4 = ActorGenerator.createCircle(x+3,y,r, BodyDef.BodyType.StaticBody);

    }

    private void setUpCircleDynamic() {
        l.i("setUpCircleDynamic()");
        float tol = 0.05f;
        float r = 1;
        float x = (Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN) / 2;
        float y = (r * 5);
        circleDynamic1 = ActorGenerator.createCircle(x-4,y,r, BodyDef.BodyType.DynamicBody);
        circleDynamic2 = ActorGenerator.createCircle(x-2,y,r, BodyDef.BodyType.DynamicBody);
        circleDynamic3 = ActorGenerator.createCircle(x+2,y,r, BodyDef.BodyType.DynamicBody);
        circleDynamic4 = ActorGenerator.createCircle(x+4,y,r, BodyDef.BodyType.DynamicBody);

        createElasticJoint(circleStatic1.getBody(), circleDynamic1.getBody(), 1,6,12);
        createElasticJoint(circleStatic2.getBody(), circleDynamic2.getBody(), 0.7f,6,12);
        createElasticJoint(circleStatic3.getBody(), circleDynamic3.getBody(), 0.5f,6,12);
        createElasticJoint(circleStatic4.getBody(), circleDynamic4.getBody(), 0.3f,6,12);


    }

    private void createElasticJoint(Body b1, Body b2, float bouncy, float minLength, float maxLength) {
        DistanceJoint distanceJoint = WorldUtils.createDistanceJoint(b1, Vector2.Zero, b2, Vector2.Zero, true);
        distanceJoint.setFrequency(bouncy);
        distanceJoint.setLength(minLength);

        RopeJoint ropeJoint = WorldUtils.createRopeJoint(b1, Vector2.Zero, b2, Vector2.Zero, true);
        ropeJoint.setMaxLength(maxLength);
    }

}
