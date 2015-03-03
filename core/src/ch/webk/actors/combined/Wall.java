package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.WallUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;

public class Wall extends GameCombinedActor {

    private final TextureRegion textureRegion;

    public Wall(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.WALL);
    }

    @Override
    public WallUserData getUserData() {
        return (WallUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion,screenRectangle.x,screenRectangle.y,screenRectangle.width,screenRectangle.height);
    }

}
