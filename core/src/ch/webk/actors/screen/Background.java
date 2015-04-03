package ch.webk.actors.screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.utils.Constants;
import ch.webk.utils.manager.ActorManager;
import ch.webk.utils.helper.GameRectangle;

public class Background extends Actor {

    private final TextureRegion textureRegion;
    private GameRectangle textureRegionBounds1;
    private GameRectangle textureRegionBounds2;
    private int speed = 100;

    public Background() {
        textureRegion = ActorManager.getTextureRegion("t_background");
        textureRegionBounds1 = new GameRectangle(0 - Constants.APP_WIDTH / 2, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        textureRegionBounds2 = new GameRectangle(Constants.APP_WIDTH / 2, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }

    @Override
    public void act(float delta) {
        if (leftBoundsReached(delta)) {
            resetBounds();
        } else {
            updateXBounds(-delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, textureRegionBounds1.x, textureRegionBounds1.y, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        batch.draw(textureRegion, textureRegionBounds2.x, textureRegionBounds2.y, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }

    private boolean leftBoundsReached(float delta) {
        return (textureRegionBounds2.x - (delta * speed)) <= 0;
    }

    private void updateXBounds(float delta) {
        textureRegionBounds1.x += delta * speed;
        textureRegionBounds2.x += delta * speed;
    }

    private void resetBounds() {
        textureRegionBounds1 = textureRegionBounds2;
        textureRegionBounds2 = new GameRectangle(Constants.APP_WIDTH, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }

}
