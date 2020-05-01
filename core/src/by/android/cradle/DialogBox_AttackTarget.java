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
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class DialogBox_AttackTarget extends BaseActor {
    private Label dialogLabelHeader;
    private Label dialogLabel01;
    private Label dialogLabel02;
    private Label dialogLabel03;
    private TextButton okButton;


    public DialogBox_AttackTarget(float x, float y, Stage s, int width, int height, CradleGame cradleGame1)
    {
        super(x,y,s, Touchable.enabled,cradleGame1);
        this.setSize(width, height);

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        AddImage("fon_white2.png",Math.round(width*0.04f),Math.round(height*0.075f),Math.round(width*0.92f), Math.round(height*0.85f));
        AddImage("goldenframe01.png",0,0,width,height);

        String ms = cradleGame.getLanguageStrings().get("attactarget");
        dialogLabelHeader= new Label(ms, BaseGame.labelStyle_Small);
        dialogLabelHeader.setWrap(true);
        dialogLabelHeader.setAlignment( Align.center );
        dialogLabelHeader.setPosition( Math.round(width*0.12f), Math.round(height*0.81f) );
        dialogLabelHeader.setWidth( width*0.8f);
        dialogLabelHeader.setColor(Color.GOLD);
        this.addActor(dialogLabelHeader);

        ms = cradleGame.getLanguageStrings().get("attactargetinfo01");
        dialogLabel01 = new Label(ms, BaseGame.labelStyle_SuperSmall);
        dialogLabel01.setWrap(true);
        dialogLabel01.setAlignment( Align.left );
        dialogLabel01.setPosition( Math.round(width*0.4f), Math.round(height*0.7f) );
        dialogLabel01.setWidth( width*0.55f);
        dialogLabel01.setColor(Color.GOLD);
        this.addActor(dialogLabel01);

        ms = cradleGame.getLanguageStrings().get("attactargetinfo02");
        dialogLabel02 = new Label(ms, BaseGame.labelStyle_SuperSmall);
        dialogLabel02.setWrap(true);
        dialogLabel02.setAlignment( Align.left );
        dialogLabel02.setPosition( Math.round(width*0.4f), Math.round(height*0.5f) );
        dialogLabel02.setWidth( width*0.55f);
        dialogLabel02.setColor(Color.GOLD);
        this.addActor(dialogLabel02);

        ms = cradleGame.getLanguageStrings().get("attactargetinfo03");
        dialogLabel03 = new Label(ms, BaseGame.labelStyle_SuperSmall);
        dialogLabel03.setWrap(true);
        dialogLabel03.setAlignment( Align.left );
        dialogLabel03.setPosition( Math.round(width*0.4f), Math.round(height*0.3f) );
        dialogLabel03.setWidth( width*0.55f);
        dialogLabel03.setColor(Color.GOLD);
        this.addActor(dialogLabel03);


        int pictSize = Math.round(getHeight() * 0.15f);
        int pictposX = Math.round(getWidth() * 0.15f);
        int pict1posY = Math.round(getHeight() * 0.65f);
        AddImage("helpimages/at_01.png", pictposX, pict1posY, pictSize, pictSize);
        int pict2posY = Math.round(getHeight() * 0.45f);
        AddImage("helpimages/at_02.png", pictposX, pict2posY, pictSize, pictSize);
        int pict3posY = Math.round(getHeight() * 0.25f);
        AddImage("helpimages/at_03.png", pictposX, pict3posY, pictSize, pictSize);

        //Ok Button
        ms = cradleGame.getLanguageStrings().get("ok");
        okButton = new TextButton( ms, BaseGame.textButtonStyle );

        if (w<1000) {
            okButton.setWidth(okButton.getWidth()*0.8f);
            okButton.setHeight(okButton.getHeight()*0.8f);
        }
        okButton.setPosition(Math.round(getWidth()/2-okButton.getWidth()/2),Math.round(height*0.12f));

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