package ch.webk.actors.lines;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.SimpleUserData;
import ch.webk.box2d.UserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.LibGdxRect;
import ch.webk.utils.WorldUtils;

public class ChainExample1 extends AChain {

    private TextureRegion[] textureRegions;

    public ChainExample1(Body body, Vector2[] points) {
        super(body, points, 0.5f, 0);
        createTextureRegions();
    }

    private void createTextureRegions() {
        textureRegions = new TextureRegion[points.length - 1];
        for(int i = 0; i < (points.length - 1); i++) {
            textureRegions[i] =  ActorManager.getTextureRegion(Constants.WALL);
        }
    }

    @Override
    protected TextureRegion[] getTextureRegions() {
        return textureRegions;
    }

    @Override
    public SimpleUserData getUserData() {
        return (SimpleUserData) userData;
    }

    @Override
    public void dispose() {}

    @Override
    public void resume() {}

}
