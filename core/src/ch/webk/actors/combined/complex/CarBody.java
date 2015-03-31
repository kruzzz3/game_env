package ch.webk.actors.combined.complex;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.box2d.SimpleUserData;
import ch.webk.light.WorldConeLight;
import ch.webk.light.WorldPointLight;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;
import ch.webk.utils.UDM;
import ch.webk.utils.WorldUtils;

public class CarBody extends GameCombinedActor {

    private Logger l = new Logger("CarBody", true);

    private final TextureRegion textureRegion;
    private WorldConeLight light;

    public CarBody(Body body) {
        super(body);
        textureRegion = ActorManager.getTextureRegion(Constants.CAR_BODY);
        setIsTouchable(true);
        createLight();
    }

    private void createLight() {
        light = new WorldConeLight(0,10);
        light.attachToBody(body, UDM.getUserData(body).getWidth()/2,-0.5f);
        light.setDistance(10);
        light.setSoftnessLength(0);
        light.setSoft(true);
        light.setXray(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //pointLight.setPosition(body.getWorldCenter());
        //pointLight.update();
        l.i("pX="+light.getPosition().x+", pY="+light.getPosition().y);
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
