package ch.webk.utils.helper;

import ch.webk.box2d.UserData;
import ch.webk.enums.UserDataType;

import com.badlogic.gdx.physics.box2d.Body;

public class UDM {

    //****************************************************************************************
    // get UserData
    //****************************************************************************************

    public static UserData getUserData(Body body) throws NullPointerException{
        try {
            return (UserData) body.getUserData();
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    //****************************************************************************************
    // destroy a body
    //****************************************************************************************

    public static void setDestroy(Body body) {
        try {
            body.setActive(false);
            getUserData(body).setDestroy(true);
        } catch (Exception e) {}
    }

    //****************************************************************************************
    // get UserDataTypes
    //****************************************************************************************

    private static UserData getUD(Body body) {
        try { return (UserData) body.getUserData(); } catch (Exception e) {}
        return null;
    }

    public static boolean bodyIsRunner(Body body) {
        UserData userData = getUD(body);
        return userData != null && userData.getUserDataType() == UserDataType.RUNNER;
    }

    public static boolean bodyIsWall(Body body) {
        UserData userData = getUD(body);
        return userData != null && userData.getUserDataType() == UserDataType.Wall;
    }

    public static boolean bodyIsSimple(Body body) {
        UserData userData = getUD(body);
        return userData != null && userData.getUserDataType() == UserDataType.SIMPLE;
    }

    public static boolean bodyIsTarget(Body body) {
        UserData userData = getUD(body);
        return userData != null && userData.getUserDataType() == UserDataType.TARGET;
    }

    public static boolean bodyIsBall(Body body) {
        UserData userData = getUD(body);
        return userData != null && userData.getUserDataType() == UserDataType.BALL;
    }

    public static boolean bodyIsBox(Body body) {
        UserData userData = getUD(body);
        return userData != null && userData.getUserDataType() == UserDataType.BOX;
    }
}
