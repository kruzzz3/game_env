package ch.webk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.actors.combined.Fps;
import ch.webk.actors.combined.Line;
import ch.webk.actors.combined.Wall;
import ch.webk.actors.combined.complex.Car;
import ch.webk.utils.CameraManipulator;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class RevoluteJointStage extends GameStage {

    private Logger l = new Logger("RevoluteJointStage", true);

    private Wall wall;
    private Actor fps;
    private Car car;

    public RevoluteJointStage() {
        super();
        l.i("RevoluteJointStage()");
        setUpFPS();
        //setUpWall();
        float x1 = 0;
        float y1 = 10;
        float x2 = Constants.APP_WIDTH * 2;
        float y2 = 10;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        x1 = x2;
        y1 = y2;
        x2 = x1 + Constants.APP_WIDTH * 0.5f;
        y2 = y1 + 100;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        x1 = x2;
        y1 = y2;
        x2 = x1 + Constants.APP_WIDTH * 2;
        y2 = y1 + 50;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        x1 = x2;
        y1 = y2;
        x2 = x1 + Constants.APP_WIDTH * 1;
        y2 = y1 + 100;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        x1 = x2;
        y1 = y2;
        x2 = x1 + Constants.APP_WIDTH * 0.5f;
        y2 = y1 - 200;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        x1 = x2;
        y1 = y2;
        x2 = x1 + Constants.APP_WIDTH * 1;
        y2 = y1;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        x1 = x2;
        y1 = y2;
        x2 = x1 + Constants.APP_WIDTH * 0.5f;
        y2 = y1 + 50;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        x1 = x2;
        y1 = y2;
        x2 = x1 + Constants.APP_WIDTH * 2;
        y2 = y1 + 20;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        x1 = x2;
        y1 = y2;
        x2 = x1 + Constants.APP_WIDTH * 1;
        y2 = y1 + 100;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        x1 = x2;
        y1 = y2;
        x2 = x1 + Constants.APP_WIDTH * 0.5f;
        y2 = y1 - 500;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        x1 = x2;
        y1 = y2;
        x2 = x1 + Constants.APP_WIDTH * 1;
        y2 = y1;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        x1 = x2;
        y1 = y2;
        x2 = x1 + Constants.APP_WIDTH * 1000;
        y2 = y1;
        WorldUtils.addActor(new Line(x1,y1,x2,y2));
        car = new Car();
        Gdx.input.setInputProcessor(this);
        startDebugRenderer();
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.VOLUME_DOWN) {
            car.drive();
            return true;
        } else if (keyCode == Input.Keys.VOLUME_UP) {
            car.driveBack();
            return true;
        }
        return super.keyDown(keyCode);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        l.i("touchDown x"+x+", y="+y);



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
        //Vector2 gravity = new Vector2(Constants.accXDegree / 10, Constants.accYDegree / 10);
        WorldUtils.getWorld().setGravity(new Vector2(0,-15));
        CameraManipulator.setPosition(car.getScreenX(), car.getScreenY());
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

}
