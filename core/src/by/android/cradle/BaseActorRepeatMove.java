package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class BaseActorRepeatMove extends BaseActor {

    int pictureSize;
    public BaseActorRepeatMove(float x, float y, int width, int height, Stage s, CradleGame cradleGame)
    {
        super(x,y,s, Touchable.disabled, cradleGame);
        //setAcceleration(1);
        setSpeed(3);
        //setMaxSpeed(10);
        //setDeceleration(400);
        loadTexture("castle/castle03_sky.png",width*2,height);
        pictureSize=width;

    }

    public void act(float dt)
    {
        super.act( dt );
        accelerateAtAngle(0);
        applyPhysics(dt);
        if (getX() > 0) {
            setX(-pictureSize);
        }
        //setAnimationPaused( !isMoving() );


    }

}
