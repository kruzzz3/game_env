package ch.webk.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.actors.combined.Fps;
import ch.webk.actors.combined.complex.Car;
import ch.webk.actors.screen.hud.Hud;
import ch.webk.utils.actor.ActorGenerator;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.manager.GameManager;
import ch.webk.utils.manipulator.CameraManipulator;

public class ChainStage extends GameStage {

    private Logger l = new Logger("ChainStage", true);

    private Actor fps;
    private Car car;

    public ChainStage() {
        super();
        l.i("ChainStage()");
        setBrightness(0.1f);
        setUpFPS();

        Vector2 points[] = new Vector2[16];
        points[0] = new Vector2(-5,100);
        points[1] = new Vector2(0,0);
        points[2] = new Vector2(20,2);
        points[3] = new Vector2(40,0);
        points[4] = new Vector2(50,-4);
        points[5] = new Vector2(70,-2);
        points[6] = new Vector2(100,10);
        points[7] = new Vector2(150,15);
        points[8] = new Vector2(200,0);
        points[9] = new Vector2(500,-5);
        points[10] = new Vector2(540,-10);
        points[11] = new Vector2(550,-5);
        points[12] = new Vector2(600,0);
        points[13] = new Vector2(640,10);
        points[14] = new Vector2(1000,5);
        points[15] = new Vector2(1005,100);

        ActorGenerator.createChainExample1(points);

        car = new Car();
        hud = new Hud();
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
        return super.touchDown(x, y, pointer, button);
    }

    private void setUpFPS() {
        fps = new Fps();
        GameManager.addActor(fps);
    }

    @Override
    public void act(float delta) {
        fps.setZIndex(fps.getZIndex()+5);
        super.act(delta);
        //Vector2 gravity = new Vector2(Constants.accXDegree / 10, Constants.accYDegree / 10);
        GameManager.getWorld().setGravity(new Vector2(0,-15));
        CameraManipulator.setPosition(car.getScreenX(), car.getScreenY());
    }

}
