package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.TargetUserData;
import ch.webk.utils.manager.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.Logger;

public class Target extends GameCombinedActor {

    private Logger l = new Logger("Target", true);

    private final TextureRegion textureRegion;
    private float x;
    private float y;

    public Target(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.TARGET);
        x = body.getPosition().x;
        y = body.getPosition().y;
        setIsTouchable(true);
    }

    @Override
    public TargetUserData getUserData() {
        return (TargetUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawTextureRegion(batch, textureRegion);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setTransform(x,y,body.getAngle()+0.1f);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void setPosition(float xNew, float yNew) {
        x = xNew;
        y = yNew;
    }

    @Override
    public void dispose() {}

    @Override
    public void resume() {}

}
