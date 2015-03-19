package ch.webk.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.ArrayList;
import java.util.Iterator;

import ch.webk.actors.GameActor;
import ch.webk.actors.combined.GameCombinedActor;
import ch.webk.actors.screen.GameScreenActor;
import ch.webk.box2d.UserData;
import ch.webk.utils.ActorManager;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public abstract class GameStage extends Stage implements ContactListener {

    private Logger l = new Logger("GameStage", true);

    private static final float VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final float VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private final float TIME_STEP = 1 / 40f;
    private float accumulator = 0f;

    private ITouchListener iTouchListener;

    private Rectangle vp;

    public GameStage() {
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        l.i("GameStage()");
        WorldUtils.setStage(this);
        ActorManager.init();
        setUpWorld();
        setUpCamera();
    }

    public void setTouchListener(ITouchListener iTouchListener) {
        this.iTouchListener = iTouchListener;
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
        WorldUtils.setDebugMatrix(debugMatrix);
    }

    protected void startDebugRenderer() {
        WorldUtils.setRenderer(new Box2DDebugRenderer());
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
            WorldUtils.getWorld().step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        //TODO: Implement interpolation
    }

    protected void update(Body body) {
        try {
            if (((UserData) body.getUserData()).getDestroy()) {
                ArrayList<Joint> joints = ((UserData) body.getUserData()).getJoints();
                for (Joint joint : joints) {
                    try {
                        WorldUtils.getWorld().destroyJoint(joint);
                        joint = null;
                    } catch (Exception e) {}
                }
                body.setUserData(null);
                WorldUtils.getWorld().destroyBody(body);
                body = null;
            }
        } catch (Exception e) {}
    }


    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        Iterator itr = getActors().iterator();

        x += WorldUtils.getCamera().position.x;
        y = (int) Constants.APP_HEIGHT - y;
        y += WorldUtils.getCamera().position.y;
        x -= Constants.APP_WIDTH / 2;
        y -= Constants.APP_HEIGHT / 2;

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

        if(iTouchListener != null) {
            iTouchListener.touchDown(x, y, GameMath.transformToWorld(x), GameMath.transformToWorld(y));
        }

        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        Iterator itr = getActors().iterator();

        x += WorldUtils.getCamera().position.x;
        y = (int) Constants.APP_HEIGHT - y;
        y += WorldUtils.getCamera().position.y;
        x -= Constants.APP_WIDTH / 2;
        y -= Constants.APP_HEIGHT / 2;

        while(itr.hasNext()) {
            Actor actor = (Actor) itr.next();
            try {
                GameCombinedActor a = (GameCombinedActor) actor;
                if (a.checkTouch(x, y)) {
                    a.touchUp();
                }
            } catch (Exception e) {}
            try {
                GameScreenActor a = (GameScreenActor) actor;
                if (a.checkTouch(x, y)) {
                    a.touchUp();
                }
            } catch (Exception e) {}
        }

        if(iTouchListener != null) {
            iTouchListener.touchUp(x, y, GameMath.transformToWorld(x), GameMath.transformToWorld(y));
        }
        return super.touchUp(x, y, pointer, button);
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

    public void stop() {
        for (Actor actor : getActors()) {
            try {
                ((GameActor) actor).dispose();
            } catch (Exception e) {}
        }
    }
}
