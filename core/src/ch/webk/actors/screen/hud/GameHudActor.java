package ch.webk.actors.screen.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ch.webk.actors.GameActor;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public abstract class GameHudActor extends GameActor {

    private Logger l = new Logger("GameHudActor", true);
    private ITouchListener iTouchListener;

    public GameHudActor(float x, float y, float width, float height, float rotDegree) {
        screenRectangle.x = GameMath.transformToScreen(x - width / 2);
        screenRectangle.y = GameMath.transformToScreen(y - height / 2);
        screenRectangle.width = GameMath.transformToScreen(width);
        screenRectangle.height = GameMath.transformToScreen(height);
        screenRectangle.rotationDegree = rotDegree;
    }

    public void setTouchListener(ITouchListener iTouchListener) {
        this.iTouchListener = iTouchListener;
    }

    @Override
    public boolean checkTouch(int x, int y) {
        if (isTouchable) {
            y += (int) (WorldUtils.getCamera().viewportHeight/2 - WorldUtils.getCamera().position.y);
            x += (int) (WorldUtils.getCamera().viewportWidth/2 - WorldUtils.getCamera().position.x);
            if (screenRectangle.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    protected void drawHudTextureRegion(Batch batch, TextureRegion textureRegion) {
        float x = WorldUtils.getCamera().position.x + screenRectangle.x - WorldUtils.getCamera().viewportWidth/2;
        float y = WorldUtils.getCamera().position.y + screenRectangle.y - WorldUtils.getCamera().viewportHeight/2;
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
    }

    public void deactivate() {
        setIsTouchable(false);
        setVisible(false);
    }

}