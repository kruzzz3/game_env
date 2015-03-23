package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.BallUserData;
import ch.webk.box2d.BoxUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;

public class BoxWood extends GameCombinedActor {

    private Logger l = new Logger("BoxWood", true);

    private final TextureRegion textureRegion;

    public BoxWood(Body body) {
        super(body);

        textureRegion = ActorManager.getTextureRegion(Constants.BOX_WOOD);
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
