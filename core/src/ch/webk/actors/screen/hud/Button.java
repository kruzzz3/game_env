package ch.webk.actors.screen.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ch.webk.actors.screen.GameScreenActor;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class Button extends GameHudActor {

    private Logger l = new Logger("Button", true);

    private final TextureRegion textureRegionBreak;

    public Button(String key, float x, float y, float width, float height, float rotDegree) {
        super(x,y,width,height,rotDegree);
        textureRegionBreak = ActorManager.getTextureRegion(key);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        toFront();
        drawHudTextureRegion(batch, textureRegionBreak);
    }

    @Override
    public void dispose() {}

    @Override
    public void resume() {}

}
