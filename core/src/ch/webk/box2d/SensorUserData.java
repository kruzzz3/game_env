package ch.webk.box2d;

import ch.webk.enums.UserDataType;
import ch.webk.utils.helper.Logger;

public class SensorUserData extends UserData {

    private Logger l = new Logger("SensorUserData", true);

    public SensorUserData() {
        super(0, 0);
        userDataType = UserDataType.SENSOR;
    }

}
