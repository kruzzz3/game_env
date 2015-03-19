package ch.webk.actors.special;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import ch.webk.actors.screen.Explosion;
import ch.webk.utils.Constants;
import ch.webk.utils.GameMath;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class ExplosionWithForce extends Explosion {

    private Logger l = new Logger("ExplosionWithForce", true);

    private float x;
    private float y;
    private float force = 10;
    private float distance = (Constants.APP_WIDTH/Constants.WORLD_TO_SCREEN) / 2;
    private float rayCount = 180;

    public ExplosionWithForce(float x, float y, float width, float height, float rotDegree) {
        super(Constants.EXPLOSION, x, y, width, height, rotDegree);
        l.i("ExplosionWithForce");
        this.x = GameMath.transformToWorld(x);
        this.y = GameMath.transformToWorld(y);
        applyForce();
    }

    private void applyForce() {
        l.i("applyForce");
        float radStep = MathUtils.PI2 / rayCount;
        for (int i = 0; i <= rayCount; i++) {
            float currentRad = radStep * i;
            final Vector2 from = new Vector2(x, y);
            final Vector2 to = GameMath.rotateVector2Radians(from,currentRad);
            to.setLength(distance);

            WorldUtils.getWorld().rayCast(new RayCastCallback() {

                @Override
                public float reportRayFixture(Fixture fix, Vector2 point, Vector2 normal, float fraction) {
                    l.i("reportRayFixture");
                    if (fix != null) {
                        if (fix.getBody() != null) {
                            Vector2 strength = new Vector2(point.x - from.x, point.y - from.y);
                            strength.setLength(force / Math.abs(point.dst(from)));
                            fix.getBody().applyLinearImpulse(strength, point, true);
                            return 0;
                        }
                    }
                    return -1;
                }

            }, from, to);
        }
    }



}
