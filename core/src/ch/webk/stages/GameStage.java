package ch.webk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.Iterator;

import ch.webk.IUIListener;
import ch.webk.actors.GameActor;
import ch.webk.actors.screen.hud.Hud;
import ch.webk.box2d.UserData;
import ch.webk.enums.Action;
import ch.webk.utils.ActorManager;
import ch.webk.utils.BreakableTimer;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.UDM;
import ch.webk.utils.WorldUtils;

public abstract class GameStage extends Stage implements ContactListener {

    private Logger l = new Logger("GameStage", true);

    private static final float VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final float VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private final float TIME_STEP = 1 / 40f;
    private float accumulator = 0f;

    private ITouchListener iTouchListener;
    private IUIListener iUIListener;

    private Rectangle vp;

    protected Hud hud;

    public GameStage() {
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        l.i("GameStage()");
        BreakableTimer.clear();
        WorldUtils.setStage(this);
        ActorManager.init();
        setUpWorld();
        setUpCamera();
        WorldUtils.initRayHandler();

        //new PointLight(WorldUtils.getRayHandler(), 360, Color.ORANGE, 30, Constants.APP_WIDTH / 2 / Constants.WORLD_TO_SCREEN, Constants.APP_HEIGHT / 2 / Constants.WORLD_TO_SCREEN);
        //new PointLight(WorldUtils.getRayHandler(), 360, Color.ORANGE, 30, 0, 0);

        //new WorldPointLight();

        //DirectionalLight light = new DirectionalLight(WorldUtils.getRayHandler(), 32, new Color(0.1f,0.1f,0.1f,0.6f),90);
        //light.setSoft(true);
        //light.setXray(true);
        //light.setSoftnessLength(1000000);
        //PointLight pointLight = new PointLight( WorldUtils.getRayHandler(), 32, new Color(1f, 0.0f, 0.0f, 0.8f), 200, 0, 0);

        //pointLight.setXray(true);
        hud = new Hud();
        startDebugRenderer();
        Gdx.input.setInputProcessor(this);
    }

    public void setTouchListener(ITouchListener iTouchListener) {
        this.iTouchListener = iTouchListener;
    }

    public void setUIListener(IUIListener iUIListener) {
        l.i("setUIListener");
        this.iUIListener = iUIListener;
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
           if (UDM.getUserData(body).getDestroy()) {
               l.i("update 2");
               final Array<JointEdge> list = body.getJointList();
               while (list.size > 0) {
                   l.i("update J");
                   WorldUtils.getWorld().destroyJoint(list.get(0).joint);
               }
               body.setUserData(null);
               l.i("update 3");
               WorldUtils.getWorld().destroyBody(body);
               body = null;
            }
        } catch (Exception e) {l.i("E e="+e);}

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
                GameActor a = (GameActor) actor;
                if (a.checkTouch(x, y)) {
                    if (a.touchDown()) {
                        break;
                    }
                }
            } catch (Exception e) {}
        }

        if(iTouchListener != null) {
            if (WorldUtils.isRunning()) {
                iTouchListener.touchDown(x, y, GameMath.transformToWorld(x), GameMath.transformToWorld(y));
            }
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
                GameActor a = (GameActor) actor;
                if (a.checkTouch(x, y)) {
                    if (a.touchUp()) {
                        break;
                    }
                }
            } catch (Exception e) {}
        }

        if(iTouchListener != null) {
            if (WorldUtils.isRunning()) {
                iTouchListener.touchUp(x, y, GameMath.transformToWorld(x), GameMath.transformToWorld(y));
            }
        }
        return super.touchUp(x, y, pointer, button);
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {

        x += WorldUtils.getCamera().position.x;
        y = (int) Constants.APP_HEIGHT - y;
        y += WorldUtils.getCamera().position.y;
        x -= Constants.APP_WIDTH / 2;
        y -= Constants.APP_HEIGHT / 2;

        if(iTouchListener != null) {
            if (WorldUtils.isRunning()) {
                iTouchListener.touchDragged(x, y, GameMath.transformToWorld(x), GameMath.transformToWorld(y));
            }
        }
        return super.touchDragged(x, y, pointer);
    }

    @Override
    public void beginContact(Contact contact) {
        l.i("beginContact");
        //lock.lock();
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        try {((UserData) a.getUserData()).beginContact(b);} catch (Exception e) {}
        try {((UserData) b.getUserData()).beginContact(a);} catch (Exception e) {}
        //lock.unlock();
    }

    @Override
    public void endContact(Contact contact) {
        l.i("endContact");
        //lock.lock();
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        try {((UserData) a.getUserData()).endContact(b);} catch (Exception e) {}
        try {((UserData) b.getUserData()).endContact(a);} catch (Exception e) {}
        //lock.unlock();
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void sendUIAction(Action action) {
        l.i("sendUIAction");
        try {
            l.i(iUIListener.toString());
        } catch (Exception e) {
            l.i("sendUIAction sdf sdf");
        }
        if (iUIListener != null) {
            l.i("sendUIAction 2");
            iUIListener.sendUIAction(action);
        }
    }

    public void stop() {
        for (Actor actor : getActors()) {
            try {
                ((GameActor) actor).dispose();
            } catch (Exception e) {}
        }
    }

    public void pause() {
        BreakableTimer.stop();
    }

    public void resume() {
        int size = getActors().size;
        for(int i = 0; i < size; i++) {
            try {
                GameActor actor = (GameActor) getActors().get(i);
                actor.resume();
            } catch (Exception e) {}
        }
    }
}
