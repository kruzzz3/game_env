package ch.webk.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.utils.Constants;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public abstract class GameActor extends Actor {

    private Logger l = new Logger("GameActor", true);

    protected Rectangle screenRectangle;
    protected boolean isTouchable = false;
    private float stateTime = 0;

    public GameActor() {
        screenRectangle = new Rectangle();
    }

    public float getScreenX() {
        return screenRectangle.x;
    }

    public float getScreenY() {
        return screenRectangle.y;
    }

    public boolean checkTouch(int x, int y) {
        if (isTouchable) {
            y = Constants.APP_HEIGHT - y;
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
        batch.setProjectionMatrix(WorldUtils.getCamera().combined);
    }

    protected void drawTextureRegion(Batch batch, TextureRegion textureRegion, float rotDegree) {
        batch.draw(textureRegion, screenRectangle.x, screenRectangle.y, screenRectangle.width * 0.5f, screenRectangle.height * 0.5f, screenRectangle.width, screenRectangle.height, 1f, 1f, rotDegree);
    }

    protected void drawLoopAnimation(Batch batch, Animation animation, float rotDegree) {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(stateTime, true),screenRectangle.x,screenRectangle.y,screenRectangle.width * 0.5f,screenRectangle.height * 0.5f,screenRectangle.width,screenRectangle.height, 1f, 1f, rotDegree);
    }

    protected void drawAnimation(Batch batch, Animation animation, float rotDegree) {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(stateTime, false),screenRectangle.x,screenRectangle.y,screenRectangle.width * 0.5f,screenRectangle.height * 0.5f,screenRectangle.width,screenRectangle.height, 1f, 1f, rotDegree);
    }

    protected void setIsTouchable(boolean isTouchable) {
        this.isTouchable = isTouchable;
    }

    public void touchDown() {}

    public void touchUp() {}

}