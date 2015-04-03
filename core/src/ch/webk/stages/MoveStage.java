package ch.webk.stages;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.actors.combined.Fps;
import ch.webk.actors.combined.Runner;
import ch.webk.utils.Constants;
import ch.webk.utils.actor.ActorGenerator;
import ch.webk.utils.helper.GameMath;
import ch.webk.utils.helper.Logger;
import ch.webk.utils.manager.GameManager;

public class MoveStage extends GameStage {

    private Logger l = new Logger("MoveStage", true);

    private Actor fps;
    private Runner runner;
    private Body defaultBody;
    private MouseJoint mouse;

    public MoveStage() {
        super();
        l.i("MoveStage()");
        setUpFPS();
        setUpWall();
        setUpRunner();
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        l.i("touchDown x"+x+", y="+y);

        float xWorld = x / Constants.WORLD_TO_SCREEN;
        float ty = (int) Constants.APP_HEIGHT -y;
        float yWorld = ty / Constants.WORLD_TO_SCREEN;
        Vector2 v1 = new Vector2(xWorld, yWorld);
        Vector2 v2 = runner.getBody().getPosition();

        float x1 = v1.x - v2.x;
        float y1 = v2.y - v1.y;
        Vector2 v = new Vector2(xWorld,yWorld);
        runner.moveTo(xWorld, yWorld);
        //mouse.setFrequency(2);
        //mouse.setTarget(v);
        return super.touchDown(x, y, pointer, button);
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

    private void setUpRunner() {
        l.i("setUpRunner()");

        float w = 2;
        float h = w * 2;
        float x = GameMath.getRandomFloat(w, Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN - w);
        float y = GameMath.getRandomFloat(h, Constants.APP_HEIGHT/Constants.WORLD_TO_SCREEN - h);
        runner = ActorGenerator.createRunner(x,y,w,h);

        float xWorld = (Constants.APP_WIDTH / Constants.WORLD_TO_SCREEN) / 2;
        float yWorld = (Constants.APP_HEIGHT / Constants.WORLD_TO_SCREEN) / 2;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(xWorld, yWorld));

        defaultBody = GameManager.getWorld().createBody(bodyDef);

        //mouse = runner.mouse(defaultBody);
    }

}
