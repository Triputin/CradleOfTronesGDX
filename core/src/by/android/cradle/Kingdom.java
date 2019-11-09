package by.android.cradle;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Kingdom extends BaseActor {

    public Kingdom(float x, float y, int width, int height, Stage s, Touchable touchable)
    {
        super(x,y,s, touchable);
        setHeight(height);
        setWidth(width);
        loadTexture("kingdoms/winterfell.png", (int) getWidth(), (int) getHeight());
        Animation<TextureRegion> animation= loadAnimationFromSheet("kingdoms/flaganimation01.png", 2, 3, 0.1f, true,50,50);
        BaseActor baseActor = new BaseActor((int) getWidth()/2,(int) getHeight(),s,Touchable.enabled);
        baseActor.setAnimation(animation);
        addActor(baseActor);

    }
}
