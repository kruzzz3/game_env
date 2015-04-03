package ch.webk.utils.manager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import ch.webk.box2d.SimpleUserData;
import ch.webk.utils.Constants;
import ch.webk.utils.manager.ActorManager;
import ch.webk.utils.actor.VerticesSolver;
import ch.webk.utils.manager.GameManager;

public class WorldManager {

    private static Body getDummyBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(0, 0));

        Body dummyBody = GameManager.getWorld().createBody(bodyDef);

        FixtureDef fixtureDef = getFixtureDef(1, 0, 0);
        fixtureDef.isSensor = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);
        fixtureDef.shape = shape;
        dummyBody.createFixture(fixtureDef);
        dummyBody.setUserData(new SimpleUserData(0,0));

        dummyBody.resetMassData();
        shape.dispose();
        return dummyBody;
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

        Body body = GameManager.getWorld().createBody(bodyDef);
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

        Body body = GameManager.getWorld().createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        fixtureDef.shape = shape;
        float w = width * Constants.WORLD_TO_SCREEN;
        float h = height * Constants.WORLD_TO_SCREEN;
        Vector2[][] vertices = VerticesSolver.getVertices(key, ActorManager.getVertices(key), w, h);
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

        Body body = GameManager.getWorld().createBody(bodyDef);

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

        Body body = GameManager.getWorld().createBody(bodyDef);

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
        return (RevoluteJoint) GameManager.getWorld().createJoint(jointDef);
    }

    public static DistanceJoint createDistanceJoint(Body b1, Vector2 localAnchorA, Body b2, Vector2 localAnchorB, boolean collideConnected) {
        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.initialize(b1, b2, b1.getWorldCenter(), b2.getWorldCenter());
        jointDef.localAnchorA.set(localAnchorA);
        jointDef.localAnchorB.set(localAnchorB);
        jointDef.collideConnected = collideConnected;
        return (DistanceJoint) GameManager.getWorld().createJoint(jointDef);
    }

    public static MouseJoint createMouseJoint(Body b, boolean collideConnected) {
        MouseJointDef jointDef = new MouseJointDef();
        jointDef.bodyA = getDummyBody();
        jointDef.bodyB = b;
        jointDef.dampingRatio = 0.2f;
        jointDef.frequencyHz = 20f;
        jointDef.maxForce = (float) (2000.0f * b.getMass());
        jointDef.collideConnected= collideConnected;
        jointDef.target.set(b.getWorldCenter());
        return (MouseJoint) GameManager.getWorld().createJoint(jointDef);
    }

    public static RopeJoint createRopeJoint(Body b1, Vector2 localAnchorA, Body b2, Vector2 localAnchorB, boolean collideConnected) {
        RopeJointDef jointDef = new RopeJointDef();
        jointDef.bodyA = b1;
        jointDef.bodyB = b2;
        jointDef.localAnchorA.set(localAnchorA);
        jointDef.localAnchorB.set(localAnchorB);
        jointDef.collideConnected = collideConnected;
        return (RopeJoint) GameManager.getWorld().createJoint(jointDef);
    }

}