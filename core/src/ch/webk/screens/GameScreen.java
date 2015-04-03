package ch.webk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import ch.webk.enums.State;
import ch.webk.stages.GameStage;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.helper.GameRectangle;
import ch.webk.utils.manager.GameManager;
import ch.webk.utils.manager.LightManager;

public class GameScreen implements Screen {

    private Logger l = new Logger("GameScreen", true);

    private GameStage stage;
    private State state = State.RUN;

    public GameScreen(GameStage stage) {
        this.stage = stage;
        GameManager.setScreen(this);
    }

    public State getState() {
        return state;
    }

    @Override
    public void render(float delta) {
        GameRectangle viewportRectangle = stage.getViewportRectangle();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport((int) viewportRectangle.x, (int) viewportRectangle.y, (int) viewportRectangle.width, (int) viewportRectangle.height);
        switch (state) {
            case RUN:
                Constants.deltaTime = Gdx.graphics.getDeltaTime();
                renderPhysics();
                renderGraphics(viewportRectangle);
                break;
            case PAUSE:
                Constants.deltaTime = 0;
                renderGraphics(viewportRectangle);
                break;
        }
    }

    private void renderPhysics() {
        stage.act(Constants.deltaTime);
    }

    private void renderGraphics(GameRectangle viewportRectangle) {
        stage.draw();
        if (LightManager.getRayHandler() != null) {
            try {
                LightManager.getRayHandler().useCustomViewport((int) viewportRectangle.x, (int) viewportRectangle.y, (int) viewportRectangle.width, (int) viewportRectangle.height);
                LightManager.getRayHandler().setCombinedMatrix(GameManager.getCamera());
                LightManager.getRayHandler().update();
                LightManager.getRayHandler().render();
            } catch (Exception e) {}
        }
    }

    @Override
    public void resize(int width, int height) {
        // calculate new viewport
        float aspectRatio = (float)width/(float)height;
        float scale = 1f;
        Vector2 crop = new Vector2(0f, 0f);
        if(aspectRatio > Constants.ASPECT_RATIO)
        {
            scale = (float)height/(float)Constants.APP_HEIGHT;
            crop.x = (width - Constants.APP_WIDTH*scale)/2f;
        }
        else if(aspectRatio < Constants.ASPECT_RATIO)
        {
            scale = (float)width/(float)Constants.APP_WIDTH;
            crop.y = (height - Constants.APP_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)Constants.APP_WIDTH;
        }

        float w = (float)Constants.APP_WIDTH*scale;
        float h = (float)Constants.APP_HEIGHT*scale;
        stage.setViewportRectangle(new GameRectangle(crop.x, crop.y, w, h));
    }

    @Override
    public void show() {
        l.i("show");
    }

    @Override
    public void hide() {
        l.i("hide");
    }

    @Override
    public void pause() {
        l.i("pause");
        state = State.PAUSE;
        stage.pause();
    }

    @Override
    public void resume() {
        l.i("resume");
        stage.resume();
        state = State.RUN;

    }

    public void stop() {
        l.i("stop");
        state = State.PAUSE;
        stage.stop();
        dispose();
    }

    @Override
    public void dispose() {
        l.i("dispose()");
    }

}