package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.SimpleUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.SimpleUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Line extends GameCombinedActor {

    private Logger l = new Logger("Line", true);

    private final TextureRegion textureRegion;
    private boolean isAwake = true;

    float x1;
    float y1;
    float x2;
    float y2;
    float rotDegree = 0;

    public Line(float x1, float y1,float x2, float y2) {
        super(null);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        setBody(WorldUtils.createLineBody(x1, y1, x2, y2));
        textureRegion = ActorManager.getTextureRegion(Constants.WALL);
    }

    @Override
    protected void updateRectangle() {
        l.i("updateRectangle");
        if (isAwake) {
            float dx = x2-x1;
            float dy = y2-y1;
            float dist = (float) Math.sqrt(dx*dx + dy*dy);
            screenRectangle.x = x1;
            screenRectangle.y = y1;
            screenRectangle.width = dist;
            screenRectangle.height = 4;
            rotDegree = (float) Math.atan2(dy, dx) * MathUtils.radiansToDegrees;
        }
        if (isAwake != body.isAwake()) {
            isAwake = body.isAwake();
        }
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
        l.i("draw");
        drawTextureRegion(batch, textureRegion,rotDegree);
    }
}