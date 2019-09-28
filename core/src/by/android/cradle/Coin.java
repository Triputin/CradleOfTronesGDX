package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Coin extends Item {

    public Coin(float x, float y,int width, int height, Stage s,int row,int col)
    {
        super(x,y,width, height,s,Touchable.disabled,row,col);
        setSelected(false, SelDirection.ArrowToNorth);
        setBoundaryPolygon(8);

    }

    @Override
    public void setSelected(boolean selected, SelDirection selDirection) {
        if(selected){
            loadTexture("assets/coinpressed.jpg", (int) getWidth(), (int) getHeight());
        }else {
            loadTexture("assets/coin1.png", (int) getWidth(), (int) getHeight());
        }

        super.setSelected(selected, selDirection);
    }

}
