package ch.webk;

import com.badlogic.gdx.Game;

import ch.webk.screens.GameScreen;
import ch.webk.stages.ChainStage;
import ch.webk.stages.DistanceJointStage;
import ch.webk.stages.ExplosionStage;
import ch.webk.stages.MonsterStage;
import ch.webk.stages.MoveStage;
import ch.webk.stages.PolyStage;
import ch.webk.stages.RevoluteJointStage;
import ch.webk.stages.RocketStage;
import ch.webk.stages.RopeJointStage;
import ch.webk.stages.ShadowStage;
import ch.webk.utils.Constants;
import ch.webk.utils.helper.Logger;

public class MyGame extends Game {

    private Logger l = new Logger("MyGame", true);

    private int stage;

    public MyGame(int stage) {
        this.stage = stage;
    }

    @Override
    public void create() {
        l.i("create stage="+stage);
        switch (stage) {
            case Constants.STAGE_ROCKET:
                l.i("create setScreen Rocket");
                setScreen(new GameScreen(new RocketStage()));
                break;
            case Constants.STAGE_POLY:
                l.i("create setScreen Poly");
                setScreen(new GameScreen(new PolyStage()));
                break;
            case Constants.STAGE_MOVE:
                l.i("create setScreen Move");
                setScreen(new GameScreen(new MoveStage()));
                break;
            case Constants.STAGE_DISTANCE_JOINT:
                l.i("create setScreen Distance");
                setScreen(new GameScreen(new DistanceJointStage()));
                break;
            case Constants.STAGE_REVOLUTE_JOINT:
                l.i("create setScreen Revoulte");
                setScreen(new GameScreen(new RevoluteJointStage()));
                break;
            case Constants.STAGE_ROPE_JOINT:
                l.i("create setScreen Move");
                setScreen(new GameScreen(new RopeJointStage()));
                break;
            case Constants.STAGE_CHAIN:
                l.i("create setScreen Chain");
                setScreen(new GameScreen(new ChainStage()));
                break;
            case Constants.STAGE_EXPLOSION:
                l.i("create setScreen Explosion");
                setScreen(new GameScreen(new ExplosionStage()));
                break;
            case Constants.STAGE_MONSTER:
                l.i("create setScreen Monster");
                setScreen(new GameScreen(new MonsterStage()));
                break;
            case Constants.STAGE_SHADOW:
                l.i("create setScreen Shadow");
                setScreen(new GameScreen(new ShadowStage()));
                break;
        }
    }

    public void stop() {
        ((GameScreen) getScreen()).stop();
    }

    public void render() {
        super.render();
    }
}
