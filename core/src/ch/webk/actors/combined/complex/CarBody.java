package ch.webk.actors.combined.complex;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.box2d.SimpleUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;

public class CarBody extends GameCombinedActor {

    private Logger l = new Logger("CarBody", true);

    private final TextureRegion textureRegion;

    public CarBody(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.CAR_BODY);
        setIsTouchable(true);
        createLight();
    }

    private void createLight() {

    }

    @Override
    public void act(float delta) {
        super.act(delta);
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
