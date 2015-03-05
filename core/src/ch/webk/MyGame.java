package ch.webk;

import com.badlogic.gdx.Game;

import ch.webk.ch.webk.screens.GameScreen;
import ch.webk.stages.PolyStage;
import ch.webk.stages.RocketStage;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;

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
        }
    }

    public void stop() {
        ((GameScreen) getScreen()).stop();
    }

    public void render() {
        super.render();
    }
}
