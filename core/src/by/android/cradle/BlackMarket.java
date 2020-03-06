package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class BlackMarket extends BaseActor {

    public BlackMarket(float x, float y, int width, int height, Stage s, Touchable touchable) {
        super(x, y, s, touchable);
        loadTexture("market01.png", width, height);
    }

}
