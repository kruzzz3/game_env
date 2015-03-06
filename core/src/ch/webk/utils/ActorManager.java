package ch.webk.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

public class ActorManager {

    private static Logger l = new Logger("ActorManager", true);
    private static HashMap<String,TextureRegion> textureRegions;
    private static HashMap<String,Animation> animations;
    private static HashMap<String,String[]> animationNames;
    private static HashMap<String,String> images;
    private static HashMap<String,String> vertices;

    public static void init() {
        l.i("init()");

        textureRegions = new HashMap<String,TextureRegion>();
        animations = new HashMap<String,Animation>();
        animationNames = new HashMap<String,String[]>();
        images = new HashMap<String,String>();
        vertices = new HashMap<String,String>();

        images.put("t_background","background.png");

        images.put(Constants.RUNNER,"runner.jpg");
        images.put(Constants.WALL,"ground.png");

        images.put(Constants.BALL + "_1","ball_1.png");
        images.put(Constants.BALL + "_2","ball_2.png");
        images.put(Constants.BALL + "_3","ball_3.png");
        images.put(Constants.BALL + "_4","ball_4.png");
        images.put(Constants.BALL + "_5","ball_5.png");
        images.put(Constants.BALL + "_6","ball_6.png");
        images.put(Constants.BALL + "_7","ball_7.png");
        images.put(Constants.BALL + "_8","ball_8.png");
        animationNames.put(Constants.BALL,getAnimation(Constants.BALL,8));

        images.put(Constants.EXPLOSION + "_1","explosion_1.png");
        images.put(Constants.EXPLOSION + "_2","explosion_2.png");
        images.put(Constants.EXPLOSION + "_3","explosion_3.png");
        images.put(Constants.EXPLOSION + "_4","explosion_4.png");
        images.put(Constants.EXPLOSION + "_5","explosion_5.png");
        images.put(Constants.EXPLOSION + "_6","explosion_6.png");
        images.put(Constants.EXPLOSION + "_7","explosion_7.png");
        images.put(Constants.EXPLOSION + "_8","explosion_8.png");
        images.put(Constants.EXPLOSION + "_9","explosion_9.png");
        images.put(Constants.EXPLOSION + "_10","explosion_10.png");
        images.put(Constants.EXPLOSION + "_11","explosion_11.png");
        images.put(Constants.EXPLOSION + "_12","explosion_12.png");
        images.put(Constants.EXPLOSION + "_13","explosion_13.png");
        images.put(Constants.EXPLOSION + "_14","explosion_14.png");
        images.put(Constants.EXPLOSION + "_15","explosion_15.png");
        images.put(Constants.EXPLOSION + "_16","explosion_16.png");
        images.put(Constants.EXPLOSION + "_17","explosion_17.png");
        images.put(Constants.EXPLOSION + "_18","explosion_18.png");
        images.put(Constants.EXPLOSION + "_19","explosion_19.png");
        images.put(Constants.EXPLOSION + "_20","explosion_20.png");
        images.put(Constants.EXPLOSION + "_21","explosion_21.png");
        images.put(Constants.EXPLOSION + "_22","explosion_22.png");
        images.put(Constants.EXPLOSION + "_23","explosion_23.png");
        images.put(Constants.EXPLOSION + "_24","explosion_24.png");
        images.put(Constants.EXPLOSION + "_25","explosion_25.png");
        animationNames.put(Constants.EXPLOSION,getAnimation(Constants.EXPLOSION,25));

        images.put(Constants.CIRCLE,"circle.png");

        images.put(Constants.TARGET,"target.png");

        images.put(Constants.POLY_STAR,"poly_star.png");
        vertices.put(Constants.POLY_STAR,"[[{\"x\":0.391250,\"y\":0.628750},{\"x\":0.008750,\"y\":0.708750},{\"x\":0.296250,\"y\":0.426250}],[{\"x\":0.296250,\"y\":0.426250},{\"x\":0.110000,\"y\":0.100000},{\"x\":0.458750,\"y\":0.276250}],[{\"x\":0.458750,\"y\":0.276250},{\"x\":0.710000,\"y\":0.020000},{\"x\":0.653750,\"y\":0.381250}],[{\"x\":0.653750,\"y\":0.381250},{\"x\":0.990000,\"y\":0.550000},{\"x\":0.621250,\"y\":0.601250}],[{\"x\":0.621250,\"y\":0.601250},{\"x\":0.568750,\"y\":0.988750},{\"x\":0.391250,\"y\":0.628750}],[{\"x\":0.391250,\"y\":0.628750},{\"x\":0.296250,\"y\":0.426250},{\"x\":0.458750,\"y\":0.276250},{\"x\":0.653750,\"y\":0.381250},{\"x\":0.621250,\"y\":0.601250}]]");

        images.put(Constants.POLY_STAIRS,"poly_stairs.png");
        vertices.put(Constants.POLY_STAIRS,"[[{\"x\":0.248750,\"y\":0.491250},{\"x\":-0.001250,\"y\":0.488750},{\"x\":0.001250,\"y\":0.006250},{\"x\":0.491250,\"y\":0.003750},{\"x\":0.488750,\"y\":0.251250}],[{\"x\":0.488750,\"y\":0.251250},{\"x\":0.738750,\"y\":0.251250},{\"x\":0.738750,\"y\":0.508750},{\"x\":0.506250,\"y\":0.743750},{\"x\":0.248750,\"y\":0.741250},{\"x\":0.248750,\"y\":0.491250}],[{\"x\":0.737828,\"y\":0.508115},{\"x\":1.000000,\"y\":0.508115},{\"x\":1.000000,\"y\":1.000000},{\"x\":0.509363,\"y\":0.998752},{\"x\":0.505618,\"y\":0.742821}]]");

        images.put(Constants.POLY_TRIANGLE,"poly_triangle.png");
        vertices.put(Constants.POLY_TRIANGLE,"[[{\"x\":0.494396,\"y\":0.003736},{\"x\":1.000000,\"y\":0.997509},{\"x\":0.000000,\"y\":0.996264}]]");

        images.put(Constants.POLY_ROCKET,"poly_rocket.png");
        vertices.put(Constants.POLY_ROCKET,"[[{\"x\":0.311250,\"y\":0.138750},{\"x\":0.225000,\"y\":0.050000},{\"x\":0.278750,\"y\":0.011250},{\"x\":0.376250,\"y\":0.011250},{\"x\":0.465000,\"y\":0.100000}],[{\"x\":0.465000,\"y\":0.100000},{\"x\":0.686250,\"y\":0.098750},{\"x\":0.948750,\"y\":0.191250},{\"x\":0.998750,\"y\":0.276250},{\"x\":0.948750,\"y\":0.371250},{\"x\":0.703750,\"y\":0.453750}],[{\"x\":0.703750,\"y\":0.453750},{\"x\":0.465000,\"y\":0.465000},{\"x\":0.316250,\"y\":0.421250},{\"x\":0.225000,\"y\":0.275000},{\"x\":0.311250,\"y\":0.138750},{\"x\":0.465000,\"y\":0.100000}],[{\"x\":0.465000,\"y\":0.465000},{\"x\":0.371250,\"y\":0.556250},{\"x\":0.278750,\"y\":0.553750},{\"x\":0.225000,\"y\":0.525000},{\"x\":0.316250,\"y\":0.421250}]]");

        //vertices.put(Constants.POLY_STAR,"");
    }

    private static String[] getAnimation(String key, int count) {
        l.i("getAnimation() key=" + key + ", count=" + count);
        String[] animations = new String[count];
        for(int i = 1; i <= count; i++) {
            animations[i-1] = key + "_" + i;
        }
        return animations;
    }

    public static Animation getAnimation(String key, float duration) {
        l.i("getAnimation() key=" + key + ", duration=" + duration);
        Animation animation;
        if (animations.containsKey(key)) {
            animation = animations.get(key);
        } else {
            String[] a = animationNames.get(key);
            TextureRegion[] runningFrames = new TextureRegion[a.length];
            for (int i = 0; i < a.length; i++) {
                runningFrames[i] = getTextureRegion(a[i]);
            }
            animation = new Animation(duration, runningFrames);
            animations.put(key,animation);
        }
        return animation;
    }

    public static TextureRegion getTextureRegion(String key) {
        l.i("getTextureRegion() key=" + key);
        TextureRegion textureRegion = null;
        if (textureRegions.containsKey(key)) {
            textureRegion = textureRegions.get(key);
        } else {
            textureRegion = new TextureRegion(new Texture(Gdx.files.internal(images.get(key))));
            textureRegions.put(key,textureRegion);
        }
        return textureRegion;
    }

    public static String getVertices(String key) {
        l.i("getVertices() key=" + key);
        return vertices.get(key);
    }

    public static BitmapFont getFont() {
        l.i("getFont()");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font_1.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        BitmapFont font15 = generator.generateFont(parameter);
        generator.dispose();
        return font15;
    }

}
