package ch.webk.actors.combined.complex;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import java.util.ArrayList;

import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.box2d.CollisionListener;
import ch.webk.utils.ActorGenerator;
import ch.webk.utils.WorldUtils;

public class Car {

    ArrayList<GameCombinedActor> actors = new ArrayList<GameCombinedActor>();;

    private GameCombinedActor carBody;
    private GameCombinedActor carWheel1;
    private GameCombinedActor carWheel2;

    RevoluteJoint j1;
    RevoluteJoint j2;

    public Car() {
        float carBodyX = 4;
        float carBodyY = 4;
        float carBodyW = 5;
        float carBodyH = 2;

        carBody = ActorGenerator.createCarBody(carBodyX,carBodyY,carBodyW,carBodyH);

        carBody.getUserData().setCollisionListener(new CollisionListener() {
            @Override
            public void beginContact(Body body) {

            }

            @Override
            public void endContact(Body body) {

            }
        });

        float carWheel1R = 0.6f;
        float carWheel1X = carBodyX-(carBodyW/2) + (carWheel1R * 1.5f);
        float carWheel1Y = carBodyY-(carBodyH/2) + (carWheel1R / 4);

        carWheel1 = ActorGenerator.createCarWheel(carWheel1X,carWheel1Y,carWheel1R);

        float carWheel2R = 0.6f;
        float carWheel2X = carBodyX+(carBodyW/2) - (carWheel2R * 1.5f);
        float carWheel2Y = carBodyY-(carBodyH/2) + (carWheel2R / 4);


        carWheel2 = ActorGenerator.createCarWheel(carWheel2X,carWheel2Y,carWheel2R);


        float localAnchorA1X = -(carBodyW/2) + (carWheel1R * 1.5f);
        float localAnchorA1Y = -(carBodyH/2) + (carWheel1R / 4);
        Vector2 localAnchorA1 = new Vector2(localAnchorA1X, localAnchorA1Y);
        j1 = createJoint(carWheel1.getBody(), Vector2.Zero, carBody.getBody(), localAnchorA1);


        float localAnchorA2X = (carBodyW/2) - (carWheel2R * 1.5f);
        float localAnchorA2Y = -(carBodyH/2) + (carWheel2R / 4);
        Vector2 localAnchorA2 = new Vector2(localAnchorA2X, localAnchorA2Y);
        j2 = createJoint(carWheel2.getBody(), Vector2.Zero, carBody.getBody(), localAnchorA2);

        carBody.getUserData().addJointReference(j1);
        carWheel1.getUserData().addJointReference(j1);
        carBody.getUserData().addJointReference(j2);
        carWheel2.getUserData().addJointReference(j2);
        actors.add(carBody);
        actors.add(carWheel1);
        actors.add(carWheel2);
    }

    public float getScreenX() {
        return carBody.getScreenX();
    }

    public float getScreenY() {
        return carBody.getScreenY();
    }

    public void drive() {
        j1.setMotorSpeed(100);
        j2.setMotorSpeed(100);
    }

    public void driveBack() {
        j1.setMotorSpeed(-200);
        j2.setMotorSpeed(-200);
    }

    private RevoluteJoint createJoint(Body b1, Vector2 localAnchorA, Body b2, Vector2 localAnchorB) {
        RevoluteJointDef jd = new RevoluteJointDef();
        jd.initialize(b1,b2,b1.getWorldCenter());

        jd.maxMotorTorque = 15;
        jd.localAnchorA.set(localAnchorA);
        jd.localAnchorB.set(localAnchorB);
        jd.enableMotor = true;
        jd.collideConnected=false;
        return (RevoluteJoint) WorldUtils.getWorld().createJoint(jd);
    }

}
