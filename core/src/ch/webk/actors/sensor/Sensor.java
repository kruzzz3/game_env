package ch.webk.actors.sensor;

import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.box2d.SensorUserData;
import ch.webk.utils.Logger;

public class Sensor extends GameCombinedActor {

    private Logger l = new Logger("Sensor", true);

    public Sensor(Body body) {
        super(body);
    }

    @Override
    public SensorUserData getUserData() {
        return (SensorUserData) userData;
    }

    @Override
    public void resume() {}

    @Override
    public void dispose() {}
}
