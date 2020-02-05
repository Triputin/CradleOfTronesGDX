package by.android.cradle;

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
    private final CradleGame  cradleGame;
    private TextButton okButton;
    private Label scoreLabel;
    private Label goldLabel;
    private Label woodLabel;
    private Label breadLabel;

    public DialogBox_EndLevel(float x, float y, Stage s, int width, int height, CradleGame cradleGame1)
    {
        super(x,y,s, Touchable.enabled);
        this.cradleGame = cradleGame1;
        this.setSize(width, height);
        AddImage("fon_white2.png",0,0,Math.round(width), Math.round(height));
        //loadTexture( "goldenframe.png",width,height );
        AddImage("goldenframe.png",0,0,width,height);
        //AddImage("fon_white.png",Math.round(width*0.075f),Math.round(height*0.1f),Math.round(width*0.85f), Math.round(height*0.8f));


        String ms = cradleGame.getLanguageStrings().get("levelresults");
        dialogLabel = new Label(ms, BaseGame.labelStyle);
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment( Align.center );
        dialogLabel.setPosition( Math.round(width*0.1f), Math.round(height*0.8f) );
        dialogLabel.setWidth( width - 2 * padding );
        dialogLabel.setFontScale(1.5f);
        dialogLabel.setColor(Color.GOLD);
        this.addActor(dialogLabel);

        //results score
        int pictSize = Math.round(height*0.09f);
        int pict1posX = Math.round(width*0.25f);
        int pict1posY = Math.round(height*0.6f);
        AddImage("goldcup.png",pict1posX,pict1posY,pictSize,pictSize);
        scoreLabel = new Label("42", BaseGame.labelStyle);
        //pict1Label.setAlignment(Align.left,Align.center);
        scoreLabel.setFontScale(1.3f);
        scoreLabel.setPosition(Math.round(width*0.7f),pict1posY+pictSize*.25f);
        this.addActor(scoreLabel);

        //results gold
        pict1posX = Math.round(width*0.25f);
        pict1posY = Math.round(height*0.5f);
        AddImage("coin2.png",pict1posX,pict1posY,pictSize,pictSize);
        goldLabel = new Label("43", BaseGame.labelStyle);
        goldLabel.setFontScale(1.3f);
        goldLabel.setPosition(Math.round(width*0.7f),pict1posY+pictSize*.25f);
        this.addActor(goldLabel);

        //results wood
        pict1posX = Math.round(width*0.25f);
        pict1posY = Math.round(height*0.4f);
        AddImage("wood.png",pict1posX,pict1posY,pictSize,pictSize);
        woodLabel = new Label("42", BaseGame.labelStyle);
        woodLabel.setFontScale(1.3f);
        woodLabel.setPosition(Math.round(width*0.7f),pict1posY+pictSize*.25f);
        this.addActor(woodLabel);

        //results bread
        pict1posX = Math.round(width*0.25f);
        pict1posY = Math.round(height*0.3f);
        AddImage("bread.png",pict1posX,pict1posY,pictSize,pictSize);
        breadLabel = new Label("42", BaseGame.labelStyle);
        breadLabel.setFontScale(1.3f);
        breadLabel.setPosition(Math.round(width*0.7f),pict1posY+pictSize*.25f);
        this.addActor(breadLabel);

        //Ok Button
        ms = cradleGame.getLanguageStrings().get("ok");
        okButton = new TextButton( ms, BaseGame.textButtonStyle );
        okButton.setPosition(Math.round(getWidth()/2-okButton.getWidth()/2),padding*1.5f);


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

    public void showForTime(int seconds, Action completeAction ){
        Action actions = sequence(Actions.scaleTo(0,0,0.01f),fadeIn(0.01f),Actions.scaleTo(1,1,2.0f), Actions.delay(seconds) ,fadeOut(1f), completeAction);

        this.setVisible(true);
        this.addAction( actions );

    }

    public void showWithOkButton(InputListener inputListener){
        addActor(okButton);
        okButton.clearListeners(); //!!!!
        okButton.addListener(inputListener);

        Action actions = sequence(Actions.scaleTo(1,0,0.01f),fadeIn(0.01f),Actions.scaleTo(1,1,0.5f));

        this.setVisible(true);
        this.addAction( actions );


    }
}
