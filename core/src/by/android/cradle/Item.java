package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Item extends BaseActor {

    private boolean selected;
    private int row;
    private int col;




    public Item(float x, float y, Stage s, Touchable touchable,int row,int col)
    {
        super(x,y,s, touchable);
        this.row = row;
        this.col = col;
        this.selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isNear(int row2 , int col2){
        //Диагонали?
        System.out.println(row2+" "+col2+" "+row+" "+col);
        if(!((row2!=row)&&(col!=col2))){
            //Расстояние?
            if((Math.abs(row-row2)<2)&&(Math.abs(col-col2)<2)) {
                return true;
            }
        }
      return false;
    }
}
