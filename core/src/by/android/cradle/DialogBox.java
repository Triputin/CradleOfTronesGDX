package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

public class DialogBox extends BaseActor
{
    private Label dialogLabel;
    private float padding = 16;
    private BaseActor fon;

    public DialogBox(float x, float y, Stage s)
    {
        super(x,y,s, Touchable.disabled);

        dialogLabel = new Label(" ", BaseGame.labelStyle);
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment( Align.topLeft );
        dialogLabel.setPosition( padding, padding );
        /*
        fon = new BaseActor(0,0,s,Touchable.disabled);
        fon.setX(0);
        fon.setY(0);
        this.addActor(fon);
        */

        this.setDialogSize( getWidth(), getHeight() );
        //loadTexture("fon_orange.png",(int) getWidth(),(int) getHeight());

        this.addActor(dialogLabel);

    }

    public void setDialogSize(float width, float height)
    {
        this.setSize(width, height);
        dialogLabel.setWidth( width - 2 * padding );
        dialogLabel.setHeight( height - 2 * padding );
        //fon.loadTexture("fon_orange.png",(int) width,(int) height);
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
}