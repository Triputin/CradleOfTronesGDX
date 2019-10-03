package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Coin2 extends Item {

    public Coin2(float x, float y, int width, int height, Stage s, int row, int col)
    {
        super(x,y,width, height,s, Touchable.disabled,row,col);
        setSelected(false, SelDirection.None);
        setBoundaryPolygon(8);

    }

    @Override
    public void setSelected(boolean selected, SelDirection selDirection) {
        if(selected){
            loadTexture("assets/coin2pressed.png", (int) getWidth(), (int) getHeight());
        }else {
            loadTexture("assets/coin2.png", (int) getWidth(), (int) getHeight());
        }

        super.setSelected(selected, selDirection);
    }

}