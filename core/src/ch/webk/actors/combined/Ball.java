package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.BallUserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;

public class Ball extends GameCombinedActor {

    private Logger l = new Logger("Ball", true);

    private Animation animation;

    public Ball(Body body) {
        super(body);

        animation = ActorManager.getAnimation(Constants.BALL, 0.2f);

        float a = GameMath.getRandomFloat(0,20) - GameMath.getRandomFloat(0,20);
        float b = GameMath.getRandomFloat(0,20) - GameMath.getRandomFloat(0,20);

        body.applyLinearImpulse(new Vector2(a, b), body.getWorldCenter(), true);

        setIsTouchable(true);
    }

    @Override
    public BallUserData getUserData() {
        return (BallUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float rotDegree = (float) Math.toDegrees(body.getAngle());
        drawLoopAnimation(batch, animation, rotDegree);
    }

    @Override
    public void touchDown() {
        l.i("touchDown() AAAAAAAAAAAA");
    }

    @Override
    public void touchUp() {
        l.i("touchUp() BBBBBBBB");
    }


}
