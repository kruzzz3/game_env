package ch.webk.actors.screen;

import ch.webk.actors.GameActor;
import ch.webk.utils.helper.GameMath;
import ch.webk.utils.helper.Logger;

public abstract class GameScreenActor extends GameActor {

    private Logger l = new Logger("GameScreenActor", true);

    public GameScreenActor(float x, float y, float width, float height, float rotDegree) {
        screenRectangle.x = GameMath.transformToScreen(x - width / 2);
        screenRectangle.y = GameMath.transformToScreen(y - height / 2);
        screenRectangle.width = GameMath.transformToScreen(width);
        screenRectangle.height = GameMath.transformToScreen(height);
        screenRectangle.rotationDegree = rotDegree;
    }

}