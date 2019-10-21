package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class ResultsActor extends BaseActor{


    public ResultsActor(float x, float y, int width, int height, Stage s, Touchable touchable)
    {
        super(x,y,s, touchable);
        setHeight(height);
        setWidth(width);
        loadTexture("results.png", (int) getWidth(), (int) getHeight());

    }
}
