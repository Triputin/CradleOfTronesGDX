package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameMapScreen extends BaseScreen {


    private Music instrumental;
    private float audioVolume;
    private MessageActor01 messageActor01;
    private ResultsActor resultsActor;
    private Kingdom[] kingdoms;

    public GameMapScreen(CradleGame cradleGame) {

        super(cradleGame);

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


        BaseActor worldMap = new BaseActor(0,0, mainStage, Touchable.disabled);
        worldMap.loadTexture( "worldmap02.png",w,h );
        BaseActor baseResultsActor = new BaseActor(w*0.25f,h-70,mainStage,Touchable.disabled);
        baseResultsActor.setWidth((int) Math.round(w*0.6));
        baseResultsActor.setHeight(70);
        resultsActor = new ResultsActor(0,0,(int) Math.round(w*0.6),70,mainStage,Touchable.disabled,baseResultsActor);
        UpdateRes();
        //Fon for results
        BaseActor fon = new BaseActor(0,0,uiStage,Touchable.disabled);
        fon.loadTexture("fon_orange.png",(int) Math.round(w*0.6)+30,70);
        fon.setX((int) Math.round(w*0.25));
        fon.setY(h-70);


        TextButton backButton = new TextButton( "Menu", BaseGame.textButtonStyle );
        backButton.setPosition(w*0.02f,h*0.05f);
        uiStage.addActor(backButton);

        backButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                instrumental.pause();
                cradleGame.setActiveMenuScreen();
                return true;
            }
        });


        //Kingdoms
        h = Gdx.graphics.getHeight();
        int kingdomsize = h/9;
        kingdoms = new Kingdom[7];
        kingdoms[0] = new Kingdom(w*0.17f, h*0.62f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_North);
        kingdoms[0].setProtectionState(0); // starting Kingdom for player
        kingdoms[1] = new Kingdom(w*0.28f, h*0.3f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Isles_and_Rivers);
        kingdoms[1].setProtectionState(2);
        kingdoms[2] = new Kingdom(w*0.6f, h*0.4f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale);
        kingdoms[3] = new Kingdom(w*0.4f, h*0.28f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach);
        kingdoms[4] = new Kingdom(w*0.45f, h*0.15f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock);
        kingdoms[5] = new Kingdom(w*0.18f, h*0.25f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands);
        kingdoms[6] = new Kingdom(w*0.78f, h*0.35f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne);
        kingdoms[6].setProtectionState(5);

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
        }


        Arena arena = new Arena(w*0.57f, h*0.045f,Math.round(kingdomsize*1.56f), kingdomsize,uiStage,Touchable.enabled);

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

        // uiTable.add(title).colspan(2);
        // uiTable.row();
        // uiTable.add(backButton);
        // uiTable.add(quitButton);

        messageActor01 = new MessageActor01(0,0,uiStage,500,200,cradleGame);
        messageActor01.setVisible(false);
    }



    public void UpdateRes() {
        GameRes gameRes= cradleGame.getGameRes();
        resultsActor.UpdateRes(gameRes);
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
        return;

    }
/*
    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
            instrumental.pause();
            cradleGame.setActivescreenGamePlay();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            instrumental.pause();
            Gdx.app.exit();
        return false;
    }

*/

}