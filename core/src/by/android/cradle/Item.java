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
    private boolean selectedFirst; // indicates that item was the first selected
    private Item previous;
    private Item next;
    private Cell cell;

    public Item(float x, float y, int width, int height, Stage s, Touchable touchable, int row, int col)
    {
        super(x,y,s, touchable);
        cell = new Cell(row,col);
        this.selected = false;
        setHeight(height);
        setWidth(width);
        selectedDirection = SelDirection.None;
        selectedFirst=false;
    }

    public Cell getCell() {
        return cell;
    }

    private SelDirection selectedDirection;
    private Image lineImage;


    public boolean isSelectedFirst() {
        return selectedFirst;
    }

   /* public void setSelectedFirst(boolean selectedFirst) {
        this.selectedFirst = selectedFirst;
    }
    */

    public SelDirection getSelectedDirection() {
        return selectedDirection;
    }

    private void AddImageDirection()
    {
        Pixmap pixmap200;
        if (selectedDirection!=SelDirection.None) {

            switch (selectedDirection){

                case ArrowToEast:
                    pixmap200 = new Pixmap(Gdx.files.internal("arrowtoeast.png"));
                    break;
                case ArrowToWest:
                    pixmap200 = new Pixmap(Gdx.files.internal("arrowtowest.png"));
                    break;
                case HorizontalLine:
                    pixmap200 = new Pixmap(Gdx.files.internal("linehoriz.png"));
                    break;
                case ArrowToNorth:
                    pixmap200 = new Pixmap(Gdx.files.internal("arrowtonorth.png"));
                    break;
                case ArrowToSouth:
                    pixmap200 = new Pixmap(Gdx.files.internal("arrowtosouth.png"));
                    break;
                case VerticalLine:
                    pixmap200 = new Pixmap(Gdx.files.internal("linevert.png"));
                    break;
                case UpToLeft:
                    pixmap200 = new Pixmap(Gdx.files.internal("LineCorner03.png"));
                    break;
                case UpToRight:
                    pixmap200 = new Pixmap(Gdx.files.internal("LineCorner04.png"));
                    break;
                case DownToLeft:
                    pixmap200 = new Pixmap(Gdx.files.internal("LineCorner02.png"));
                    break;
                case DownToRight:
                    pixmap200 = new Pixmap(Gdx.files.internal("LineCorner01.png"));
                    break;
                    default:
                        pixmap200 = new Pixmap(Gdx.files.internal("linehoriz.png"));
            }


            // Изменяем размер загружаемой картинки из файла на заданный
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

    private SelDirection findDirection ( Item firstItem, Item secondItem){
        SelDirection selDirection = SelDirection.None;
        if(firstItem==null || secondItem==null)return  selDirection;

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

    public SelDirection findDirection (){
        SelDirection selDirection = SelDirection.None;
        if (previous==null){
            if (next ==null) {
                return selDirection;
            }else{
                if (next.getRow()!=this.getRow()){
                    selDirection= SelDirection.VerticalLine;
                }else{
                    selDirection= SelDirection.HorizontalLine;
                }
                return selDirection;
            }
        }

        if (next==null){
            selDirection = findDirection(previous, this);
            return selDirection;

        }
        ItemPos prevPos = findItemPos(previous);
        ItemPos nextPos = findItemPos(next);
        //System.out.println("prevPos "+ prevPos+ "  Col "+previous.getCol()+"  Row "+previous.getRow());
        //System.out.println("nextPos "+nextPos+ "  Col "+next.getCol()+"  Row "+next.getRow());
        if (prevPos==ItemPos.Up && nextPos==ItemPos.Right) selDirection=SelDirection.UpToRight;
        if (prevPos==ItemPos.Up && nextPos==ItemPos.Down) selDirection=SelDirection.VerticalLine;
        if (prevPos==ItemPos.Up && nextPos==ItemPos.Left) selDirection=SelDirection.UpToLeft;
        if (prevPos==ItemPos.Right && nextPos==ItemPos.Down) selDirection=SelDirection.DownToRight;
        if (prevPos==ItemPos.Right && nextPos==ItemPos.Left) selDirection=SelDirection.HorizontalLine;
        if (prevPos==ItemPos.Right && nextPos==ItemPos.Up) selDirection=SelDirection.UpToRight;
        if (prevPos==ItemPos.Down && nextPos==ItemPos.Left) selDirection=SelDirection.DownToLeft;
        if (prevPos==ItemPos.Down && nextPos==ItemPos.Up) selDirection=SelDirection.VerticalLine;
        if (prevPos==ItemPos.Down && nextPos==ItemPos.Right) selDirection=SelDirection.DownToRight;
        if (prevPos==ItemPos.Left && nextPos==ItemPos.Up) selDirection=SelDirection.UpToLeft;
        if (prevPos==ItemPos.Left && nextPos==ItemPos.Right) selDirection=SelDirection.HorizontalLine;
        if (prevPos==ItemPos.Left && nextPos==ItemPos.Down) selDirection=SelDirection.DownToLeft;
        return selDirection;
    }

    public ItemPos findItemPos (Item item)
    {
        if (item.getRow()<cell.getRow()) return ItemPos.Down;
        if (item.getRow()>cell.getRow()) return ItemPos.Up;
        if (item.getCol()>cell.getCol()) return ItemPos.Right;
        if (item.getCol()<cell.getCol()) return ItemPos.Left;
        return ItemPos.None;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected, Item previous) {
        this.selected = selected;
        if(selected==false){
            this.previous=null;
            this.next=null;
        }

        if (previous != this) this.previous= previous;
        this.selectedDirection = findDirection(previous,this);
        AddImageDirection();
    }

    public void setSelectedNext(Item next){
        this.next = next;
        this.selectedDirection =findDirection();
        AddImageDirection();
    }

    public int getRow() {
        return cell.getRow();
    }

    public int getCol() {
        return cell.getCol();
    }

    public void setCell(Cell cell) {
        this.cell = new Cell(cell);

    }

    public boolean isNear(int row2 , int col2){
        //Диагонали?
        //System.out.println(row2+" "+col2+" "+row+" "+col);
        if(!((row2!=cell.getRow())&&(cell.getCol()!=col2))){
            //Расстояние?
            if((Math.abs(cell.getRow()-row2)<2)&&(Math.abs(cell.getCol()-col2)<2)) {
                return true;
            }
        }
      return false;
    }

    public Item getNext(){
        return next;
    }

    public int getCountOfSelectedItems()
    {
        int count=1;
        Item item1 = this;
        while (item1.getNext()!=null){
            item1 = item1.getNext();
            count++;
        }
        return count;
    }
}
