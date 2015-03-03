package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.TargetUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;

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

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setTransform(x,y, body.getAngle());
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void setPosition(float xNew, float yNew) {
        yNew = Constants.APP_HEIGHT - yNew;
        x = xNew / Constants.WORLD_TO_SCREEN;
        y = yNew / Constants.WORLD_TO_SCREEN;
    }
}
