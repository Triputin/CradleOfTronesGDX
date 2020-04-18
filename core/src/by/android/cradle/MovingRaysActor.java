package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class MovingRaysActor extends BaseActor {

    //BaseActor rayActor;
   // private Animation<TextureRegion> specialEffectAnimation;

    public MovingRaysActor(float x, float y, int width, int height, Stage s)
    {
        super(x,y,s, Touchable.disabled);
        setHeight(height);
        setWidth(width);
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        setBoundaryPolygon(8);
        String[] filenames1 =
                { "solareffect/sf01.png", "solareffect/sf02.png",
                        "solareffect/sf03.png", "solareffect/sf04.png", "solareffect/sf05.png"
                };
        loadAnimationFromFiles(filenames1, 0.2f, true,  (int)getWidth(),  (int) getHeight());

        /*
        rayActor = new BaseActor(0,0,s,Touchable.disabled);
        rayActor.setSize((int) getWidth(), (int) getHeight());
        rayActor.AddImage("ray01_100_100.png", 0,-Math.round(height*0.25f),(int) getWidth(), (int) getHeight());
        rayActor.AddImage("ray01_100_100.png", 0,-Math.round(height*0.5f),(int) getWidth(), (int) getHeight());
        rayActor.AddImage("ray01_100_100.png", 0,-Math.round(height*0.75f),(int) getWidth(), (int) getHeight());
        rayActor.AddImage("ray01_100_100.png", 0,-Math.round(height*1.0f),(int) getWidth(), (int) getHeight());
        rayActor.AddImage("ray01_100_100.png", 0,0,(int) getWidth(), (int) getHeight());
        rayActor.AddImage("ray01_100_100.png", 0,Math.round(height*0.25f),(int) getWidth(), (int) getHeight());
        rayActor.AddImage("ray01_100_100.png", 0,Math.round(height*0.5f),(int) getWidth(), (int) getHeight());
        rayActor.AddImage("ray01_100_100.png", 0,Math.round(height*0.75f),(int) getWidth(), (int) getHeight());
        rayActor.AddImage("ray01_100_100.png", 0,Math.round(height*1.0f),(int) getWidth(), (int) getHeight());
        //AddImage("ray01_100_100.png",0,0, (int) getWidth(), (int) getHeight());

        */


    }

/*
    public void startAnimation(float x, float y, float x2, float y2){
        Action actions;
        rayActor.setPosition(x,y);
        rayActor.setRotation(-45);
        actions = Actions.forever(sequence( Actions.moveTo(x2,y2,2.6f, Interpolation.linear), Actions.moveTo(x,y,0.01f, Interpolation.linear)));
        rayActor.addAction(actions);
    }
    */



}
