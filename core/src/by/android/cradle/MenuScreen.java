package by.android.cradle;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class MenuScreen extends BaseScreen {

    static final String TAG = "MenuScreen";

    public MenuScreen(CradleGame cradleGame,IPlayServices ply) {
        super(cradleGame,ply);
    }


    public void initialize()
    {

        instrumental = Gdx.audio.newMusic(Gdx.files.internal("sounds/2_hearts.mp3"));
        audioVolume = 0.7f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        PlayMusic();


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


        String s = cradleGame.getLanguageStrings().get("start");
        TextButton startButton = new TextButton( "   "+s+"   " , BaseGame.textButtonStyle );
        startButton.setScale(2,2);

        startButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                //ply.submitScore(cradleGame.leaderboard,100);
                instrumental.pause();
                cradleGame.setActiveGameMapScreen(false);
                return true;
            }
        });

        s = cradleGame.getLanguageStrings().get("help");
        TextButton helpButton = new TextButton( "   "+s+"   ", BaseGame.textButtonStyle );
        // startButton.setPosition(150,150);
        // uiStage.addActor(startButton);

        helpButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                instrumental.pause();
                cradleGame.setActiveHelpScreen();
                return true;
            }
        });

        /*
        s = cradleGame.getLanguageStrings().get("quit");
        TextButton quitButton = new TextButton( "   "+s+"   ", BaseGame.textButtonStyle );
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
*/



        s = cradleGame.getLanguageStrings().get("signin");
        final TextButton signInButton = new TextButton( ""+s+"", BaseGame.textButtonStyle );
        s = cradleGame.getLanguageStrings().get("scores_table");
        final TextButton scoreButton = new TextButton( ""+s+"", BaseGame.textButtonStyle );

        signInButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent)) return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown)) return false;

                //GPS second try
                if(ply.isSignedIn()) {
                    GdxLog.d(TAG,"Already SignedIn Google PlayServices");
                }
                else {
                    ply.onStartMethod();
                    ply.signIn();
                    GdxLog.d(TAG,"SignedIn Google PlayServices");
                   // signInButton.setVisible(false);
                   // scoreButton.setVisible(true);
                }

                return true;
            }
        });


        scoreButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent)) return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown)) return false;

                if(ply.isSignedIn()) {
                    ply.showScore(cradleGame.leaderboard);
                    //GdxLog.d(TAG,"SignedIn Google PlayServices");
                }

                return true;
            }
        });

        //uiTable.add(title).colspan(2);
        uiTable.row();
        uiTable.add(startButton);
        uiTable.row();
        uiStage.addActor(signInButton);
        signInButton.setX(0);
        signInButton.setY(h-signInButton.getHeight());
        uiTable.row();
        uiTable.add(scoreButton);
        if(ply.isSignedIn()) {
            signInButton.setVisible(false);
            //scoreButton.setVisible(true);

        }
        uiTable.row();
        uiTable.add(helpButton);
        uiTable.row();
       // uiTable.add(quitButton);


        //Settings button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("settings_button.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button settingsButton = new Button( buttonStyle );
        //restartButton.setColor( Color.CYAN );
        settingsButton.setPosition(10,10);
        settingsButton.setSize(h*0.25f,h*0.25f);
        uiStage.addActor(settingsButton);

        settingsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    cradleGame.setActiveSettingsScreen();
                }
                return false;
            }
        });


    }

    public void update(float dt)
    {

    }

    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER))

        cradleGame.setActiveGameMapScreen(false);
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        return false;
    }


}

