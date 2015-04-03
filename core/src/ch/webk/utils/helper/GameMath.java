package ch.webk.utils.helper;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import ch.webk.utils.Constants;

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

    public static float getDistance(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        return (float) Math.sqrt(dx*dx + dy*dy);
    }

    public static float getAngleRadians(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        return (float) Math.atan2(dy, dx);
    }

    public static Vector2 rotateVector2Radians(Vector2 v, float radians) {
        float ca = MathUtils.cos(radians);
        float sa = MathUtils.sin(radians);
        return new Vector2(ca*v.x - sa*v.y, sa*v.x + ca*v.y);
    }

    public static float getAngleDegree(float x1, float y1, float x2, float y2) {
        return getAngleRadians(x1, y1, x2, y2) * MathUtils.radiansToDegrees;
    }

    public static Vector2 getCenter(Vector2[] points) {
        float x = 0;
        float y = 0;
        for (Vector2 point : points) {
            x += point.x;
            y += point.y;
        }
        return new Vector2(x / points.length, y / points.length);
    }
}
