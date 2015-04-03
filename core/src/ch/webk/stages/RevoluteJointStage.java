package ch.webk.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.actors.combined.Fps;
import ch.webk.actors.combined.complex.Car;
import ch.webk.actors.screen.hud.Hud;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.manager.GameManager;
import ch.webk.utils.manipulator.CameraManipulator;

public class RevoluteJointStage extends GameStage {

    private Logger l = new Logger("RevoluteJointStage", true);

    private Actor fps;
    private Car car;

    public RevoluteJointStage() {
        super();
        l.i("RevoluteJointStage()");
        useLight();
        setBrightness(0.4f);
        setUpFPS();
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
        l.i("touchDown x"+x+", y="+y);
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
        GameManager.getWorld().setGravity(new Vector2(0,-1));
        CameraManipulator.setPosition(car.getScreenX(), car.getScreenY());
    }

}
