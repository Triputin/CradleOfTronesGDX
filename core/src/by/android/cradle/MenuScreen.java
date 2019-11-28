package by.android.cradle;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen extends BaseScreen {

    public void initialize()
    {

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        //Flags

        Flag f1=new Flag(w*0.525f,h*0.79f,(int)Math.round(w*0.12f),(int)Math.round(h*0.2f),uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_North);
        Flag f2=new Flag(w*0.11f,h*0.48f,(int)Math.round(w*0.08f),(int)Math.round(h*0.15f),uiStage,Touchable.enabled,KingdomNames.Kingdom_of_the_North);


        // BaseActor castleSky = new BaseActor(-w,0, uiStage,Touchable.disabled);
        BaseActorRepeatMove baseActorRepeatMove = new BaseActorRepeatMove(-w,0,w,h,uiStage);
        //baseActorRepeatMove.loadTexture("castle/castle03_sky.png",w*2,h);
        uiTable.addActor(baseActorRepeatMove);

        //castleSky.loadTexture( "castle/castle03_sky.png",w*2,h );
         //castle.setSize(800,600);
        //uiTable.addActor(castleSky);
        //Action moveAction = Actions.sequence(Actions.moveBy(0.3f,0),Actions.delay(0.05f));
        //castleSky.addAction( Actions.forever(moveAction) );

        BaseActor castle = new BaseActor(0,0, uiStage,Touchable.disabled);
        castle.loadTexture( "castle/castle02.png",w,h );
       // castle.setSize(800,600);
        uiTable.addActor(castle);



       // BaseActor title = new BaseActor(0,0, mainStage, Touchable.disabled);
       // title.loadTexture( "assets/starfish-collector.png" );

        TextButton startButton = new TextButton( "Start", BaseGame.textButtonStyle );
        // startButton.setPosition(150,150);
        // uiStage.addActor(startButton);

        startButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                CradleGame.setActiveScreen(new GameMapScreen(new ScreenGamePlay()));
                return true;
            }
        });


        TextButton quitButton = new TextButton( "Quit", BaseGame.textButtonStyle );
        // quitButton.setPosition(500,150);
        // uiStage.addActor(quitButton);

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
        uiTable.row();
        uiTable.add(startButton);
        uiTable.add(quitButton);






    }

    public void update(float dt)
    {

    }

    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
            CradleGame.setActiveScreen( new GameMapScreen(new ScreenGamePlay()));
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        return false;
    }


}

