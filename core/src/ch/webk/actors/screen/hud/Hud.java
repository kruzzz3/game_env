package ch.webk.actors.screen.hud;

import ch.webk.enums.Action;
import ch.webk.enums.State;
import ch.webk.utils.Constants;
import ch.webk.utils.Logger;
import ch.webk.utils.WorldUtils;

public class Hud {

    private Logger l = new Logger("Hud", true);

    private Banner hud_banner;
    private Button hud_break;
    private Button hud_play;
    private Button hud_settings;
    private Button hud_menu;

    public Hud() {
        hud_banner = new Banner(Constants.APP_WIDTH_WORLD / 2, Constants.APP_HEIGHT_WORLD / 2, Constants.APP_WIDTH_WORLD, Constants.APP_HEIGHT_WORLD, 0);
        WorldUtils.addActor(hud_banner);
        hud_banner.deactivate();

        hud_break = new Button(Constants.BTN_BREAK, Constants.APP_WIDTH_WORLD - 1, Constants.APP_HEIGHT_WORLD - 1, 2, 2, 0);
        WorldUtils.addActor(hud_break);
        hud_break.deactivate();
        hud_break.setTouchListener(new ITouchListener() {
            @Override
            public boolean touchDown() {
                openBanner();
                return true;
            }
        });

        hud_play = new Button(Constants.BTN_PLAY, Constants.APP_WIDTH_WORLD - 1, Constants.APP_HEIGHT_WORLD - 1, 2, 2, 0);
        WorldUtils.addActor(hud_play);
        hud_play.deactivate();
        hud_play.setTouchListener(new ITouchListener() {
            @Override
            public boolean touchDown() {
                closeBanner();
                return true;
            }
        });

        hud_menu = new Button(Constants.BTN_MENU, 1, Constants.APP_HEIGHT_WORLD - 1, 2, 2, 0);
        WorldUtils.addActor(hud_menu);
        hud_menu.deactivate();
        hud_menu.setTouchListener(new ITouchListener() {
            @Override
            public boolean touchDown() {
                l.i("hud_menu");
                WorldUtils.getStage().sendUIAction(Action.OPEN_MENU);
                return true;
            }
        });

        hud_settings = new Button(Constants.BTN_SETTINGS, 4, Constants.APP_HEIGHT_WORLD - 1, 2, 2, 0);
        WorldUtils.addActor(hud_settings);
        hud_settings.deactivate();





        hud_break.activate();
    }

    private void closeBanner() {
        hud_banner.deactivate();
        hud_break.activate();
        hud_play.deactivate();
        hud_settings.deactivate();
        hud_menu.deactivate();
        WorldUtils.getScreen().resume();
    }

    private void openBanner() {
        hud_banner.activate();
        hud_break.deactivate();
        hud_play.activate();
        hud_settings.activate();
        hud_menu.activate();
        WorldUtils.getScreen().pause();
    }
}
