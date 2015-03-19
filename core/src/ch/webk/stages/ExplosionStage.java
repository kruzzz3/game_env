package ch.webk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.actors.combined.Fps;
import ch.webk.actors.special.ExplosionWithForce;
import ch.webk.utils.ActorGenerator;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class ExplosionStage extends GameStage {

    private Logger l = new Logger("ExplosionStage", true);

    private Actor fps;
    private Actor ball;

    public ExplosionStage() {
        super();
        l.i("ExplosionStage()");
        setUpFPS();
        setUpWall();

        Vector2 points[] = new Vector2[2];
        points[0] = new Vector2(5,10);
        points[1] = new Vector2(10,10);
        ActorGenerator.createChainExample1(points);

        points[0] = new Vector2(15,6);
        points[1] = new Vector2(20,6);
        ActorGenerator.createChainExample1(points);

        ball = ActorGenerator.createBall(5,2,1);

        setTouchListener(new ITouchListener() {
            @Override
            public void touchDown(float screenX, float screenY, float worldX, float worldY) {
                new ExplosionWithForce(screenX, screenY, 50, 50, 0);
            }

            @Override
            public void touchUp(float screenX, float screenY, float worldX, float worldY) {

            }
        });

        Gdx.input.setInputProcessor(this);
        startDebugRenderer();
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        l.i("touchDown x"+x+", y="+y);
        return super.touchDown(x, y, pointer, button);
    }

    private void setUpFPS() {
        fps = new Fps();
        WorldUtils.addActor(fps);
    }

    private void setUpWall() {
        float width = GameMath.transformToWorld(Constants.APP_WIDTH);
        float height = GameMath.transformToWorld(Constants.APP_HEIGHT);

        Vector2[] points = new Vector2[5];
        points[0] = new Vector2(0,0);
        points[1] = new Vector2(width,0);
        points[2] = new Vector2(width,height);
        points[3] = new Vector2(0,height);
        points[4] = new Vector2(0,0);

        ActorGenerator.createChainExample1(points);
    }

    @Override
    public void act(float delta) {
        fps.setZIndex(fps.getZIndex()+5);
        super.act(delta);
        //Vector2 gravity = new Vector2(Constants.accXDegree / 10, Constants.accYDegree / 10);
        WorldUtils.getWorld().setGravity(new Vector2(0,-1));
    }

}
