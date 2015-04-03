package ch.webk.actors.combined;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.webk.utils.manager.ActorManager;

public class Fps extends Actor {
    private BitmapFont mBitmapFont;

    public Fps() {
        mBitmapFont = ActorManager.getFont();
        mBitmapFont.setColor(0.9f, 0.5f, 0.5f, 1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        mBitmapFont.draw(batch, "FPS:"+ Gdx.graphics.getFramesPerSecond(), 50, 50);
    }

}
