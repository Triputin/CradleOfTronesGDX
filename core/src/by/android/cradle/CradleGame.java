package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import by.android.cradle.BaseGame;

public class CradleGame extends BaseGame
{
    public final int MaxGameMapLevel=3;
    private MenuScreen menuScreen;
    private GameMapScreen gameMapScreen;
    private ScreenGamePlay screenGamePlay;
    private HelpScreen helpScreen;
    private Preferences prefs;
    private IActivityRequestHandler myRequestHandler;

    private int gameMapLevel;

    public CradleGame(IActivityRequestHandler handler) {
        myRequestHandler = handler;
    }

    public void create()
    {
        super.create();
        prefs = Gdx.app.getPreferences("settings.prefs");

        GameRes.Bread=prefs.getInteger("Bread", 100);
        GameRes.Wood=prefs.getInteger("Wood", 100);;
        GameRes.Gold=prefs.getInteger("Gold", 50);;
        gameMapLevel = prefs.getInteger("gameMapLevel", 1);
        gameMapLevel=3;
        menuScreen = new MenuScreen(this);
        screenGamePlay = new ScreenGamePlay(this);
        gameMapScreen = new GameMapScreen(this);
        helpScreen = new HelpScreen(this);

        Kingdom[] kingdoms = gameMapScreen.getKingdoms();
        kingdoms[0].setProtectionState(0); // starting Kingdom for player
        for (int i = 1; i < kingdoms.length; i++) {
            kingdoms[i].setProtectionState(prefs.getInteger("kingdomProtectionState"+i, 5));
            //System.out.println("CradleGame.create Get:kingdomProtectionState"+i+);
        }

        int gameLevel = prefs.getInteger("gameLevel", 1);
        screenGamePlay.setGameLevel(gameLevel);

        setActiveScreen( menuScreen );

        myRequestHandler.showAds(false);
    }


    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public void setMenuScreen(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }

    public GameMapScreen getGameMapScreen() {
        return gameMapScreen;
    }

    public void setGameMapScreen(GameMapScreen gameMapScreen) {
        this.gameMapScreen = gameMapScreen;

    }

    public ScreenGamePlay getScreenGamePlay() {
        return screenGamePlay;
    }

    public void setScreenGamePlay(ScreenGamePlay screenGamePlay) {
        this.screenGamePlay = screenGamePlay;
    }

    public static void setActiveScreen(BaseScreen s)
    {
        game.setScreen(s);
    }


    public  void setActiveMenuScreen()
    {
        System.out.println("setActiveMenuScreen()");
        game.setScreen(menuScreen);
        gameMapScreen.PauseMusic();
        menuScreen.PlayMusic();
        myRequestHandler.showAds(false);
    }

    public  void setActiveHelpScreen()
    {
        System.out.println("setActiveHelpScreen()");
        game.setScreen(helpScreen);
        menuScreen.PauseMusic();
        helpScreen.PlayMusic();
        myRequestHandler.showAds(false);
    }

    public  void setActiveGameMapScreen()
    {
        System.out.println("setActiveGameMapScreen");
        game.setScreen(gameMapScreen);
        gameMapScreen.UpdateRes();

        screenGamePlay.setPaused(true);
        menuScreen.PauseMusic();
        gameMapScreen.PlayMusic();
        gameMapScreen.SetMessageActorVisibility(false);
        myRequestHandler.showAds(true);

        prefs.putInteger("gameLevel", screenGamePlay.getGameLevel());
        prefs.putInteger("gameMapLevel", gameMapLevel);
        prefs.putInteger("Gold", GameRes.Gold);
        prefs.putInteger("Wood", GameRes.Wood);
        prefs.putInteger("Bread", GameRes.Bread);
        Kingdom[] kingdoms = gameMapScreen.getKingdoms();

        for (int i = 1; i < kingdoms.length; i++) {
            prefs.putInteger("kingdomProtectionState"+i, kingdoms[i].getProtectionState());
            //System.out.println("setActiveGameMapScreen Put:kingdomProtectionState"+i);
        }
        prefs.flush();

    }

    public  void setActivescreenGamePlay(AttackType attackType, Kingdom attackedKingdom)
    {
        System.out.println("setActivescreenGamePlay()");
        game.setScreen(screenGamePlay);
        screenGamePlay.UpdateRes();
        screenGamePlay.setAttackedKingdom(attackedKingdom);
        screenGamePlay.StartNewLevel();
        menuScreen.PauseMusic();
        gameMapScreen.PauseMusic();
        myRequestHandler.showAds(false);
    }

    public void restartGame(){

        screenGamePlay.setGameLevel(1);
        prefs.putInteger("gameLevel", screenGamePlay.getGameLevel());

        gameMapLevel = 1;
        prefs.putInteger("gameMapLevel", gameMapLevel);
        gameMapScreen.initializeMap(gameMapLevel);
        Kingdom[] kingdoms = gameMapScreen.getKingdoms();
        //kingdoms[0].setProtectionState(0); // starting Kingdom for player
        for (int i = 1; i < kingdoms.length; i++) {
            prefs.putInteger("kingdomProtectionState"+i, kingdoms[i].getProtectionState());
        }

        GameRes.Bread=100;
        GameRes.Wood=100;
        GameRes.Gold=50;
        prefs.putInteger("Gold", GameRes.Gold);
        prefs.putInteger("Wood", GameRes.Wood);
        prefs.putInteger("Bread", GameRes.Bread);
        prefs.flush();

    }

    public void setGameResGold(int gold){
        GameRes.Gold = gold;
        //prefs.putInteger("Gold", gold);
    }
    public void setGameResWood(int wood){
        GameRes.Wood = wood;
        //prefs.putInteger("Wood", wood);
    }
    public void setGameResBread(int bread){
        GameRes.Bread = bread;
        //prefs.putInteger("Bread", bread);
    }

    public int getGameMapLevel() {
        return gameMapLevel;
    }

    public void setGameMapLevel(int gameMapLevel) {
        this.gameMapLevel = gameMapLevel;
        prefs.putInteger("gameMapLevel", gameMapLevel);
        Kingdom[] kingdoms = gameMapScreen.getKingdoms();
        kingdoms[0].setProtectionState(0); // starting Kingdom for player
        for (int i = 1; i < kingdoms.length; i++) {
            kingdoms[i].resetProtectionState(gameMapLevel);
            prefs.putInteger("kingdomProtectionState"+i, kingdoms[i].getProtectionState());
        }
        prefs.flush();

    }
}
