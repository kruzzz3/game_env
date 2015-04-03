package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.BoxUserData;
import ch.webk.utils.manager.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.Logger;

public class BarrelWood extends GameCombinedActor {

    private Logger l = new Logger("BarrelWood", true);

    private final TextureRegion textureRegion;

    public BarrelWood(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.BARREL_WOOD);
    }

    @Override
    public BoxUserData getUserData() {
        return (BoxUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawTextureRegion(batch, textureRegion);
    }

    @Override
    public void dispose() {}

    @Override
    public void resume() {}

}
