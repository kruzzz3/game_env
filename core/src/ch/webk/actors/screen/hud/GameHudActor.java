package ch.webk.actors.screen.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ch.webk.actors.GameActor;
import ch.webk.lights.box2dLight.FixedAreaLight;
import ch.webk.utils.helper.GameMath;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.manager.GameManager;
import ch.webk.utils.manager.LightManager;

public abstract class GameHudActor extends GameActor {

    private Logger l = new Logger("GameHudActor", true);
    private ITouchListener iTouchListener;
    private FixedAreaLight fixedAreaLight;

    public GameHudActor(float x, float y, float width, float height, float rotDegree) {
        screenRectangle.x = GameMath.transformToScreen(x - width / 2);
        screenRectangle.y = GameMath.transformToScreen(y - height / 2);
        screenRectangle.width = GameMath.transformToScreen(width);
        screenRectangle.height = GameMath.transformToScreen(height);
        screenRectangle.rotationDegree = rotDegree;
        if (LightManager.getRayHandler() != null) {
            fixedAreaLight = new FixedAreaLight(LightManager.getClearColor(), x, y, width, height);
        }
    }

    public void setTouchListener(ITouchListener iTouchListener) {
        this.iTouchListener = iTouchListener;
    }

    @Override
    public boolean checkTouch(int x, int y) {
        if (isTouchable) {
            y += (int) (GameManager.getCamera().viewportHeight/2 - GameManager.getCamera().position.y);
            x += (int) (GameManager.getCamera().viewportWidth/2 - GameManager.getCamera().position.x);
            if (screenRectangle.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    protected void drawHudTextureRegion(Batch batch, TextureRegion textureRegion) {
        float x = GameManager.getCamera().position.x + screenRectangle.x - GameManager.getCamera().viewportWidth / 2;
        float y = GameManager.getCamera().position.y + screenRectangle.y - GameManager.getCamera().viewportHeight / 2;
        batch.draw(textureRegion, x, y, screenRectangle.width * 0.5f, screenRectangle.height * 0.5f, screenRectangle.width, screenRectangle.height, 1f, 1f, screenRectangle.rotationDegree);
    }

    @Override
    public boolean touchDown() {
        if (iTouchListener != null) {
            return iTouchListener.touchDown();
        }
        return false;
    }

    public void activate() {
        setVisible(true);
        setIsTouchable(true);
        if (fixedAreaLight != null) {
            fixedAreaLight.setActive(true);
        }
    }

    public void deactivate() {
        setIsTouchable(false);
        setVisible(false);
        if (fixedAreaLight != null) {
            fixedAreaLight.setActive(false);
        }
    }

}