package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Item extends BaseActor {

    private boolean selected;

    public boolean isSelectedFirst() {
        return selectedFirst;
    }

    public void setSelectedFirst(boolean selectedFirst) {
        this.selectedFirst = selectedFirst;
    }

    private boolean selectedFirst; // indicates that item was the first selected
    private int row;
    private int col;
    private SelDirection selectedDirection;
    private Image lineImage;

    public SelDirection getSelectedDirection() {
        return selectedDirection;
    }


    public Item(float x, float y, int width, int height, Stage s, Touchable touchable, int row, int col)
    {
        super(x,y,s, touchable);
        this.row = row;
        this.col = col;
        this.selected = false;
        setHeight(height);
        setWidth(width);
        selectedDirection = SelDirection.None;
        selectedFirst=false;
    }

    public void AddImageDirection()
    {
        Pixmap pixmap200;
        if (selectedDirection!=SelDirection.None) {
            // Изменяем размер загружаемой картинки из файла на заданный
            if ((selectedDirection==SelDirection.ArrowToEast) || (selectedDirection==SelDirection.ArrowToWest)|| (selectedDirection==SelDirection.HorizontalLine)) {
                 pixmap200 = new Pixmap(Gdx.files.internal("assets/linehoriz.png"));
            }
            else {
                 pixmap200 = new Pixmap(Gdx.files.internal("assets/linevert.png"));
            }

            Pixmap pixmap100 = new Pixmap((int) getWidth(), (int) getHeight(), pixmap200.getFormat());
            pixmap100.drawPixmap(pixmap200,
                    0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                    0, 0, pixmap100.getWidth(), pixmap100.getHeight()
            );
            Texture texture = new Texture(pixmap100);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            pixmap200.dispose();
            pixmap100.dispose();
            TextureRegion imgTextureRegion = new TextureRegion(texture);
            imgTextureRegion.setRegion(0,0,getWidth(),getHeight());

            TextureRegionDrawable imgTextureRegionDrawable = new TextureRegionDrawable(imgTextureRegion);
            Image img = new Image(texture);
            //img.setDrawable(imgTextureRegionDrawable);
            img.setSize(getWidth(),getHeight());
            img.setPosition(0, 0);
            if (lineImage!=null){
                removeActor(lineImage);
            }
            addActor(img);
            lineImage=img;
        }else
        {
            if (lineImage!=null){
                removeActor(lineImage);
            }

        }

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
        AddImageDirection();
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
