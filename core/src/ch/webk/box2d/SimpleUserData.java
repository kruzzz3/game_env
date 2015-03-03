package ch.webk.box2d;

import ch.webk.enums.UserDataType;

public class SimpleUserData extends UserData {

    public SimpleUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.SIMPLE;
    }

}
