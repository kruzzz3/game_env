package ch.webk.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import box2dLight.RayHandler;
import ch.webk.utils.Constants;
import ch.webk.utils.WorldUtils;

public class WorldPointLight extends AWorldLight {

    public WorldPointLight() {
        super(WorldUtils.getRayHandler(), new Color(0.9f,0.9f,0.9f,0.9f), 15f, 0f, 0f);
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
        distance = dist * Constants.WORLD_TO_SCREEN;
        dirty = true;
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
    public void setDirection(float directionDegree) {}
}
