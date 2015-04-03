package ch.webk.box2d;

import ch.webk.enums.UserDataType;

public class BoxUserData extends UserData {

    public BoxUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.BOX;
    }

}
