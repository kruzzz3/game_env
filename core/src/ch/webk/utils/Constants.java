package ch.webk.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static float APP_WIDTH = 960;
    public static float APP_HEIGHT = 540;
    public static float ASPECT_RATIO = APP_WIDTH/APP_HEIGHT;
    public static float WORLD_TO_SCREEN = 32f;
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, 0);

    public static int accXDegree = 0;
    public static int accYDegree = 0;

    public static final int STAGE_ROCKET = 1;
    public static final int STAGE_POLY = 2;
    public static final int STAGE_MOVE = 3;
    public static final int STAGE_DISTANCE_JOINT = 4;
    public static final int STAGE_ROPE_JOINT = 5;
    public static final int STAGE_REVOLUTE_JOINT = 6;

    public static final String WALL = "t_wall";

    public static final String POLY_STAR = "t_poly_star";
    public static final String POLY_STAIRS = "t_poly_stairs";
    public static final String POLY_TRIANGLE = "t_poly_triangle";
    public static final String POLY_ROCKET = "t_poly_rocket";
    public static final String TARGET = "t_target";
    public static final String RUNNER = "t_runner";
    public static final String CIRCLE = "t_circle";
    public static final String RECT = "t_rect";
    public static final String BALL = "t_ball";
    public static final String EXPLOSION = "t_explosion";

    public static final String CAR_BODY = "t_car_body";
    public static final String CAR_WHEEL = "t_car_wheel";

}
