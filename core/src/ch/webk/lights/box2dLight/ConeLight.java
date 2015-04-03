package ch.webk.lights.box2dLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.utils.Constants;

/**
 * Light shaped as a circle's sector with given radius, direction and angle
 * 
 * <p>Extends {@link PositionalLight}
 * 
 * @author kalle_h
 */
public class ConeLight extends PositionalLight {

	float coneDegree;

	/**
	 * Creates light shaped as a circle's sector with given radius, direction and arc angle
	 * 
	 * @param rayHandler
	 *            not {@code null} instance of RayHandler
	 * @param rays
	 *            number of rays - more rays make light to look more realistic
	 *            but will decrease performance, can't be less than MIN_RAYS
	 * @param color
	 *            color, set to {@code null} to use the default color
	 * @param distance
	 *            distance of cone light
	 * @param x
	 *            axis position
	 * @param y
	 *            axis position
	 * @param directionDegree
	 *            direction of cone light
	 * @param coneDegree
	 *            half-size of cone light, centered over direction
	 */
	public ConeLight(RayHandler rayHandler, int rays, Color color, float distance, float x, float y, float directionDegree, float coneDegree) {
		super(rayHandler, rays, color, distance, x, y, directionDegree);
		setConeDegree(coneDegree);
	}

    public ConeLight(RayHandler rayHandler, int rays, float directionDegree, float coneDegree) {
        super(rayHandler, rays, Light.DefaultColor, 1, 0, 0, directionDegree);
        setConeDegree(coneDegree);
    }
	
	@Override
	public void update () {
		updateBody();
		if (dirty) setEndPoints();
		
		if (cull()) return;
		if (staticLight && !dirty) return;
		
		dirty = false;
		updateMesh();
	}

    @Override
    public void attachToBody(Body body) {
        attachToBody(body, 0, 0, this.direction);
    }

    @Override
    public void attachToBody(Body body, float offsetX, float offsetY) {
        attachToBody(body, offsetX, offsetY, this.direction);
    }

    @Override
    public void attachToBody(Body body, float offsetX, float offsetY, float degrees) {
        super.attachToBody(body, offsetX, offsetY, degrees);
    }

    /**
	 * Sets light direction
	 * <p>Actual recalculations will be done only on {@link #update()} call
	 */
	public void setDirection(float direction) {
		this.direction = direction;
		dirty = true;
	}

	/**
	 * @return this lights cone degree
	 */
	public float getConeDegree() {
		return coneDegree;
	}

	/**
	 * How big is the arc of cone
	 * 
	 * <p>Arc angle = coneDegree * 2, centered over direction angle
	 * <p>Actual recalculations will be done only on {@link #update()} call
	 * 
	 */
	public void setConeDegree(float coneDegree) {
		this.coneDegree = MathUtils.clamp(coneDegree, 0f, 180f);
		dirty = true;
	}

	/**
	 * Sets light distance
	 * 
	 * <p>MIN value capped to 0.1f meter
	 * <p>Actual recalculations will be done only on {@link #update()} call
	 */
	public void setDistance(float dist) {
		dist *= RayHandler.gammaCorrectionParameter;
        dist *= Constants.WORLD_TO_SCREEN;
		this.distance = dist < 0.01f ? 0.01f : dist;
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
