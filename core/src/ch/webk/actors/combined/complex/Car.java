package ch.webk.actors.combined.complex;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

import java.util.ArrayList;

import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.utils.ActorGenerator;
import ch.webk.utils.WorldUtils;

public class Car {

    ArrayList<GameCombinedActor> actors = new ArrayList<GameCombinedActor>();;

    private GameCombinedActor carBody;
    private GameCombinedActor carWheel1;
    private GameCombinedActor carWheel2;

    private float carBodyX = 4;
    private float carBodyY = 4;
    private float carBodyW = 5;
    private float carBodyH = 2;
    private float carWheelR = 0.6f;

    RevoluteJoint j1;
    RevoluteJoint j2;

    public Car() {
        carBody = ActorGenerator.createCarBody(carBodyX, carBodyY, carBodyW, carBodyH);
        actors.add(carBody);

        // Wheel 1
        float localAnchorAX = -(carBodyW/2) + (carWheelR * 1.5f);
        float localAnchorAY = -(carBodyH/2) + (carWheelR / 4);
        float carWheel1X = carBodyX + localAnchorAX;
        float carWheel1Y = carBodyY + localAnchorAY;
        carWheel1 = ActorGenerator.createCarWheel(carWheel1X, carWheel1Y, carWheelR);
        actors.add(carWheel1);

        Vector2 localAnchorA = new Vector2(localAnchorAX, localAnchorAY);
        j1 = WorldUtils.createRevoluteJoint(carWheel1.getBody(), Vector2.Zero, carBody.getBody(), localAnchorA, false);
        j1.enableMotor(true);
        j1.setMaxMotorTorque(15);

        // Wheel 2
        float localAnchorBX = (carBodyW/2) - (carWheelR * 1.5f);
        float localAnchorBY = -(carBodyH/2) + (carWheelR / 4);
        float carWheel2X = carBodyX + localAnchorBX;
        float carWheel2Y = carBodyY + localAnchorBY;
        carWheel2 = ActorGenerator.createCarWheel(carWheel2X, carWheel2Y, carWheelR);
        actors.add(carWheel2);

        Vector2 localAnchorA2 = new Vector2(localAnchorBX, localAnchorBY);
        j2 = WorldUtils.createRevoluteJoint(carWheel2.getBody(), Vector2.Zero, carBody.getBody(), localAnchorA2, false);
        j2.enableMotor(true);
        j2.setMaxMotorTorque(15);

    }

    public float getScreenX() {
        return carBody.getScreenX();
    }

    public float getScreenY() {
        return carBody.getScreenY();
    }

    public void drive() {
        j1.setMotorSpeed(200);
        j2.setMotorSpeed(200);
    }

    public void driveBack() {
        j1.setMotorSpeed(-200);
        j2.setMotorSpeed(-200);
    }

}
