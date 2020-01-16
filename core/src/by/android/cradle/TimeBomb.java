package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class TimeBomb extends DragAndDropActor {


    public TimeBomb(float x, float y, int width, int height, Stage s, Touchable touchable) {
        super(x, y, s);
        setHeight(height);
        setWidth(width);
        loadTexture("timebomb.png", (int) getWidth(), (int) getHeight());

    }
}
