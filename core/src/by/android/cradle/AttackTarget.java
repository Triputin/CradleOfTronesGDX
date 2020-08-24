package by.android.cradle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class AttackTarget extends BaseActor {
    private Label attackTextLabel;
    private Label qttyCelltoClearLabel;
    private Image imageCelltoClear;
    private ResultsActorForAttack resultsActorForAttack;

    public AttackTarget(float x, float y, Stage s, int width, int height , final CradleGame cradleGame) {
        super(x, y, s, Touchable.enabled, cradleGame);
        this.setSize(width, height);
        AddImage("fon_white2.png",Math.round(width*0.02f),Math.round(height*0.02f),Math.round(width*.98f), Math.round(height*0.98f));
        AddImage("goldenframe03.png",0,0,width,height);
        imageCelltoClear = new Image(cradleGame.getCradleAssetManager().getAnimation(Assets.GAMECELL_LOCK01_ANIMATION_ID).getKeyFrame(0).getTexture());
        imageCelltoClear.setPosition(width*0.35f, height*0.1f);
        addActor(imageCelltoClear);

        // Attack label
        String ms = cradleGame.getLanguageStrings().get("destroy");
        attackTextLabel = new Label(ms, BaseGame.labelStyle_Small);
        attackTextLabel.setWrap(true);
        attackTextLabel.setAlignment( Align.center );
        attackTextLabel.setPosition( Math.round(width*0.077f), Math.round(height*0.67f) );
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

        switch(attackTargetInfo.attackTypeInfo){
            case SingleTimeClearUp:
                ms = cradleGame.getLanguageStrings().get("destroy");
                attackTextLabel.setText(ms);
                qttyCelltoClearLabel.setVisible(true);
                imageCelltoClear.setVisible(true);
                resultsActorForAttack.setVisible(false);
                break;
            case SingleTimeResources:
                ms = cradleGame.getLanguageStrings().get("collect");
                attackTextLabel.setText(ms);
                qttyCelltoClearLabel.setVisible(false);
                imageCelltoClear.setVisible(false);
                resultsActorForAttack.setVisible(true);
                break;
            case SingleClearUp:
                ms = cradleGame.getLanguageStrings().get("destroy");
                attackTextLabel.setText(ms);
                qttyCelltoClearLabel.setVisible(true);
                imageCelltoClear.setVisible(true);
                resultsActorForAttack.setVisible(false);
                break;
            case SingleResources:
                ms = cradleGame.getLanguageStrings().get("collect");
                attackTextLabel.setText(ms);
                qttyCelltoClearLabel.setVisible(false);
                imageCelltoClear.setVisible(false);
                resultsActorForAttack.setVisible(true);
                break;
            case DoubleClearUp:
                ms = cradleGame.getLanguageStrings().get("destroy");
                attackTextLabel.setText(ms);
                qttyCelltoClearLabel.setVisible(true);
                imageCelltoClear.setVisible(true);
                resultsActorForAttack.setVisible(false);
                break;
            case DoubleResources:
                ms = cradleGame.getLanguageStrings().get("collect");
                attackTextLabel.setText(ms);
                qttyCelltoClearLabel.setVisible(false);
                imageCelltoClear.setVisible(false);
                resultsActorForAttack.setVisible(true);
                break;
            default:
                ms = cradleGame.getLanguageStrings().get("destroy");
                attackTextLabel.setText(ms);
                qttyCelltoClearLabel.setVisible(true);
                imageCelltoClear.setVisible(true);
                resultsActorForAttack.setVisible(false);
        }
    }

}
