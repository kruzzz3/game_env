package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.SimpleUserData;
import ch.webk.utils.manager.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.Logger;

public class Circle extends GameCombinedActor {

    private Logger l = new Logger("Circle", true);

    private final TextureRegion textureRegion;

    public Circle(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.CIRCLE);
        setIsTouchable(true);
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
    public boolean touchDown() {
        l.i("touchDown");
        return super.touchDown();
    }

    @Override
    public void dispose() {}

    @Override
    public void resume() {}


}
