package ch.webk.utils.helper;

import com.badlogic.gdx.math.Rectangle;

public class GameRectangle extends Rectangle {

    public float rotationDegree = 0;
    private float offsetY = 0;

    public GameRectangle() {
        super(0, 0, 0, 0);
    }

    public GameRectangle(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public void setOffsetY(float offsetY) {
        y += offsetY;
        this.offsetY = offsetY;
    }

    public void setOffsetYWorld(float offsetY) {
        offsetY = GameMath.transformToScreen(offsetY);
        y += offsetY;
        this.offsetY = offsetY;
    }

    public float getOffsetY() {
        return offsetY;
    }
}
