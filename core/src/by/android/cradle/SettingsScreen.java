package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;

public class SettingsScreen extends BaseScreen {



    static final String TAG = "SettingsScreen";

    public SettingsScreen(CradleGame cradleGame, IPlayServices ply) {
        super(cradleGame, ply);
    }


    public void initialize() {

        instrumental = Gdx.audio.newMusic(Gdx.files.internal("sounds/2_hearts.mp3"));
        audioVolume = 0.7f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        PauseMusic();


        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();


        //Close button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("close_button.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button closeButton = new Button( buttonStyle );
        closeButton.setSize(h*0.15f,h*0.15f);
        closeButton.setPosition(10,h-10-closeButton.getHeight());
        uiStage.addActor(closeButton);

        closeButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    cradleGame.setActiveMenuScreen();
                }
                return false;
            }
        });


        String s = cradleGame.getLanguageStrings().get("restart");
        TextButton restartButton = new TextButton( "   "+s+"   ", BaseGame.textButtonStyle );

        restartButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent)) return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown)) return false;
                ShowRestartDialog();

                return true;
            }
        });


       // uiTable.row();
        //uiTable.add(restartButton);
        uiStage.addActor(restartButton);
        restartButton.setPosition(w/2f,h/3f, Align.center);

        //Music button
        buttonStyle = new Button.ButtonStyle();
        Texture buttonTex1 = new Texture(Gdx.files.internal("music_on_button.png"));
        Texture buttonTex2 = new Texture(Gdx.files.internal("music_off_button.png"));
        TextureRegion buttonRegion1 =  new TextureRegion(buttonTex1);
        TextureRegion buttonRegion2 =  new TextureRegion(buttonTex2);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion1 );
        buttonStyle.checked = new TextureRegionDrawable( buttonRegion2 );

        final Button musicButton = new Button( buttonStyle );
        musicButton.setSize(h*0.25f,h*0.25f);
        musicButton.setPosition(w*0.3f,h-200-closeButton.getHeight());
        if (cradleGame.isMusicOn()) {
            musicButton.setChecked(false);
        }else {
            musicButton.setChecked(true);
        }

        uiStage.addActor(musicButton);


        musicButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    cradleGame.setMusicOn(!cradleGame.isMusicOn());
                    PlayMusic();
                    if (cradleGame.isMusicOn()) {
                        //soundButton.setChecked(false);
                        System.out.println("musicOn");
                    }else {
                        //soundButton.setChecked(true);
                        System.out.println("musicOff");
                    }
                }
                return false;
            }
        });




        //Sound button
        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();
        Texture buttonTex3 = new Texture(Gdx.files.internal("sound_on_button02.png"));
        Texture buttonTex4 = new Texture(Gdx.files.internal("sound_off_button02.png"));
        TextureRegion buttonRegion3 =  new TextureRegion(buttonTex3);
        TextureRegion buttonRegion4 =  new TextureRegion(buttonTex4);
        buttonStyle2.up = new TextureRegionDrawable( buttonRegion3 );
        buttonStyle2.checked = new TextureRegionDrawable( buttonRegion4 );

        final Button soundButton = new Button( buttonStyle2 );
        soundButton.setSize(h*0.25f,h*0.25f);
        soundButton.setPosition(w*0.55f,h-200-closeButton.getHeight());
        if (cradleGame.isSoundOn()) {
            soundButton.setChecked(false);
        }else {
            soundButton.setChecked(true);
        }

        uiStage.addActor(soundButton);


        soundButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    cradleGame.setSoundOn(!cradleGame.isSoundOn());
                    if (cradleGame.isSoundOn()) {
                        //soundButton.setChecked(false);
                        System.out.println("soundOn");
                    }else {
                        //soundButton.setChecked(true);
                        System.out.println("soundOff");
                    }
                }
                return false;
            }
        });
    }

    public void update(float dt)
    {

    }


    public void ShowRestartDialog(){
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        String s = cradleGame.getLanguageStrings().get("information");
        Dialog dialog = new Dialog(s, BaseGame.skin, "dialog") {
            public void result(Object obj) {
                if (obj.toString()=="true"){
                cradleGame.restartGame();
                    System.out.println("Game restarted");
                }
            }
        };
        s = cradleGame.getLanguageStrings().get("are_you_shure_restart_game");
        dialog.text(s);
        s = cradleGame.getLanguageStrings().get("ok");
        dialog.button(s, true); //sends "true" as the result
        s = cradleGame.getLanguageStrings().get("cancel");
        dialog.button(s, false); //sends "false" as the result
        dialog.setSize(h*0.4f,h*0.4f); //don't work
        dialog.show(uiStage);

    }

}
