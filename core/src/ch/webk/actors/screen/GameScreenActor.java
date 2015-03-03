package ch.webk.actors.screen;

import ch.webk.actors.GameActor;
import ch.webk.utils.Logger;

public abstract class GameScreenActor extends GameActor {

    private Logger l = new Logger("GameScreenActor", true);

    protected float rotDegree = 0;

    public GameScreenActor(float x, float y, float width, float height, float rotDegree) {
        screenRectangle.x = x;
        screenRectangle.y = y;
        screenRectangle.width = width;
        screenRectangle.height = height;
        this.rotDegree = rotDegree;
    }

}