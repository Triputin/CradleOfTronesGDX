package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Timer;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CradleGame extends BaseGame
{
    //settings
    public  final String leaderboard = "CgkIiby2l-0EEAIQAQ";
    private final String LAST_LOGIN_DAY="lastloginday";
    private final int DAILY_GIFT_AMOUNT = 25;
    public final int MaxGameMapLevel=4;
    private int gameMapLevel;
    private int difficultyLevel;
    private KnightParams knightParams;
    private boolean isSoundOn;
    private boolean isMusicOn;

    //game screens
    private MenuScreen menuScreen;
    private GameMapScreen gameMapScreen;
    private ScreenGamePlay screenGamePlay;
    private HelpScreen helpScreen;
    private ShopScreen shopScreen;
    private SettingsScreen settingsScreen;
    private KnightScreen knightScreen;

    private static long SPLASH_MINIMUM_MILLIS = 3000L;
    //private SplashScreen splashScreen;
    //
    private Preferences prefs;
    private IActivityRequestHandler myRequestHandler; //interface for AdMob
    private I18NBundle languageStrings; //loader for multilanguage strings
    private IPlayServices ply; //interface to connect core with android google play services


    public CradleGame(IActivityRequestHandler handler, IPlayServices ply) {
        //connect core code with platform objects from launchers which implemented interfaces
        this.myRequestHandler = handler;
        this.ply=ply;

    }

    public void create()
    {
        setScreen(new SplashScreen2());
        myRequestHandler.showAds(false);
        super.create();

        final long splash_start_time = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        // ... carga de datos
                        // ... carga de fuentes tipograficas
                        // ... carga de sonidos
                        // ... carga de imagenes
                        // ... carga de recursos de internacionalizacion
                        // ... otros

                        Init();
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
                });
            }
        }).start();





     }

     public void Init(){
         // Gdx.app.setLogLevel(Application.LOG_DEBUG);
         //Setup settings provider
         prefs = Gdx.app.getPreferences("settings.prefs");

         knightParams = new KnightParams(prefs); // loads Knight settings


         // For debug ru locale
         //Locale locale = new Locale("ru");
         //languageStrings = I18NBundle.createBundle(Gdx.files.internal("strings/strings"),locale);

         //Default locale for realise
         languageStrings = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));

         getResFromStorage();
         //gameMapLevel=1; // for debug
         //GameRes.Score=999; // for debug
         knightParams.CheckKnightLevelAtScore(GameRes.Score); // Set KnightLevel according current dependence from Score. It's for old players.
         menuScreen = new MenuScreen(this,ply);
         screenGamePlay = new ScreenGamePlay(this,ply);

         int gameLevel = prefs.getInteger("gameLevel", 1);
         //gameLevel = 3; // for debug purpose
         screenGamePlay.setGameLevel(gameLevel);
         difficultyLevel = prefs.getInteger("levelofhardness", 0);
         //difficultyLevel = 0; // for debug purpose
         gameMapScreen = new GameMapScreen(this,ply);
         helpScreen = new HelpScreen(this,ply);
         shopScreen = new ShopScreen(this,ply);

         Kingdom[] kingdoms = gameMapScreen.getKingdoms();
         if (gameMapLevel==1) {
             kingdoms[0].setProtectionState(0); // starting Kingdom for player
         } else {
             kingdoms[0].setProtectionState(5+gameMapLevel);
         }
         //For Debug
         //kingdoms[0].setProtectionState(1);
         for (int i = 1; i < kingdoms.length; i++) {
             kingdoms[i].setProtectionState(prefs.getInteger("kingdomProtectionState"+i, 5));

             //For Debug
             //kingdoms[i].setProtectionState(0);
             //System.out.println("CradleGame.create Get:kingdomProtectionState"+i+);
         }

         settingsScreen = new SettingsScreen(this,ply);
         knightScreen = new KnightScreen(this,ply);


         // Check daily gift
         GregorianCalendar calendarG = new GregorianCalendar();
         calendarG.setTime(new Date());

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
        isSoundOn = prefs.getBoolean("issoundon", true);
        isMusicOn = prefs.getBoolean("ismusicon", true);
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
        game.setScreen(menuScreen);
        gameMapScreen.PauseMusic();
        settingsScreen.PauseMusic();
        menuScreen.PlayMusic();
        myRequestHandler.showAds(false);
    }

    public  void setActiveHelpScreen()
    {
        GdxLog.print("setActiveHelpScreen():","Called");
        game.setScreen(helpScreen);
        menuScreen.PauseMusic();
        helpScreen.PlayMusic();
        myRequestHandler.showAds(false);
    }

    public  void setActiveKnightScreen()
    {
        GdxLog.print("setActiveKnightScreen():","Called");
        game.setScreen(knightScreen);
        gameMapScreen.PauseMusic();
        knightScreen.SetParams(knightParams);
        //knightScreen.PlayMusic();
        myRequestHandler.showAds(false);
    }

    public  void setActiveSettingsScreen()
    {

        GdxLog.print("setActiveSettingsScreen():","Called");
        game.setScreen(settingsScreen);
        menuScreen.PauseMusic();
        settingsScreen.PlayMusic();
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

    public  void setActiveGameMapScreen(boolean showNewHeroLevel)
    {
        //System.out.println("setActiveGameMapScreen");
        GdxLog.print("setActiveGameMapScreen():","Called");
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        game.setScreen(gameMapScreen);
        gameMapScreen.UpdateRes(); //Shows Thron if maplevel ended

        screenGamePlay.setPaused(true);
        menuScreen.PauseMusic();
        gameMapScreen.PlayMusic();
        gameMapScreen.SetMessageActorVisibility(false);
        myRequestHandler.showAds(true);

        saveGameRes();
        ply.submitScore(leaderboard,GameRes.Score);

        gameMapScreen.getKnight().setKnightParams(knightParams);
        //For Debug
        screenGamePlay.printNumberOfActors();

        //Show hero have rised level dialog
        System.out.println("setActiveGameMapScreen KnightLevel=" + knightParams.getKnightLevel());
        if (showNewHeroLevel) {
            String s = getLanguageStrings().get("information");
            Dialog dialog = new CustomDialog(s, BaseGame.skin, h*0.7f,h*0.3f) {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            s = getLanguageStrings().get("you_hero_rised_level")+ knightParams.getKnightLevel();
            dialog.text(s);
            s = getLanguageStrings().get("ok");
            dialog.button(s, true); //sends "true" as the result
            dialog.show(gameMapScreen.uiStage);
        }

        if (knightParams.getLifes()<=0){
            ShowAllLifesLostDialog();
        }

    }

    public  void setActivescreenGamePlay(AttackType attackType, Kingdom attackedKingdom)
    {
        //System.out.println("setActivescreenGamePlay()");
        GdxLog.print("setActivescreenGamePlay():","Called");
        game.setScreen(screenGamePlay);
        screenGamePlay.UpdateRes();
        screenGamePlay.setAttackedKingdom(attackedKingdom);
        screenGamePlay.HideDialog();
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
        gameMapScreen.setFirstMapLevelRun(true);
        prefs.putInteger("gameMapLevel", gameMapLevel);
        // reset levelofhardness
        difficultyLevel = 0;
        prefs.putInteger("levelofhardness", difficultyLevel);
        gameMapScreen.initializeMap(gameMapLevel);
        Kingdom[] kingdoms = gameMapScreen.getKingdoms();
        //kingdoms[0].setProtectionState(0); // starting Kingdom for player
        for (int i = 1; i < kingdoms.length; i++) {
            prefs.putInteger("kingdomProtectionState"+i, kingdoms[i].getProtectionState());
        }

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
        gameMapScreen.setFirstMapLevelRun(true);
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
            Dialog dialog = new Dialog(s, BaseGame.skin, "dialog") {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            s = getLanguageStrings().get("you_hero_lost_all_lives")+ knightParams.getKnightLevel();
            dialog.text(s);
            s = getLanguageStrings().get("ok");
            dialog.button(s, true); //sends "true" as the result
            //dialog.button("No", false); //sends "false" as the result
            dialog.setSize(h*0.4f,h*0.4f); //don't work
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
}
