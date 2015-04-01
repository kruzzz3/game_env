package ch.webk.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static float APP_WIDTH = 960;
    public static float APP_HEIGHT = 540;
    public static float APP_WIDTH_WORLD = 30;
    public static float APP_HEIGHT_WORLD = 16.875f;
    public static float ASPECT_RATIO = APP_WIDTH/APP_HEIGHT;
    public static float WORLD_TO_SCREEN = 32f;
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, 0);

    public static float deltaTime = 0;
    public static boolean drawHud = false;
    public static int accXDegree = 0;
    public static int accYDegree = 0;

    public static final int STAGE_ROCKET = 1;
    public static final int STAGE_POLY = 2;
    public static final int STAGE_MOVE = 3;
    public static final int STAGE_DISTANCE_JOINT = 4;
    public static final int STAGE_ROPE_JOINT = 5;
    public static final int STAGE_REVOLUTE_JOINT = 6;
    public static final int STAGE_CHAIN = 7;
    public static final int STAGE_EXPLOSION = 8;
    public static final int STAGE_MONSTER = 9;

    public static final String BANNER = "t_banner";
    public static final String BTN_BREAK = "t_btn_break";
    public static final String BTN_PLAY = "t_btn_play";
    public static final String BTN_SETTINGS = "t_btn_settings";
    public static final String BTN_MENU = "t_btn_menu";

    public static final String WALL = "t_wall";

    public static final String POLY_STAR = "t_poly_star";
    public static final String POLY_STAIRS = "t_poly_stairs";
    public static final String POLY_TRIANGLE = "t_poly_triangle";
    public static final String POLY_ROCKET = "t_poly_rocket";
    public static final String TARGET = "t_target";
    public static final String RUNNER = "t_runner";
    public static final String CIRCLE = "t_circle";
    public static final String RECT = "t_rect";
    public static final String BALL = "a_ball";
    public static final String EXPLOSION = "a_explosion";
    public static final String SMOKE = "a_smoke";
    public static final String BOX_WOOD = "t_wood_box";
    public static final String BARREL_WOOD = "t_barrel_box";
    public static final String BOMB = "t_bomb";

    public static final String MONSTER_LAND_GREEN = "a_monster_land_green";
    public static final String MONSTER_LAND_BLUE = "a_monster_land_blue";

    public static final String CAR_BODY = "t_car_body";
    public static final String CAR_WHEEL = "t_car_wheel";

}
