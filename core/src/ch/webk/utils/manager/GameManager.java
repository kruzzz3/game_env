package ch.webk.utils.manager;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.screens.GameScreen;
import ch.webk.enums.State;
import ch.webk.stages.GameStage;
import ch.webk.utils.helper.Logger;

public class GameManager {

    private static Logger l = new Logger("GameManager", true);

    private static World world;
    private static GameStage stage;
    private static OrthographicCamera camera;
    private static Box2DDebugRenderer renderer;
    private static Matrix4 debugMatrix;
    private static GameScreen screen;

    public static void setScreen(GameScreen screen) {
        GameManager.screen = screen;
    }

    public static boolean isRunning() {
        if (getScreen().getState() == State.RUN) {
            return true;
        }
        return false;
    }

    public static GameScreen getScreen() {
        return GameManager.screen;
    }

    public static void setWorld(World world) {
        GameManager.world = world;
    }

    public static World getWorld() {
        return GameManager.world;
    }

    public static void setStage(GameStage stage) {
        GameManager.stage = stage;
    }

    public static GameStage getStage() {
        return GameManager.stage;
    }

    public static void setCamera(OrthographicCamera camera) {
        GameManager.camera = camera;
    }

    public static OrthographicCamera getCamera() {
        return GameManager.camera;
    }

    public static void setRenderer(Box2DDebugRenderer renderer) {
        GameManager.renderer = renderer;
    }

    public static Box2DDebugRenderer getRenderer() {
        return GameManager.renderer;
    }

    public static void setDebugMatrix(Matrix4 debugMatrix) {
        GameManager.debugMatrix = debugMatrix;
    }

    public static Matrix4 getDebugMatrix() {
        return GameManager.debugMatrix;
    }

    public static void addActor(Actor actor) {
        GameManager.stage.addActor(actor);
    }

}
