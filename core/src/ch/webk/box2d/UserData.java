package ch.webk.box2d;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;

import java.util.ArrayList;

import ch.webk.enums.UserDataType;
import ch.webk.utils.Logger;

public abstract class UserData {

    private Logger l = new Logger("UserData", true);

    protected UserDataType userDataType;
    private float width;
    private float height;
    private float rotationFixRadians = 0;
    private boolean destroy = false;
    private ICollisionListener iCollisionListener;
    private ArrayList<Joint> joints = new ArrayList<Joint>();

    public UserData(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setRotationFixDegree(Body body, float rotationFixDegree) {
        this.rotationFixRadians = rotationFixDegree * MathUtils.degreesToRadians;
        body.setTransform(body.getPosition().x,body.getPosition().y, this.rotationFixRadians);
    }

    public float getRotationFixRadians() {
        return rotationFixRadians;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public UserDataType getUserDataType() {
        return userDataType;
    }

    public void addJointReference(Joint joint) {
        joints.add(joint);
    }

    public void removeJointReference(Joint joint) {
        joints.remove(joint);
    }

    public ArrayList<Joint> getJoints() {
        return joints;
    }

    public void setCollisionListener(ICollisionListener iCollisionListener) {
        this.iCollisionListener = iCollisionListener;
    }

    public void beginContact(Body body) {
        if (iCollisionListener != null) {
            iCollisionListener.beginContact(body);
        }
    }

    public void endContact(Body body) {
        if (iCollisionListener != null) {
            iCollisionListener.endContact(body);
        }
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public boolean getDestroy() {
        return destroy;
    }
}