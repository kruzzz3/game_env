package ch.webk.actors.screen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;

public class Explosion extends GameScreenActor {

    private Logger l = new Logger("Explosion", true);

    private Animation animation;

    public Explosion(float x, float y, float width, float height, float rotDegree) {
        super(x,y,width,height,rotDegree);
        animation = ActorManager.getAnimation(Constants.EXPLOSION, 0.1f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawAnimation(batch, animation, rotDegree);
        if (animation.isAnimationFinished(getStateTime())) {
            remove();
        }
    }
}
