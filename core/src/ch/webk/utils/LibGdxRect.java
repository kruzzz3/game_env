package ch.webk.utils;

import com.badlogic.gdx.math.Rectangle;

public class LibGdxRect extends Rectangle {

    public float rotationDegree = 0;
    private float offsetY = 0;

    public void setOffsetY(float offsetY) {
        y += offsetY;
        this.offsetY = offsetY;
    }

    public float getOffsetY() {
        return offsetY;
    }
}
