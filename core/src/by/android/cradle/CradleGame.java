package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;




public class CradleGame extends BaseGame implements IVideoEventListener
{
    //settings
    public  final String leaderboard = "CgkIiby2l-0EEAIQAQ";
    private final String LAST_LOGIN_DAY="lastloginday";
    private final String LAST_KNIGHT_ITEM_GENERATED_DAY="lastknightitemgeneratedday";
    private final int KINGDOMS_MAX_QTTY = 36;
    private final int DAILY_GIFT_AMOUNT = 25;
    private  int kingdomsize;
    private  int kingdom_main_size;
    private int bombSize;
    private int buttonXSize;
    private int buttonYSize;
    private int bombs_frame_sizeX;
    private int bombs_frame_sizeY;
    private int bomb_lock_size;

    public final int MaxGameMapLevel=4;
    private int gameMapLevel; //currentGameMapLevelToShow
    private int maxOpenedMapLevel; // Player win maps levels+1;
    private int difficultyLevel; //1-easy 2-middle 3-hard
    private KnightParams knightParams;
    private boolean isSoundOn;
    private boolean isMusicOn;
    //----------------------game review
    private int isGameLiked; // 0 - didn't asked, 1- didn't like; 2 - liked
    private int lastDayAskedToVote;
    private int lastScoreWhenAskedToVote;
    //Game tutorial
    private boolean isWeaponUsed;
    private boolean isKingdomGoldCollected;

    private ArrayList<KnightItemParams> knightItemsParamsDailyArrayList;

    public static String DefaultWorkerTag = "CradleOfThronesWorker"; //notifications tag for clearing unneeded

    //game screens
    private MenuScreen menuScreen;
    private GameMapScreen gameMapScreen;
    private ScreenGamePlay screenGamePlay;
    private HelpScreen helpScreen;
    private ShopScreen shopScreen;
    private SettingsScreen settingsScreen;
    private KnightScreen knightScreen;
    private BlackMarketScreen blackMarketScreen;
    private WorldScreen worldScreen;

    //Game params and constants
    private int w; //width of device or window
    private int h; //height of device or window
    private int resHeight; // height of resources line
    private int cellSize; // size of game cells and items
    private final int CellCount = 7;

    private static long SPLASH_MINIMUM_MILLIS = 3000L;
    private SplashScreen2 splashScreen;
    //
    private Preferences prefs;
    private IActivityRequestHandler myRequestHandler; //interface for AdMob
    private I18NBundle languageStrings; //loader for multilanguage strings
    private IPlayServices ply; //interface to connect core with android google play services
    private INotification notification; // interface for sending notifications to user

    private int attackQtty;

    private int loadingState = 0;
    //private CradleGame self;

    public IGoogleServices iGoogleServices;

    public CradleGame(IActivityRequestHandler handler, IPlayServices ply, INotification notification,IGoogleServices iGoogleServices) {
        //connect core code with platform objects from launchers which implemented interfaces
        this.myRequestHandler = handler;
        this.ply=ply;
        this.notification = notification;
        //this.self = this;
        notification.cancelReminder(DefaultWorkerTag); //remove all notifications
        this.iGoogleServices = iGoogleServices;
        this.iGoogleServices.setVideoEventListener(this);


    }

    public void create()
    {
        //splashScreen = new SplashScreen2();
        //setScreen(splashScreen);

        // Get screen size
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        resHeight = (int)Math.round(h*0.12);
        cellSize = (h-resHeight)/CellCount;
        this.kingdomsize = h/9;
        kingdom_main_size = Math.round(this.kingdomsize * 1.5f);
        bombSize = h / 6;

        bombs_frame_sizeY = Math.round(h*0.7f);
        bombs_frame_sizeX = Math.round(h*0.3f);
        bomb_lock_size = Math.round(h*0.27f);

        buttonXSize = Math.round(h*0.2f);
        buttonYSize = Math.round(h*0.1f);

        setScreen(new LoadingScreen(this));

        myRequestHandler.showAds(false);
        super.create();


/*
        // post a Runnable to the rendering thread that processes the result
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
                //results.add(result);


            }
        });

*/






     }

     /*
public void Init(){
    final long splash_start_time = System.currentTimeMillis();
    new Thread(new Runnable() {
        @Override
        public void run() {

            //cradleAssetManager.prepareAnimations(self);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    loadingState=1;

                }
            });

            Init01();
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    loadingState=2;

                }
            });
            Init02();
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    loadingState=3;

                }
            });
            Init03();
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    loadingState=4;

                }
            });


                        // Se muestra el menu principal tras la SpashScreen
                        long splash_elapsed_time = System.currentTimeMillis() - splash_start_time;
                        if (splash_elapsed_time < SPLASH_MINIMUM_MILLIS) {
                            Timer.schedule(
                                    new Timer.Task() {
                                        @Override
                                        public void run() {
                                            setScreen(menuScreen);
                                        }
                                    }, (float)(SPLASH_MINIMUM_MILLIS - splash_elapsed_time) / 1000f);
                        } else {
                            setScreen(menuScreen);
                        }







        }
    }).start();

}
*/


    public void Init01(){
        // Gdx.app.setLogLevel(Application.LOG_DEBUG);

        prepareStyles();

        //Setup settings provider
        long ct = System.currentTimeMillis();
        prefs = Gdx.app.getPreferences("settings.prefs2");

        knightParams = new KnightParams(prefs); // loads Knight settings


        checkIfAllKingdomParamsSaved();

        // For debug ru locale
        //Locale locale = new Locale("it");
        //Locale locale = new Locale("en");
        //languageStrings = I18NBundle.createBundle(Gdx.files.internal("strings/strings"),locale);

        //Default locale for realise
        languageStrings = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));

        getResFromStorage();
        //maxOpenedMapLevel=1; // for debug
        //GameRes.Gold=1000; // for debug
        //gameMapLevel=1; // for debug
        //GameRes.Score=999; // for debug
        knightParams.CheckKnightLevelAtScore(GameRes.Score); // Set KnightLevel according current dependence from Score. It's for old players.
        menuScreen = new MenuScreen(this,ply);

        System.out.println("Init01 time : " +(System.currentTimeMillis()-ct));
    }

    public void Init02(){
        long ct = System.currentTimeMillis();
        screenGamePlay = new ScreenGamePlay(this,ply);

        int gameLevel = prefs.getInteger("gameLevel", 1);
        //gameLevel = 1; // for debug purpose
        screenGamePlay.setGameLevel(gameLevel);
        difficultyLevel = prefs.getInteger("levelofhardness", 0);
        //difficultyLevel = 0; // for debug purpose
        System.out.println("Init02 time : " +(System.currentTimeMillis()-ct));
    }

    public void Init03(){
        long ct = System.currentTimeMillis();
        gameMapScreen = new GameMapScreen(this,ply);
        System.out.println("Init03 time : " +(System.currentTimeMillis()-ct));
    }

    public void Init04(){
        long ct = System.currentTimeMillis();
        helpScreen = new HelpScreen(this,ply);
        shopScreen = new ShopScreen(this,ply);
        settingsScreen = new SettingsScreen(this,ply);

        System.out.println("Init04 time : " +(System.currentTimeMillis()-ct));
    }

    public void Init05(){
        long ct = System.currentTimeMillis();
        knightScreen = new KnightScreen(this,ply);
        System.out.println("Init05 time : " +(System.currentTimeMillis()-ct));
    }


    public void Init06(){
        long ct = System.currentTimeMillis();
        worldScreen = new WorldScreen(this,ply);
        System.out.println("Init06 time : " +(System.currentTimeMillis()-ct));
    }

    public void Init07(){
        long ct = System.currentTimeMillis();

        // Check daily gift
        GregorianCalendar calendarG = new GregorianCalendar();
        calendarG.setTime(new Date());
        getDailySetOfKnightItems(calendarG);
        blackMarketScreen = new BlackMarketScreen(this,ply);

        if(!prefs.contains(LAST_LOGIN_DAY)) {
            //first day in App
            prefs.putInteger(LAST_LOGIN_DAY, calendarG.get(Calendar.DAY_OF_YEAR));
            prefs.flush();
        }

        //updateDailyGiftValue(prefs,calendarG); //for Debug only!!!

        if((prefs.getInteger(LAST_LOGIN_DAY)+1)==calendarG.get(Calendar.DAY_OF_YEAR)){
            //next loginday up to a year

            updateDailyGiftValue(prefs,calendarG);

        }else{

            if(calendarG.get(Calendar.DAY_OF_YEAR)==1) {

                // check for the 1st day of the year

                boolean isLeap = calendarG.isLeapYear(calendarG.get(Calendar.YEAR));
                if (isLeap && prefs.getInteger(LAST_LOGIN_DAY)==366 ) {

                    updateDailyGiftValue(prefs,calendarG);

                }else  if(prefs.getInteger(LAST_LOGIN_DAY)==365){
                    updateDailyGiftValue(prefs,calendarG);

                }
                else
                    prefs.putInteger(LAST_LOGIN_DAY,calendarG.get(Calendar.DAY_OF_YEAR));
                prefs.putInteger("dailyCombo", 0);
                prefs.flush();
            }
            else
                prefs.putInteger(LAST_LOGIN_DAY,calendarG.get(Calendar.DAY_OF_YEAR));
            prefs.putInteger("dailyCombo", 0);
            prefs.flush();

        }
        System.out.println("Init07 time : " +(System.currentTimeMillis()-ct));
    }

    public void getResFromStorage(){
        GameRes.Bread=prefs.getInteger("Bread", 100);
        GameRes.Wood=prefs.getInteger("Wood", 100);
        GameRes.Gold=prefs.getInteger("Gold", 50);
        GameRes.TimeBomb=prefs.getInteger("TimeBomb", 0);
        GameRes.SquareBomb1=prefs.getInteger("SquareBomb1", 0);
        GameRes.SquareBomb2=prefs.getInteger("SquareBomb2", 0);
        GameRes.Score=prefs.getInteger("Score", 0);
        gameMapLevel = prefs.getInteger("gameMapLevel", 1);
        maxOpenedMapLevel = prefs.getInteger("maxOpenedMapLevel", 1);
        //maxOpenedMapLevel=4;// for debug
        // for old players who don't have maxOpenedMapLevel in prefs
        if (maxOpenedMapLevel<gameMapLevel){
            maxOpenedMapLevel = gameMapLevel;
        }
        isSoundOn = prefs.getBoolean("issoundon", true);
        isMusicOn = prefs.getBoolean("ismusicon", true);
        isGameLiked = prefs.getInteger("isGameLiked", 0);
        lastDayAskedToVote = prefs.getInteger("lastDayAskedToVote", 0);
        lastScoreWhenAskedToVote = prefs.getInteger("lastScoreWhenAskedToVote", 0);
        //Tutorial
        isWeaponUsed=prefs.getBoolean("isweaponused", false);
        isKingdomGoldCollected=prefs.getBoolean("iskingdomgoldcollected", false);

    }

    public boolean isSoundOn() {
        return isSoundOn;
    }

    public void setSoundOn(boolean soundOn) {
        isSoundOn = soundOn;
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }

    public void setMusicOn(boolean musicOn) {
        isMusicOn = musicOn;
    }

    public void saveGameRes(){

        prefs.putInteger("gameLevel", screenGamePlay.getGameLevel());
        prefs.putInteger("gameMapLevel", gameMapLevel);
        prefs.putInteger("maxOpenedMapLevel", maxOpenedMapLevel);
        prefs.putInteger("levelofhardness", difficultyLevel);
        prefs.putInteger("Gold", GameRes.Gold);
        prefs.putInteger("Wood", GameRes.Wood);
        prefs.putInteger("Bread", GameRes.Bread);
        prefs.putInteger("TimeBomb", GameRes.TimeBomb);
        prefs.putInteger("SquareBomb1", GameRes.SquareBomb1);
        prefs.putInteger("SquareBomb2", GameRes.SquareBomb2);
        prefs.putInteger("Score", GameRes.Score);
        prefs.putBoolean("issoundon", isSoundOn);
        prefs.putBoolean("ismusicon", isMusicOn);
        prefs.putInteger("isgameliked", isGameLiked);
        prefs.putInteger("lastDayAskedToVote", lastDayAskedToVote);
        prefs.putInteger("lastScoreWhenAskedToVote", lastScoreWhenAskedToVote);


        Kingdom[] kingdoms = gameMapScreen.getKingdoms();

        for (int i = 1; i < kingdoms.length; i++) {
            prefs.putInteger("kingdomProtectionState"+i, kingdoms[i].getProtectionState());
            //System.out.println("setActiveGameMapScreen Put:kingdomProtectionState"+i);
        }

        knightParams.save();
        prefs.flush();
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
        myRequestHandler.showAds(false);
        game.setScreen(menuScreen);
        gameMapScreen.PauseMusic();
        settingsScreen.PauseMusic();
        menuScreen.PlayMusic();
       ply.logEvent("1", "setActiveMenuScreen", "Switch to");
    }

    public  void setActiveHelpScreen()
    {
        GdxLog.print("setActiveHelpScreen():","Called");
        game.setScreen(helpScreen);
        menuScreen.PauseMusic();
        helpScreen.PlayMusic();
        myRequestHandler.showAds(false);
        ply.logEvent("2", "setActiveHelpScreen", "Switch to");
    }

    public  void setActiveWorldScreen()
    {
        GdxLog.print("setActiveWorldScreen():","Called");
        myRequestHandler.showAds(false);
        game.setScreen(worldScreen);
        worldScreen.initializeMap();
        gameMapScreen.PauseMusic();
        worldScreen.PlayMusic();
        ply.logEvent("21", "setActiveWorldScreen", "Switch to");
    }

    public  void setActiveKnightScreen()
    {
        GdxLog.print("setActiveKnightScreen():","Called");
        myRequestHandler.showAds(false);
        game.setScreen(knightScreen);
        gameMapScreen.PauseMusic();
        knightScreen.SetParams(knightParams);
        //knightScreen.PlayMusic();
        ply.logEvent("3", "setActiveKnightScreen", "Switch to");

    }


    public  void setActiveBlackMarketScreen()
    {
        GdxLog.print("setActiveBlackMarketScreen():","Called");
        myRequestHandler.showAds(false);
        game.setScreen(blackMarketScreen);
        gameMapScreen.PauseMusic();
        ply.logEvent("10", "setActiveBlackMarketScreen", "Switch to");

    }
    public  void setActiveSettingsScreen()
    {

        GdxLog.print("setActiveSettingsScreen():","Called");
        myRequestHandler.showAds(false);
        game.setScreen(settingsScreen);
        menuScreen.PauseMusic();
        settingsScreen.PlayMusic();
        ply.logEvent("4", "setActiveSettingsScreen", "Switch to");

    }

    public  void setActiveShopScreen()
    {
        //System.out.println("setActiveShopScreen()");
        GdxLog.print("setActiveShopScreen():","Called");
        myRequestHandler.showAds(false);
        game.setScreen(shopScreen);
        gameMapScreen.PauseMusic();
        shopScreen.setupResources();
        ply.logEvent("5", "setActiveShopScreen", "Switch to");

    }

    public  void setActiveGameMapScreen(boolean showNewHeroLevel, int mapLevel)
    {
        System.out.println("setActiveGameMapScreen");
        ply.logEvent("6", "setActiveGameMapScreen", "Switch to");
        //ply.logEvent("7", "DifficultyLevel is ", String.valueOf(difficultyLevel));
        //ply.logEvent("8", "GameMapLevel is ", String.valueOf(gameMapLevel));
        //ply.logEvent("9", "gameLevel is ", String.valueOf(screenGamePlay.getGameLevel()));

        GdxLog.print("setActiveGameMapScreen():","Called");
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        game.setScreen(gameMapScreen);
        myRequestHandler.showAds(true);
        if (mapLevel>0) {
            gameMapScreen.initializeMap(mapLevel);
        }
        gameMapScreen.UpdateRes(); //Shows Thron if maplevel ended and hides arrows if gamelevel>1

        screenGamePlay.setPaused(true);
        menuScreen.PauseMusic();
        worldScreen.PauseMusic();
        //screenGamePlay.PauseMusic();
        gameMapScreen.PlayMusic();
        gameMapScreen.SetMessageActorVisibility(false);


        saveGameRes();
        ply.submitScore(leaderboard,GameRes.Score);

        gameMapScreen.getKnight().setKnightParams(knightParams);
        //For Debug
        //screenGamePlay.printNumberOfActors();

        //Show hero have rised level dialog
        System.out.println("setActiveGameMapScreen KnightLevel=" + knightParams.getKnightLevel());
        if (showNewHeroLevel) {
            String s = getLanguageStrings().get("information");
            Dialog dialog = new CustomDialog("", BaseGame.skin, h*0.7f,h*0.3f) {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            s = getLanguageStrings().get("you_hero_rised_level")+ knightParams.getKnightLevel();
            dialog.text(s);
            s = getLanguageStrings().get("ok");
            dialog.button(s, true,BaseGame.textButtonStyle); //sends "true" as the result
            dialog.show(gameMapScreen.uiStage);
        }

        if (knightParams.getLifes()<=0){
            ShowAllLifesLostDialog();
        }

        gameMapScreen.askToRateTheGame();

    }

    public  void setActivescreenGamePlay(AttackType attackType, Kingdom attackedKingdom)
    {
        //System.out.println("setActivescreenGamePlay()");
        GdxLog.print("setActivescreenGamePlay():","Called");
        myRequestHandler.showAds(false);
        game.setScreen(screenGamePlay);
        screenGamePlay.UpdateRes();
        screenGamePlay.setAttackedKingdom(attackedKingdom);
        screenGamePlay.HideDialog();
        screenGamePlay.StartNewLevel();
        menuScreen.PauseMusic();
        gameMapScreen.PauseMusic();

        ply.logEvent("7", "setActivescreenGamePlay", "Switch to");

    }

    public void restartGame(){
        // Get screen size
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        screenGamePlay.setGameLevel(1);
        screenGamePlay.getHall().loadTexture("game_of_thrones_locations4.jpg",w,h);
        prefs.putInteger("gameLevel", screenGamePlay.getGameLevel());

        gameMapLevel = 1;
        setMaxOpenedMapLevel(1);
        gameMapScreen.setFirstMapLevelRun(true);
        prefs.putInteger("gameMapLevel", gameMapLevel);
        prefs.putInteger("maxOpenedMapLevel", maxOpenedMapLevel);
        // reset levelofhardness
        difficultyLevel = 0;
        prefs.putInteger("levelofhardness", difficultyLevel);

        for (int i =0; i<KINGDOMS_MAX_QTTY; i++){
            prefs.putInteger("KingdomProtectionState"+i,Kingdom.getKingdomPlannedProtectionState(i));
        }

        for (int i=0; i<gameMapScreen.getKingdoms().length;i++){
            gameMapScreen.getKingdoms()[i].resetProtectionState(gameMapLevel);
            gameMapScreen.getKingdoms()[i].resetFlag();
        }
        gameMapScreen.getGameMapLevelInfo().resetMapLevelsWinned();
        gameMapScreen.initializeMap(gameMapLevel);


        GameRes.Bread=100;
        GameRes.Wood=100;
        GameRes.Gold=50;
        GameRes.TimeBomb=0;
        GameRes.SquareBomb1=0;
        GameRes.SquareBomb2=0;
        GameRes.Score=0;

        prefs.putInteger("Gold", GameRes.Gold);
        prefs.putInteger("Wood", GameRes.Wood);
        prefs.putInteger("Bread", GameRes.Bread);
        prefs.putInteger("TimeBomb", GameRes.TimeBomb);
        prefs.putInteger("SquareBomb1", GameRes.SquareBomb1);
        prefs.putInteger("SquareBomb2", GameRes.SquareBomb2);
        prefs.putInteger("Score", GameRes.Score);

        knightParams.reset();
        if (screenGamePlay.knight!=null) {screenGamePlay.knight.reDraw();}

        //Tutorial
        prefs.putBoolean("isweaponused", false);
        prefs.putBoolean("iskingdomgoldcollected", false);

        prefs.flush();
        ply.logEvent("10", "restartGame", "Game restarted");


    }

    public void setGameResGold(int gold){
        GameRes.Gold = gold;
        prefs.putInteger("Gold", gold);
        prefs.flush();
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


    // Изменяет текущий отображаемый уровень. После этого метода требуется вызов cradleGame.setActiveGameMapScreen(false, gameMapLevel);
    public void setGameMapLevel(int gameMapLevel) {
        // Get screen size
        if (gameMapScreen==null) return;
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        this.gameMapLevel = gameMapLevel;
        prefs.putInteger("gameMapLevel", gameMapLevel);
        System.out.println("setGameMapLevel = "+gameMapLevel);
        if(gameMapLevel>maxOpenedMapLevel){
            maxOpenedMapLevel=gameMapLevel;
            prefs.putInteger("maxOpenedMapLevel", maxOpenedMapLevel);
        }
        Kingdom[] kingdoms = gameMapScreen.getKingdoms();

        //Надо удать это обнуление оно не должно быть здесь
        /*
        if (gameMapLevel ==1) {
            kingdoms[0].setProtectionState(0); // starting Kingdom for player
        }
*/

        prefs.flush();


        //gameMapScreen.setFirstMapLevelRun(true);
        if(gameMapLevel==1){
            screenGamePlay.getHall().loadTexture( "game_of_thrones_locations4.jpg",w,h );}
        else{
            if(gameMapLevel==2) {
                screenGamePlay.getHall().loadTexture("castle/castlelevel02.png", w, h);
            }else {
                screenGamePlay.getHall().loadTexture("castle/castlelevel03.png", w, h);
            }
        }

        //ply.logLevelUpEvent(String.valueOf(gameMapLevel), "setGameMapLevel", "setGameMapLevel");
    }

    public I18NBundle getLanguageStrings() {
        return languageStrings;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public KnightParams getKnightParams() {
        return knightParams;
    }

    public void ShowAllLifesLostDialog(){
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
            String s = getLanguageStrings().get("information");
            Dialog dialog = new CustomDialog("", BaseGame.skin, h*0.7f,h*0.3f) {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
            };
            s = getLanguageStrings().get("you_hero_lost_all_lives")+ knightParams.getKnightLevel();
            dialog.text(s);
            s = getLanguageStrings().get("ok");
            dialog.button(s, true,BaseGame.textButtonStyle); //sends "true" as the result
            //dialog.button("No", false); //sends "false" as the result
            //dialog.setSize(h*0.4f,h*0.4f); //don't work

            dialog.show(gameMapScreen.uiStage);

    }

    public void updateDailyGiftValue(Preferences preferences, GregorianCalendar calendarG){

        preferences.putInteger(LAST_LOGIN_DAY,calendarG.get(Calendar.DAY_OF_YEAR));
        preferences.putInteger("dailyCombo", preferences.getInteger("dailyCombo",0) + 1);
        //preferences.putInteger("Coin", preferences.getInteger("Coin",0) + preferences.getInteger("dailyCombo",0) * 25);

        int dc = preferences.getInteger("dailyCombo",0);
        if (dc>=4) {dc=4;} // Max combo
        int resQtty = DAILY_GIFT_AMOUNT + DAILY_GIFT_AMOUNT*dc;

        float x = (float) Math.random()*3f;
        int y = Math.round(x);
        String resType = "Gold";
        switch (y){
            case 1:
                resType = "Gold";
                GameRes.Gold=prefs.getInteger("Gold", 50)+resQtty;
                prefs.putInteger("Gold", GameRes.Gold);
                break;
            case 2:
                resType = "Wood";
                GameRes.Gold=prefs.getInteger("Wood", 100)+resQtty;
                prefs.putInteger("Wood", GameRes.Wood);
                break;
            case 3:
                resType = "Bread";
                GameRes.Gold=prefs.getInteger("Bread", 100)+resQtty;
                prefs.putInteger("Bread", GameRes.Bread);
                break;
        }

        menuScreen.setShowDailyGift(true,resType,resQtty );
        preferences.flush();
    }


    public void connectUs() {
        ply.connectUs();
    }

    public void rateGame() {
        ply.rateGame();
    }

    private void getDailySetOfKnightItems(GregorianCalendar calendarG){
        knightItemsParamsDailyArrayList = new ArrayList<>();
        int lastKnightItemsGeneratedDay = prefs.getInteger(LAST_KNIGHT_ITEM_GENERATED_DAY,0);

        //Don't generate if set exists or was generated today already
        if (lastKnightItemsGeneratedDay == calendarG.get(Calendar.DAY_OF_YEAR)){

            for (int i=0; i<9;i++){
                KnightItemParams knightItemParams = new KnightItemParams(KnightItemType.Helmet,1,1,
                        0,0,0);

                knightItemParams.load(prefs,200+i);
                knightItemsParamsDailyArrayList.add(knightItemParams);
            }

            return;
        }

        //Generate abd save new KnightItemParams
        knightItemsParamsDailyArrayList.clear();
        for (int i=0; i<9;i++){
            KnightItemParams knightItemParams = KnightItemParams.generateRandomKnightItemParamsForMarket();
            knightItemsParamsDailyArrayList.add(knightItemParams);
        }

        saveDailySetOfKnightItems(calendarG);

    }

    private void saveDailySetOfKnightItems(GregorianCalendar calendar){
        prefs.putInteger(LAST_KNIGHT_ITEM_GENERATED_DAY, calendar.get(Calendar.DAY_OF_YEAR));
        int i=0;
        for (KnightItemParams knightItemParams: knightItemsParamsDailyArrayList){
            knightItemParams.save(prefs,200+i);
            i++;
        }

    }

    public ArrayList<KnightItemParams> getKnightItemsParamsDailyArrayList() {
        return knightItemsParamsDailyArrayList;
    }

    public int getMaxGameMapLevel() {
        return MaxGameMapLevel;
    }

    public Preferences getPrefs() {
        return prefs;
    }

    public int getIsGameLiked() {
        return isGameLiked;
    }

    public void setIsGameLiked(int isGameLiked) {
        this.isGameLiked = isGameLiked;
        prefs.putInteger("isgameliked", isGameLiked);
        prefs.flush();

    }

    public int getLastDayAskedToVote() {
        return lastDayAskedToVote;
    }

    public void setLastDayAskedToVote(int lastDayAskedToVote) {
        this.lastDayAskedToVote = lastDayAskedToVote;
        prefs.putInteger("lastDayAskedToVote", lastDayAskedToVote);
    }

    public int getLastScoreWhenAskedToVote() {
        return lastScoreWhenAskedToVote;
    }

    public void setLastScoreWhenAskedToVote(int lastScoreWhenAskedToVote) {
        this.lastScoreWhenAskedToVote = lastScoreWhenAskedToVote;
        prefs.putInteger("lastScoreWhenAskedToVote", lastScoreWhenAskedToVote);
    }

    public int getMaxOpenedMapLevel() {
        return maxOpenedMapLevel;
    }

    public void setMaxOpenedMapLevel(int maxOpenedMapLevel) {
        this.maxOpenedMapLevel = maxOpenedMapLevel;
        prefs.putInteger("maxOpenedMapLevel", maxOpenedMapLevel);
        prefs.flush();
    }

    public void scheduleReminder(int delayMinutes){

        //notification.cancelReminder(DefaultWorkerTag);

        String title = getLanguageStrings().get("app_name");
        String message = getLanguageStrings().get("reminder_message01");
        notification.scheduleReminder(delayMinutes,title,message,"1",DefaultWorkerTag);
        notification.scheduleReminder(delayMinutes+60,title,message,"2",DefaultWorkerTag);
        notification.scheduleReminder(delayMinutes+180,title,message,"3",DefaultWorkerTag);
        message = getLanguageStrings().get("reminder_message02");
        notification.scheduleReminder(delayMinutes+1400,title,message,"4",DefaultWorkerTag);
        notification.scheduleReminder(delayMinutes+1400+4320,title,message,"5",DefaultWorkerTag);
        notification.scheduleReminder(delayMinutes+1400+4320+4320,title,message,"6",DefaultWorkerTag);
        notification.scheduleReminder(delayMinutes+1400+4320+4320+4320,title,message,"7",DefaultWorkerTag);
        notification.scheduleReminder(delayMinutes+28800,title,message,"8",DefaultWorkerTag);

    }

    @Override
    public void dispose() {
        //scheduleReminder(20); duplicates and notifications don't work if called here
        System.out.println("CradleGame Dispose");
        super.dispose();
        cradleAssetManager.dispose();
    }



    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getResHeight() {
        return resHeight;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getCellCount() {
        return CellCount;
    }

    private void checkIfAllKingdomParamsSaved(){
    int value = 0;
        for (int i =0; i<KINGDOMS_MAX_QTTY; i++){

            value = getPrefs().getInteger("LevelOfKingdom"+i,-1);
            if (value==-1){
                 getPrefs().putInteger("LevelOfKingdom"+i,1);
            }

            value = getPrefs().getInteger("KingdomProtectionState"+i,-1);
            if (value==-1){
                getPrefs().putInteger("KingdomProtectionState"+i,Kingdom.getKingdomPlannedProtectionState(i));
            }

            KingdomRes kingdomRes = Kingdom.getKingdomPlannedRes(i);
            value = getPrefs().getInteger("KingdomGoldParams"+i,-1);
            if (value==-1){
                getPrefs().putInteger("KingdomGoldParams"+i,kingdomRes.Gold);
            }

            value = getPrefs().getInteger("KingdomBreadParams"+i,-1);
            if (value==-1){
                getPrefs().putInteger("KingdomBreadParams"+i,kingdomRes.Bread);
            }

            value = getPrefs().getInteger("KingdomWoodParams"+i,-1);
            if (value==-1){
                getPrefs().putInteger("KingdomWoodParams"+i,kingdomRes.Wood);
            }

        }

        getPrefs().flush();
    }


    public int getKingdomsize() {
        return kingdomsize;
    }

    public int getMainKingdomsize() {
        return kingdom_main_size;
    }

    public int getBombSize() {
        return bombSize;
    }

    public int getButtonXSize() {
        return buttonXSize;
    }

    public int getButtonYSize() {
        return buttonYSize;
    }

    private void prepareStyles(){
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        // parameters for generating a custom bitmap font
        FreeTypeFontGenerator fontGenerator =
                new FreeTypeFontGenerator(Gdx.files.internal("opensans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = Math.round(h*0.03f); //24
        fontParameters.characters = FONT_CHARACTERS;
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 2;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParameters.magFilter = Texture.TextureFilter.Linear;

        BitmapFont customFont = fontGenerator.generateFont(fontParameters);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = customFont;


        //SuperSmall label text
        fontParameters.size = Math.round(h*0.033f);
        BitmapFont customFont_SuperSmall = fontGenerator.generateFont(fontParameters);
        labelStyle_SuperSmall = new Label.LabelStyle();
        labelStyle_SuperSmall.font = customFont_SuperSmall;

        //Small label text
        fontParameters.size = Math.round(h*0.045f);
        BitmapFont customFont_small = fontGenerator.generateFont(fontParameters);
        labelStyle_Small = new Label.LabelStyle();
        labelStyle_Small.font = customFont_small;

        //Middle label text
        fontParameters.size = Math.round(h*0.06f);
        BitmapFont customFont_Middle = fontGenerator.generateFont(fontParameters);
        labelStyle_Middle = new Label.LabelStyle();
        labelStyle_Middle.font = customFont_Middle;

        //Big label text
        fontParameters.size = Math.round(h*0.14f);
        BitmapFont customFont_Big = fontGenerator.generateFont(fontParameters);
        labelStyle_Big = new Label.LabelStyle();
        labelStyle_Big.font = customFont_Big;


        //TextButtonStyle
        textButtonStyle = new TextButton.TextButtonStyle();
        //Texture   buttonTex   = new Texture( Gdx.files.internal("goldbutton.png") );
        Texture   buttonTex = getCradleAssetManager().getAnimation(Assets.BUTTONGOLD_ANIMATION_ID).getKeyFrame(0).getTexture();
        NinePatch buttonPatch = new NinePatch(buttonTex, 10,10,14,14);
        textButtonStyle.up    = new NinePatchDrawable( buttonPatch );
        //Texture   buttonTex2   = new Texture( Gdx.files.internal("buttons/goldbutton_pressed.png") );
        Texture   buttonTex2 = getCradleAssetManager().getAnimation(Assets.BUTTONGOLD_PRESSED_ANIMATION_ID).getKeyFrame(0).getTexture();
        NinePatch buttonPatch2 = new NinePatch(buttonTex2, 10,10,14,14);
        textButtonStyle.down    = new NinePatchDrawable( buttonPatch2 );
        textButtonStyle.font      = customFont;
        textButtonStyle.fontColor = Color.GRAY;
/*
        float scale = 0.5f;
        float currentHeight = textButtonStyle.up.getMinHeight();
        textButtonStyle.up.setMinHeight(currentHeight * scale);

*/

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        /*
        if (w>1000){
            fontParameters.size = 40;
        } else {
            fontParameters.size = 24;
        }
        */

        fontParameters.size = Math.round(h*0.038f);;

        BitmapFont customFontCheck = fontGenerator.generateFont(fontParameters);
        textButtonStyleCheck = new TextButton.TextButtonStyle();

        Texture   buttonTexCheck   = new Texture( Gdx.files.internal("button01_normal_s.png") );
        NinePatch buttonPatchCheck = new NinePatch(buttonTexCheck, 14,14,14,14);
        textButtonStyleCheck.up    = new NinePatchDrawable( buttonPatchCheck );

        Texture   buttonTexCheck3= new Texture( Gdx.files.internal("button01_checked_s.png") );
        NinePatch buttonPatchCheck3 = new NinePatch(buttonTexCheck3, 14,14,24,24);
        textButtonStyleCheck.checked = new NinePatchDrawable( buttonPatchCheck3 );

        textButtonStyleCheck.font      = customFontCheck;
        textButtonStyleCheck.fontColor = Color.GRAY;


        //Don't work!!!!! Dialog crashes!
        // This is the Skin that we'll use to design our dialog
        dialogSkin = new Skin();
        // The only mandatory resource required for a Dialog is the WindowStyle
        Window.WindowStyle ws = new Window.WindowStyle();
        ws.titleFontColor = Color.GOLD;
        ws.titleFont = customFont;
        ws.stageBackground = new Image(new Texture( Gdx.files.internal("buttons/goldbutton.png") )).getDrawable();

        TextureRegion texture_region = new TextureRegion(new Texture( Gdx.files.internal("buttons/goldbutton.png") ));
        Sprite sprite = new Sprite(texture_region);
        NinePatch np = new NinePatch(sprite, 15, 15, 15, 15);
        NinePatchDrawable npd = new NinePatchDrawable(np);
        // We're using the 9patch drawable as the dialog element background
        ws.background = npd;
        // This WindowStyle needs to be set as the default style in the skin
        dialogSkin.add("default", ws);
    }

    public int getAttackQtty() {
        return attackQtty;
    }

    public void setAttackQtty(int attackQtty) {
        this.attackQtty = attackQtty;
    }

    public IPlayServices getPly() {
        return ply;
    }

    public int getBombs_frame_sizeX() {
        return bombs_frame_sizeX;
    }

    public int getBombs_frame_sizeY() {
        return bombs_frame_sizeY;
    }

    public int getBomb_lock_size() {
        return bomb_lock_size;
    }

    public boolean isWeaponUsed() {
        return isWeaponUsed;
    }

    public void setWeaponUsed(boolean weaponUsed) {
        isWeaponUsed = weaponUsed;
        prefs.putBoolean("isweaponused", true);
        prefs.flush();
    }

    public boolean isKingdomGoldCollected() {
        return isKingdomGoldCollected;
    }


    @Override
    public void onRewardedEvent(String type, int amount) {
        // player has just finished the video and was rewarded
        System.out.println("player has just finished the video and was rewarded");
        screenGamePlay.useRewardForVideo();
    }

    @Override
    public void onRewardedVideoAdLoadedEvent() {
        // video is ready and can be presented to the player
    }

    @Override
    public void onRewardedVideoAdClosedEvent() {
        // player has closed the video so no reward for him
        System.out.println("player has closed the video so no reward for him");
        screenGamePlay.NouseRewardForVideo();
    }

    public IGoogleServices getiGoogleServices() {
        return iGoogleServices;
    }

    public boolean isAdMobViewCreated(){
        return myRequestHandler.isAdMobViewCreated();
    }

}
