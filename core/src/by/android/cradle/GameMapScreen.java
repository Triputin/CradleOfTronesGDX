package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class GameMapScreen extends BaseScreen {


    private Music instrumental;
    private float audioVolume;
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

    public GameMapScreen(CradleGame cradleGame,IPlayServices ply) {

        super(cradleGame,ply);
        isUpdateMapNeeded = false;
    }

    public void SetMessageActorVisibility(boolean visible){
        messageActor01.setVisible(visible);
    }

    public void PlayMusic(){
        instrumental.play();
    }

    public void PauseMusic(){
        instrumental.pause();
    }


    public void initialize()
    {

        instrumental = Gdx.audio.newMusic(Gdx.files.internal("sounds/new_land.mp3"));
        audioVolume = 0.7f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);


        final int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        //h=h-60; //add place for adMob

        initializeMap(cradleGame.getGameMapLevel());


        BaseActor baseResultsActor = new BaseActor(w*0.25f,h-70,mainStage,Touchable.disabled);
        baseResultsActor.setWidth((int) Math.round(w*0.8));
        baseResultsActor.setHeight(70);
        resultsActor = new ResultsActor(0,0,(int) Math.round(w*0.8),70,mainStage,Touchable.disabled,baseResultsActor);

        //Fon for results
        BaseActor fon = new BaseActor(0,0,uiStage,Touchable.disabled);
        fon.loadTexture("fon_orange.png",(int) Math.round(w*0.8)+30,70);
        fon.setX((int) Math.round(w*0.25));
        fon.setY(h-70);

       //Menu Button
        String s = cradleGame.getLanguageStrings().get("menu");
        TextButton backButton = new TextButton( s, BaseGame.textButtonStyle );
        backButton.setPosition(w*0.00f,h*0.88f);
        uiStage.addActor(backButton);

        backButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                instrumental.pause();
                UpdateRes();
                cradleGame.setActiveMenuScreen();
                return true;
            }
        });


        //Shop Button
        s = cradleGame.getLanguageStrings().get("exchange");
        shopButton = new TextButton( s, BaseGame.textButtonStyle );
        shopButton.setPosition(w*0.00f,h*0.03f);
        uiStage.addActor(shopButton);

        shopButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                instrumental.pause();
                cradleGame.setActiveShopScreen();
                return true;
            }
        });
        // uiTable.add(title).colspan(2);
        // uiTable.row();
        // uiTable.add(backButton);
        // uiTable.add(quitButton);

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

    public void initializeMap(int mapLevel){

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

        if (continueButton!=null){
            continueButton.remove();
        }
        if (shopButton!=null){
            shopButton.remove();
        }

        kingdoms = new Kingdom[7];
        if( worldMap==null) {
            worldMap = new BaseActor(0, 0, mainStage, Touchable.disabled);
        }
        switch(mapLevel){

            case 2:

                    worldMap.loadTexture("maps/worldmap03.png", w, h);

                    kingdoms[0] = new Kingdom(w * 0.3f, h * 0.56f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_North);
                    kingdoms[1] = new Kingdom(w * 0.26f, h * 0.3f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_Isles_and_Rivers);
                    kingdoms[2] = new Kingdom(w*0.6f, h*0.55f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale);
                    kingdoms[3] = new Kingdom(w*0.4f, h*0.45f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach);
                    kingdoms[4] = new Kingdom(w*0.45f, h*0.15f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock);
                    kingdoms[5] = new Kingdom(w*0.75f, h*0.25f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands);
                    kingdoms[6] = new Kingdom(w*0.85f, h*0.6f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne);
                    arena = new Arena(w * 0.3f, h * 0.18f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled);

                break;
            case 1:
                worldMap.loadTexture( "maps/worldmap02.png",w,h );

                    kingdoms[0] = new Kingdom(w * 0.16f, h * 0.56f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_North);
                    kingdoms[1] = new Kingdom(w * 0.28f, h * 0.3f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_Isles_and_Rivers);
                    kingdoms[2] = new Kingdom(w*0.6f, h*0.4f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale);
                    kingdoms[3] = new Kingdom(w*0.4f, h*0.28f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach);
                    kingdoms[4] = new Kingdom(w*0.45f, h*0.15f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock);
                    kingdoms[5] = new Kingdom(w*0.14f, h*0.25f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands);
                    kingdoms[6] = new Kingdom(w*0.78f, h*0.35f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne);
                    arena = new Arena(w * 0.77f, h * 0.25f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled);
                break;
            case 3:

                worldMap.loadTexture("maps/worldmap04.png", w, h);

                kingdoms[0] = new Kingdom(w * 0.2f, h * 0.56f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_North);
                kingdoms[1] = new Kingdom(w * 0.1f, h * 0.3f, kingdomsize, kingdomsize, uiStage, Touchable.enabled, KingdomNames.Kingdom_of_the_Isles_and_Rivers);
                kingdoms[2] = new Kingdom(w*0.55f, h*0.55f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale);
                kingdoms[3] = new Kingdom(w*0.35f, h*0.45f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach);
                kingdoms[4] = new Kingdom(w*0.45f, h*0.15f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock);
                kingdoms[5] = new Kingdom(w*0.6f, h*0.25f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands);
                kingdoms[6] = new Kingdom(w*0.75f, h*0.6f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne);
                arena = new Arena(w * 0.15f, h * 0.15f, Math.round(kingdomsize * 1.56f), kingdomsize, uiStage, Touchable.enabled);

                break;

        }



        InputListener inputListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if ( event.getType().equals(InputEvent.Type.touchDown) ) {
                    Kingdom kingdom = (Kingdom) event.getListenerActor();
                    if(kingdom.getProtectionState()==0){
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
        if (messageActor01!=null) {
         messageActor01.setZIndex(arena.getZIndex() + 1);
        }

        if (throne!=null){
            throne.remove();
        }
        if (messageLabel!=null){
            messageLabel.remove();
        }


    }


    public void UpdateRes() {
        resultsActor.UpdateRes();
        if (isUpdateMapNeeded){
            //changeMapTexture(cradleGame.getGameMapLevel());
            initializeMap(cradleGame.getGameMapLevel());
            isUpdateMapNeeded=false;
        }

        checkWin();
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


        Action completeAction = new Action(){
            public boolean act( float delta ) {
                // Do your stuff

                if (isUpdateMapNeeded) {
                    String s = cradleGame.getLanguageStrings().get("continue_to_next_level");
                    continueButton = new TextButton("   "+s+"   ", BaseGame.textButtonStyle);
                    uiStage.addActor(continueButton);
                    continueButton.addListener(new InputListener() {
                    public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                        if (!(e instanceof InputEvent))
                            return false;

                        if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                            return false;
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
        messageLabel.setX((ww-w)/2f);
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





}