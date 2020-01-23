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
    private int numberOfSlides = 10;

    public HelpScreen(CradleGame cradleGame,IPlayServices ply) {
        super(cradleGame,ply);
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
        String s = cradleGame.getLanguageStrings().get("menu");
        TextButton menuButton = new TextButton( s, BaseGame.textButtonStyle );
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
        s = cradleGame.getLanguageStrings().get("next");
        TextButton nextButton = new TextButton( s, BaseGame.textButtonStyle );
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
        s = cradleGame.getLanguageStrings().get("previous");
        TextButton prevButton = new TextButton( s, BaseGame.textButtonStyle );
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
        String s;
       switch(slideNumber){

           case 1:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im01.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help01_text");
               dialogBox.setText(s);
               break;
           case 2:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im02.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help02_text");
               dialogBox.setText(s);
               break;
           case 3:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im03.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help03_text");
               dialogBox.setText(s);
               break;
           case 4:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im04.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help04_text");
               dialogBox.setText(s);
               break;
           case 5:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im04.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help05_text");
               dialogBox.setText(s);
               break;
           case 6:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im05.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help06_text");
               dialogBox.setText(s);
               break;
           case 7:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im06.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help07_text");
               dialogBox.setText(s);
               break;
           case 8:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im06.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help08_text");
               dialogBox.setText(s);
               break;
           case 9:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im06.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help09_text");
               dialogBox.setText(s);
               break;
           case 10:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled);
               slide1.loadTexture("helpimages/im06.png",(int) Math.round(w*0.5),(int) Math.round(h*0.6));
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help10_text");
               dialogBox.setText(s);
               break;
       }
    }


    public void update(float dt)
    {

    }


}

