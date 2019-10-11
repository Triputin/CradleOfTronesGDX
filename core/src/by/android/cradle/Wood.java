package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Wood extends Item {

    public Wood(float x, float y, int width, int height, Stage s, int row, int col)
    {
        super(x,y,width, height,s, Touchable.disabled,row,col);
        setSelected(false, null);
        setBoundaryPolygon(8);
    }

    @Override
    public void setSelected(boolean selected, Item prev) {
        if(selected){
            loadTexture("woodpressed.png", (int) getWidth(), (int) getHeight());
        }else {
            loadTexture("wood.png", (int) getWidth(), (int) getHeight());
        }

        super.setSelected(selected, prev);
    }

}
