package ch.webk.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import ch.webk.actors.combined.Ball;
import ch.webk.actors.combined.Circle;
import ch.webk.actors.combined.PolyRocket;
import ch.webk.actors.combined.PolyStairs;
import ch.webk.actors.combined.PolyStar;
import ch.webk.actors.combined.PolyTriangle;
import ch.webk.actors.combined.Runner;
import ch.webk.actors.combined.Target;
import ch.webk.actors.combined.complex.CarBody;
import ch.webk.actors.combined.complex.CarWheel;
import ch.webk.actors.lines.ChainExample1;
import ch.webk.box2d.BallUserData;
import ch.webk.box2d.PolyRocketUserData;
import ch.webk.box2d.SimpleUserData;
import ch.webk.box2d.TargetUserData;

public class ActorGenerator {

    private static Logger l = new Logger("ActorGenerator", true);

    public static Runner createRunner(float posX, float posY, float width, float height) {
        l.i("createRunner() posX=" + posX + ", posY=" + posY + ", width=" + width + ", height="+height);

        FixtureDef fixtureDef = WorldUtils.getFixtureDef(0.1f, 0.1f, 0f);
        Body body = WorldUtils.getRectBody(fixtureDef, BodyDef.BodyType.DynamicBody, posX, posY, width, height);
        body.setUserData(new SimpleUserData(width, height));

        Runner actor = new Runner(body);
        WorldUtils.addActor(actor);
        return actor;
    }

    public static Ball createBall(float posX, float posY, float radius) {
        l.i("createRunner() posX=" + posX + ", posY=" + posY + ", radius=" + radius);

        FixtureDef fixtureDef = WorldUtils.getFixtureDef(0.1f, 0.1f, 0f);
        Body body = WorldUtils.getCircleBody(fixtureDef, BodyDef.BodyType.DynamicBody, posX, posY, radius);
        body.setUserData(new BallUserData(radius * 2, radius * 2));

        Ball actor = new Ball(body);
        WorldUtils.addActor(actor);
        return actor;
    }

    public static PolyStar createPolyStar(float posX, float posY, float width, float height) {
        l.i("createPolyStar() posX=" + posX + ", posY=" + posY + ", width=" + width + ", height="+height);

        FixtureDef fixtureDef = WorldUtils.getFixtureDef(0.1f, 0.1f, 0.5f);
        Body body = WorldUtils.getComplexBody(Constants.POLY_STAR, fixtureDef, BodyDef.BodyType.DynamicBody, posX, posY, width, height);
        body.setUserData(new SimpleUserData(width, height));

        PolyStar actor = new PolyStar(body);
        WorldUtils.addActor(actor);
        return actor;
    }

    public static PolyStairs createPolyStairs(float posX, float posY, float width, float height) {
        l.i("createPolyStairs() posX=" + posX + ", posY=" + posY + ", width=" + width + ", height="+height);

        FixtureDef fixtureDef = WorldUtils.getFixtureDef(0.1f, 0.1f, 0.5f);
        Body body = WorldUtils.getComplexBody(Constants.POLY_STAIRS, fixtureDef, BodyDef.BodyType.DynamicBody, posX, posY, width, height);
        body.setUserData(new SimpleUserData(width, height));

        PolyStairs actor = new PolyStairs(body);
        WorldUtils.addActor(actor);
        return actor;
    }

    public static PolyTriangle createPolyTriangle(float posX, float posY, float width, float height) {
        l.i("createPolyTriangle() posX=" + posX + ", posY=" + posY + ", width=" + width + ", height="+height);

        FixtureDef fixtureDef = WorldUtils.getFixtureDef(0.1f, 0.1f, 0.5f);
        Body body = WorldUtils.getComplexBody(Constants.POLY_TRIANGLE, fixtureDef, BodyDef.BodyType.DynamicBody, posX, posY, width, height);
        body.setUserData(new SimpleUserData(width, height));

        PolyTriangle actor = new PolyTriangle(body);
        WorldUtils.addActor(actor);
        return actor;
    }

    public static PolyRocket createPolyRocket(float posX, float posY, float width, float height, Target target) {
        l.i("createPolyRocket() posX=" + posX + ", posY=" + posY + ", width=" + width + ", height="+height);

        FixtureDef fixtureDef = WorldUtils.getFixtureDef(0.1f, 0.1f, 0f);
        Body body = WorldUtils.getComplexBody(Constants.POLY_ROCKET, fixtureDef, BodyDef.BodyType.DynamicBody, posX, posY, width, height);
        body.setUserData(new PolyRocketUserData(width, height));

        PolyRocket actor = new PolyRocket(body, target);
        WorldUtils.addActor(actor);
        return actor;
    }

    public static Target createTarget(float posX, float posY, float radius) {
        l.i("createTarget() posX=" + posX + ", posY=" + posY + ", radius=" + radius);

        FixtureDef fixtureDef = WorldUtils.getFixtureDef(0.1f, 0.1f, 0f);
        Body body = WorldUtils.getCircleBody(fixtureDef, BodyDef.BodyType.StaticBody, posX, posY, radius);
        body.setUserData(new TargetUserData(radius * 2, radius * 2));

        Target actor = new Target(body);
        WorldUtils.addActor(actor);
        return actor;
    }

    public static Circle createCircle(float posX, float posY, float radius, BodyDef.BodyType type) {
        l.i("createCircle() posX=" + posX + ", posY=" + posY + ", radius=" + radius);

        FixtureDef fixtureDef = WorldUtils.getFixtureDef(1f, 0f, 0f);
        Body body = WorldUtils.getCircleBody(fixtureDef, type, posX, posY, radius);
        body.setUserData(new SimpleUserData(radius * 2, radius * 2));

        Circle actor = new Circle(body);
        WorldUtils.addActor(actor);
        return actor;
    }

    public static CarBody createCarBody(float posX, float posY, float width, float height) {
        l.i("createCarBody() posX=" + posX + ", posY=" + posY + ", width=" + width + ", height="+height);

        FixtureDef fixtureDef = WorldUtils.getFixtureDef(1f, 0.2f, 0f);
        Body body = WorldUtils.getComplexBody(Constants.CAR_BODY, fixtureDef, BodyDef.BodyType.DynamicBody, posX, posY, width, height);
        body.setUserData(new SimpleUserData(width, height));

        CarBody actor = new CarBody(body);
        WorldUtils.addActor(actor);
        return actor;
    }

    public static CarWheel createCarWheel(float posX, float posY, float radius) {
        l.i("createCarWheel() posX=" + posX + ", posY=" + posY + ", radius=" + radius);

        FixtureDef fixtureDef = WorldUtils.getFixtureDef(1f, 1f, 0.1f);
        Body body = WorldUtils.getCircleBody(fixtureDef, BodyDef.BodyType.DynamicBody, posX, posY, radius);
        body.setUserData(new SimpleUserData(radius * 2, radius * 2));

        CarWheel actor = new CarWheel(body);
        WorldUtils.addActor(actor);
        return actor;
    }

    public static ChainExample1 createChainExample1(Vector2[] points) {
        l.i("createChainExample1()");

        FixtureDef fixtureDef = WorldUtils.getFixtureDef(1f, 0.2f, 0);
        Body body = WorldUtils.createChainBody(fixtureDef, points);
        body.setUserData(new SimpleUserData(0,0));

        ChainExample1 actor = new ChainExample1(body, points);
        WorldUtils.addActor(actor);
        return actor;
    }

}
