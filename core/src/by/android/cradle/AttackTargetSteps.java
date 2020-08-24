package by.android.cradle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;



public class AttackTargetSteps extends BaseActor {
    private Label attackLabel;

    private Label qttyStepsLabel;


    public AttackTargetSteps(float x, float y, Stage s, int width, int height , final CradleGame cradleGame) {
        super(x, y, s, Touchable.enabled, cradleGame);
        this.setSize(width, height);
        AddImage("fon_white2.png",Math.round(width*0.02f),Math.round(height*0.02f),Math.round(width*.98f), Math.round(height*0.98f));
        AddImage("goldenframe03.png",0,0,width,height);
        Image img = new Image(cradleGame.getCradleAssetManager().getAnimation(Assets.GAMECELL_LOCK01_ANIMATION_ID).getKeyFrame(0).getTexture());
        img.setPosition(width*0.35f, height*0.1f);
        addActor(img);

        // Attack label
        String ms = cradleGame.getLanguageStrings().get("stepsleft");
        attackLabel = new Label(ms, BaseGame.labelStyle_Small);
        attackLabel.setWrap(true);
        attackLabel.setAlignment( Align.center );
        attackLabel.setPosition( Math.round(width*0.077f), Math.round(height*0.67f) );
        attackLabel.setWidth( width*0.8f );
        attackLabel.setColor(Color.GOLD);
        addActor(attackLabel);

        // Qtty label
        qttyStepsLabel = new Label("1", BaseGame.labelStyle_Small);
        qttyStepsLabel.setWrap(true);
        qttyStepsLabel.setAlignment( Align.center );
        qttyStepsLabel.setPosition( Math.round(width*0.077f), Math.round(height*0.2f) );
        qttyStepsLabel.setWidth( width*0.8f );
        qttyStepsLabel.setColor(Color.GOLD);
        addActor(qttyStepsLabel);


    }

    public int getAttackStepsQtty() {
        return Integer.parseInt(qttyStepsLabel.getText().toString());
    }

    public void setAttackStepsQtty(int qtty){
        qttyStepsLabel.setText(qtty);
    }
}