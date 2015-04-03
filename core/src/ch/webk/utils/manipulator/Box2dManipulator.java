package ch.webk.utils.manipulator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.webk.box2d.UserData;
import ch.webk.utils.helper.Logger;

public class Box2dManipulator {

    private static Logger l = new Logger("Box2dManipulator", true);

    private static float getRot(Body body, float rotRadians) {
        float r = 0;
        try {
            r = body.getAngle() - ((UserData) body.getUserData()).getRotationFixRadians() + rotRadians;
            r = (r + MathUtils.PI2) % (MathUtils.PI2);
            if (r < 0) {
                r = MathUtils.PI2 + r;
            }
        } catch (Exception e) {
            l.i("e=getRot");
        }
        return Math.abs(r);
    }

    public static void steerAt(Body body, Vector2 target, float angularAcceleration, float maxAngularVelocity, float rotRadians) {
        try {
            Vector2 source = body.getWorldCenter();

            float targetX = target.x - source.x;
            float targetY = target.y - source.y;

            float a = getRot(body, rotRadians);
            //Vector2 v2 = new Vector2(source.x - target.x, source.y - target.x);
            float b = Math.abs(MathUtils.atan2(targetX, targetY));

            if (target.x > source.x) {
                b = 2 * MathUtils.PI - b;
            }

            boolean lT = false;
            boolean rT = false;

            if (body.getAngularVelocity() < 0) {
                lT = true;
            } else if (body.getAngularVelocity() > 0) {
                rT = true;
            }

            float d = a - b;

            float tol = 5 * MathUtils.degreesToRadians;

            if (Math.abs(a - b) > tol) {
                float turn = -Math.signum(d);
                if (d > MathUtils.PI) {
                    turn *= -1;

                } else if (d < -MathUtils.PI) {
                    turn *= -1;

                }

                body.applyAngularImpulse(turn * angularAcceleration, true);
            } else {
                body.setAngularVelocity(0);
            }

            if (body.getAngularVelocity() > maxAngularVelocity) {
                body.setAngularVelocity(maxAngularVelocity);
            } else if (body.getAngularVelocity() < -maxAngularVelocity) {
                body.setAngularVelocity(-maxAngularVelocity);
            }
        } catch (Exception e) {
            l.i("e=steerAt");
        }
    }

    public static Vector2 getLookDirection(Body body, float rotRadians) {
        Vector2 vLookDirt = new Vector2(0,0);
        try {
            Vector2 v = new Vector2(0,0.1f);
            float ca = MathUtils.cos(getRot(body, rotRadians));
            float sa = MathUtils.sin(getRot(body, rotRadians));
            vLookDirt.x = ca * v.x -sa * v.y;
            vLookDirt.y = sa * v.x + ca * v.y;
        } catch (Exception e) {
            l.i("e=getLookDirection");
        }
        return vLookDirt;
    }

}
