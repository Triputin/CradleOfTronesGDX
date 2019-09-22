package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Item extends BaseActor {

    private boolean selected;



    public Item(float x, float y, Stage s, Touchable touchable)
    {
        super(x,y,s, touchable);
        this.selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
