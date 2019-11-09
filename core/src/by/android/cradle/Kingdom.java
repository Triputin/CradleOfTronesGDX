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

       // Animation<TextureRegion> animation= loadAnimationFromSheet("kingdoms/flaganimation01.png", 2, 3, 0.1f, true,50,50);
        String[] filenames =
                { "flag/flag02.png", "flag/flag03.png",
                        "flag/flag04.png", "flag/flag05.png", "flag/flag06.png",
                        "flag/flag07.png", "flag/flag08.png", "flag/flag09.png",
                        "flag/flag10.png", "flag/flag11.png", "flag/flag12.png"
                };

       Animation<TextureRegion> animation = loadAnimationFromFiles(filenames, 0.1f, true,  50,  50);
       BaseActor baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
       baseActor.setAnimation(animation);
       addActor(baseActor);
       baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
       animation = loadTexture("flag/flagbasement.png", (int) getWidth(), (int) getHeight());
       baseActor.setAnimation(animation);
       addActor(baseActor);

       loadTexture("kingdoms/winterfell.png", (int) getWidth(), (int) getHeight());
    }
}
