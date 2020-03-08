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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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

    public GameMapScreen(CradleGame cradleGame,IPlayServices ply) {

        super(cradleGame,ply);
        isUpdateMapNeeded = false;

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
        musicArray[0] = Gdx.audio.newMusic(Gdx.files.internal("sounds/new_land.mp3"));
        musicArray[0].setLooping(true);
        musicArray[0].setVolume(audioVolume);
        musicArray[0].pause();
        musicArray[1] = Gdx.audio.newMusic(Gdx.files.internal("sounds/soundmap02.mp3"));
        musicArray[1].setLooping(true);
        musicArray[1].setVolume(audioVolume);
        musicArray[1].pause();
        instrumental=musicArray[0];
        //instrumental = Gdx.audio.newMusic(Gdx.files.internal("sounds/new_land.mp3"));


        isWinMapLevel = false;

        if ((cradleGame.getGameMapLevel()==1)&&(cradleGame.getScreenGamePlay().getGameLevel()==1)){
            isFirstMapLevelRun=true;
        }else {
            isFirstMapLevelRun=false;
        }
        final int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        //h=h-60; //add place for adMob



        initializeMap(cradleGame.getGameMapLevel());
        instrumental.pause();

        int knSize = Math.round(h*0.4f);
        int wpSize = Math.round(h*0.1f);
        if (knight!=null){knight.remove();}
        knight = new Knight(-knSize*0.1f,h-knSize+knSize*0.15f,knSize,knSize,uiStage,cradleGame.getKnightParams());
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


        //Fon for results
        BaseActor fon = new BaseActor(0,0,mainStage,Touchable.disabled);
        fon.loadTexture("fon_orange.png",(int) Math.round(w*0.8)+30,70);
        fon.setX((int) Math.round(w*0.25));
        fon.setY(h-70);

        BaseActor baseResultsActor = new BaseActor(w*0.25f,h-70,mainStage,Touchable.disabled);
        baseResultsActor.setWidth((int) Math.round(w*0.8));
        baseResultsActor.setHeight(70);
        resultsActor = new ResultsActor(0,0,(int) Math.round(w*0.8),70,mainStage,Touchable.disabled,baseResultsActor);


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

        messageActor01 = new MessageActor01(0,0,uiStage,500,200,cradleGame);
        messageActor01.setVisible(false);
        UpdateRes();


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
        int kingdomsize = h/9;
        if (kingdoms!=null){
            for (int i = 0; i < kingdoms.length; i++) {
                kingdoms[i].remove();
            }
        }
        if (arena!=null) {
            arena.remove();
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
            worldMap = new BaseActor(0, 0, mainStage, Touchable.disabled);
        }
        instrumental.pause();
        instrumental = musicArray[0];
        switch(mapLevel){

            case 2:

                    worldMap.loadTexture("maps/worldmap02.png", w, h);

                    kingdoms[0] = new Kingdom(w * 0.3f, h * 0.56f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_North);
                    kingdoms[1] = new Kingdom(w * 0.26f, h * 0.3f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_Isles_and_Rivers);
                    kingdoms[2] = new Kingdom(w*0.6f, h*0.55f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale);
                    kingdoms[3] = new Kingdom(w*0.4f, h*0.45f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach);
                    kingdoms[4] = new Kingdom(w*0.45f, h*0.15f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock);
                    kingdoms[5] = new Kingdom(w*0.75f, h*0.25f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands);
                    kingdoms[6] = new Kingdom(w*0.72f, h*0.6f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne);
                    arena = new Arena(w * 0.3f, h * 0.18f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled);
                    blackMarket = new BlackMarket(w * 0.54f, h * 0.4f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled);
                    instrumental = musicArray[1];
                break;
            case 1:
                worldMap.loadTexture( "maps/worldmap01.png",w,h );

                    kingdoms[0] = new Kingdom(w * 0.16f, h * 0.56f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_North);
                    kingdoms[1] = new Kingdom(w * 0.28f, h * 0.3f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_Isles_and_Rivers);
                    kingdoms[2] = new Kingdom(w*0.6f, h*0.4f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale);
                    kingdoms[3] = new Kingdom(w*0.4f, h*0.28f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach);
                    kingdoms[4] = new Kingdom(w*0.45f, h*0.15f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock);
                    kingdoms[5] = new Kingdom(w*0.14f, h*0.25f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands);
                    kingdoms[6] = new Kingdom(w*0.78f, h*0.35f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne);
                    arena = new Arena(w * 0.77f, h * 0.25f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled);
                    blackMarket = new BlackMarket(w * 0.68f, h * 0.32f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled);

                break;
            case 3:

                worldMap.loadTexture("maps/worldmap03.png", w, h);

                kingdoms[0] = new Kingdom(w * 0.2f, h * 0.56f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_North);
                kingdoms[1] = new Kingdom(w * 0.1f, h * 0.3f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_Isles_and_Rivers);
                kingdoms[2] = new Kingdom(w*0.55f, h*0.55f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale);
                kingdoms[3] = new Kingdom(w*0.35f, h*0.45f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach);
                kingdoms[4] = new Kingdom(w*0.45f, h*0.15f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock);
                kingdoms[5] = new Kingdom(w*0.6f, h*0.25f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands);
                kingdoms[6] = new Kingdom(w*0.75f, h*0.6f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne);
                arena = new Arena(w * 0.15f, h * 0.15f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled);
                blackMarket = new BlackMarket(w * 0.3f, h * 0.2f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled);

                break;

            case 4:

                worldMap.loadTexture("maps/worldmap04.png", w, h);

                kingdoms[0] = new Kingdom(w * 0.5f, h * 0.45f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_North);
                kingdoms[1] = new Kingdom(w * 0.4f, h * 0.3f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_Isles_and_Rivers);
                kingdoms[2] = new Kingdom(w*0.45f, h*0.65f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale);
                kingdoms[3] = new Kingdom(w*0.3f, h*0.45f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach);
                kingdoms[4] = new Kingdom(w*0.6f, h*0.33f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock);
                kingdoms[5] = new Kingdom(w*0.7f, h*0.3f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands);
                kingdoms[6] = new Kingdom(w*0.65f, h*0.6f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne);
                arena = new Arena(w * 0.6f, h * 0.15f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled);
                blackMarket = new BlackMarket(w * 0.45f, h * 0.27f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled);

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
            kingdoms[i].resetProtectionState(mapLevel);
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
            mapLevelInfoDialog = new DialogBox(w / 2 - dialogXSize / 2, h / 2 - dialogYSize / 2, uiStage, dialogXSize, dialogYSize, cradleGame);
        }
        else
        {
            mapLevelInfoDialog.remove();
            mapLevelInfoDialog = new DialogBox(w / 2 - dialogXSize / 2, h / 2 - dialogYSize / 2, uiStage, dialogXSize, dialogYSize, cradleGame);

        }
        mapLevelInfoDialog.setVisible(false);

        //Castle is yours info dialog
        if (castleIsYoursDialog == null ){
            castleIsYoursDialog = new DialogBox(0, 0, uiStage, Math.round(w*0.25f), Math.round(h*0.2f), cradleGame);

        } else
        {
            castleIsYoursDialog.remove();
            castleIsYoursDialog = new DialogBox(0, 0, uiStage, Math.round(w*0.25f), Math.round(h*0.2f), cradleGame);
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
        levelOfHardnessDialog.setFontScale(1.6f);
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
                    System.out.println("Levl of hardness ="+ lvlH);
                    cradleGame.setDifficultyLevel(lvlH);
                    if (isFirstMapLevelRun){
                        startInfoDialog(mapLevel);
                    }
                    return true;
                }
            };

            levelOfHardnessDialog.showWithOkButton(inputListener3);
        }

        if (isFirstMapLevelRun && (!levelOfHardnessDialog.isVisible())){
            startInfoDialog(mapLevel);
        }
    }

    private void startInfoDialog(int mapLevel){
        final InputListener inputListener2 = new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                mapLevelInfoDialog.setVisible(false);
                setFirstMapLevelRun(false);
                return true;
            }
        };

        mapLevelInfoDialog.setText(getTextForMapLevel(mapLevel));
        mapLevelInfoDialog.setFontScale(1.5f);
        mapLevelInfoDialog.setZIndex(101);
        mapLevelInfoDialog.showWithOkButton(inputListener2);
    }


    public void UpdateRes() {
        resultsActor.UpdateRes();
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

        // win game reached!!!
        isWinMapLevel=true;
        System.out.println("CheckWin called");
        WinGame();
        return;

    }



    public void WinGame(){
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
        if (messageLabel!=null){
            messageLabel.remove();
        }

        throne = new BaseActor(0,-h,uiStage,Touchable.disabled);
        throne.loadTexture("ironthrone.png",w,h);
        throne.setX((ww-w)/2);
        throne.setY(-h);
        //win.setOpacity(80);
        Action actions = sequence(moveTo((ww-w)/2,0,2f));
        throne.addAction(actions);

        //System.out.println("Throne anim started!!!");
        Action completeAction = new Action(){
            public boolean act( float delta ) {
                // Do your stuff

                if (isUpdateMapNeeded) {
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
                        return true;
                    }
                });

            }
                return true;
            }
        };


        if (cradleGame.getGameMapLevel()<cradleGame.MaxGameMapLevel) {
            cradleGame.setGameMapLevel(cradleGame.getGameMapLevel() + 1);
            isUpdateMapNeeded=true;
        }

        actions = sequence(fadeOut(0.01f), Actions.delay(3),fadeIn(1f) , completeAction);

        messageLabel = new Label("...", BaseGame.labelStyle);
        String s = cradleGame.getLanguageStrings().get("throne_is_yours");
        messageLabel.setText(s);
        messageLabel.setX(ww/2f- messageLabel.getWidth()/2f);
        messageLabel.setY(h*0.3f);
        messageLabel.setFontScale(3);
        messageLabel.setVisible(true);
        messageLabel.setColor(Color.RED);
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
             default:
                 s="";

     }
     return s;

}

    public Knight getKnight() {
        return knight;
    }
}