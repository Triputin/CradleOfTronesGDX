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



public class DialogBox_NotEnoughSteps extends BaseActor {

    private Label dialogLabel;
    private Label dialogLabel2;
    private int padding = 42;
    private TextButton okButton;
    private TextButton cancelButton;
    private Label scoreLabel;

    public DialogBox_NotEnoughSteps(float x, float y, Stage s, int width, int height, CradleGame cradleGame1)
    {
        super(x,y,s, Touchable.enabled,cradleGame1);
        this.setSize(width, height);

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        AddImage("fon_white2.png",Math.round(width*0.04f),Math.round(height*0.075f),Math.round(width*0.92f), Math.round(height*0.85f));
        AddImage("goldenframe01.png",0,0,width,height);


        String ms = cradleGame.getLanguageStrings().get("nostepsleft");
        dialogLabel2 = new Label(ms, BaseGame.labelStyle_Small);
        dialogLabel2.setWrap(true);
        dialogLabel2.setAlignment( Align.center );
        dialogLabel2.setPosition( Math.round(width*0.07f), Math.round(height*0.6f) );
        dialogLabel2.setWidth( width*0.85f );
        dialogLabel2.setColor(Color.GOLD);
        this.addActor(dialogLabel2);

        //Ok Button
        ms = cradleGame.getLanguageStrings().get("watchads");
        okButton = new TextButton( ms, BaseGame.textButtonStyle );

        if (w<1000) {
            okButton.setWidth(okButton.getWidth()*0.8f);
            okButton.setHeight(okButton.getHeight()*0.8f);
        }
        okButton.setPosition(Math.round(getWidth()/4-okButton.getWidth()/2),Math.round(height*0.12f));


        //Cancel Button
        ms = cradleGame.getLanguageStrings().get("cancel");
        cancelButton = new TextButton( ms, BaseGame.textButtonStyle );

        if (w<1000) {
            cancelButton.setWidth(cancelButton.getWidth()*0.8f);
            cancelButton.setHeight(cancelButton.getHeight()*0.8f);
        }
        cancelButton.setPosition(Math.round(getWidth()/4*3-cancelButton.getWidth()/2),Math.round(height*0.12f));
    }


    /*
    add in InputListener !
    if(cradleGame.getiGoogleServices().hasVideoLoaded()) {
                        cradleGame.getiGoogleServices().showRewardedVideoAd();
                    }
     */

    public void showWithOkButton(InputListener okinputListener,InputListener cancelinputListener, AttackTypeInfo attackTypeInfo){
        addActor(okButton);
        okButton.clearListeners(); //!!!!
        okButton.addListener(okinputListener);
        addActor(cancelButton);
        cancelButton.clearListeners(); //!!!!
        cancelButton.addListener(cancelinputListener);

        String ms;
        switch(attackTypeInfo){
            case SingleTimeClearUp:
            case SingleTimeResources:
                ms = cradleGame.getLanguageStrings().get("notimeleft");
                break;
                default:
                    ms = cradleGame.getLanguageStrings().get("nostepsleft");
        }

        dialogLabel2.setText(ms);

        Action actions = sequence(Actions.scaleTo(1,0,0.01f),fadeIn(0.01f),Actions.scaleTo(1,1,0.5f));

        this.setVisible(true);
        this.addAction( actions );


    }
}
