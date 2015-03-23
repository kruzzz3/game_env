package ch.webk.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ch.webk.ch.webk.screens.GameScreen;
import ch.webk.enums.State;
import ch.webk.stages.GameStage;

public class WorldUtils {

    private static Logger l = new Logger("WorldUtils", true);

    private static World world;
    private static GameStage stage;
    private static OrthographicCamera camera;
    private static Box2DDebugRenderer renderer;
    private static Matrix4 debugMatrix;
    private static GameScreen screen;

    public static void setScreen(GameScreen screen) {
        WorldUtils.screen = screen;
    }

    public static boolean isRunning() {
        if (getScreen().getState() == State.RUN) {
            return true;
        }
        return false;
    }

    public static GameScreen getScreen() {
        return screen;
    }

    public static void setWorld(World world) {
        WorldUtils.world = world;
    }

    public static World getWorld() {
        return world;
    }

    public static void setStage(GameStage stage) {
        WorldUtils.stage = stage;
    }

    public static GameStage getStage() {
        return stage;
    }

    public static void setCamera(OrthographicCamera c) {
        WorldUtils.camera = c;
    }

    public static OrthographicCamera getCamera() {
        return camera;
    }

    public static void setRenderer(Box2DDebugRenderer renderer) {
        WorldUtils.renderer = renderer;
    }

    public static Box2DDebugRenderer getRenderer() {
        return renderer;
    }

    public static void setDebugMatrix(Matrix4 debugMatrix) {
        WorldUtils.debugMatrix = debugMatrix;
    }

    public static Matrix4 getDebugMatrix() {
        return debugMatrix;
    }

    public static void addActor(Actor a) {
        stage.addActor(a);
    }

    public static FixtureDef getFixtureDef(float density, float friction, float restitution) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        return fixtureDef;
    }

    public static Body createChainBody(FixtureDef fixtureDef, Vector2[] points) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        ChainShape shape = new ChainShape();
        shape.createChain(points);
        fixtureDef.shape = shape;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        //body.setTransform(GameMath.getCenter(points), 0);

        body.resetMassData();
        shape.dispose();
        return body;
    }

    public static Body getComplexBody(String key, FixtureDef fixtureDef, BodyDef.BodyType type, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(x, y));

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        fixtureDef.shape = shape;
        float w = width * Constants.WORLD_TO_SCREEN;
        float h = height * Constants.WORLD_TO_SCREEN;
        Vector2[][] vertices = VerticesManager.getVertices(key,ActorManager.getVertices(key),w,h);
        final int length = vertices.length;
        for (int i = 0; i < length; i++) {
            shape.set(vertices[i]);
            body.createFixture(fixtureDef);
        }

        body.resetMassData();
        shape.dispose();
        return body;
    }

    public static Body getRectBody(FixtureDef fixtureDef, BodyDef.BodyType type, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(x, y));

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        body.resetMassData();
        shape.dispose();
        return body;
    }

    public static Body getCircleBody(FixtureDef fixtureDef, BodyDef.BodyType type, float x, float y, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(x, y));

        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        body.resetMassData();
        shape.dispose();
        return body;
    }

    public static RevoluteJoint createRevoluteJoint(Body b1, Vector2 localAnchorA, Body b2, Vector2 localAnchorB, boolean collideConnected) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.initialize(b1,b2,b1.getWorldCenter());
        jointDef.localAnchorA.set(localAnchorA);
        jointDef.localAnchorB.set(localAnchorB);
        jointDef.collideConnected = collideConnected;
        return (RevoluteJoint) world.createJoint(jointDef);
    }

    public static DistanceJoint createDistanceJoint(Body b1, Vector2 localAnchorA, Body b2, Vector2 localAnchorB, boolean collideConnected) {
        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.initialize(b1, b2, b1.getWorldCenter(), b2.getWorldCenter());
        jointDef.localAnchorA.set(localAnchorA);
        jointDef.localAnchorB.set(localAnchorB);
        jointDef.collideConnected = collideConnected;
        return (DistanceJoint) WorldUtils.getWorld().createJoint(jointDef);
    }

    public static RopeJoint createRopeJoint(Body b1, Vector2 localAnchorA, Body b2, Vector2 localAnchorB, boolean collideConnected) {
        RopeJointDef jointDef = new RopeJointDef();
        jointDef.bodyA = b1;
        jointDef.bodyB = b2;
        jointDef.localAnchorA.set(localAnchorA);
        jointDef.localAnchorB.set(localAnchorB);
        jointDef.collideConnected = collideConnected;
        return (RopeJoint) WorldUtils.getWorld().createJoint(jointDef);
    }

}