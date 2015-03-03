package ch.webk;

import com.badlogic.gdx.Game;

import ch.webk.ch.webk.screens.GameScreen;
import ch.webk.stages.TestStage;

public class MyGame extends Game {

    private String config;

    public MyGame(String config) {
        this.config = config;
    }

    @Override
    public void create() {
        setScreen(new GameScreen(new TestStage()));
    }

}
