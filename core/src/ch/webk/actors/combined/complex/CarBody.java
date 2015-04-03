package ch.webk.actors.combined.complex;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.box2d.SimpleUserData;
import ch.webk.lights.box2dLight.ConeLight;
import ch.webk.lights.box2dLight.PointLight;
import ch.webk.utils.manager.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.helper.UDM;
import ch.webk.utils.manager.LightManager;

public class CarBody extends GameCombinedActor {

    private Logger l = new Logger("CarBody", true);

    private final TextureRegion textureRegion;
    private ConeLight lightFront1;
    private PointLight lightFront2;
    private PointLight lightBack;

    public CarBody(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.CAR_BODY);
        setIsTouchable(true);
        createLight();
    }

    private void createLight() {
        lightFront1 = new ConeLight(LightManager.getRayHandler(), 36, Color.WHITE, 20, 0, 0, -6, 5);
        lightFront1.attachToBody(body, UDM.getUserData(body).getWidth()/2 - 0.1f,-UDM.getUserData(body).getHeight()/4);

        lightFront2 = new PointLight(LightManager.getRayHandler(), 36, LightManager.setAlpha(Color.WHITE,0.8f), 5, 0, 0);
        lightFront2.attachToBody(body, UDM.getUserData(body).getWidth()/2 - 0.1f,-UDM.getUserData(body).getHeight()/4);
        lightFront2.setXray(true);

        lightBack = new PointLight(LightManager.getRayHandler(), 36, Color.RED, 3, 5, 5);
        lightBack.attachToBody(body, -UDM.getUserData(body).getWidth()/2-0.1f,-UDM.getUserData(body).getHeight()/4);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public SimpleUserData getUserData() {
        return (SimpleUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawTextureRegion(batch, textureRegion);
    }

    @Override
    public void dispose() {}

    @Override
    public void resume() {}

}
