package ch.webk.box2d;

import ch.webk.enums.UserDataType;

public class PolyRocketUserData extends UserData {

    public PolyRocketUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.ROCKET;
    }

}
