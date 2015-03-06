package ch.webk.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class GameMath {

    public static float transformToScreen(float n) {
        return Constants.WORLD_TO_SCREEN * n;
    }

    public static float transformToWorld(float n) {
        return n / Constants.WORLD_TO_SCREEN;
    }

    public static float getRandomFloat(float min, float max) {
        Random r = new Random();
        float f = r.nextFloat() * (max - min) + min;
        return f;
    }

    public static Vector2 relVector(Vector2 source, Vector2 target) {
       return relVector(source.x,source.y,target.x,target.y);
    }

    public static Vector2 relVector(float xSource, float ySource, float xTarget, float yTarget) {
        float turnX = 1;
        if (xTarget < xSource) {
            turnX = -1;
        }
        float turnY = 1;
        if (yTarget < ySource) {
            turnY = -1;
        }
        return new Vector2(turnX * Math.abs(xTarget - xSource), turnY * Math.abs(yTarget - ySource));
    }
}
