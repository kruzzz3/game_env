package ch.webk.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.utils.Constants;
import ch.webk.utils.helper.GameRectangle;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.manager.GameManager;

public abstract class GameActor extends Actor {

    private Logger l = new Logger("GameActor", true);

    protected GameRectangle screenRectangle;
    protected boolean isTouchable = false;
    private float stateTime = 0;

    private ITouchListener iTouchListener;

    public GameActor() {
        screenRectangle = new GameRectangle();
    }

    public float getScreenX() {
        return screenRectangle.x;
    }

    public float getScreenY() {
        return screenRectangle.y;
    }

    public boolean checkTouch(int x, int y) {
        if (isTouchable) {
            if (screenRectangle.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public float getStateTime() {
        return stateTime;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setProjectionMatrix(GameManager.getCamera().combined);
    }

    protected void drawTextureRegion(Batch batch, TextureRegion textureRegion) {
        batch.draw(textureRegion, screenRectangle.x, screenRectangle.y, screenRectangle.width * 0.5f, screenRectangle.height * 0.5f, screenRectangle.width, screenRectangle.height, 1f, 1f, screenRectangle.rotationDegree);
    }

    protected void drawTextureRegion(Batch batch, GameRectangle screenRectangle, TextureRegion textureRegion) {
        batch.draw(textureRegion, screenRectangle.x, screenRectangle.y, screenRectangle.width * 0.5f, screenRectangle.height * 0.5f, screenRectangle.width, screenRectangle.height, 1f, 1f, screenRectangle.rotationDegree);
    }

    protected void drawLoopAnimation(Batch batch, Animation animation) {
        stateTime += Constants.deltaTime;
        batch.draw(animation.getKeyFrame(stateTime, true), screenRectangle.x, screenRectangle.y, screenRectangle.width * 0.5f, screenRectangle.height * 0.5f, screenRectangle.width, screenRectangle.height, 1f, 1f, screenRectangle.rotationDegree);
    }

    protected void drawLoopAnimation(Batch batch, Animation animation, boolean flipX) {
        stateTime += Constants.deltaTime;
        TextureRegion textureRegion = animation.getKeyFrame(stateTime, true);
        if (textureRegion.isFlipX() != flipX) {
            textureRegion.flip(true, false);
        }
        batch.draw(textureRegion, screenRectangle.x, screenRectangle.y, screenRectangle.width * 0.5f, screenRectangle.height * 0.5f, screenRectangle.width, screenRectangle.height, 1f, 1f, screenRectangle.rotationDegree);
    }

    protected void drawAnimation(Batch batch, Animation animation) {
        stateTime += Constants.deltaTime;
        batch.draw(animation.getKeyFrame(stateTime, false), screenRectangle.x, screenRectangle.y, screenRectangle.width * 0.5f, screenRectangle.height * 0.5f, screenRectangle.width, screenRectangle.height, 1f, 1f, screenRectangle.rotationDegree);
    }

    protected void setIsTouchable(boolean isTouchable) {
        this.isTouchable = isTouchable;
    }

    public void setTouchListener(ITouchListener iTouchListener) {
        this.iTouchListener = iTouchListener;
    }

    public boolean touchDown() {
        if (iTouchListener != null) {
            iTouchListener.touchDown();
        }
        return false;
    }

    public boolean touchUp() {
        if (iTouchListener != null) {
            iTouchListener.touchUp();
        }
        return false;
    }

    abstract public void dispose();

    abstract public void resume();

}