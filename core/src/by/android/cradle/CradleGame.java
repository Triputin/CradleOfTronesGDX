package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import by.android.cradle.BaseGame;

public class CradleGame extends BaseGame
{
    public final int MaxGameMapLevel=3;
    private MenuScreen menuScreen;
    private GameMapScreen gameMapScreen;
    private ScreenGamePlay screenGamePlay;
    private HelpScreen helpScreen;
    private ShopScreen shopScreen;
    private Preferences prefs;
    private IActivityRequestHandler myRequestHandler;
    private int gameMapLevel;
    private I18NBundle languageStrings;

    public CradleGame(IActivityRequestHandler handler) {
        myRequestHandler = handler;
    }

    public void create()
    {
        super.create();
        prefs = Gdx.app.getPreferences("settings.prefs");
        // For debug ru locale
        //Locale locale = new Locale("ru");
        //languageStrings = I18NBundle.createBundle(Gdx.files.internal("strings/strings"),locale);

        //Default locale for realise
        languageStrings = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));

        GameRes.Bread=prefs.getInteger("Bread", 100);
        GameRes.Wood=prefs.getInteger("Wood", 100);
        GameRes.Gold=prefs.getInteger("Gold", 50);
        GameRes.TimeBomb=prefs.getInteger("TimeBomb", 2);
        GameRes.SquareBomb1=prefs.getInteger("SquareBomb1", 2);
        GameRes.SquareBomb2=prefs.getInteger("SquareBomb2", 2);

        gameMapLevel = prefs.getInteger("gameMapLevel", 1);
        //gameMapLevel=3;// for test purpose

        menuScreen = new MenuScreen(this);
        screenGamePlay = new ScreenGamePlay(this);
        gameMapScreen = new GameMapScreen(this);
        helpScreen = new HelpScreen(this);
        shopScreen = new ShopScreen(this);

        Kingdom[] kingdoms = gameMapScreen.getKingdoms();
        kingdoms[0].setProtectionState(0); // starting Kingdom for player
        for (int i = 1; i < kingdoms.length; i++) {
            kingdoms[i].setProtectionState(prefs.getInteger("kingdomProtectionState"+i, 5));
            //System.out.println("CradleGame.create Get:kingdomProtectionState"+i+);
        }

        int gameLevel = prefs.getInteger("gameLevel", 1);
        //gameLevel = 80; // for debug purpose
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
        //System.out.println("setActiveMenuScreen()");
        GdxLog.print("setActiveMenuScreen():","Called");
        game.setScreen(menuScreen);
        gameMapScreen.PauseMusic();
        menuScreen.PlayMusic();
        myRequestHandler.showAds(false);
    }

    public  void setActiveHelpScreen()
    {
        //System.out.println("setActiveHelpScreen()");
        GdxLog.print("setActiveHelpScreen():","Called");
        game.setScreen(helpScreen);
        menuScreen.PauseMusic();
        helpScreen.PlayMusic();
        myRequestHandler.showAds(false);
    }

    public  void setActiveShopScreen()
    {
        //System.out.println("setActiveShopScreen()");
        GdxLog.print("setActiveShopScreen():","Called");
        game.setScreen(shopScreen);
        gameMapScreen.PauseMusic();
        myRequestHandler.showAds(false);
        shopScreen.setupResources();
    }

    public  void setActiveGameMapScreen()
    {
        //System.out.println("setActiveGameMapScreen");
        GdxLog.print("setActiveGameMapScreen():","Called");
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
        prefs.putInteger("TimeBomb", GameRes.TimeBomb);
        prefs.putInteger("SquareBomb1", GameRes.SquareBomb1);
        prefs.putInteger("SquareBomb2", GameRes.SquareBomb2);

        Kingdom[] kingdoms = gameMapScreen.getKingdoms();

        for (int i = 1; i < kingdoms.length; i++) {
            prefs.putInteger("kingdomProtectionState"+i, kingdoms[i].getProtectionState());
            //System.out.println("setActiveGameMapScreen Put:kingdomProtectionState"+i);
        }
        prefs.flush();
        //For Debug
        //screenGamePlay.printNumberOfActors();

    }

    public  void setActivescreenGamePlay(AttackType attackType, Kingdom attackedKingdom)
    {
        //System.out.println("setActivescreenGamePlay()");
        GdxLog.print("setActivescreenGamePlay():","Called");
        game.setScreen(screenGamePlay);
        screenGamePlay.UpdateRes();
        screenGamePlay.setAttackedKingdom(attackedKingdom);
        screenGamePlay.StartNewLevel();
        menuScreen.PauseMusic();
        gameMapScreen.PauseMusic();
        myRequestHandler.showAds(false);
    }

    public void restartGame(){
        // Get screen size
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        screenGamePlay.setGameLevel(1);
        screenGamePlay.getHall().loadTexture("game_of_thrones_locations4.jpg",w,h);
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
        GameRes.TimeBomb=2;
        GameRes.SquareBomb1=2;
        GameRes.SquareBomb2=2;

        prefs.putInteger("Gold", GameRes.Gold);
        prefs.putInteger("Wood", GameRes.Wood);
        prefs.putInteger("Bread", GameRes.Bread);
        prefs.putInteger("TimeBomb", GameRes.TimeBomb);
        prefs.putInteger("SquareBomb1", GameRes.SquareBomb1);
        prefs.putInteger("SquareBomb2", GameRes.SquareBomb2);

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
        // Get screen size
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        this.gameMapLevel = gameMapLevel;
        prefs.putInteger("gameMapLevel", gameMapLevel);
        Kingdom[] kingdoms = gameMapScreen.getKingdoms();
        kingdoms[0].setProtectionState(0); // starting Kingdom for player
        for (int i = 1; i < kingdoms.length; i++) {
            kingdoms[i].resetProtectionState(gameMapLevel);
            prefs.putInteger("kingdomProtectionState"+i, kingdoms[i].getProtectionState());
        }
        prefs.flush();

        if(gameMapLevel==1){
            screenGamePlay.getHall().loadTexture( "game_of_thrones_locations4.jpg",w,h );}
        else{
            if(gameMapLevel==2) {
                screenGamePlay.getHall().loadTexture("castle/castlelevel02.png", w, h);
            }else {
                screenGamePlay.getHall().loadTexture("castle/castlelevel03.png", w, h);
            }
        }

    }

    public I18NBundle getLanguageStrings() {
        return languageStrings;
    }
}
