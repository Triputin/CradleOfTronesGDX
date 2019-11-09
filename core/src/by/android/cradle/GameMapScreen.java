package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameMapScreen extends BaseScreen {

    public void initialize()
    {

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        BaseActor worldMap = new BaseActor(0,0, mainStage, Touchable.disabled);
        worldMap.loadTexture( "worldmap01.png",w,h );
        // ocean.setSize(800,600);

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

                CradleGame.setActiveScreen(new ScreenGamePlay());
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