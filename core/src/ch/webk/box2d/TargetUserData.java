package ch.webk.box2d;

import ch.webk.enums.UserDataType;

public class TargetUserData extends UserData {

    public TargetUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.TARGET;
    }

}
