package ch.webk.lights.box2dLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.utils.Constants;
import ch.webk.utils.manager.GameManager;
import ch.webk.utils.manager.LightManager;

public class FixedAreaLight extends Light {

    protected final Vector2 start[];
    protected final Vector2 end[];
    protected float sin;
    protected float cos;

    private float posX = 0;
    private float posY = 0;
    private float width = 0;
    private float height = 0;

    public FixedAreaLight(Color color, float x, float y, float width, float height) {
        super(LightManager.getRayHandler(), 8, color, 0, 0);
        setSoftnessLength(0);
        setXray(true);
        this.posX = x * Constants.WORLD_TO_SCREEN;
        this.posY = y * Constants.WORLD_TO_SCREEN;
        this.width = width * Constants.WORLD_TO_SCREEN / 2;
        this.height = height * Constants.WORLD_TO_SCREEN / 2;

        vertexNum = (vertexNum - 1) * 2;
        start = new Vector2[rayNum];
        end = new Vector2[rayNum];
        for (int i = 0; i < rayNum; i++) {
            start[i] = new Vector2();
            end[i] = new Vector2();
        }

        lightMesh = new Mesh(
                Mesh.VertexDataType.VertexArray, staticLight, vertexNum, 0,
                new VertexAttribute(VertexAttributes.Usage.Position, 2, "vertex_positions"),
                new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, "quad_colors"),
                new VertexAttribute(VertexAttributes.Usage.Generic, 1, "s"));
        softShadowMesh = new Mesh(
                Mesh.VertexDataType.VertexArray, staticLight, vertexNum, 0,
                new VertexAttribute(VertexAttributes.Usage.Position, 2, "vertex_positions"),
                new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, "quad_colors"),
                new VertexAttribute(VertexAttributes.Usage.Generic, 1, "s"));

        update();
    }

    @Override
    void update () {
        if (staticLight && !dirty) return;
        dirty = false;

        final float sizeOfScreen = width > height ? width : height;

        float xAxelOffSet = sizeOfScreen * cos;
        float yAxelOffSet = sizeOfScreen * sin;

        // preventing length <0 assertion error on box2d.
        if ((xAxelOffSet * xAxelOffSet < 0.1f) && (yAxelOffSet * yAxelOffSet < 0.1f)) {
            xAxelOffSet = 1;
            yAxelOffSet = 1;
        }

        final float widthOffSet = sizeOfScreen * -sin;
        final float heightOffSet = sizeOfScreen * cos;

        final float portionX = 2f * widthOffSet / (rayNum - 1);
        final float portionY = 2f * heightOffSet / (rayNum - 1);

        for (int i = 0; i < rayNum; i++) {
            final float steppedX = i * portionX + posX + GameManager.getCamera().position.x - GameManager.getCamera().viewportWidth / 2;
            final float steppedY = i * portionY + posY - height + GameManager.getCamera().position.y - GameManager.getCamera().viewportHeight / 2;
            m_index = i;
            start[i].x = steppedX - xAxelOffSet;
            start[i].y = steppedY - yAxelOffSet;

            mx[i] = end[i].x = steppedX + xAxelOffSet;
            my[i] = end[i].y = steppedY + yAxelOffSet;
        }

        // update light mesh
        // ray starting point
        int size = 0;
        final int arraySize = rayNum;

        for (int i = 0; i < arraySize; i++) {
            segments[size++] = start[i].x;
            segments[size++] = start[i].y;
            segments[size++] = colorF;
            segments[size++] = 1f;
            segments[size++] = mx[i];
            segments[size++] = my[i];
            segments[size++] = colorF;
            segments[size++] = 1f;
        }
        lightMesh.setVertices(segments, 0, size);
    }

    @Override
    void render () {
        rayHandler.lightRenderedLastFrame++;
        lightMesh.render(rayHandler.lightShader, GL20.GL_TRIANGLE_STRIP, 0, vertexNum);
    }

    @Override
    public void setDirection (float direction) {
        this.direction = direction;
        sin = MathUtils.sinDeg(direction);
        cos = MathUtils.cosDeg(direction);
        if (staticLight) dirty = true;
    }

    @Override
    public boolean contains (float x, float y) {
        boolean oddNodes = false;
        float x2 = mx[rayNum] = start[0].x;
        float y2 = my[rayNum] = start[0].y;
        float x1, y1;
        for (int i = 0; i <= rayNum; x2 = x1, y2 = y1, ++i) {
            x1 = mx[i];
            y1 = my[i];
            if (((y1 < y) && (y2 >= y)) || (y1 >= y) && (y2 < y)) {
                if ((y - y1) / (y2 - y1) * (x2 - x1) < (x - x1)) oddNodes = !oddNodes;
            }
        }
        for (int i = 0; i < rayNum; x2 = x1, y2 = y1, ++i) {
            x1 = start[i].x;
            y1 = start[i].y;
            if (((y1 < y) && (y2 >= y)) || (y1 >= y) && (y2 < y)) {
                if ((y - y1) / (y2 - y1) * (x2 - x1) < (x - x1)) oddNodes = !oddNodes;
            }
        }
        return oddNodes;
    }

    /** Not applicable for this light type **/
    @Deprecated
    @Override
    public void attachToBody (Body body) {
    }

    /** Not applicable for this light type **/
    @Deprecated
    @Override
    public void setPosition (float x, float y) {
    }

    /** Not applicable for this light type
     * <p>Always return {@code null}
     **/
    @Deprecated
    @Override
    public Body getBody () {
        return null;
    }

    /** Not applicable for this light type
     * <p>Always return {@code 0}
     **/
    @Deprecated
    @Override
    public float getX () {
        return posX;
    }

    /** Not applicable for this light type
     * <p>Always return {@code 0}
     **/
    @Deprecated
    @Override
    public float getY () {
        return posY;
    }

    /** Not applicable for this light type **/
    @Deprecated
    @Override
    public void setPosition (Vector2 position) {
    }

    /** Not applicable for this light type **/
    @Deprecated
    @Override
    public void setDistance(float dist) {
    }

    /** Not applicable for this light type **/
    @Deprecated
    @Override
    public void setIgnoreAttachedBody(boolean flag) {
    }

    /** Not applicable for this light type
     * <p>Always return {@code false}
     **/
    @Deprecated
    @Override
    public boolean getIgnoreAttachedBody() {
        return false;
    }


}
