package ch.webk.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import box2dLight.RayHandler;
import ch.webk.utils.Constants;
import ch.webk.utils.WorldUtils;

public class WorldConeLight extends AWorldLight {

    private float coneDegree;

    public WorldConeLight(float directionDegree, float coneDegree) {
        super(WorldUtils.getRayHandler(), Color.WHITE, 15f, 0f, 0f, directionDegree);
        setConeDegree(coneDegree);
    }

    public void update () {
        updateBody();
        if (dirty) setEndPoints();

        if (cull()) return;
        if (staticLight && !dirty) return;

        dirty = false;
        updateMesh();
    }

    public void setDirection(float direction) {
        this.direction = direction;
        dirty = true;
    }

    public float getConeDegree() {
        return coneDegree;
    }

    public void setConeDegree(float coneDegree) {
        this.coneDegree = MathUtils.clamp(coneDegree, 0f, 180f);
        dirty = true;
    }


    @Override
    public void setDistance(float dist) {
        distance = dist * Constants.WORLD_TO_SCREEN;
        dirty = true;
    }

    /** Updates lights sector basing on distance, direction and coneDegree **/
    protected void setEndPoints() {
        for (int i = 0; i < rayNum; i++) {
            float angle = direction + coneDegree - 2f * coneDegree * i
                    / (rayNum - 1f);
            final float s = sin[i] = MathUtils.sinDeg(angle);
            final float c = cos[i] = MathUtils.cosDeg(angle);
            endX[i] = distance * c;
            endY[i] = distance * s;
        }
    }

}
