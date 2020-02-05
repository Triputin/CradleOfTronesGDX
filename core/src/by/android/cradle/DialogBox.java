package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class DialogBox extends BaseActor
{
    private Label dialogLabel;
    private float padding = 42;
    private final CradleGame  cradleGame;
    TextButton okButton;

    public DialogBox(float x, float y, Stage s, int width, int height,CradleGame cradleGame1)
    {
        super(x,y,s, Touchable.enabled);
        this.cradleGame = cradleGame1;
        loadTexture( "goldenframe.png",width,height );
        AddImage("fon_white.png",Math.round(width*0.075f),Math.round(height*0.1f),Math.round(width*0.85f), Math.round(height*0.8f));

        dialogLabel = new Label(" ", BaseGame.labelStyle);
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment( Align.topLeft );
        dialogLabel.setPosition( Math.round(width*0.075f), Math.round(height*0.1f) );
        this.setSize(width, height);
        dialogLabel.setWidth( Math.round(width*0.85f) );
        dialogLabel.setHeight( Math.round(height*0.8f));

        this.addActor(dialogLabel);

        //Ok Button
        String ms = cradleGame.getLanguageStrings().get("ok");
        okButton = new TextButton( ms, BaseGame.textButtonStyle );
        okButton.setPosition(Math.round(getWidth()/2-okButton.getWidth()/2),padding);


    }

    public void setText(String text)
    {  dialogLabel.setText(text);  }

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

    public void alignTop()
    {  dialogLabel.setAlignment( Align.top );  }

    public void showForTime(int seconds,Action completeAction ){
        Action actions = sequence(Actions.scaleTo(0,0,0.01f),fadeIn(0.01f),Actions.scaleTo(1,1,1.0f), Actions.delay(seconds) ,fadeOut(1f), completeAction);

        this.setVisible(true);
        this.addAction( actions );

    }

    public void showWithOkButton(InputListener inputListener){
        addActor(okButton);
        okButton.addListener(inputListener);

        Action actions = sequence(Actions.scaleTo(0,0,0.01f),fadeIn(0.01f),Actions.scaleTo(1,1,0.5f));

        this.setVisible(true);
        this.addAction( actions );


    }
}