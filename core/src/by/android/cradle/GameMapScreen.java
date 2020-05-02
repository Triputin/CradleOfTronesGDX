package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class GameMapScreen extends BaseScreen {

    private MessageActor01 messageActor01;
    private ResultsActor resultsActor;
    private Kingdom[] kingdoms;
    private Arena arena;
    private BaseActor throne;
    private Label messageLabel;
    private BaseActor worldMap;
    private TextButton continueButton;
    private TextButton shopButton;
    private boolean isUpdateMapNeeded;
    private boolean isWinMapLevel; //set when map level completed
    private DialogBox mapLevelInfoDialog;
    private boolean isFirstMapLevelRun;
    private LevelOfHardnessDialogBox levelOfHardnessDialog;
    private DialogBox castleIsYoursDialog;
    private Knight knight;
    private Weapon weapon;
    private BlackMarket blackMarket;
    private GameMapLevelInfo gameMapLevelInfo; // holds info about winned gameMapLevels
    private ArrowDownActor arrowDownActor;

    public DialogBox_AttackTarget dialogBoxAttackTarget;

    //private int kingdomsize;

    public GameMapScreen(CradleGame cradleGame,IPlayServices ply) {

        super(cradleGame,ply);
        isUpdateMapNeeded = false;
        gameMapLevelInfo = new GameMapLevelInfo(cradleGame);

    }

    public void SetMessageActorVisibility(boolean visible){
        messageActor01.setVisible(visible);
    }



    public boolean isFirstMapLevelRun() {
        return isFirstMapLevelRun;
    }

    public void setFirstMapLevelRun(boolean firstMapLevelRun) {
        isFirstMapLevelRun = firstMapLevelRun;
    }

    public void initialize()
    {
        audioVolume = 0.7f;
        //musicArray[0] = Gdx.audio.newMusic(Gdx.files.internal("sounds/new_land.mp3"));
        musicArray[0] = cradleGame.getCradleAssetManager().manager.get(Assets.MUSIC_NEW_LAND);
        musicArray[0].setLooping(true);
        musicArray[0].setVolume(audioVolume);
        musicArray[0].pause();
        //musicArray[1] = Gdx.audio.newMusic(Gdx.files.internal("sounds/soundmap02.mp3"));
        musicArray[1] = cradleGame.getCradleAssetManager().manager.get(Assets.MUSIC_SOUNDMAP02);
        musicArray[1].setLooping(true);
        musicArray[1].setVolume(audioVolume);
        musicArray[1].pause();
        instrumental=musicArray[0];

        isWinMapLevel = false;

        if ((cradleGame.getGameMapLevel()==1)&&(cradleGame.getScreenGamePlay().getGameLevel()==1)){
            isFirstMapLevelRun=true;
        }else {
            isFirstMapLevelRun=false;
        }
        final int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        //h=h-60; //add place for adMob


        //Fon for results
        int resHeight = (int)Math.round(h*0.12);
        /*
        BaseActor fon = new BaseActor(0,0,uiStage,Touchable.disabled);
        fon.loadTexture("fon_orange.png",(int) Math.round(w*0.8)+30,resHeight);
        fon.setX((int) Math.round(w*0.25));
        fon.setY(h-resHeight);
*/
        BaseActor baseResultsActor = new BaseActor(w*0.25f,h-resHeight,uiStage,Touchable.disabled, cradleGame);
        baseResultsActor.setWidth((int) Math.round(w*0.75));
        baseResultsActor.setHeight(resHeight);
        resultsActor = new ResultsActor(0,0,(int) Math.round(w*0.75),resHeight,uiStage,Touchable.disabled,baseResultsActor,cradleGame);

        initializeMap(cradleGame.getGameMapLevel());
        instrumental.pause();

        int knSize = Math.round(h*0.4f);
        int wpSize = Math.round(h*0.1f);
        if (knight!=null){knight.remove();}
        knight = new Knight(-knSize*0.1f,h-knSize+knSize*0.15f,knSize,knSize,uiStage,cradleGame.getKnightParams(),cradleGame);
        knight.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    cradleGame.setActiveKnightScreen();
                    return true;
                }
                return false;
            }
        });


        if(weapon!=null){weapon.remove();}
        weapon = new Weapon(knSize*0.585f,h-knSize*0.34f,wpSize,wpSize,mainStage,cradleGame,knight);




        //Menu button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("back_button02.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button backButton = new Button( buttonStyle );
        backButton.setSize(h*0.28f,h*0.28f);
        backButton.setPosition(w-backButton.getWidth()+10,h*0.63f);
        uiStage.addActor(backButton);

        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    instrumental.pause();
                    UpdateRes();
                    cradleGame.setActiveMenuScreen();
                    return true;
                }
                return false;
            }
        });


        //Shop button
        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();

        Texture buttonTex2 = new Texture( Gdx.files.internal("shop_button.png") );
        TextureRegion buttonRegion2 =  new TextureRegion(buttonTex2);
        buttonStyle2.up = new TextureRegionDrawable( buttonRegion2 );
        Button shopButton = new Button( buttonStyle2 );
        shopButton.setSize(h*0.25f,h*0.25f);
        shopButton.setPosition(w-shopButton.getWidth(),h*0.42f);
        uiStage.addActor(shopButton);

        shopButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    instrumental.pause();
                    cradleGame.setActiveShopScreen();
                    return true;
                }
                return false;
            }
        });

        //World button
        Button.ButtonStyle buttonStyleW = new Button.ButtonStyle();

        Texture buttonTexW = new Texture( Gdx.files.internal("world_button.png") );
        TextureRegion buttonRegionW =  new TextureRegion(buttonTexW);
        buttonStyleW.up = new TextureRegionDrawable( buttonRegionW );
        Button worldButton = new Button( buttonStyleW );
        worldButton.setSize(h*0.235f,h*0.235f);
        worldButton.setPosition(w-shopButton.getWidth(),h*0.2f);
        uiStage.addActor(worldButton);

        worldButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    instrumental.pause();
                    cradleGame.setActiveWorldScreen();
                    return true;
                }
                return false;
            }
        });

        messageActor01 = new MessageActor01(0,0,uiStage,Math.round(cradleGame.getH()*0.6f),Math.round(cradleGame.getH()*0.25f),cradleGame);
        messageActor01.setVisible(false);
        UpdateRes();

        int dialogSize = Math.round(h*0.9f);
        dialogBoxAttackTarget = new DialogBox_AttackTarget(w/2-dialogSize/2,h/2-dialogSize/2,uiStage,dialogSize,dialogSize,cradleGame);
        dialogBoxAttackTarget.setVisible(false);

        // Test ---------------------
        /*
        int dialogSize = Math.round(h*0.8f);
        final DialogBox_EndLevel dialogBox = new DialogBox_EndLevel(w/2-dialogSize/2,h/2-dialogSize/2,uiStage,dialogSize,dialogSize,cradleGame);
        //dialogBox.setText("Test dialog");
        //dialogBox.setDialogSize(400,400);
        //dialogBox.alignCenter();
        dialogBox.setVisible(false);
        //Test Dialog Button
        final Action  completeAction = new Action(){
            public boolean act( float delta ) {
                // Do your stuff
                return true;
            }
        };

        final InputListener inputListener2 =new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                cradleGame.setActiveMenuScreen();

                return true;
            }
        };
        TextButton testButton = new TextButton( "Test dialog", BaseGame.textButtonStyle );
        testButton.setPosition(w*0.1f,h*0.2f);
        uiStage.addActor(testButton);
        testButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                //dialogBox.showForTime(5,completeAction);
                dialogBox.showWithOkButton(inputListener2);
                return true;
            }
        });

        //End of test -------------------
*/


    }

    public void initializeMap(final int mapLevel){

        final int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        int kingdomsize = cradleGame.getKingdomsize();
        if (kingdoms!=null){
            for (int i = 0; i < kingdoms.length; i++) {
                kingdoms[i].saveParams();
                kingdoms[i].remove();
            }
        }
        if (arena!=null) {
            arena.remove();
        }

        if(arrowDownActor!=null){
            arrowDownActor.remove();
        }
        if (blackMarket!=null) {
            blackMarket.remove();
        }

        if (continueButton!=null){
            continueButton.remove();
        }

        /*
        if (shopButton!=null){
            shopButton.remove();
        }
*/

        kingdoms = new Kingdom[7];
        if( worldMap==null) {
            worldMap = new BaseActor(0, 0, mainStage, Touchable.disabled,cradleGame);
        }
        instrumental.pause();
        instrumental = musicArray[0];
        switch(mapLevel){

            case 2:

                    //worldMap.loadTexture("maps/worldmap02.png", w, h);
                    worldMap.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.WORLDMAP02_ANIMATION_ID));

                    kingdoms[0] = new Kingdom(w * 0.3f, h * 0.56f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_North,10,cradleGame,resultsActor);
                    kingdoms[1] = new Kingdom(w * 0.26f, h * 0.3f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_Isles_and_Rivers,11,cradleGame,resultsActor);
                    kingdoms[2] = new Kingdom(w*0.6f, h*0.55f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale,12,cradleGame,resultsActor);
                    kingdoms[3] = new Kingdom(w*0.4f, h*0.45f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach,13,cradleGame,resultsActor);
                    kingdoms[4] = new Kingdom(w*0.45f, h*0.15f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock,14,cradleGame,resultsActor);
                    kingdoms[5] = new Kingdom(w*0.75f, h*0.25f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands,15,cradleGame,resultsActor);
                    kingdoms[6] = new Kingdom(w*0.72f, h*0.6f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne,16,cradleGame,resultsActor);
                    arena = new Arena(w * 0.3f, h * 0.18f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled,cradleGame);
                    blackMarket = new BlackMarket(w * 0.54f, h * 0.4f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled,cradleGame);
                    instrumental = musicArray[1];
                break;
            case 1:
                    //worldMap.loadTexture( "maps/worldmap01.png",w,h );
                    worldMap.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.WORLDMAP01_ANIMATION_ID));

                    kingdoms[0] = new Kingdom(w * 0.16f, h * 0.56f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_North,0,cradleGame,resultsActor);
                    kingdoms[1] = new Kingdom(w * 0.28f, h * 0.3f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_Isles_and_Rivers,1,cradleGame,resultsActor);
                    kingdoms[2] = new Kingdom(w*0.6f, h*0.4f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale,2,cradleGame,resultsActor);
                    kingdoms[3] = new Kingdom(w*0.4f, h*0.28f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach,3,cradleGame,resultsActor);
                    kingdoms[4] = new Kingdom(w*0.45f, h*0.15f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock,4,cradleGame,resultsActor);
                    kingdoms[5] = new Kingdom(w*0.14f, h*0.25f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands,5,cradleGame,resultsActor);
                    kingdoms[6] = new Kingdom(w*0.78f, h*0.35f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne,6,cradleGame,resultsActor);
                    arena = new Arena(w * 0.75f, h * 0.25f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled,cradleGame);
                    blackMarket = new BlackMarket(w * 0.68f, h * 0.32f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled,cradleGame);
                    if ((cradleGame.getGameMapLevel()==1)&&(cradleGame.getScreenGamePlay().getGameLevel()==1)){
                       arrowDownActor = new ArrowDownActor(w*0.6f, h*0.5f,kingdomsize, kingdomsize,uiStage, cradleGame);
                       arrowDownActor.setVisible(false);
                    }
                break;
            case 3:

                //worldMap.loadTexture("maps/worldmap03.png", w, h);
                worldMap.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.WORLDMAP03_ANIMATION_ID));

                kingdoms[0] = new Kingdom(w * 0.2f, h * 0.56f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_North,20,cradleGame,resultsActor);
                kingdoms[1] = new Kingdom(w * 0.1f, h * 0.3f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_Isles_and_Rivers,21,cradleGame,resultsActor);
                kingdoms[2] = new Kingdom(w*0.55f, h*0.55f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale,22,cradleGame,resultsActor);
                kingdoms[3] = new Kingdom(w*0.35f, h*0.45f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach,23,cradleGame,resultsActor);
                kingdoms[4] = new Kingdom(w*0.45f, h*0.15f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock,24,cradleGame,resultsActor);
                kingdoms[5] = new Kingdom(w*0.6f, h*0.25f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands,25,cradleGame,resultsActor);
                kingdoms[6] = new Kingdom(w*0.75f, h*0.6f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne,26,cradleGame,resultsActor);
                arena = new Arena(w * 0.15f, h * 0.15f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled,cradleGame);
                blackMarket = new BlackMarket(w * 0.3f, h * 0.2f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled,cradleGame);

                break;

            case 4:

                //worldMap.loadTexture("maps/worldmap04.png", w, h);
                worldMap.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.WORLDMAP04_ANIMATION_ID));

                kingdoms[0] = new Kingdom(w * 0.5f, h * 0.45f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_North,30,cradleGame,resultsActor);
                kingdoms[1] = new Kingdom(w * 0.4f, h * 0.3f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_Isles_and_Rivers,31,cradleGame,resultsActor);
                kingdoms[2] = new Kingdom(w*0.45f, h*0.65f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale,32,cradleGame,resultsActor);
                kingdoms[3] = new Kingdom(w*0.3f, h*0.45f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach,33,cradleGame,resultsActor);
                kingdoms[4] = new Kingdom(w*0.6f, h*0.33f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock,34,cradleGame,resultsActor);
                kingdoms[5] = new Kingdom(w*0.7f, h*0.3f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands,35,cradleGame,resultsActor);
                kingdoms[6] = new Kingdom(w*0.65f, h*0.6f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne,36,cradleGame,resultsActor);
                arena = new Arena(w * 0.6f, h * 0.15f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled,cradleGame);
                blackMarket = new BlackMarket(w * 0.45f, h * 0.27f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled,cradleGame);

                break;

        }

        PauseMusic();

        InputListener inputListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if ( event.getType().equals(InputEvent.Type.touchDown) ) {
                    Kingdom kingdom = (Kingdom) event.getListenerActor();
                    if(kingdom.getProtectionState()==0){
                        if ((castleIsYoursDialog.getWidth()+kingdom.getX())>w){
                            castleIsYoursDialog.setX(w-castleIsYoursDialog.getWidth());
                        } else{
                            castleIsYoursDialog.setX(kingdom.getX()+70);
                        }
                        /*
                        castleIsYoursDialog.setY(kingdom.getY()+70);
                        String s = cradleGame.getLanguageStrings().get("castleIsYours");
                        castleIsYoursDialog.setText(s);
                        final Action  completeAction = new Action(){
                            public boolean act( float delta ) {
                                //move outside to allow underline kingdom to be attacked
                                castleIsYoursDialog.setX(-castleIsYoursDialog.getWidth());
                                return true;
                            }
                        };
                        castleIsYoursDialog.showForTime(1,completeAction);
                        */
                        return false;
                    }
                    //if ( Item.class.isAssignableFrom(a.getClass()) ){
                    KingdomRes kingdomRes = kingdom.getKingdomResForAttack();

                    instrumental.pause();
                    // cradleGame.setActivescreenGamePlay();
                    // cradleGame.getScreenGamePlay().UpdateRes();
                    // cradleGame.getScreenGamePlay().StartNewLevel(1);
                    if ((messageActor01.getWidth()+kingdom.getX())>w){
                        messageActor01.setX(w-messageActor01.getWidth());
                    } else{
                        messageActor01.setX(kingdom.getX()+50);
                    }
                    messageActor01.setY(kingdom.getY()+50);
                    messageActor01.SetRes(kingdomRes);
                    messageActor01.setAttackType(AttackType.AttackKingdom);
                    messageActor01.setAttackedKingdom(kingdom);
                    messageActor01.setVisible(true);

                }
                return false;
            }
        };

        for (int i = 0; i < kingdoms.length; i++) {
            kingdoms[i].addListener(inputListener);
            //kingdoms[i].resetProtectionState(mapLevel);
        }
        kingdoms[0].resetFlag();// if protection state>0 then setup gray flag


        inputListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if ( event.getType().equals(InputEvent.Type.touchDown) ) {
                    Arena arena = (Arena) event.getListenerActor();
                    KingdomRes kingdomRes = arena.GetWinningRes(10);
                    instrumental.pause();
                    if ((messageActor01.getWidth()+arena.getX())>w){
                        messageActor01.setX(w-messageActor01.getWidth());
                    } else{
                        messageActor01.setX(arena.getX()+50);
                    }

                    messageActor01.setY(arena.getY()+50);
                    messageActor01.SetRes(kingdomRes);
                    messageActor01.setAttackType(AttackType.AttackArena);
                    messageActor01.setAttackedKingdom(null);
                    messageActor01.setVisible(true);

                }
                return false;
            }
        };

        arena.addListener(inputListener);

        inputListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if ( event.getType().equals(InputEvent.Type.touchDown) ) {
                    cradleGame.setActiveBlackMarketScreen();
                }
                return false;
            }
        };

        blackMarket.addListener(inputListener);

        if (messageActor01!=null) {
         messageActor01.setZIndex(arena.getZIndex() + 1);
        }

        if (throne!=null){
            throne.remove();
        }
        if (messageLabel!=null){
            messageLabel.remove();
        }

        // Level info
        int dialogYSize = Math.round(h*0.8f);
        int dialogXSize = Math.round(w*0.7f);
        if (mapLevelInfoDialog==null) {
            mapLevelInfoDialog = new DialogBox(w / 2 - dialogXSize / 2, h / 2 - dialogYSize / 2, uiStage, dialogXSize, dialogYSize, cradleGame, BaseGame.labelStyle_SuperSmall);
        }
        else
        {
            mapLevelInfoDialog.remove();
            mapLevelInfoDialog = new DialogBox(w / 2 - dialogXSize / 2, h / 2 - dialogYSize / 2, uiStage, dialogXSize, dialogYSize, cradleGame, BaseGame.labelStyle_SuperSmall);

        }
        mapLevelInfoDialog.setVisible(false);

        if (cradleGame.getScreenGamePlay().getGameLevel()==1) {
            if (dialogBoxAttackTarget == null) {
                int dialogSize = Math.round(h * 0.9f);
                dialogBoxAttackTarget = new DialogBox_AttackTarget(w / 2 - dialogSize / 2, h / 2 - dialogSize / 2, uiStage, dialogSize, dialogSize, cradleGame);
                dialogBoxAttackTarget.setVisible(false);

            } else {
                dialogBoxAttackTarget.remove();
                int dialogSize = Math.round(h * 0.9f);
                dialogBoxAttackTarget = new DialogBox_AttackTarget(w / 2 - dialogSize / 2, h / 2 - dialogSize / 2, uiStage, dialogSize, dialogSize, cradleGame);
                dialogBoxAttackTarget.setVisible(false);

            }
        }

        //Castle is yours info dialog
        if (castleIsYoursDialog == null ){
            castleIsYoursDialog = new DialogBox(0, 0, uiStage, Math.round(w*0.25f), Math.round(h*0.2f), cradleGame, BaseGame.labelStyle_Small);

        } else
        {
            castleIsYoursDialog.remove();
            castleIsYoursDialog = new DialogBox(0, 0, uiStage, Math.round(w*0.25f), Math.round(h*0.2f), cradleGame, BaseGame.labelStyle_Small);
        }
        castleIsYoursDialog.setVisible(false);
        castleIsYoursDialog.alignCenter();

        // Choose level of hardness
        dialogYSize = Math.round(h*0.6f);
        dialogXSize = Math.round(w*0.7f);
        if (levelOfHardnessDialog==null) {
            levelOfHardnessDialog = new LevelOfHardnessDialogBox(w / 2 - dialogXSize / 2, h / 2 - dialogYSize / 2, uiStage, dialogXSize, dialogYSize, cradleGame);
        } else{
            levelOfHardnessDialog.remove();
            levelOfHardnessDialog = new LevelOfHardnessDialogBox(w / 2 - dialogXSize / 2, h / 2 - dialogYSize / 2, uiStage, dialogXSize, dialogYSize, cradleGame);

        }
        levelOfHardnessDialog.setVisible(false);
        //levelOfHardnessDialog.setFontScale(1.6f);
        //levelOfHardnessDialog.setZIndex(200);




        // Choose level of hardness
        if(cradleGame.getDifficultyLevel()==0){

            final InputListener inputListener3 = new InputListener() {
                public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                    if (!(e instanceof InputEvent))
                        return false;

                    if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                        return false;

                    levelOfHardnessDialog.setVisible(false);
                    int lvlH=levelOfHardnessDialog.getSelectedDifficultyLevel();
                    System.out.println("Level of hardness ="+ lvlH);
                    cradleGame.setDifficultyLevel(lvlH);
                    if (isFirstMapLevelRun){
                        isFirstMapLevelRun=false;
                        startInfoDialog(mapLevel);
                    }
                    return true;
                }
            };

            levelOfHardnessDialog.showWithOkButton(inputListener3);
        }

        if (isFirstMapLevelRun && (!levelOfHardnessDialog.isVisible())){
            isFirstMapLevelRun=false;
            startInfoDialog(mapLevel);
        }
/*
        if (isFirstMapLevelRun==false){
            if (arrowDownActor!=null) {
                arrowDownActor.setVisible(true);
            }
        }
        */

    }

    private void startInfoDialog(int mapLevel){

        final int kingdomsize = cradleGame.getKingdomsize();
        final InputListener inputListener2 = new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                mapLevelInfoDialog.setVisible(false);
                setFirstMapLevelRun(false);
                int w = Gdx.graphics.getWidth();
                int h = Gdx.graphics.getHeight();
                if ((cradleGame.getGameMapLevel()==1)&&(cradleGame.getScreenGamePlay().getGameLevel()==1)) {
                    arrowDownActor.startAnimation(w * 0.6f + kingdomsize * 0.1f, h * 0.4f + kingdomsize * 1.5f, w * 0.6f + kingdomsize * 0.1f, h * 0.4f + kingdomsize);
                    arrowDownActor.setVisible(true);
                }
                return true;
            }
        };

        mapLevelInfoDialog.setText(getTextForMapLevel(mapLevel));
        int w = Gdx.graphics.getWidth();

        /*
        if (w>1000){
            mapLevelInfoDialog.setFontScale(1.4f);
        } else {
            mapLevelInfoDialog.setFontScale(0.6f);
        }
       */

        mapLevelInfoDialog.setZIndex(101);
        mapLevelInfoDialog.showWithOkButton(inputListener2);
    }


    public void UpdateRes() {
        resultsActor.UpdateRes();
        for (int i=0; i<kingdoms.length;i++){
            kingdoms[i].saveParams();
        }
        if (cradleGame.getScreenGamePlay().getGameLevel()>1){
            if(arrowDownActor!=null) {
                arrowDownActor.setVisible(false);
            }
        }
        checkWin();
        if (isUpdateMapNeeded && (!isWinMapLevel)){
            //changeMapTexture(cradleGame.getGameMapLevel());
            System.out.println("initializeMap called");
            initializeMap(cradleGame.getGameMapLevel());
            isUpdateMapNeeded=false;
        }


    }


    public void update(float dt)
    {

    }

    public void checkWin(){
        for (int i=0; i<kingdoms.length;i++){
            if (kingdoms[i].getProtectionState()>0){
                return; // No win
            }
        }

        if ((gameMapLevelInfo!=null) && gameMapLevelInfo.isGameMapLevelWinned(cradleGame.getGameMapLevel()))
        {
         return;// Already winned before
        }
        // win game reached!!!
        isWinMapLevel=true;
        System.out.println("CheckWin called");
        WinGame();
        return;

    }



    public void WinGame(){

        if (gameMapLevelInfo==null){return;}
        if(cradleGame.getGameMapLevel()<cradleGame.getMaxOpenedMapLevel()){
            return;
        }


        // Get screen size
        int w = Gdx.graphics.getWidth();
        int ww = w;
        int h = Gdx.graphics.getHeight();
        if (w<h) {
            h=w;
        } else w=h;


        if (throne!=null){
            throne.remove();
        }


        throne = new BaseActor(0,-h,uiStage,Touchable.disabled,cradleGame);
        throne.loadTexture("ironthrone.png",w,h);
        throne.setX((ww-w)/2);
        throne.setY(-h);
        //win.setOpacity(80);
        Action actions = sequence(moveTo((ww-w)/2,0,2f));
        throne.addAction(actions);

        if (messageLabel!=null){
            messageLabel.remove();
        }


        Action completeAction = new Action(){
            public boolean act( float delta ) {
                // Do your stuff


                    String s = cradleGame.getLanguageStrings().get("continue_to_next_level");
                    continueButton = new TextButton("   "+s+"   ", BaseGame.textButtonStyle);
                    uiStage.addActor(continueButton);
                    int h = Gdx.graphics.getHeight();
                    continueButton.setPosition(5, h*0.03f);
                    continueButton.addListener(new InputListener() {
                    public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                        if (!(e instanceof InputEvent))
                            return false;

                        if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                            return false;
                        isWinMapLevel=false;
                        UpdateRes();
                        cradleGame.setGameMapLevel(cradleGame.getMaxOpenedMapLevel());
                        setFirstMapLevelRun(true);
                        //ply.logLevelUpEvent("3", "Level of map is won", "Map level won");
                        cradleGame.setActiveGameMapScreen(false,cradleGame.getGameMapLevel());
                        return true;
                    }
                });


                return true;
            }
        };


        if (cradleGame.getMaxOpenedMapLevel()<cradleGame.MaxGameMapLevel) {
            gameMapLevelInfo.setMapLevelWinned(cradleGame.getGameMapLevel());


            cradleGame.setMaxOpenedMapLevel(cradleGame.getMaxOpenedMapLevel()+1);

            isUpdateMapNeeded=true;
        } else {
            isUpdateMapNeeded=true;
        }

        actions = sequence(fadeOut(0.01f), Actions.delay(3),fadeIn(1f) , completeAction);

        String s = cradleGame.getLanguageStrings().get("throne_is_yours");
        messageLabel = new Label(s, BaseGame.labelStyle_Big);
        //messageLabel.setFontScale(3);
        messageLabel.setVisible(true);
        messageLabel.setX((ww-messageLabel.getWidth())/2.0f);
        messageLabel.setY(h*0.4f);
        messageLabel.setColor(Color.RED);
        messageLabel.setAlignment(Align.center);
        messageLabel.addAction(actions);
        uiStage.addActor(messageLabel);

    }

    public Kingdom[] getKingdoms() {
        return kingdoms;
    }


public String getTextForMapLevel(int maplevel){
     String s;
        switch (maplevel){
         case 1:
             s = cradleGame.getLanguageStrings().get("maplevel1text");
             break;
             case 2:
                s = cradleGame.getLanguageStrings().get("maplevel2text");
                break;
            case 3:
                s = cradleGame.getLanguageStrings().get("maplevel3text");
                break;
            case 4:
                s = cradleGame.getLanguageStrings().get("maplevel4text");
                break;
             default:
                 s="";

     }
     return s;

}

    public Knight getKnight() {
        return knight;
    }

    public void askToRateTheGame(){
        //ShowDoYouLikeTheGameDialog(); // for debug
        GregorianCalendar calendarG = new GregorianCalendar();
        calendarG.setTime(new Date());
        // Check if question needed
        int isLiked = cradleGame.getIsGameLiked();
        if (isLiked==0){
                if (((GameRes.Score - cradleGame.getLastScoreWhenAskedToVote()) > 500) && ((calendarG.get(Calendar.DAY_OF_YEAR) - cradleGame.getLastDayAskedToVote()) > 5)) {
                    ShowDoYouLikeTheGameDialog();
                }
        }

    }

    public void ShowDoYouLikeTheGameDialog(){
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        final GregorianCalendar calendarG = new GregorianCalendar();
        calendarG.setTime(new Date());


        Dialog dialog = new CustomDialog("", BaseGame.skin, h*0.7f,h*0.3f) {
            public void result(Object obj) {
                if (obj.toString()=="true"){
                    ShowDoYouLikeToVoteDialog();
                } else{
                    cradleGame.setIsGameLiked(1);
                    ShowWritUsDialog();
                }
                cradleGame.setLastDayAskedToVote(calendarG.get(Calendar.DAY_OF_YEAR));
                cradleGame.setLastScoreWhenAskedToVote(GameRes.Score);
            }

        };
        String s = cradleGame.getLanguageStrings().get("doyoulikethegame");
        dialog.text(s);
        s = cradleGame.getLanguageStrings().get("yes");
        dialog.button(s, true,BaseGame.textButtonStyle); //sends "true" as the result
        s = cradleGame.getLanguageStrings().get("no");
        dialog.button(s, false,BaseGame.textButtonStyle); //sends "false" as the result

        dialog.show(uiStage);

    }


    public void ShowDoYouLikeToVoteDialog(){
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        final GregorianCalendar calendarG = new GregorianCalendar();
        calendarG.setTime(new Date());

        Dialog dialog = new CustomDialog("", BaseGame.skin, h*0.7f,h*0.3f) {
            public void result(Object obj) {
                if (obj.toString()=="true"){
                    cradleGame.setIsGameLiked(2);
                    goToGooglePlay();
                }
                cradleGame.setLastDayAskedToVote(calendarG.get(Calendar.DAY_OF_YEAR));
                cradleGame.setLastScoreWhenAskedToVote(GameRes.Score);
            }

        };
        String s = cradleGame.getLanguageStrings().get("doyouliketorate");
        dialog.text(s);
        s = cradleGame.getLanguageStrings().get("yes");
        dialog.button(s, true,BaseGame.textButtonStyle); //sends "true" as the result
        s = cradleGame.getLanguageStrings().get("no");
        dialog.button(s, false,BaseGame.textButtonStyle); //sends "false" as the result

        dialog.show(uiStage);

    }


    public void ShowWritUsDialog(){
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        final GregorianCalendar calendarG = new GregorianCalendar();
        calendarG.setTime(new Date());


        Dialog dialog = new CustomDialog("", BaseGame.skin, h*0.7f,h*0.3f) {
            public void result(Object obj) {
                if (obj.toString()=="true"){
                    connectUs();
                    cradleGame.setIsGameLiked(1);
                } else{
                    cradleGame.setIsGameLiked(1);
                }
                cradleGame.setLastDayAskedToVote(calendarG.get(Calendar.DAY_OF_YEAR));
                cradleGame.setLastScoreWhenAskedToVote(GameRes.Score);
            }

        };
        String s = cradleGame.getLanguageStrings().get("writeus");
        dialog.text(s);
        s = cradleGame.getLanguageStrings().get("yes");
        dialog.button(s, true,BaseGame.textButtonStyle); //sends "true" as the result
        s = cradleGame.getLanguageStrings().get("no");
        dialog.button(s, false,BaseGame.textButtonStyle); //sends "false" as the result

        dialog.show(uiStage);

    }

    public void goToGooglePlay(){
        cradleGame.rateGame();
    }

    public void connectUs() {
        cradleGame.connectUs();
    }

    public GameMapLevelInfo getGameMapLevelInfo() {
        return gameMapLevelInfo;
    }


}