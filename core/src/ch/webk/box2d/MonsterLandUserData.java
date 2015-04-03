package ch.webk.box2d;

import ch.webk.enums.UserDataType;
import ch.webk.utils.helper.Logger;

public class MonsterLandUserData extends UserData {

    private Logger l = new Logger("MonsterLandUserData", true);

    public MonsterLandUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.MONSTER_LAND;
    }

}
