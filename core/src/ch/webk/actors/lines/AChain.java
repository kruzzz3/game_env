package ch.webk.actors.lines;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.utils.helper.GameMath;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.helper.GameRectangle;

public abstract class AChain extends GameCombinedActor {

    private Logger l = new Logger("AChain", true);

    protected Vector2[] points;
    protected float thickness;
    protected float offsetY;
    protected GameRectangle[] screenRectangles;

    public AChain(Body body, Vector2[] points, float thickness, float offsetY) {
        super(body);
        this.points = points;
        this.thickness = thickness;
        this.offsetY = offsetY;
        createScreenRectangles();
    }

    private void createScreenRectangles() {
        screenRectangles = new GameRectangle[points.length - 1];
        for(int i = 0; i < (points.length - 1); i++) {
            float x1 = points[i].x;
            float y1 = points[i].y;
            float x2 = points[i + 1].x;
            float y2 = points[i + 1].y;

            float distance = GameMath.getDistance(x1, y1, x2, y2);
            screenRectangles[i] = new GameRectangle();
            screenRectangles[i].x = GameMath.transformToScreen((x1 + x2) / 2 - distance / 2);
            screenRectangles[i].y = GameMath.transformToScreen((y1 + y2) / 2 - thickness / 2);
            screenRectangles[i].setOffsetY(GameMath.transformToScreen(offsetY));
            screenRectangles[i].width = GameMath.transformToScreen(distance);
            screenRectangles[i].height = GameMath.transformToScreen(thickness);
            screenRectangles[i].rotationDegree = GameMath.getAngleDegree(x1, y1, x2, y2);
        }
    }

    protected abstract TextureRegion[] getTextureRegions();

    @Override
    protected void updateRectangle() {}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (int i = 0; i < screenRectangles.length; i++) {
            drawTextureRegion(batch, screenRectangles[i], getTextureRegions()[i]);
        }
    }


}
