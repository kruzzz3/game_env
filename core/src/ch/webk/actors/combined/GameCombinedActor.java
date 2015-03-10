package ch.webk.actors.combined;

import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.actors.GameActor;
import ch.webk.box2d.UserData;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;

public abstract class GameCombinedActor extends GameActor {

    private Logger l = new Logger("GameCombinedActor", true);

    protected Body body;
    protected UserData userData;
    private boolean isAwake = true;

    public GameCombinedActor(Body body) {
        this.body = body;
        if (body != null) {
            this.userData = (UserData) body.getUserData();
        }
    }

    public void setBody(Body body) {
        this.body = body;
        this.userData = (UserData) body.getUserData();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (body.getUserData() != null) {
            updateRectangle();
        } else {
            remove();
        }
    }

    protected void updateRectangle() {
        if (isAwake) {
            screenRectangle.x = GameMath.transformToScreen(body.getPosition().x - userData.getWidth() / 2);
            screenRectangle.y = GameMath.transformToScreen(body.getPosition().y - userData.getHeight() / 2);
            screenRectangle.width = GameMath.transformToScreen(userData.getWidth());
            screenRectangle.height = GameMath.transformToScreen(userData.getHeight());
        }
        if (isAwake != body.isAwake()) {
            isAwake = body.isAwake();
        }
    }

    public boolean checkTouch(int x, int y) {
        if (isTouchable) {
            y = (int) Constants.APP_HEIGHT - y;
            if (screenRectangle.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public Body getBody() {
        return body;
    }

    public abstract UserData getUserData();

}