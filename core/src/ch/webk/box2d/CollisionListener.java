package ch.webk.box2d;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class CollisionListener {

    public abstract void beginContact(Body body);
}
