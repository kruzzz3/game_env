package ch.webk.box2d;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import ch.webk.utils.ActorManager;
import ch.webk.utils.BodyUtils;
import ch.webk.utils.Constants;

public class CustomRayCastCallback implements RayCastCallback {

    private SortedSet<Float> keys;
    private HashMap<Float,Body> bodies;
    private HashMap<Float,Vector2> points;
    private HashMap<Float,Vector2> normals;

    public CustomRayCastCallback() {
        bodies = new HashMap<Float,Body>();
        points = new HashMap<Float,Vector2>();
        normals = new HashMap<Float,Vector2>();
        keys = new TreeSet<Float>();
    }

    @Override
    public float reportRayFixture(Fixture fix, Vector2 point, Vector2 normal, float fraction) {
        if (fix != null) {
            if (fix.getBody() != null) {
                bodies.put(fraction,fix.getBody());
                points.put(fraction,point);
                normals.put(fraction,normal);
                keys.add(fraction);
            }
        }
        return -1;
    }

    public Body getNearestBody() {
        if (!bodies.isEmpty()) {
            return bodies.get(keys.first());
        }
        return null;
    }

    public Vector2 getNearestPoint() {
        if (!points.isEmpty()) {
            return points.get(keys.first());
        }
        return null;
    }

    public Vector2 getNearestNormal() {
        if (!normals.isEmpty()) {
            return normals.get(keys.first());
        }
        return null;
    }
}
