package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class AttackTarget extends BaseActor {
    private Label attackTextLabel;
    private Label qttyCelltoClearLabel;
    private Image imageCelltoClear;
    private ResultsActorForAttack resultsActorForAttack;
    TextButton retreatButton;

    public AttackTarget(float x, float y, Stage s, int width, int height , final CradleGame cradleGame) {
        super(x, y, s, Touchable.enabled, cradleGame);
        this.setSize(width, height);
        AddImage("fon_white2.png",Math.round(width*0.02f),Math.round(height*0.02f),Math.round(width*.98f), Math.round(height*0.98f));
        AddImage("goldenframe03.png",0,0,width,height);
        imageCelltoClear = new Image(cradleGame.getCradleAssetManager().getAnimation(Assets.GAMECELL_LOCK01_ANIMATION_ID).getKeyFrame(0).getTexture());
        imageCelltoClear.setPosition(width*0.35f, height*0.1f);
        addActor(imageCelltoClear);

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        // Attack label
        String ms = cradleGame.getLanguageStrings().get("destroy");
        attackTextLabel = new Label(ms, BaseGame.labelStyle_Small);
        attackTextLabel.setWrap(true);
        attackTextLabel.setAlignment( Align.center );
        attackTextLabel.setPosition( Math.round(width*0.077f), Math.round(height*0.55f) );
        attackTextLabel.setWidth( width*0.8f );
        attackTextLabel.setColor(Color.GOLD);
        addActor(attackTextLabel);

        // Qtty label
        qttyCelltoClearLabel = new Label("1", BaseGame.labelStyle_Small);
        qttyCelltoClearLabel.setWrap(true);
        qttyCelltoClearLabel.setAlignment( Align.center );
        qttyCelltoClearLabel.setPosition( Math.round(width*0.077f), Math.round(height*0.2f) );
        qttyCelltoClearLabel.setWidth( width*0.8f );
        qttyCelltoClearLabel.setColor(Color.GOLD);
        addActor(qttyCelltoClearLabel);

        //BaseActor baseActorRes = new BaseActor(x,y,getStage(),Touchable.disabled,cradleGame);
        //resultsActorForAttack = new ResultsActorForAttack(x+width*0.1f,(int)(y+height/2),(int)(width*0.8f),(int)(height*0.25f),s,Touchable.disabled,this,cradleGame);
        resultsActorForAttack = new ResultsActorForAttack(width*0.1f,(int)(height/4),(int)(width*0.8f),(int)(height*0.25f),s,Touchable.disabled,this,cradleGame,BaseGame.labelStyle_Small);

        addActor(resultsActorForAttack);

        //Retreat Button
        ms = " "+cradleGame.getLanguageStrings().get("retreat")+" ";
        retreatButton = new TextButton( ms, BaseGame.textButtonStyleCheck );
        if (w<1000) {
            retreatButton.setWidth(retreatButton.getWidth()*0.8f);
            retreatButton.setHeight(retreatButton.getHeight()*0.8f);
        }
        retreatButton.setPosition(this.getWidth()*0.5f-retreatButton.getWidth()*0.38f,height*0.07f);
        addActor(retreatButton);
        InputListener inputListenerRetreat =new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                cradleGame.getScreenGamePlay().LoseLevel();
                return true;
            }
        };

        retreatButton.addListener(inputListenerRetreat);


    }

/*
    public void setAttackQtty(int qttyCelltoClear){
        qttyCelltoClearLabel.setText(qttyCelltoClear);

    }
*/
    public void setAttackTypeAndQtty(AttackTargetInfo attackTargetInfo, int qttyCelltoClear){
        qttyCelltoClearLabel.setText(qttyCelltoClear);
        resultsActorForAttack.UpdateRes(attackTargetInfo.kingdomRes);
        String ms;
        //attackTextLabel.setPosition( Math.round(this.getWidth()*0.077f), Math.round(this.getHeight()*0.67f) );
        attackTextLabel.setFontScale(1.0f);

        switch(attackTargetInfo.attackTypeInfo){
            case SingleTimeClearUp:
                ms = cradleGame.getLanguageStrings().get("destroy");
                attackTextLabel.setText(ms);
                retreatButton.setVisible(false);
                qttyCelltoClearLabel.setVisible(true);
                imageCelltoClear.setVisible(true);
                resultsActorForAttack.setVisible(false);
                break;
            case SingleTimeResources:
                ms = cradleGame.getLanguageStrings().get("collect");
                attackTextLabel.setText(ms);
                retreatButton.setVisible(false);
                qttyCelltoClearLabel.setVisible(false);
                imageCelltoClear.setVisible(false);
                resultsActorForAttack.setVisible(true);
                break;
            case SingleClearUp:
                ms = cradleGame.getLanguageStrings().get("destroy");
                attackTextLabel.setText(ms);
                retreatButton.setVisible(false);
                qttyCelltoClearLabel.setVisible(true);
                imageCelltoClear.setVisible(true);
                resultsActorForAttack.setVisible(false);
                break;
            case SingleResources:
                ms = cradleGame.getLanguageStrings().get("collect");
                attackTextLabel.setText(ms);
                retreatButton.setVisible(false);
                qttyCelltoClearLabel.setVisible(false);
                imageCelltoClear.setVisible(false);
                resultsActorForAttack.setVisible(true);
                break;
            case DoubleClearUp:
                ms = cradleGame.getLanguageStrings().get("destroy");
                attackTextLabel.setText(ms);
                retreatButton.setVisible(false);
                qttyCelltoClearLabel.setVisible(true);
                imageCelltoClear.setVisible(true);
                resultsActorForAttack.setVisible(false);
                break;
            case DoubleResources:
                ms = cradleGame.getLanguageStrings().get("collect");
                attackTextLabel.setText(ms);
                retreatButton.setVisible(false);
                qttyCelltoClearLabel.setVisible(false);
                imageCelltoClear.setVisible(false);
                resultsActorForAttack.setVisible(true);
                break;
            case DoubleFight:
                ms = cradleGame.getLanguageStrings().get("defeattheenemyknight");
                //attackTextLabel.setPosition( Math.round(this.getWidth()*0.15f), Math.round(this.getHeight()*2.1f) );
                attackTextLabel.setFontScale(0.7f);
                attackTextLabel.setText(ms);
                retreatButton.setVisible(true);
                qttyCelltoClearLabel.setVisible(false);
                imageCelltoClear.setVisible(false);
                resultsActorForAttack.setVisible(false);
                break;
            default:
                ms = cradleGame.getLanguageStrings().get("destroy");
                attackTextLabel.setText(ms);
                //attackTextLabel.setPosition( Math.round(this.getWidth()*0.077f), Math.round(this.getHeight()*0.67f) );
                retreatButton.setVisible(false);
                qttyCelltoClearLabel.setVisible(true);
                imageCelltoClear.setVisible(true);
                resultsActorForAttack.setVisible(false);
        }
    }

}
