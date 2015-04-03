package ch.webk.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

import ch.webk.actors.combined.Bomb;
import ch.webk.actors.combined.Fps;
import ch.webk.utils.Constants;
import ch.webk.utils.actor.ActorGenerator;
import ch.webk.utils.helper.GameMath;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.manager.GameManager;

public class ExplosionStage extends GameStage {

    private Logger l = new Logger("ExplosionStage", true);

    private Actor fps;
    private ArrayList<Bomb> bombs;

    public ExplosionStage() {
        super();
        l.i("ExplosionStage()");
        setUpFPS();

        bombs = new ArrayList<Bomb>();
        setUpFPS();
        setUpWall();


        ActorGenerator.createBoxWood(7,1,2,2);
        ActorGenerator.createBoxWood(9,1,2,2);
        ActorGenerator.createBoxWood(11,1,2,2);
        ActorGenerator.createBoxWood(7.5f,2.5f,1,1);
        ActorGenerator.createBoxWood(8.5f,2.5f,1,1);
        ActorGenerator.createBoxWood(9.5f,2.5f,1,1);
        ActorGenerator.createBoxWood(10.5f,2.5f,1,1);
        ActorGenerator.createBoxWood(9,4,2,2);

        ActorGenerator.createBoxWood(27,1,2,2);
        ActorGenerator.createBoxWood(27,3,2,2);
        ActorGenerator.createBoxWood(27,4.5f,1,1);
        ActorGenerator.createBarrelWood(27,6,1);

        ActorGenerator.createPlatform(2.5f,9.5f,10,1);
        ActorGenerator.createBoxWood(2,13,1,6);
        ActorGenerator.createBoxWood(3.5f,13,1,6);
        ActorGenerator.createBoxWood(5,13,1,6);
        ActorGenerator.createBoxWood(6.5f,13,1,6);

        ActorGenerator.createPlatform(18,5.5f,8,1);
        ActorGenerator.createPlatform(14.75f,6.05f,1.5f,0.1f);
        ActorGenerator.createPlatform(21.25f,6.05f,1.5f,0.1f);
        ActorGenerator.createBarrelWood(16,7,1);
        ActorGenerator.createBarrelWood(18,7,1);
        ActorGenerator.createBarrelWood(20,7,1);
        ActorGenerator.createBarrelWood(17,9,1);
        ActorGenerator.createBarrelWood(19,9,1);
        ActorGenerator.createBarrelWood(18,11,1);

        ActorGenerator.createPlatform(27,11.5f,6,1);
        ActorGenerator.createBoxWood(25f,12.5f,1,1);
        ActorGenerator.createBoxWood(25f,13.5f,1,1);
        ActorGenerator.createBoxWood(25f,14.5f,1,1);
        ActorGenerator.createBoxWood(25f,15.5f,1,1);
        ActorGenerator.createBarrelWood(28,14,2);

        ActorGenerator.createPlatform(4.5f, 3f, 0.5f, 6);
        ActorGenerator.createBoxWood(2,0.5f,1,1);
        ActorGenerator.createBoxWood(2,1.5f,1,1);
        ActorGenerator.createBoxWood(2,2.5f,1,1);
        ActorGenerator.createBoxWood(2,3.5f,1,1);
        ActorGenerator.createBoxWood(2,4.5f,1,1);
        ActorGenerator.createBoxWood(2,5.5f,1,1);





        setTouchListener(new ITouchListener() {
            @Override
            public void touchDown(float screenX, float screenY, float worldX, float worldY) {
                bombs.add(ActorGenerator.createBomb(worldX,worldY,1,1));
            }

            @Override
            public void touchUp(float screenX, float screenY, float worldX, float worldY) {

            }

            @Override
            public void touchDragged(float screenX, float screenY, float worldX, float worldY) {

            }
        });
    }

    @Override
    public boolean keyDown(int keyCode) {
        l.i("keyDown 1");
        if (keyCode == Input.Keys.VOLUME_UP) {
            l.i("keyDown 2");
            for(Bomb bomb : bombs) {
                bomb.explode();
            }
            bombs = new ArrayList<Bomb>();
            return true;
        }
        return super.keyDown(keyCode);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        l.i("touchDown x"+x+", y="+y);
        return super.touchDown(x, y, pointer, button);
    }

    private void setUpFPS() {
        fps = new Fps();
        GameManager.addActor(fps);
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
        GameManager.getWorld().setGravity(new Vector2(0,-10));
    }

}
