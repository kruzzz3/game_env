package ch.webk.utils;

import ch.webk.box2d.UserData;
import com.badlogic.gdx.physics.box2d.Body;

public class UDM {

    public static UserData getUserData(Body body) throws NullPointerException{
        try {
            return (UserData) body.getUserData();
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    public static void setDestroy(Body body) {
        try {
            body.setActive(false);
            getUserData(body).setDestroy(true);
        } catch (Exception e) {}
    }
}
