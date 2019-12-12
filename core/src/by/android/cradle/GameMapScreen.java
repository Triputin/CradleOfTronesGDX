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

    private Label goldQuantityLabel;
    private Label woodQuantityLabel;
    private Label breadQuantityLabel;


    public GameMapScreen(CradleGame cradleGame) {

        super(cradleGame);

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


        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();


        BaseActor worldMap = new BaseActor(0,0, mainStage, Touchable.disabled);
        worldMap.loadTexture( "worldmap02.png",w,h );

        new ResultsActor(w*0.25f,h-70,(int) Math.round(w*0.6),70,mainStage,Touchable.disabled);
        DrawResults((int) Math.round(w*0.25),h-70, (int) Math.round(w*0.6));
        UpdateRes();

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
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        int kingdomsize = h/9;
        Kingdom[] kingdoms = new Kingdom[7];
        kingdoms[0] = new Kingdom(w*0.17f, h*0.62f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_North);
        kingdoms[1] = new Kingdom(w*0.28f, h*0.3f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Isles_and_Rivers);
        kingdoms[2] = new Kingdom(w*0.6f, h*0.4f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale);
        kingdoms[3] = new Kingdom(w*0.4f, h*0.28f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach);
        kingdoms[4] = new Kingdom(w*0.45f, h*0.15f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock);
        kingdoms[5] = new Kingdom(w*0.18f, h*0.25f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands);
        kingdoms[6] = new Kingdom(w*0.78f, h*0.35f,kingdomsize,kingdomsize,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne);

        InputListener inputListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if ( event.getType().equals(InputEvent.Type.touchDown) ) {
                    Kingdom kingdom = (Kingdom) event.getListenerActor();
                    KingdomRes kingdomRes = kingdom.getKingdomResForAttack();
                    GameRes.Gold -= kingdomRes.Gold;
                    GameRes.Wood -= kingdomRes.Wood;
                    GameRes.Bread -= kingdomRes.Bread;
                    instrumental.pause();
                   // cradleGame.setActivescreenGamePlay();
                   // cradleGame.getScreenGamePlay().UpdateRes();
                   // cradleGame.getScreenGamePlay().StartNewLevel(1);

                    MessageActor01 messageActor01 = new MessageActor01(kingdom.getX(),kingdom.getY()+150,mainStage,500,100,cradleGame);

                }
                return false;
            }
        };

        for (int i = 0; i < kingdoms.length; i++) {
            kingdoms[i].addListener(inputListener);
        }

        // uiTable.add(title).colspan(2);
        // uiTable.row();
        // uiTable.add(backButton);
        // uiTable.add(quitButton);
    }

    public void DrawResults(int x, int h, int gameFieldWidth){
        //Fon
        BaseActor fon = new BaseActor(0,0,uiStage,Touchable.disabled);
        fon.loadTexture("fon_orange.png",gameFieldWidth+30,70);
        fon.setX(x);
        fon.setY(h);
        goldQuantityLabel = new Label(" "+0, BaseGame.labelStyle);
        goldQuantityLabel.setColor( Color.GOLDENROD );
        goldQuantityLabel.setPosition( x+gameFieldWidth*0.16f,h+10 );
        goldQuantityLabel.setFontScale(2f);
        mainStage.addActor(goldQuantityLabel);

        woodQuantityLabel = new Label(" "+0, BaseGame.labelStyle);
        woodQuantityLabel.setColor( Color.GOLDENROD );
        woodQuantityLabel.setPosition( x+ gameFieldWidth*0.47f,h+10 );
        woodQuantityLabel.setFontScale(2f);
        mainStage.addActor(woodQuantityLabel);

        breadQuantityLabel = new Label(" "+0, BaseGame.labelStyle);
        breadQuantityLabel.setColor( Color.GOLDENROD );
        breadQuantityLabel.setPosition( x+gameFieldWidth*0.85f,h+10 );
        breadQuantityLabel.setFontScale(2f);
        mainStage.addActor(breadQuantityLabel);

    }


    public void UpdateRes() {
        GameRes gameRes= cradleGame.getGameRes();
        goldQuantityLabel.setText(" " + gameRes.Gold);
        woodQuantityLabel.setText(" " + gameRes.Wood);
        breadQuantityLabel.setText(" " + gameRes.Bread);


    }

    public void update(float dt)
    {

    }

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


}