package by.android.cradle;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Align;

public class HelpScreen extends BaseScreen {
    private Music instrumental;
    private float audioVolume;
    private BaseActor slide1;
    private int slideNumber;
    private Label infoLabel;
    private DialogBox dialogBox;
    private float padding = 16;
    private int numberOfSlides = 6;

    public HelpScreen(CradleGame cradleGame) {
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

        instrumental = Gdx.audio.newMusic(Gdx.files.internal("sounds/2_hearts.mp3"));
        audioVolume = 0.7f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        //instrumental.play();

        slideNumber=1;
        final int w = Gdx.graphics.getWidth();
        final int h = Gdx.graphics.getHeight();

        //Fon
        BaseActor fon = new BaseActor(0,0,uiStage,Touchable.disabled);
        fon.loadTexture("castle/castle04.png",w,h);
        uiStage.addActor(fon);



        //Menu Button
        TextButton menuButton = new TextButton( "Menu", BaseGame.textButtonStyle );
        menuButton.setPosition(w*0.00f,h*0.88f);
        uiStage.addActor(menuButton);

        menuButton.addListener(new InputListener() {
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

        //Next Button
        TextButton nextButton = new TextButton( "Next", BaseGame.textButtonStyle );
        nextButton.setPosition(w*0.8f,h*0.2f);
        uiStage.addActor(nextButton);

        nextButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                if (slideNumber<numberOfSlides) {
                    slideNumber++;
                    SetUpSlide(slideNumber, w, h);
                }
                return true;
            }
        });

        //Prev Button
        TextButton prevButton = new TextButton( "Previous", BaseGame.textButtonStyle );
        prevButton.setPosition(w*0.5f,h*0.2f);
        uiStage.addActor(prevButton);

        prevButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                if (slideNumber>1) {
                    slideNumber--;
                    SetUpSlide(slideNumber, w, h);
                }
                return true;
            }
        });

/*
        infoLabel = new Label(" ", BaseGame.labelStyle);
        infoLabel.setWrap(true);
        infoLabel.setAlignment( Align.topLeft );
        infoLabel.setPosition( padding, (int) Math.round(h*0.4));
        uiStage.addActor(infoLabel);
*/
        dialogBox = new DialogBox(padding, 0,uiStage);
        dialogBox.setDialogSize(w/2,(int) Math.round(h*0.7));
        dialogBox.setFontScale(1.8f);
        //dialogBox.setBackgroundColor(Color.ORANGE);
        SetUpSlide(1,w,h);

    }

    public void SetUpSlide(int slideNumber,int w,int h){

        if (slide1!=null){
            slide1.remove();
        }

        int x = (int) w/2;
        int y= (int) Math.round(h*0.4);
       switch(slideNumber){

           case 1:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im01.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               dialogBox.setText("Choose a castle to attack. If you have enough resources you will be able to attack by pressing the Attack button.");
               break;
           case 2:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im02.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               dialogBox.setText("If you don't have enough resources you can attack an Arena by pressing the Attack button.");
               break;
           case 3:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im03.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               dialogBox.setText("Match 3 or more tiles of same kind by clicking on adjacent pieces and drag the selection.");
               break;
           case 4:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im04.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               dialogBox.setText("Orange plaques are below some of the tiles. Make chains over them to destroy them and finish the attack.");
               break;
           case 5:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im04.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               dialogBox.setText("Some of tiles are hidden in wooden cases. Collect tiles near them to destroy them.");
               break;
           case 6:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im05.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               dialogBox.setText("Please note, there are special silver coins all around. You can use them to make chains with items of any kind.");
               break;
       }
    }


    public void update(float dt)
    {

    }


}

