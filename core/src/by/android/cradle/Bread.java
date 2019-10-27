package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Bread extends Item {

    public Bread(float x, float y, int width, int height, Stage s, int row, int col)
    {
        super(x,y,width, height,s, Touchable.disabled,row,col);
        setSelected(false, null);
        setBoundaryPolygon(8);
    }

    @Override
    public void setSelected(boolean selected, Item prev) {
        if(selected){
            loadTexture("breadpressed.png", (int) getWidth(), (int) getHeight());
        }else {
            loadTexture("bread.png", (int) getWidth(), (int) getHeight());
        }

        super.setSelected(selected, prev);
    }

}