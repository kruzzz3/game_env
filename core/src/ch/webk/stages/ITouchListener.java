package ch.webk.stages;

public interface ITouchListener {

    public abstract void touchDown(float screenX, float screenY, float worldX, float worldY);
    public abstract void touchUp(float screenX, float screenY, float worldX, float worldY);
}
