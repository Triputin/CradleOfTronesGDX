package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Item extends BaseActor {

    private boolean selected;
    private int row;
    private int col;
    private SelDirection selectedDirection;




    public Item(float x, float y,int width, int height, Stage s, Touchable touchable,int row,int col)
    {
        super(x,y,s, touchable);
        this.row = row;
        this.col = col;
        this.selected = false;
        setHeight(height);
        setWidth(width);
        selectedDirection = SelDirection.None;
    }

    public SelDirection findDirection ( Item firstItem, Item secondItem){
        SelDirection selDirection = SelDirection.None;
        if ((firstItem.getCol()==secondItem.getCol())&&(firstItem.getRow()==secondItem.getRow())){
            selDirection = SelDirection.None;
            return selDirection;
        }

        if (firstItem.getCol()!=secondItem.getCol()){
            if (firstItem.getCol()>secondItem.getCol()){
                selDirection = SelDirection.ArrowToWest;
            }else{
                selDirection = SelDirection.ArrowToEast;
            }
        }
        if (firstItem.getRow()!=secondItem.getRow()){
            if (firstItem.getRow()>secondItem.getRow()){
                selDirection = SelDirection.ArrowToSouth;
            }else{
                selDirection = SelDirection.ArrowToNorth;
            }
        }
        return selDirection;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected, SelDirection selDirection) {
        this.selected = selected;
        this.selectedDirection = selDirection;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isNear(int row2 , int col2){
        //Диагонали?
        //System.out.println(row2+" "+col2+" "+row+" "+col);
        if(!((row2!=row)&&(col!=col2))){
            //Расстояние?
            if((Math.abs(row-row2)<2)&&(Math.abs(col-col2)<2)) {
                return true;
            }
        }
      return false;
    }
}
