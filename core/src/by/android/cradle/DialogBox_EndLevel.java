package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class DialogBox_EndLevel  extends BaseActor{

    private Label dialogLabel;
    private float padding = 42;
    private TextButton okButton;
    private TextButton videoButton;
    private Label watchVideoLabel;

    private Label scoreLabel;
    private Label goldLabel;
    private Label woodLabel;
    private Label breadLabel;

    public DialogBox_EndLevel(float x, float y, Stage s, int width, int height, CradleGame cradleGame1)
    {
        super(x,y,s, Touchable.enabled,cradleGame1);
        this.setSize(width, height);
        AddImage("fon_white2.png",Math.round(width*0.03f),Math.round(height*0.075f),Math.round(width*.95f), Math.round(height*0.85f));
        //loadTexture( "goldenframe.png",width,height );
        AddImage("goldenframe01.png",0,0,width,height);
        //AddImage("fon_white.png",Math.round(width*0.075f),Math.round(height*0.1f),Math.round(width*0.85f), Math.round(height*0.8f));

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        String ms = cradleGame.getLanguageStrings().get("levelresults");
        dialogLabel = new Label(ms, BaseGame.labelStyle_Middle);
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment( Align.center );
        dialogLabel.setPosition( Math.round(width*0.077f), Math.round(height*0.81f) );
        dialogLabel.setWidth( width - 2 * padding );
        //dialogLabel.setFontScale(fontScale);
        dialogLabel.setColor(Color.GOLD);
        this.addActor(dialogLabel);

        //results score
        int pictSize = Math.round(height*0.15f);
        int pict1posX = Math.round(width*0.2f);
        int pict1posY = Math.round(height*0.63f);
        AddImage("goldcup.png",pict1posX,pict1posY,pictSize,pictSize);
        scoreLabel = new Label("42", BaseGame.labelStyle_Middle);
        //pict1Label.setAlignment(Align.left,Align.center);
        //scoreLabel.setFontScale(fontScale);
        scoreLabel.setPosition(Math.round(width*0.7f),pict1posY+pictSize*.3f);
        this.addActor(scoreLabel);

        //results gold
        pict1posX = Math.round(width*0.2f);
        pict1posY = Math.round(height*0.5f);
        AddImage("coin2.png",pict1posX,pict1posY,pictSize,pictSize);
        goldLabel = new Label("43", BaseGame.labelStyle_Middle);
        //goldLabel.setFontScale(fontScale);
        goldLabel.setPosition(Math.round(width*0.7f),pict1posY+pictSize*.25f);
        this.addActor(goldLabel);

        //results wood
        pict1posX = Math.round(width*0.2f);
        pict1posY = Math.round(height*0.38f);
        AddImage("wood.png",pict1posX,pict1posY,pictSize,pictSize);
        woodLabel = new Label("42", BaseGame.labelStyle_Middle);
        //woodLabel.setFontScale(fontScale);
        woodLabel.setPosition(Math.round(width*0.7f),pict1posY+pictSize*.25f);
        this.addActor(woodLabel);

        //results bread
        pict1posX = Math.round(width*0.2f);
        pict1posY = Math.round(height*0.28f);
        AddImage("bread.png",pict1posX,pict1posY,pictSize,pictSize);
        breadLabel = new Label("42", BaseGame.labelStyle_Middle);
        //breadLabel.setFontScale(fontScale);
        breadLabel.setPosition(Math.round(width*0.7f),pict1posY+pictSize*.25f);
        this.addActor(breadLabel);

        //Watch video label
        ms = cradleGame.getLanguageStrings().get("doubleattackresults");
        watchVideoLabel = new Label(ms, BaseGame.labelStyle_SuperSmall);
        watchVideoLabel.setWrap(true);
        watchVideoLabel.setAlignment( Align.center );
        watchVideoLabel.setPosition( Math.round(width*0.04f), Math.round(height*0.2f) );
        watchVideoLabel.setWidth( width *0.45f );
        watchVideoLabel.setColor(Color.GOLD);
        this.addActor(watchVideoLabel);

        //Watch video Button
        ms = cradleGame.getLanguageStrings().get("watchads");
        videoButton = new TextButton( ms, BaseGame.textButtonStyle );

        if (w<1000) {
            videoButton.setWidth(videoButton.getWidth()*0.8f);
            videoButton.setHeight(videoButton.getHeight()*0.8f);
        }
        videoButton.setPosition(Math.round(getWidth()/4-videoButton.getWidth()/2),Math.round(height*0.07f));


        //Ok Button
        ms = cradleGame.getLanguageStrings().get("ok");
        okButton = new TextButton( ms, BaseGame.textButtonStyle );
        if (w<1000) {
            okButton.setWidth(okButton.getWidth()*0.8f);
            okButton.setHeight(okButton.getHeight()*0.8f);
        }
        okButton.setPosition(Math.round(getWidth()/4*3-okButton.getWidth()/2),Math.round(height*0.07f));
        this.addActor(okButton);

    }

    public void setResults(int score, int gold, int wood, int bread)
    {
       scoreLabel.setText(score);
       goldLabel.setText(gold);
       woodLabel.setText(wood);
       breadLabel.setText(bread);
    }

    public void setFontScale(float scale)
    {  dialogLabel.setFontScale(scale);  }

    public void setFontColor(Color color)
    {  dialogLabel.setColor(color);  }

    public void setBackgroundColor(Color color)
    {  this.setColor(color); }

    public void alignTopLeft()
    {  dialogLabel.setAlignment( Align.topLeft );  }

    public void alignCenter()
    {  dialogLabel.setAlignment( Align.center );  }
/*
    public void showForTime(int seconds, Action completeAction ){
        Action actions = sequence(Actions.scaleTo(0,0,0.01f),fadeIn(0.01f),Actions.scaleTo(1,1,2.0f), Actions.delay(seconds) ,fadeOut(1f), completeAction);

        this.setVisible(true);
        this.addAction( actions );

    }
*/

    public void showWithOkButton(InputListener inputListenerOK, InputListener inputListenerAds){
        cradleGame.getScreenGamePlay().setEndLevelDialogActive(true);
        videoButton.setVisible(true);
        watchVideoLabel.setVisible(true);
        addActor(okButton);
        okButton.clearListeners(); //!!!!
        okButton.addListener(inputListenerOK);
        addActor(videoButton);
        videoButton.clearListeners(); //!!!!
        videoButton.addListener(inputListenerAds);


        Action actions = sequence(Actions.scaleTo(1,0,0.01f),fadeIn(0.01f),Actions.scaleTo(1,1,0.5f));

        this.setVisible(true);
        this.addAction( actions );


    }

    public void hideWatchAdButton(){
        videoButton.setVisible(false);
        watchVideoLabel.setVisible(false);
    }


}
