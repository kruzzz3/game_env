package ch.webk.box2d;

import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.enums.UserDataType;
import ch.webk.utils.helper.UDM;

public class BallUserData extends UserData {

    public BallUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.BALL;
    }

}
