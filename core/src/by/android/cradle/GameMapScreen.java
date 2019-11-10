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
        backButton.setPosition(150,100);
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


        TextButton quitButton = new TextButton( "Quit", BaseGame.textButtonStyle );
        quitButton.setPosition(300,100);
        uiStage.addActor(quitButton);

        quitButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){

                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                Gdx.app.exit();
                return true;
            }
        });


        //Kingdoms
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        Kingdom winterfell = new Kingdom(w*0.15f, h*0.75f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_North);
        Kingdom kingdom2 = new Kingdom(w*0.29f, h*0.55f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Isles_and_Rivers);
        Kingdom kingdom3 = new Kingdom(w*0.45f, h*0.61f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Mountain_and_the_Vale);
        Kingdom kingdom4 = new Kingdom(w*0.45f, h*0.44f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Reach);
        Kingdom kingdom5 = new Kingdom(w*0.45f, h*0.25f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Rock);
        Kingdom kingdom6 = new Kingdom(w*0.12f, h*0.48f,50,50,uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_Stormlands);
        Kingdom kingdom7 = new Kingdom(w*0.65f, h*0.4f,50,50,uiStage,Touchable.enabled,KingdomNames.Principality_of_Dorne);
        //uiTable.add(title).colspan(2);
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