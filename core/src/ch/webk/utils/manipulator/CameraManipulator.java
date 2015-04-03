package ch.webk.utils.manipulator;

import com.badlogic.gdx.math.Matrix4;

import ch.webk.utils.Constants;
import ch.webk.utils.manager.GameManager;

public class CameraManipulator {

    public static void setPosition(float x, float y) {
        GameManager.getCamera().position.set(x, y, 0);
        GameManager.getCamera().update();

        if (GameManager.getRenderer() != null) {
            Matrix4 debugMatrix = new Matrix4(GameManager.getCamera().combined);
            debugMatrix.scale(Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN, 0);
            GameManager.setDebugMatrix(debugMatrix);
        }
    }

}
