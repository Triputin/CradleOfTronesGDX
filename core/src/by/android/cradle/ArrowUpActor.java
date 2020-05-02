package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class ArrowUpActor extends BaseActor {

    BaseActor arrowActor;
    Label label;

    public ArrowUpActor(float x, float y, float width, float height, Stage s, CradleGame cradleGame)
    {
        super(x,y,s, Touchable.disabled, cradleGame);
        setHeight(height);
        setWidth(width);
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        arrowActor = new BaseActor(0,0,s,Touchable.disabled,cradleGame);
        arrowActor.setSize((int) getWidth(), (int) getHeight());
        arrowActor.AddImage("arrow_up.png", 0,0,(int) getWidth(), (int) getHeight());
        //arrowActor.addAction(fadeOut(0.01f));
        String st = cradleGame.getLanguageStrings().get("useweapon");
        label = new Label(st, BaseGame.labelStyle_Small);
        label.setWrap(true);
        label.setWidth(width*2.0f);
        label.setColor(Color.GOLD);
        label.setAlignment(Align.center);
        addActor(label);
    }

    public void startAnimation(float x, float y, float x2, float y2){
        Action actions;
        arrowActor.setPosition(x,y);
        label.setPosition(-arrowActor.getHeight()*1.2f,-arrowActor.getHeight()*1.4f);
        actions = Actions.forever(sequence( Actions.moveTo(x2,y2,0.6f, Interpolation.smooth),Actions.delay(0.02f), Actions.moveTo(x,y,0.6f, Interpolation.smooth)));
        arrowActor.addAction(actions);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        arrowActor.setVisible(visible);
        label.setVisible(visible);
    }

    @Override
    public boolean remove() {
        super.remove();
        if (arrowActor!=null){
            arrowActor.remove();
        }
        if (label!=null){
            label.remove();
        }
        return true;
    }
}