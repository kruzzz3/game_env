package ch.webk.actors.special;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.actors.screen.Explosion;
import ch.webk.utils.BodyUtils;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;
import ch.webk.box2d.CustomRayCastCallback;

public class ExplosionWithForce extends Explosion {

    private Logger l = new Logger("ExplosionWithForce", true);

    private float x;
    private float y;
    private float force = 5f;
    private float distance = 20;
    private float rayCount = 36;

    public ExplosionWithForce(float x, float y, float width, float height, float rotDegree) {
        super(Constants.EXPLOSION, x, y, width, height, rotDegree);
        l.i("ExplosionWithForce distance="+distance);
        this.x = x;
        this.y = y;
        applyForce();
    }

    private void applyForce() {
        l.i("applyForce");
        float radStep = MathUtils.PI2 / rayCount;
        Vector2 v = new Vector2(0,1);
        for (int i = 0; i <= rayCount; i++) {
            float currentRad = radStep * i;
            //l.i("i="+i+", currentRad="+currentRad);
            final Vector2 from = new Vector2(x, y);
            l.i("i="+i+", fromX="+from.x+", fromY="+from.y+", deg="+currentRad* MathUtils.radiansToDegrees);
            final Vector2 to = GameMath.rotateVector2Radians(v, currentRad);
            to.setLength(distance);
            to.add(from);
            l.i("i="+i+", toX="+to.x+", toY="+to.y);
            final int j = i;

            CustomRayCastCallback customRayCastCallback = new CustomRayCastCallback();
            WorldUtils.getWorld().rayCast(customRayCastCallback, from, to);
            Body body = customRayCastCallback.getNearestBody();
            l.i("body="+body);
            if (BodyUtils.bodyIsBox(body)) {
                Vector2 point = customRayCastCallback.getNearestPoint();
                Vector2 strength = new Vector2(point.x - from.x, point.y - from.y);

                strength.setLength(force / Math.abs(point.dst(from)));
                float dist = Math.abs(point.dst(from));
                //l.i("i=" + j + ", dist=" + dist + ", strengthX=" + strength.x + ", strengthY=" + strength.y);
                body.applyLinearImpulse(strength, point, true);
            }
        }
    }



}
