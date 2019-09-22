package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Coin extends Item {

    public Coin(float x, float y, Stage s,int row,int col)
    {
        super(x,y,s, Touchable.disabled,row,col);
        loadTexture("assets/coin.jpg");
        setBoundaryPolygon(8);

    }

    @Override
    public void setSelected(boolean selected) {
        if(selected){
            loadTexture("assets/coinpressed.jpg");
        }else {
            loadTexture("assets/coin.jpg");
        }

        super.setSelected(selected);
    }

}
