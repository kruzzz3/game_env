package ch.webk.actors.screen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

import ch.webk.utils.manager.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.Logger;

public class Explosion extends GameScreenActor {

    private Logger l = new Logger("Explosion", true);

    private Animation animation;

    public Explosion(float x, float y, float width, float height, float rotDegree) {
        super(x,y,width,height,rotDegree);
        animation = ActorManager.getAnimation(Constants.EXPLOSION, 0.1f);
    }

    public Explosion(String key, float x, float y, float width, float height, float rotDegree) {
        super(x,y,width,height,rotDegree);
        animation = ActorManager.getAnimation(key, 0.05f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawAnimation(batch, animation);
        if (animation.isAnimationFinished(getStateTime())) {
            remove();
        }
    }

    @Override
    public void dispose() {}

    @Override
    public void resume() {}

}
