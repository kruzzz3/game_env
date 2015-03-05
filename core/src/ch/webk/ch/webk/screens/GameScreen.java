package ch.webk.ch.webk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import ch.webk.stages.GameStage;
import ch.webk.stages.RocketStage;
import ch.webk.utils.Constants;
import ch.webk.MyGame;
import ch.webk.utils.Logger;

public class GameScreen implements Screen {

    private Logger l = new Logger("GameScreen", true);

    private GameStage stage;
    private boolean stop = false;

    public GameScreen(GameStage stage) {
        this.stage = stage;
    }

    @Override
    public void render(float delta) {
        // set viewport
        if (!stop) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Rectangle viewport = stage.getVp();
            Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
            //Update the stage
            stage.act(delta);
            stage.draw();
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
        stage.setVp(new Rectangle(crop.x, crop.y, w, h));
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void stop() {
        stop = true;
        stage.stop();
        dispose();
    }

    @Override
    public void dispose() {
        l.i("dispose()");
    }

}