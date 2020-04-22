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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class HelpScreen extends BaseScreen {
    private BaseActor slide1;
    private int slideNumber;
    private Label infoLabel;
    private DialogBox dialogBox;
    private float padding = 16;
    private int numberOfSlides = 13;

    public HelpScreen(CradleGame cradleGame,IPlayServices ply) {
        super(cradleGame,ply);
    }


    public void initialize()
    {

        //instrumental = Gdx.audio.newMusic(Gdx.files.internal("sounds/2_hearts.mp3"));
        instrumental = cradleGame.getCradleAssetManager().manager.get(Assets.MUSIC_2_HEARTS);
        audioVolume = 0.7f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        //instrumental.play();

        slideNumber=1;
        final int w = Gdx.graphics.getWidth();
        final int h = Gdx.graphics.getHeight();

        //Fon
        BaseActor fon = new BaseActor(0,0,uiStage,Touchable.disabled,cradleGame);
        fon.loadTexture("castle/castle04.png",w,h);
        uiStage.addActor(fon);



        //Menu button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTex = new Texture( Gdx.files.internal("back_button02.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button backButton = new Button( buttonStyle );
        backButton.setSize(h*0.21f,h*0.21f);
        backButton.setPosition(10,h*0.00f);
        uiStage.addActor(backButton);

        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    instrumental.pause();
                    cradleGame.setActiveMenuScreen();
                    return true;
                }
                return false;
            }
        });


        //Next Button
        Button.ButtonStyle buttonStyleN = new Button.ButtonStyle();
        Texture buttonTexN = new Texture( Gdx.files.internal("forward_button.png") );
        TextureRegion buttonRegionN =  new TextureRegion(buttonTexN);
        buttonStyleN.up = new TextureRegionDrawable( buttonRegionN );

        Button nextButton = new Button( buttonStyleN );
        nextButton.setSize(h*0.2f,h*0.2f);
        nextButton.setPosition(w*0.85f,h*0.02f);
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
        Button.ButtonStyle buttonStyleP = new Button.ButtonStyle();
        Texture buttonTexP = new Texture( Gdx.files.internal("backward_button.png") );
        TextureRegion buttonRegionP =  new TextureRegion(buttonTexP);
        buttonStyleP.up = new TextureRegionDrawable( buttonRegionP );

        Button prevButton = new Button( buttonStyleP );
        prevButton.setSize(h*0.2f,h*0.2f);
        prevButton.setPosition(w*0.6f,h*0.02f);
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
        dialogBox = new DialogBox(padding, h*0.2f,uiStage,w/2,(int) Math.round(h*0.8f),cradleGame);
        //dialogBox.setDialogSize(w/2,(int) Math.round(h*0.8));
        dialogBox.setFontScale(1.8f);
        dialogBox.alignTopLeft();
        //dialogBox.setBackgroundColor(Color.ORANGE);
        SetUpSlide(1,w,h);

    }

    public void SetUpSlide(int slideNumber,int w,int h){

        if (slide1!=null){
            slide1.remove();
        }

        int x = (int)  Math.round(w/1.9f);
        int y= (int) Math.round(h*0.33);
        int width = (int) Math.round(w*0.5);
        int height = (int) Math.round(h*0.6);

        float fontScale = 1.0f;
        if (w>1000){
            fontScale = 1.8f;
        } else {
            fontScale = 0.9f;
        }
        String s;
       switch(slideNumber){

           case 1:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im01.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help01_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 2:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im02.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);

               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help02_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 3:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im03.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help03_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 4:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im04.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help04_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 5:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im04.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help05_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 6:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im05.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help06_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 7:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im06.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help07_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 8:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im06.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help08_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 9:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im06.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help09_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 10:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im06.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help10_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 11:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im07.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help11_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 12:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im08.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help12_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
           case 13:
               slide1 = new BaseActor(x,y,uiStage,Touchable.disabled,cradleGame);
               slide1.loadTexture("helpimages/im09.png", Math.round(width*0.9f), Math.round(height*0.9f));
               slide1.AddImage("goldenframe01.png",-Math.round(width*0.05f),-Math.round(height*0.05f),width,height);
               uiStage.addActor(slide1);
               s = cradleGame.getLanguageStrings().get("help13_text");
               dialogBox.setText(s);
               dialogBox.setFontScale(fontScale);
               break;
       }
    }


    public void update(float dt)
    {

    }


}

