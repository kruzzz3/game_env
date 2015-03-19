package ch.webk.box2d;

import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.enums.UserDataType;
import ch.webk.utils.BodyUtils;

public class BoxUserData extends UserData {

    public BoxUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.BOX;
    }

}
