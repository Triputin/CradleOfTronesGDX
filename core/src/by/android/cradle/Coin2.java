package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Coin2 extends Item {

    public Coin2(float x, float y, int width, int height, Stage s, int row, int col)
    {
        super(x,y,width, height,s, Touchable.disabled,row,col);
        setSelected(false, null);
        setBoundaryPolygon(8);
        //Action spin = Actions.rotateBy(5, 1);
        //addAction( Actions.forever(spin) );
    }

    @Override
    public void setSelected(boolean selected, Item prev) {
        if(selected){
            loadTexture("coin2pressed.png", (int) getWidth(), (int) getHeight());
        }else {
            loadTexture("coin2.png", (int) getWidth(), (int) getHeight());
        }

        super.setSelected(selected, prev);
    }

}