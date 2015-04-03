package ch.webk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
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
import ch.webk.lights.box2dLight.FixedAreaLight;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.BreakableTimer;
import ch.webk.utils.helper.GameMath;
import ch.webk.utils.helper.GameRectangle;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.helper.UDM;
import ch.webk.utils.manager.ActorManager;
import ch.webk.utils.manager.GameManager;
import ch.webk.utils.manager.LightManager;

public abstract class GameStage extends Stage implements ContactListener {

    private Logger l = new Logger("GameStage", true);

    private static final float VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final float VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private final float TIME_STEP = 1 / 40f;
    private float accumulator = 0f;

    private ITouchListener iTouchListener;
    private IUIListener iUIListener;

    private GameRectangle viewportRectangle;
    private FixedAreaLight fixedAreaLight;

    protected Hud hud;

    public GameStage() {
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        l.i("GameStage()");
        init();
    }

    private void init() {
        BreakableTimer.clear();
        GameManager.setStage(this);
        ActorManager.init();
        setUpWorld();
        setUpCamera();

        //startDebugRenderer();
        Gdx.input.setInputProcessor(this);
    }

    public void useLight() {
        LightManager.initRayHandler();
        setBrightness(0);
    }

    public void setBrightness(float brightness) {
        Color c  = Color.BLACK;
        c = new Color(c.r,c.g,c.b,brightness);

        if (fixedAreaLight != null) {
            fixedAreaLight.setActive(false);
            fixedAreaLight.remove();
            fixedAreaLight = null;
        }

        fixedAreaLight = new FixedAreaLight(c, Constants.APP_WIDTH_WORLD/2, Constants.APP_HEIGHT_WORLD/2, Constants.APP_WIDTH_WORLD, Constants.APP_HEIGHT_WORLD);
        fixedAreaLight.setSoft(true);
        fixedAreaLight.setXray(true);
        fixedAreaLight.setSoftnessLength(Float.POSITIVE_INFINITY);
    }

    public void setTouchListener(ITouchListener iTouchListener) {
        this.iTouchListener = iTouchListener;
    }

    public void setUIListener(IUIListener iUIListener) {
        l.i("setUIListener");
        this.iUIListener = iUIListener;
    }

    public void setViewportRectangle(GameRectangle viewportRectangle) {
        this.viewportRectangle = viewportRectangle;
    }

    public GameRectangle getViewportRectangle() {
        return viewportRectangle;
    }

    private void setUpWorld() {
        l.i("setUpWorld()");
        World world = new World(Constants.WORLD_GRAVITY, true);
        GameManager.setWorld(world);
        GameManager.getWorld().setContactListener(this);
    }

    private void setUpCamera() {
        l.i("setupCamera()");
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();Matrix4 debugMatrix=new Matrix4(camera.combined);
        debugMatrix.scale(Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN, 0);

        GameManager.setCamera(camera);
        GameManager.setDebugMatrix(debugMatrix);
    }

    protected void startDebugRenderer() {
        GameManager.setRenderer(new Box2DDebugRenderer());
    }

    @Override
    public void draw() {
        super.draw();
        if (GameManager.getRenderer() != null) {
            GameManager.getRenderer().render(GameManager.getWorld(), GameManager.getDebugMatrix());
        }
    }



    @Override
    public void act(float delta) {
        super.act(delta);

        Array<Body> bodies = new Array<Body>(GameManager.getWorld().getBodyCount());
        GameManager.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            update(body);
        }

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            GameManager.getWorld().step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        //TODO: Implement interpolation
    }

    protected void update(Body body) {
       try {
           if (UDM.getUserData(body).getDestroy()) {
               final Array<JointEdge> list = body.getJointList();
               while (list.size > 0) {
                   GameManager.getWorld().destroyJoint(list.get(0).joint);
               }
               body.setUserData(null);
               GameManager.getWorld().destroyBody(body);
               body = null;
            }
        } catch (Exception e) {l.i("E e="+e);}

    }


    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        Iterator itr = getActors().iterator();

        x += GameManager.getCamera().position.x;
        y = (int) Constants.APP_HEIGHT - y;
        y += GameManager.getCamera().position.y;
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
            if (GameManager.isRunning()) {
                iTouchListener.touchDown(x, y, GameMath.transformToWorld(x), GameMath.transformToWorld(y));
            }
        }

        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        Iterator itr = getActors().iterator();

        x += GameManager.getCamera().position.x;
        y = (int) Constants.APP_HEIGHT - y;
        y += GameManager.getCamera().position.y;
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
            if (GameManager.isRunning()) {
                iTouchListener.touchUp(x, y, GameMath.transformToWorld(x), GameMath.transformToWorld(y));
            }
        }
        return super.touchUp(x, y, pointer, button);
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {

        x += GameManager.getCamera().position.x;
        y = (int) Constants.APP_HEIGHT - y;
        y += GameManager.getCamera().position.y;
        x -= Constants.APP_WIDTH / 2;
        y -= Constants.APP_HEIGHT / 2;

        if(iTouchListener != null) {
            if (GameManager.isRunning()) {
                iTouchListener.touchDragged(x, y, GameMath.transformToWorld(x), GameMath.transformToWorld(y));
            }
        }
        return super.touchDragged(x, y, pointer);
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
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    public void sendUIAction(Action action) {
        try {
            l.i(iUIListener.toString());
        } catch (Exception e) {}
        if (iUIListener != null) {
            iUIListener.sendUIAction(action);
        }
    }

    public void stop() {
        LightManager.disposeRayHandler();
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
