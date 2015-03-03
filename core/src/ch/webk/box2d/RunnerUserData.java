package ch.webk.box2d;

import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.enums.UserDataType;
import ch.webk.utils.BodyUtils;

public class RunnerUserData extends UserData {

    public RunnerUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.RUNNER;
    }

    @Override
    public void beginContact(Body body) {
        if (BodyUtils.bodyIsWall(body)) {
            //destroy = true;
        }
    }
}
