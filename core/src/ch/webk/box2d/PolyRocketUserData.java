package ch.webk.box2d;

import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.enums.UserDataType;
import ch.webk.utils.BodyUtils;

public class PolyRocketUserData extends UserData {

    CollisionListener collisionListener;

    public PolyRocketUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.ROCKET;
    }

    public void setCollisionListener(CollisionListener collisionListener) {
        this.collisionListener = collisionListener;
    }

    @Override
    public void beginContact(Body body) {
        super.beginContact(body);
        if (collisionListener != null) {
            collisionListener.beginContact(body);
        }
        if (BodyUtils.bodyIsTarget(body)) {
            setDestroy(true);
        }
    }
}
