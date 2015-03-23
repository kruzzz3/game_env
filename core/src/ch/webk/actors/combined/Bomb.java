package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.actors.special.ExplosionWithForce;
import ch.webk.box2d.BallUserData;
import ch.webk.box2d.SimpleUserData;
import ch.webk.box2d.UserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class Bomb extends GameCombinedActor {

    private Logger l = new Logger("Bomb", true);

    private final TextureRegion textureRegion;

    public Bomb(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.BOMB);
    }

    public void explode() {
        l.i("explode");
        float width = ((UserData)body.getUserData()).getWidth() * 6;
        WorldUtils.addActor(new ExplosionWithForce(body.getWorldCenter().x, body.getWorldCenter().y, width, width, 0));
        getUserData().setDestroy(true);
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
