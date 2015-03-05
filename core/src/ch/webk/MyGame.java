package ch.webk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import ch.webk.ch.webk.screens.GameScreen;
import ch.webk.stages.GameStage;
import ch.webk.stages.RocketStage;
import ch.webk.utils.Constants;

public class MyGame extends Game {

    private int stage;

    public MyGame(int stage) {
        this.stage = stage;
    }

    @Override
    public void create() {
        switch (stage) {
            case Constants.STAGE_ROCKET: setScreen(new GameScreen(new RocketStage()));
        }
    }

    public void stop() {
        ((GameScreen) getScreen()).stop();
    }

    public void render() {
        super.render();
    }
}
