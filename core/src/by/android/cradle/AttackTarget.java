package by.android.cradle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class AttackTarget extends BaseActor {
    private Label attackLabel;

    private Label qttyLabel;


    public AttackTarget(float x, float y, Stage s, int width, int height , final CradleGame cradleGame) {
        super(x, y, s, Touchable.enabled, cradleGame);
        this.setSize(width, height);
        AddImage("fon_white2.png",Math.round(width*0.02f),Math.round(height*0.02f),Math.round(width*.98f), Math.round(height*0.98f));
        AddImage("goldenframe03.png",0,0,width,height);
        Image img = new Image(cradleGame.getCradleAssetManager().getAnimation(Assets.GAMECELL_LOCK01_ANIMATION_ID).getKeyFrame(0).getTexture());
        img.setPosition(width*0.35f, height*0.1f);
        addActor(img);

        // Attack label
        String ms = cradleGame.getLanguageStrings().get("destroy");
        attackLabel = new Label(ms, BaseGame.labelStyle_Small);
        attackLabel.setWrap(true);
        attackLabel.setAlignment( Align.center );
        attackLabel.setPosition( Math.round(width*0.077f), Math.round(height*0.67f) );
        attackLabel.setWidth( width*0.8f );
        attackLabel.setColor(Color.GOLD);
        addActor(attackLabel);

        // Qtty label
        qttyLabel = new Label("1", BaseGame.labelStyle_Small);
        qttyLabel.setWrap(true);
        qttyLabel.setAlignment( Align.center );
        qttyLabel.setPosition( Math.round(width*0.077f), Math.round(height*0.2f) );
        qttyLabel.setWidth( width*0.8f );
        qttyLabel.setColor(Color.GOLD);
        addActor(qttyLabel);


    }


    public void setAttackQtty(int qtty){
        qttyLabel.setText(qtty);
    }
}
