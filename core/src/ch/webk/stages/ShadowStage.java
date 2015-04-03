package ch.webk.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.actors.combined.Fps;
import ch.webk.actors.combined.Target;
import ch.webk.actors.screen.hud.Hud;
import ch.webk.lights.box2dLight.ConeLight;
import ch.webk.utils.actor.ActorGenerator;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.manager.GameManager;
import ch.webk.utils.manager.LightManager;

public class ShadowStage extends GameStage {

    private Logger l = new Logger("ShadowStage", true);

    private Target target;
    private Actor fps;
    private boolean newTarget = false;

    public ShadowStage() {
        super();
        l.i("ShadowStage()");
        useLight();
        setBrightness(0.1f);
        setUpTarget(1, 1);
        setUpFPS();

        ActorGenerator.createPlatform(2.5f,9.5f,5,0.1f);
        ActorGenerator.createPlatform(25.5f,15.5f,5,0.1f);
        ActorGenerator.createPlatform(18.5f,11.5f,8,0.1f);
        ActorGenerator.createPlatform(9.5f,5.5f,3,0.1f);
        ActorGenerator.createPlatform(22.5f,2.5f,7,0.1f);
        ActorGenerator.createPlatform(19,7f,8,0.1f);


        ActorGenerator.createPlatform(7.5f,6.5f,0.1f,8);
        ActorGenerator.createPlatform(10.5f,8.5f,0.1f,6);
        ActorGenerator.createPlatform(18.5f,14.5f,0.1f,7);
        ActorGenerator.createPlatform(28.5f,2.5f,0.1f,4);
        ActorGenerator.createPlatform(3.5f,13.5f,0.1f,5);
        ActorGenerator.createPlatform(18.5f,7f,0.1f,5);

        setTouchListener(new ITouchListener() {
            @Override
            public void touchDown(float screenX, float screenY, float worldX, float worldY) {
                if (newTarget) {
                    newTarget = false;
                    setUpTarget(worldX, worldY);
                } else {
                    target.setPosition(worldX, worldY);
                }
            }

            @Override
            public void touchUp(float screenX, float screenY, float worldX, float worldY) {

            }

            @Override
            public void touchDragged(float screenX, float screenY, float worldX, float worldY) {
                target.setPosition(worldX, worldY);
            }
        });

        hud = new Hud();

    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.VOLUME_UP) {
            newTarget = true;
            return true;
        }
        return super.keyDown(keyCode);
    }

    private void setUpFPS() {
        fps = new Fps();
        GameManager.addActor(fps);
    }

    private void setUpTarget(float x, float y) {
        target = ActorGenerator.createTarget(x, y, 0.1f);
        ConeLight pointLight1 = new ConeLight(LightManager.getRayHandler(), 180,0,45);
        pointLight1.attachToBody(target.getBody(), 0, 0);
        pointLight1.setColor(LightManager.setAlpha(Color.RED, 0.8f));
        pointLight1.setDistance(100);

        ConeLight pointLight2 = new ConeLight(LightManager.getRayHandler(), 180,90,45);
        pointLight2.attachToBody(target.getBody(), 0, 0);
        pointLight2.setColor(LightManager.setAlpha(Color.BLUE, 0.8f));
        pointLight2.setDistance(100);

        ConeLight pointLight3 = new ConeLight(LightManager.getRayHandler(), 180,180,45);
        pointLight3.attachToBody(target.getBody(), 0, 0);
        pointLight3.setColor(LightManager.setAlpha(Color.YELLOW, 0.8f));
        pointLight3.setDistance(100);

        ConeLight pointLight4 = new ConeLight(LightManager.getRayHandler(), 180,270,45);
        pointLight4.attachToBody(target.getBody(), 0, 0);
        pointLight4.setColor(LightManager.setAlpha(Color.GREEN, 0.8f));
        pointLight4.setDistance(100);
    }

    @Override
    public void act(float delta) {
        fps.setZIndex(fps.getZIndex()+5);
        super.act(delta);
    }

}
