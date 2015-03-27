package ch.webk.actors.combined.complex;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.box2d.SimpleUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;

public class CarWheel extends GameCombinedActor {

    private Logger l = new Logger("CarWheel", true);

    private final TextureRegion textureRegion;

    public CarWheel(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.CAR_WHEEL);
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
    public void dispose() {}

    @Override
    public void resume() {}

}
