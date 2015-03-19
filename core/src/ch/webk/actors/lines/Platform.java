package ch.webk.actors.lines;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.box2d.BallUserData;
import ch.webk.box2d.BoxUserData;
import ch.webk.box2d.SimpleUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;

public class Platform extends GameCombinedActor {

    private Logger l = new Logger("Platform", true);

    private final TextureRegion textureRegion;

    public Platform(Body body) {
        super(body);

        textureRegion = ActorManager.getTextureRegion(Constants.WALL);
    }

    @Override
    public SimpleUserData getUserData() {
        return (SimpleUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawTextureRegion(batch, textureRegion);
    }

    @Override
    public void touchDown() {}

    @Override
    public void touchUp() {}

    @Override
    public void dispose() {}

}
