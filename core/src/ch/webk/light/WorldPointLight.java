package ch.webk.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import box2dLight.RayHandler;

public class WorldPointLight extends AWorldLight {

    public WorldPointLight(RayHandler rayHandler, int rays) {
        this(rayHandler, rays, Color.WHITE, 15f, 0f, 0f);
    }

    public WorldPointLight(RayHandler rayHandler, int rays, Color color, float distance, float x, float y) {
        super(rayHandler, color, distance, x, y);
    }

    public void update () {
        updateBody();
        if (dirty) setEndPoints();

        if (cull()) return;
        if (staticLight && !dirty) return;

        dirty = false;
        updateMesh();
    }

    @Override
    public void setDistance(float dist) {
        distance = dist;
    }

    /** Updates light basing on it's distance and rayNum **/
    public void setEndPoints() {
        float angleNum = 360f / (rayNum - 1);
        for (int i = 0; i < rayNum; i++) {
            final float angle = angleNum * i;
            sin[i] = MathUtils.sinDeg(angle);
            cos[i] = MathUtils.cosDeg(angle);
            endX[i] = distance * cos[i];
            endY[i] = distance * sin[i];
        }
    }

    /** Not applicable for this light type **/
    @Deprecated
    @Override
    public void setDirection(float directionDegree) {
    }
}
