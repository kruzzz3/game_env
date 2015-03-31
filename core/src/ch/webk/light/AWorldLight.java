package ch.webk.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import box2dLight.PositionalLight;
import box2dLight.RayHandler;
import ch.webk.utils.Constants;

public abstract class AWorldLight extends PositionalLight {

    private static final int RAYS_NUM = 32;
    private float lightDistance = 0;
    private float x = 0;
    private float y = 0;
    private float bodyOffsetX = 0;
    private float bodyOffsetY = 0;
    private float bodyAngleOffset = 0;

    public AWorldLight(RayHandler rayHandler, Color color, float lightDistance, float x, float y) {
        super(rayHandler, RAYS_NUM, color, lightDistance * Constants.WORLD_TO_SCREEN, x * Constants.WORLD_TO_SCREEN, y * Constants.WORLD_TO_SCREEN, 0);
        this.lightDistance = lightDistance;
        this.x = x;
        this.y = y;
    }

    public AWorldLight(RayHandler rayHandler, Color color, float lightDistance, float x, float y, float directionDegree) {
        super(rayHandler, RAYS_NUM, color, lightDistance * Constants.WORLD_TO_SCREEN, x * Constants.WORLD_TO_SCREEN, y * Constants.WORLD_TO_SCREEN, directionDegree);
        this.lightDistance = lightDistance;
        this.x = x;
        this.y = y;
    }

    @Override
    public void attachToBody(Body body, float offsetX, float offsetY) {
        this.bodyOffsetX = offsetX * Constants.WORLD_TO_SCREEN;
        this.bodyOffsetY = offsetY * Constants.WORLD_TO_SCREEN;
        super.attachToBody(body, bodyOffsetX, bodyOffsetY);
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(super.getPosition().x / Constants.WORLD_TO_SCREEN,super.getPosition().y / Constants.WORLD_TO_SCREEN);
    }

    @Override
    public float getX() {
        return getPosition().x;
    }

    /** @return vertical starting position of light in world coordinates **/
    @Override
    public float getY() {
        return getPosition().y;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x * Constants.WORLD_TO_SCREEN, y * Constants.WORLD_TO_SCREEN);
    }

    @Override
    public void setPosition(Vector2 position) {
        super.setPosition(new Vector2(position.x * Constants.WORLD_TO_SCREEN, position.y * Constants.WORLD_TO_SCREEN));
    }

    @Override
    public boolean contains(float x, float y) {
        return super.contains(x * Constants.WORLD_TO_SCREEN, y * Constants.WORLD_TO_SCREEN);
    }

    @Override
    protected void updateBody() {
        if (getBody() == null || staticLight) return;

        final Vector2 vec = getBody().getPosition();
        float angle = getBody().getAngle();
        final float cos = MathUtils.cos(angle);
        final float sin = MathUtils.sin(angle);
        final float dX = bodyOffsetX * cos - bodyOffsetY * sin;
        final float dY = bodyOffsetX * sin + bodyOffsetY * cos;
        float tempX = (vec.x * Constants.WORLD_TO_SCREEN) + dX;
        float tempY = (vec.y * Constants.WORLD_TO_SCREEN) + dY;
        super.setPosition(tempX, tempY);
        setDirection(angle * MathUtils.radiansToDegrees);
    }
}
