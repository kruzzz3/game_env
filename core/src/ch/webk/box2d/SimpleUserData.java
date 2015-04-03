package ch.webk.box2d;

import ch.webk.enums.UserDataType;
import ch.webk.utils.helper.Logger;

public class SimpleUserData extends UserData {

    private Logger l = new Logger("SimpleUserData", true);

    public SimpleUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.SIMPLE;
    }

}
