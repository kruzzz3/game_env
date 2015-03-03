package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.SimpleUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;

public class PolyStairs extends GameCombinedActor {

    private Logger l = new Logger("PolyStairs", true);

    private final TextureRegion textureRegion;

    public PolyStairs(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.POLY_STAIRS);
        setIsTouchable(true);
    }

    @Override
    public SimpleUserData getUserData() {
        return (SimpleUserData) userData;
    }

    @Override
    public void touchDown() {
        l.i("touchDown()");
    }

    @Override
    public void touchUp() {
        l.i("touchUp()");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float rotDegree = (float) Math.toDegrees(body.getAngle());
        drawTextureRegion(batch, textureRegion, rotDegree);
    }
}
