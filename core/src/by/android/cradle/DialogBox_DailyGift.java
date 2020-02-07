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
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class DialogBox_DailyGift extends BaseActor {

    private Label dialogLabel;
    private int padding = 42;
    private final CradleGame  cradleGame;
    private TextButton okButton;
    private Label scoreLabel;

    public DialogBox_DailyGift(float x, float y, Stage s, int width, int height, CradleGame cradleGame1)
    {
        super(x,y,s, Touchable.enabled);
        this.cradleGame = cradleGame1;
        this.setSize(width, height);
        AddImage("fon_white2.png",Math.round(width*0.04f),Math.round(height*0.075f),Math.round(width*0.92f), Math.round(height*0.85f));
        AddImage("goldenframe01.png",0,0,width,height);

        String ms = cradleGame.getLanguageStrings().get("dayily_gift_text");
        dialogLabel = new Label(ms, BaseGame.labelStyle);
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment( Align.center );
        dialogLabel.setPosition( Math.round(width*0.07f), Math.round(height*0.8f) );
        dialogLabel.setWidth( width - 2 * padding );
        dialogLabel.setFontScale(1.5f);
        dialogLabel.setColor(Color.GOLD);
        this.addActor(dialogLabel);


        int pictSize = Math.round(getHeight() * 0.25f);
        int pict1posX = Math.round(getWidth() * 0.25f);
        int pict1posY = Math.round(getHeight() * 0.4f);

        scoreLabel = new Label("0", BaseGame.labelStyle);
        //pict1Label.setAlignment(Align.left,Align.center);
        scoreLabel.setFontScale(1.6f);
        scoreLabel.setPosition(Math.round(getWidth()*0.6f),pict1posY+pictSize*.4f);
        this.addActor(scoreLabel);


        //Ok Button
        ms = cradleGame.getLanguageStrings().get("ok");
        okButton = new TextButton( ms, BaseGame.textButtonStyle );
        okButton.setPosition(Math.round(getWidth()/2-okButton.getWidth()/2),padding*1.5f);

    }


    public void setGift(String resType,int resQtty) {
        int pictSize = Math.round(getHeight() * 0.25f);
        int pict1posX = Math.round(getWidth() * 0.25f);
        int pict1posY = Math.round(getHeight() * 0.4f);

        scoreLabel.setText(String.valueOf(resQtty));

        switch (resType) {
            case "Gold":
                AddImage("coin2.png", pict1posX, pict1posY, pictSize, pictSize);
                break;
            case "Wood":
                AddImage("wood.png", pict1posX, pict1posY, pictSize, pictSize);
                break;
            case "Bread":
                AddImage("bread.png", pict1posX, pict1posY, pictSize, pictSize);
                break;
        }

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
