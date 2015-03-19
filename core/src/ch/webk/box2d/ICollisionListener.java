package ch.webk.box2d;

import com.badlogic.gdx.physics.box2d.Body;

public interface ICollisionListener {

    public abstract void beginContact(Body body);
    public abstract void endContact(Body body);
}
