package ch.webk.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.Iterator;

import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.actors.screen.GameScreenActor;
import ch.webk.box2d.UserData;
import ch.webk.utils.ActorGenerator;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class GameStage extends Stage implements ContactListener {

    private Logger l = new Logger("GameStage", true);

    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private final float TIME_STEP = 1 / 45f;
    private float accumulator = 0f;

    private Rectangle vp;

    public GameStage() {
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        l.i("GameStage()");
        WorldUtils.setStage(this);
        ActorManager.init();
        setUpWorld();
        setUpCamera();
    }

    public void setVp(Rectangle viewport) {
        vp = viewport;
    }

    public Rectangle getVp() {
        return vp;
    }

    private void setUpWorld() {
        l.i("setUpWorld()");
        World world = new World(Constants.WORLD_GRAVITY, true);
        WorldUtils.setWorld(world);
        WorldUtils.getWorld().setContactListener(this);
    }

    private void setUpCamera() {
        l.i("setupCamera()");
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();Matrix4 debugMatrix=new Matrix4(camera.combined);
        debugMatrix.scale(Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN, 0);

        WorldUtils.setCamera(camera);
        //WorldUtils.setRenderer(new Box2DDebugRenderer());
        WorldUtils.setDebugMatrix(debugMatrix);
    }

    protected void setUpTest() {
        l.i("setUpTest()");

        float w1 = GameMath.getRandomFloat(3,6);
        float x1 = GameMath.getRandomFloat(w1, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w1);
        float y1 = GameMath.getRandomFloat(w1, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w1);
        ActorGenerator.createPolyStar(x1,y1,w1,w1);

        float w2 = GameMath.getRandomFloat(3,6);
        float x2 = GameMath.getRandomFloat(w2, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w2);
        float y2 = GameMath.getRandomFloat(w2, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w2);
        ActorGenerator.createPolyStar(x2,y2,w2,w2);

        float w3 = GameMath.getRandomFloat(3,6);
        float x3 = GameMath.getRandomFloat(w3, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w3);
        float y3 = GameMath.getRandomFloat(w3, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w3);
        ActorGenerator.createPolyStairs(x3,y3,w3,w3);

        float w4 = GameMath.getRandomFloat(3,6);
        float x4 = GameMath.getRandomFloat(w4, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w4);
        float y4 = GameMath.getRandomFloat(w4, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w4);
        ActorGenerator.createPolyStairs(x4, y4, w4, w4);

        float w5 = GameMath.getRandomFloat(3,6);
        float x5 = GameMath.getRandomFloat(w5, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w5);
        float y5 = GameMath.getRandomFloat(w5, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w5);
        ActorGenerator.createPolyTriangle(x5, y5, w5, w5);

        float w6 = GameMath.getRandomFloat(3,6);
        float x6 = GameMath.getRandomFloat(w6, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w6);
        float y6 = GameMath.getRandomFloat(w6, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - w6);
        ActorGenerator.createPolyTriangle(x6,y6,w6,w6);

    }

    @Override
    public void draw() {
        super.draw();
        if (WorldUtils.getRenderer() != null) {
            WorldUtils.getRenderer().render(WorldUtils.getWorld(), WorldUtils.getDebugMatrix());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        Array<Body> bodies = new Array<Body>(WorldUtils.getWorld().getBodyCount());
        WorldUtils.getWorld().getBodies(bodies);

        for (Body body : bodies) {
            update(body);
        }

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            WorldUtils.getWorld().step(TIME_STEP, 8, 3);
            accumulator -= TIME_STEP;
        }

        //TODO: Implement interpolation
    }

    protected void update(Body body) {
        try {
            if (((UserData) body.getUserData()).getDestroy()) {
                body.setUserData(null);
                WorldUtils.getWorld().destroyBody(body);
                body = null;
            }
        } catch (Exception e) {}
    }


    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        Iterator itr = getActors().iterator();
        while(itr.hasNext()) {
            Actor actor = (Actor) itr.next();
            try {
                GameCombinedActor a = (GameCombinedActor) actor;
                if (a.checkTouch(x, y)) {
                    a.touchDown();
                }
            } catch (Exception e) {}
            try {
                GameScreenActor a = (GameScreenActor) actor;
                if (a.checkTouch(x, y)) {
                    a.touchDown();
                }
            } catch (Exception e) {}
        }
        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Iterator itr = getActors().iterator();
        while(itr.hasNext()) {
            Actor actor = (Actor) itr.next();
            try {
                GameCombinedActor a = (GameCombinedActor) actor;
                if (a.checkTouch(screenX, screenY)) {
                    a.touchUp();
                }
            } catch (Exception e) {}
            try {
                GameScreenActor a = (GameScreenActor) actor;
                if (a.checkTouch(screenX, screenY)) {
                    a.touchUp();
                }
            } catch (Exception e) {}
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        try {((UserData) a.getUserData()).beginContact(b);} catch (Exception e) {}
        try {((UserData) b.getUserData()).beginContact(a);} catch (Exception e) {}
    }

    @Override
    public void endContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        try {((UserData) a.getUserData()).endContact(b);} catch (Exception e) {}
        try {((UserData) b.getUserData()).endContact(a);} catch (Exception e) {}
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
