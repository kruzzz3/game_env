package ch.webk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.actors.combined.Fps;
import ch.webk.actors.combined.Wall;
import ch.webk.utils.ActorGenerator;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class PolyStage extends GameStage {

    private Logger l = new Logger("PolyStage", true);

    private Wall wall;
    private Actor fps;
    private float accelX = 0;
    private float accelY = 0;

    public PolyStage() {
        super();
        l.i("PolyStage()");
        setUpWall();
        setUpFPS();
        setUpTest();
        Gdx.input.setInputProcessor(this);
        startDebugRenderer();
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.VOLUME_UP) {
            float w = GameMath.getRandomFloat(0.2f,1.2f);
            float x = Constants.APP_WIDTH / 2 / Constants.WORLD_TO_SCREEN;
            float y = Constants.APP_HEIGHT / 2 / Constants.WORLD_TO_SCREEN;

            ActorGenerator.createBall(x,y,w);
            return true;
        } else if (keyCode == Input.Keys.VOLUME_DOWN) {
            float w = GameMath.getRandomFloat(0.5f,2f);
            float h = GameMath.getRandomFloat(0.5f,2f);
            float x = GameMath.getRandomFloat(w, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w);
            float y = GameMath.getRandomFloat(h, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - h);
            float r = GameMath.getRandomFloat(0,3f);
            if (r < 1) {
                ActorGenerator.createPolyStar(x,y,w,h);
            } else if (r < 2) {
                ActorGenerator.createPolyStairs(x,y,w,h);
            } else if (r <= 3) {
                ActorGenerator.createPolyTriangle(x,y,w,h);
            }


            return true;
        }
        return super.keyDown(keyCode);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return super.touchDown(x, y, pointer, button);
    }

    private void setUpFPS() {
        fps = new Fps();
        WorldUtils.addActor(fps);
    }

    @Override
    public void act(float delta) {
        fps.setZIndex(fps.getZIndex()+5);
        super.act(delta);
        accelX = Gdx.input.getAccelerometerX();
        accelY = Gdx.input.getAccelerometerY();
        l.i("accelX="+accelX);
        l.i("accelY="+accelY);
        //CameraManipulator.setPosition(runner.getScreenX(), runner.getScreenY());
    }

    private void setUpWall() {
        float width = GameMath.transformToWorld(Constants.APP_WIDTH);
        float height = GameMath.transformToWorld(Constants.APP_HEIGHT);
        float thickness = GameMath.transformToWorld(Constants.WORLD_TO_SCREEN / 4);

        wall = new Wall(WorldUtils.getWall(0f, height - thickness, width, thickness));
        WorldUtils.addActor(wall);

        wall = new Wall(WorldUtils.getWall(0f, 0f, thickness, height));
        WorldUtils.addActor(wall);

        wall = new Wall(WorldUtils.getWall(width - thickness, 0f, thickness, height));
        WorldUtils.addActor(wall);

        wall = new Wall(WorldUtils.getWall(0f, 0f, width, thickness));
        WorldUtils.addActor(wall);
    }

    private void setUpTest() {
        l.i("setUpTest()");

        float w1 = GameMath.getRandomFloat(3,6);
        float x1 = GameMath.getRandomFloat(w1, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w1);
        float y1 = GameMath.getRandomFloat(w1, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w1);
        ActorGenerator.createPolyStar(x1,y1,w1,w1);

        float w2 = GameMath.getRandomFloat(3,6);
        float x2 = GameMath.getRandomFloat(w2, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w2);
        float y2 = GameMath.getRandomFloat(w2, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w2);
        ActorGenerator.createPolyStar(x2,y2,w2,w2);

        float w3 = GameMath.getRandomFloat(3,6);
        float x3 = GameMath.getRandomFloat(w3, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w3);
        float y3 = GameMath.getRandomFloat(w3, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w3);
        ActorGenerator.createPolyStairs(x3,y3,w3,w3);

        float w4 = GameMath.getRandomFloat(3,6);
        float x4 = GameMath.getRandomFloat(w4, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w4);
        float y4 = GameMath.getRandomFloat(w4, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w4);
        ActorGenerator.createPolyStairs(x4, y4, w4, w4);

        float w5 = GameMath.getRandomFloat(3,6);
        float x5 = GameMath.getRandomFloat(w5, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w5);
        float y5 = GameMath.getRandomFloat(w5, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w5);
        ActorGenerator.createPolyTriangle(x5, y5, w5, w5);

        float w6 = GameMath.getRandomFloat(3,6);
        float x6 = GameMath.getRandomFloat(w6, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w6);
        float y6 = GameMath.getRandomFloat(w6, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w6);
        ActorGenerator.createPolyTriangle(x6,y6,w6,w6);

    }

}
