package ch.webk.utils;

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
}
