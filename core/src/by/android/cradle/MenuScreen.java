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

    private final String TAG = "MenuScreen";
    private boolean showDailyGift;
    private String dailyGiftType;
    private int dailyGiftQtty;
    private DialogBox_DailyGift dialogBox_dailyGift;

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


       // String s = cradleGame.getLanguageStrings().get("start");
        //TextButton startButton = new TextButton( "   "+s+"   " , BaseGame.textButtonStyle );

        //Button Start
        Button.ButtonStyle buttonStylesSt = new Button.ButtonStyle();
        String localeString= java.util.Locale.getDefault().getLanguage().toString();
        Texture buttonTexsSt;
        if (localeString=="ru"){
            buttonTexsSt = new Texture( Gdx.files.internal("shield_play06.png") );
        }else {
            buttonTexsSt = new Texture( Gdx.files.internal("shield_play05.png") );
        }

        TextureRegion buttonRegionsSt =  new TextureRegion(buttonTexsSt);
        buttonStylesSt.up = new TextureRegionDrawable( buttonRegionsSt );


        Button startButton = new Button( buttonStylesSt );
        startButton.setSize(h*0.55f,h*0.4f);
        startButton.setPosition(w*0.56f-startButton.getWidth()/2f,h*0.1f);
        uiStage.addActor(startButton);
        startButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                instrumental.pause();
                cradleGame.setActiveGameMapScreen(false);
                return true;
            }
        });

        /*
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

*/

/*
        s = cradleGame.getLanguageStrings().get("signin");
        final TextButton signInButton = new TextButton( ""+s+"", BaseGame.textButtonStyle );

  */

/*
        s = cradleGame.getLanguageStrings().get("scores_table");
        final TextButton scoreButton = new TextButton( ""+s+"", BaseGame.textButtonStyle );
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

*/
        //LeaderBoard button
        Button.ButtonStyle buttonStyles = new Button.ButtonStyle();

        Texture buttonTexs = new Texture( Gdx.files.internal("leaderboard_button.png") );
        TextureRegion buttonRegions =  new TextureRegion(buttonTexs);
        buttonStyles.up = new TextureRegionDrawable( buttonRegions );


        Button leaderBoardButton = new Button( buttonStyles );
        leaderBoardButton.setSize(h*0.21f,h*0.21f);
        leaderBoardButton.setPosition(w-leaderBoardButton.getWidth()-10,h*0.75f);
        uiStage.addActor(leaderBoardButton);



        leaderBoardButton.addListener(new InputListener() {
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

        //Help button
        Button.ButtonStyle buttonStylesH = new Button.ButtonStyle();

        Texture buttonTexsH = new Texture( Gdx.files.internal("help_button.png") );
        TextureRegion buttonRegionsH =  new TextureRegion(buttonTexsH);
        buttonStylesH.up = new TextureRegionDrawable( buttonRegionsH );


        Button helpButton = new Button( buttonStylesH );
        helpButton.setSize(h*0.22f,h*0.22f);
        helpButton.setPosition(w-helpButton.getWidth()-10,h*0.03f);
        uiStage.addActor(helpButton);



        helpButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent)) return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown)) return false;

                instrumental.pause();
                cradleGame.setActiveHelpScreen();

                return true;
            }
        });

/*
        //Sign in button
        Button.ButtonStyle buttonStyles = new Button.ButtonStyle();

        Texture buttonTexs = new Texture( Gdx.files.internal("signin_button.png") );
        TextureRegion buttonRegions =  new TextureRegion(buttonTexs);
        buttonStyles.up = new TextureRegionDrawable( buttonRegions );


        Button signInButton = new Button( buttonStyles );
        signInButton.setSize(h*0.25f,h*0.25f);
        signInButton.setPosition(w-signInButton.getWidth()-10,10);
        uiStage.addActor(signInButton);



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

*/



        //uiTable.add(title).colspan(2);
        uiTable.row();
        //uiTable.add(startButton);
        uiTable.row();
        /*
        uiStage.addActor(signInButton);
        signInButton.setX(0);
        signInButton.setY(h-signInButton.getHeight());
        uiTable.row();
        */
        //uiTable.add(scoreButton);
       // if(ply.isSignedIn()) {
            //signInButton.setVisible(false);
            //scoreButton.setVisible(true);

        //}
       // uiTable.row();
       // uiTable.add(helpButton);
       // uiTable.row();
       // uiTable.add(quitButton);


        //Settings button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("settings_button.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button settingsButton = new Button( buttonStyle );
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



        int dialogSize = Math.round(h*0.8f);
        dialogBox_dailyGift = new DialogBox_DailyGift(w/2-dialogSize/2,h/2-dialogSize/2,uiStage,dialogSize,dialogSize,cradleGame);
        dialogBox_dailyGift.setVisible(false);

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

    public boolean isShowDailyGift() {
        return showDailyGift;
    }

    public void setShowDailyGift(boolean showDailyGift, String dailyGiftType, int resQtty) {
        this.showDailyGift = showDailyGift;
        this.dailyGiftType = dailyGiftType;
        this.dailyGiftQtty = resQtty;

        InputListener inputListener3 =new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                dialogBox_dailyGift.setVisible(false);
                return true;
            }
        };
        dialogBox_dailyGift.setGift(dailyGiftType,resQtty);

        dialogBox_dailyGift.showWithOkButton(inputListener3);
    }
}

