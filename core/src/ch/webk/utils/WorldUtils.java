package ch.webk.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ch.webk.box2d.WallUserData;

public class WorldUtils {

    private static Logger l = new Logger("WorldUtils", true);

    private static World world;
    private static Stage stage;
    private static OrthographicCamera camera;
    private static Box2DDebugRenderer renderer;
    private static Matrix4 debugMatrix;

    public static void setWorld(World world) {
        WorldUtils.world = world;
    }

    public static World getWorld() {
        return world;
    }

    public static void setStage(Stage stage) {
        WorldUtils.stage = stage;
    }

    public static Stage getStage() {
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

    public static Body getWall(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(x + width / 2, y + height / 2));

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = getFixtureDef(1f, 0f, 0f);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        body.setUserData(new WallUserData(width, height));

        body.resetMassData();
        shape.dispose();
        return body;
    }

}