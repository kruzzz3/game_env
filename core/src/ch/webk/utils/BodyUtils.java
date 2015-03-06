package ch.webk.utils;

import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.UserData;
import ch.webk.enums.UserDataType;

public class BodyUtils {

    public static boolean bodyIsRunner(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.RUNNER;
    }

    public static boolean bodyIsWall(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.Wall;
    }

    public static boolean bodyIsSimple(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.SIMPLE;
    }

    public static boolean bodyIsTarget(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.TARGET;
    }

    public static boolean bodyIsBall(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.BALL;
    }

}
