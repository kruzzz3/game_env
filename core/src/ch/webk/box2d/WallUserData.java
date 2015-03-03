package ch.webk.box2d;

import ch.webk.enums.UserDataType;

public class WallUserData extends UserData {

    public WallUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.Wall;
    }

}