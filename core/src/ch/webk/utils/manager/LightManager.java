package ch.webk.utils.manager;

import com.badlogic.gdx.graphics.Color;

import ch.webk.lights.box2dLight.RayHandler;
import ch.webk.utils.helper.GameMath;

public class LightManager {

    //****************************************************************************************
    // ray handler
    //****************************************************************************************

    private static RayHandler rayHandler;

    public static void initRayHandler() {
        if (rayHandler != null) {
            LightManager.rayHandler.dispose();
            LightManager.rayHandler = null;
        }

        LightManager.rayHandler = new RayHandler(GameManager.getWorld());
        LightManager.rayHandler.setShadows(true);
        LightManager.rayHandler.setCulling(true);
        LightManager.rayHandler.setBlur(true);
    }

    public static void disposeRayHandler() {
        if (rayHandler != null) {
            LightManager.rayHandler.dispose();
            LightManager.rayHandler = null;
        }
    }

    public static RayHandler getRayHandler() {
        return rayHandler;
    }

    //****************************************************************************************
    // color
    //****************************************************************************************

    public static Color setAlpha(Color c, float alpha) {
        return new Color(c.r,c.g,c.b,alpha);
    }

    public static Color getRandColor() {
        return new Color(GameMath.getRandomFloat(0, 1), GameMath.getRandomFloat(0,1), GameMath.getRandomFloat(0,1), 0.9f);
    }

    public static Color getClearColor() {
        return setAlpha(Color.BLACK, 1f);
    }

}
