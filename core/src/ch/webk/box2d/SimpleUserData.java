package ch.webk.box2d;

import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.enums.UserDataType;
import ch.webk.utils.BodyUtils;
import ch.webk.utils.Logger;

public class SimpleUserData extends UserData {

    private Logger l = new Logger("SimpleUserData", true);

    public SimpleUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.SIMPLE;
    }

}
