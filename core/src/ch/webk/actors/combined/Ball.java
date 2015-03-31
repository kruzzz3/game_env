package ch.webk.actors.combined;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import box2dLight.ConeLight;
import ch.webk.box2d.BallUserData;
import ch.webk.light.WorldPointLight;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class Ball extends GameCombinedActor {

    private Logger l = new Logger("Ball", true);

    private Animation animation;

    private WorldPointLight light;

    public Ball(Body body) {
        super(body);

        animation = ActorManager.getAnimation(Constants.BALL, 0.2f);

        float a = GameMath.getRandomFloat(0,20) - GameMath.getRandomFloat(0,20);
        float b = GameMath.getRandomFloat(0,20) - GameMath.getRandomFloat(0,20);

        body.applyLinearImpulse(new Vector2(a, b), body.getWorldCenter(), true);

        setIsTouchable(true);
        createLight();
    }

    private void createLight() {
        light = new WorldPointLight();
        light.attachToBody(body,0,0);
        light.setDistance(10);
        light.setXray(false);
    }

    @Override
    public BallUserData getUserData() {
        return (BallUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawLoopAnimation(batch, animation);
    }

    @Override
    public void dispose() {}

    @Override
    public void resume() {}

}
