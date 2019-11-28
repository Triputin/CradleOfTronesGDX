package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameMapScreen extends BaseScreen {

    ScreenGamePlay screenGamePlay;

    public GameMapScreen(ScreenGamePlay screenGamePlay) {
        super();
        this.screenGamePlay = screenGamePlay;
    }

    public void initialize()
    {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        BaseActor worldMap = new BaseActor(0,0, mainStage, Touchable.disabled);
        worldMap.loadTexture( "worldmap01.png",w,h );

        // BaseActor title = new BaseActor(0,0, mainStage, Touchable.disabled);
        // title.loadTexture( "assets/starfish-collector.png" );

        TextButton backButton = new TextButton( "Back", BaseGame.textButtonStyle );
        backButton.setPosition(150,50);
        uiStage.addActor(backButton);

        backButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                CradleGame.setActiveScreen(screenGamePlay);
                return true;
            }
        });

        //Kingdoms
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        Kingdom[] kingdoms = new Kingdom[7];
        kingdoms[0] = new Kingdom(w*0.15f, h*0.75f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_North);
        kingdoms[1] = new Kingdom(w*0.29f, h*0.55f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Isles_and_Rivers);
        kingdoms[2] = new Kingdom(w*0.45f, h*0.61f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale);
        kingdoms[3] = new Kingdom(w*0.45f, h*0.44f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach);
        kingdoms[4] = new Kingdom(w*0.45f, h*0.25f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock);
        kingdoms[5] = new Kingdom(w*0.12f, h*0.48f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands);
        kingdoms[6] = new Kingdom(w*0.65f, h*0.4f,50,50,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne);

        InputListener inputListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if ( event.getType().equals(InputEvent.Type.touchDown) ) {
                    Kingdom kingdom = (Kingdom) event.getListenerActor();
                    KingdomRes kingdomRes = kingdom.getKingdomResForAttack();
                    GameRes.Gold -= kingdomRes.Gold;
                    GameRes.Wood -= kingdomRes.Wood;
                    GameRes.Bread -= kingdomRes.Bread;
                    CradleGame.setActiveScreen(screenGamePlay);
                    screenGamePlay.UpdateRes();
                    screenGamePlay.StartNewLevel(1);
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

    public void update(float dt)
    {

    }

    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
            CradleGame.setActiveScreen( new ScreenGamePlay() );
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        return false;
    }


}