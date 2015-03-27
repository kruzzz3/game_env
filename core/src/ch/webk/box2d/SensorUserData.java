package ch.webk.box2d;

import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.enums.UserDataType;
import ch.webk.utils.BodyUtils;
import ch.webk.utils.Logger;

public class SensorUserData extends UserData {

    private Logger l = new Logger("SensorUserData", true);

    public SensorUserData() {
        super(0, 0);
        userDataType = UserDataType.SENSOR;
    }

}
