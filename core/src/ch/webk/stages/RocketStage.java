package ch.webk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.actors.combined.Fps;
import ch.webk.actors.combined.Target;
import ch.webk.actors.combined.Wall;
import ch.webk.actors.screen.Background;
import ch.webk.utils.ActorGenerator;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class RocketStage extends GameStage {

    private Logger l = new Logger("RocketStage", true);

    private Wall wall;
    private Actor fps;
    private Target target;

    public RocketStage() {
        super();
        l.i("RocketStage()");
        setUpBackground();
        setUpFPS();
        setUpTarget();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.VOLUME_UP) {
            float w = GameMath.getRandomFloat(0.6f,0.6f);
            float x = Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN-2;
            float y = 2;

            x = Constants.APP_WIDTH / 2 / Constants.WORLD_TO_SCREEN;
            y = Constants.APP_HEIGHT / 2 / Constants.WORLD_TO_SCREEN;

            ActorGenerator.createPolyRocket(x, y, w * 2, w, target);
            return true;
        }
        return super.keyDown(keyCode);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        target.setPosition(x,y);
        return super.touchDown(x, y, pointer, button);
    }

    private void setUpBackground() {
        addActor(new Background());
    }

    private void setUpFPS() {
        fps = new Fps();
        WorldUtils.addActor(fps);
    }

    @Override
    public void act(float delta) {
        fps.setZIndex(fps.getZIndex()+5);
        super.act(delta);
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

    private void setUpTarget() {
        target = ActorGenerator.createTarget(1, 1, 0.5f);
    }

}
