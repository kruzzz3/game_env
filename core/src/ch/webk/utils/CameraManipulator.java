package ch.webk.utils;

import com.badlogic.gdx.math.Matrix4;

public class CameraManipulator {

    public static void setPosition(float x, float y) {
        WorldUtils.getCamera().position.set(x, y, 0);
        WorldUtils.getCamera().update();
        if (WorldUtils.getRenderer() != null) {
            Matrix4 debugMatrix = new Matrix4(WorldUtils.getCamera().combined);
            debugMatrix.scale(Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN, 0);
            WorldUtils.setDebugMatrix(debugMatrix);
        }
    }

}
