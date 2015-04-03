package ch.webk.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.actors.combined.Fps;
import ch.webk.utils.Constants;
import ch.webk.utils.actor.ActorGenerator;
import ch.webk.utils.helper.GameMath;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.manager.GameManager;

public class MonsterStage extends GameStage {

    private Logger l = new Logger("MonsterStage", true);


    private boolean create = false;
    private Actor fps;

    public MonsterStage() {
        super();
        l.i("MoveStage()");
        setUpFPS();
        ActorGenerator.createPlatform(Constants.APP_WIDTH_WORLD / 2, 0, Constants.APP_WIDTH_WORLD, 0.5f);
        ActorGenerator.createPlatform(0,Constants.APP_HEIGHT/2,0.5f,Constants.APP_HEIGHT);
        ActorGenerator.createPlatform(Constants.APP_WIDTH_WORLD,Constants.APP_HEIGHT/2,0.5f,Constants.APP_HEIGHT);
        ActorGenerator.createPlatform(Constants.APP_WIDTH_WORLD / 2,Constants.APP_HEIGHT_WORLD,Constants.APP_WIDTH_WORLD,0.5f);

        ActorGenerator.createPlatform(5,14,10,0.5f);
        ActorGenerator.createPlatform(9,9,8,0.5f);

        ActorGenerator.createPlatform(27,13,6,0.5f);

        ActorGenerator.createPlatform(24,10,4,0.5f);

        ActorGenerator.createPlatform(12,6,8,0.5f);



        ActorGenerator.createMonsterLand(Constants.MONSTER_LAND_GREEN,5,1,1.2f,1.3f);
        ActorGenerator.createMonsterLand(Constants.MONSTER_LAND_BLUE,15,1,1.2f,1.3f);
        ActorGenerator.createMonsterLand(Constants.MONSTER_LAND_GREEN,20,1,1.2f,1.3f);
        ActorGenerator.createMonsterLand(Constants.MONSTER_LAND_BLUE,25,1,1.2f,1.3f);

        setTouchListener(new ITouchListener() {
            @Override
            public void touchDown(float screenX, float screenY, float worldX, float worldY) {
                if (create) {
                    float r = GameMath.getRandomFloat(0,1);
                    if (r < 0.5f) {
                        ActorGenerator.createMonsterLand(Constants.MONSTER_LAND_GREEN, worldX, worldY, 1.2f, 1.3f);
                    } else {
                        ActorGenerator.createMonsterLand(Constants.MONSTER_LAND_BLUE, worldX, worldY, 1.2f, 1.3f);
                    }
                }
            }

            @Override
            public void touchUp(float screenX, float screenY, float worldX, float worldY) {

            }

            @Override
            public void touchDragged(float screenX, float screenY, float worldX, float worldY) {

            }
        });

        GameManager.getWorld().setGravity(new Vector2(0,-15));
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.VOLUME_UP) {
            create = true;
            return true;
        }
        return super.keyDown(keyCode);
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (keyCode == Input.Keys.VOLUME_UP) {
            create = false;
            return true;
        }
        return super.keyUp(keyCode);
    }

    private void setUpFPS() {
        fps = new Fps();
        GameManager.addActor(fps);
    }

    @Override
    public void act(float delta) {
        fps.setZIndex(fps.getZIndex()+5);
        super.act(delta);
        // CameraManipulator.setPosition(runner.getScreenX(), runner.getScreenY());
    }

}
