package ch.webk.actors.combined.complex;

import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

import java.util.ArrayList;

import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.utils.ActorGenerator;
import ch.webk.utils.WorldUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class Car {

    ArrayList<GameCombinedActor> actors;

    private GameCombinedActor carBody;
    private GameCombinedActor carWheel1;
    private GameCombinedActor carWheel2;

    RevoluteJoint j1;
    RevoluteJoint j2;

    public Car() {
        actors = new ArrayList<GameCombinedActor>();

        float carBodyX = 4;
        float carBodyY = 4;
        float carBodyW = 5;
        float carBodyH = 2;

        carBody = ActorGenerator.createCarBody(carBodyX,carBodyY,carBodyW,carBodyH);

        float carWheel1R = 0.6f;
        float carWheel1X = carBodyX-(carBodyW/2) + (carWheel1R * 1.5f);
        float carWheel1Y = carBodyY-(carBodyH/2) + (carWheel1R / 4);

        carWheel1 = ActorGenerator.createCarWheel(carWheel1X,carWheel1Y,carWheel1R);

        float carWheel2R = 0.6f;
        float carWheel2X = carBodyX+(carBodyW/2) - (carWheel1R * 1.5f);
        float carWheel2Y = carBodyY-(carBodyH/2) + (carWheel1R / 4);


        carWheel2 = ActorGenerator.createCarWheel(carWheel2X,carWheel2Y,carWheel2R);

        j1 = createJoint(carWheel1.getBody(), carBody.getBody());
        j2 = createJoint(carWheel2.getBody(), carBody.getBody());

    }

    public float getScreenX() {
        return carBody.getScreenX();
    }

    public float getScreenY() {
        return carBody.getScreenY();
    }

    public void drive() {
        j1.setMotorSpeed(50);
        j2.setMotorSpeed(50);
    }

    public void driveBack() {
        j1.setMotorSpeed(-50);
        j2.setMotorSpeed(-50);
    }

    private RevoluteJoint createJoint(Body b1, Body b2) {
        RevoluteJointDef jd = new RevoluteJointDef();
        jd.initialize(b1,b2,b1.getWorldCenter());
        jd.maxMotorTorque= 10;
        jd.enableMotor = true;
        jd.collideConnected=false;
        return (RevoluteJoint) WorldUtils.getWorld().createJoint(jd);
    }

}
