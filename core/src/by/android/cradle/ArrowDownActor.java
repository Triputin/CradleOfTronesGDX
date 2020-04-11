package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class ArrowDownActor extends BaseActor {

    CradleGame cradleGame;
    BaseActor arrowActor;
    Label label;

    public ArrowDownActor(float x, float y,float width, float height, Stage s, CradleGame cradleGame)
    {
        super(x,y,s, Touchable.disabled);
        setHeight(height);
        setWidth(width);
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        float fontScale = 1.0f;
        if (w>1000){
            fontScale = 1.5f;
        } else {
            fontScale = 0.8f;
        }
        arrowActor = new BaseActor(0,0,s,Touchable.disabled);
        //arrowActor.loadTexture("arrow_down.png", (int) getWidth(), (int) getHeight());
        arrowActor.setSize((int) getWidth(), (int) getHeight());
        arrowActor.AddImage("arrow_down.png", 0,0,(int) getWidth(), (int) getHeight());
        //arrowActor.addAction(fadeOut(0.01f));
        this.cradleGame = cradleGame;
        String st = cradleGame.getLanguageStrings().get("pressheretoattack");
        label = new Label(st, BaseGame.labelStyle);
        label.setFontScale(fontScale);
        label.setColor(Color.GOLD);
        addActor(label);
    }

    public void startAnimation(float x, float y, float x2, float y2){
        Action actions;
        arrowActor.setPosition(x,y);
        label.setPosition(-label.getWidth()*0.4f,arrowActor.getHeight()*1.5f);
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
