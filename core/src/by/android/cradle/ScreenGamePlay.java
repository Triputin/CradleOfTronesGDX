package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class ScreenGamePlay extends BaseScreen {
    private int cellSize;
    private final int CellCount = 5;
    private GameField gameField;
    private float gameFieldX;
    private float gameFieldY;
    private boolean flag = false ;
    private Item lastSelectedItem;
    private Item firstSelectedItem;
    private ItemPos directionToFill;

    public void initialize()
    {
        // Get screen size
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        if (w<h) {
            h=w;
        } else w=h;
        cellSize = w/CellCount;

        BaseActor.setWorldBounds(cellSize*CellCount,cellSize*CellCount);
        gameFieldX=0;
        gameFieldY=0;
        gameField = new GameField(gameFieldX,gameFieldX,mainStage,cellSize*CellCount,cellSize*CellCount);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/undo.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button restartButton = new Button( buttonStyle );
        restartButton.setColor( Color.CYAN );
        restartButton.setPosition(720,520);
        uiStage.addActor(restartButton);

        restartButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    RestartLevel();
                }
                return false;
            }
        });
        //gameField.boundToWorld();
        gameField.setTouchable(Touchable.enabled);




        gameField.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                flag = true;

                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ){

                        //Coin
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Coin")){
                        if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldX)){
                            if (lastSelectedItem==null){
                                lastSelectedItem =(Coin) CoinActor;
                                firstSelectedItem= lastSelectedItem;
                            }
                            ((Coin)CoinActor).setSelected(true, lastSelectedItem);
                            lastSelectedItem =(Coin) CoinActor;
                        }
                    }
                    //Coin2
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Coin2")){
                        if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldX)){
                            if (lastSelectedItem==null){
                                lastSelectedItem =(Coin2) CoinActor;
                                firstSelectedItem= lastSelectedItem;
                            }
                            ((Coin2)CoinActor).setSelected(true, lastSelectedItem);
                            lastSelectedItem =(Coin2) CoinActor;
                        }
                    }
                }

                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    flag = false;
                    InputEvent ie = (InputEvent)event;
                if (lastSelectedItem==null) return;
                    String className = lastSelectedItem.getClass().getName();
                    if ( ie.getType().equals(InputEvent.Type.touchUp) ){

                        if (firstSelectedItem.getCountOfSelectedItems()>=3) {
                            FillRemovedCells(removeSelectedItems());
                        }else{
                            for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, className)){
                                ((Item)CoinActor).setSelected(false,null);
                            }
                        }
                        lastSelectedItem = null;
                        firstSelectedItem = null;
                    }
            }
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y){

               return false;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer){
                InputEvent ie = (InputEvent)event;
                if (lastSelectedItem==null) return;
                if(flag){
                    String className = lastSelectedItem.getClass().getName();
                    if ( ie.getType().equals(InputEvent.Type.touchDragged)){

                        for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, className)){
                            if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldX)){
                                if(lastSelectedItem.isNear(((Item)CoinActor).getRow(),((Item)CoinActor).getCol())){
                                    if(!((Item)CoinActor).isSelected()){
                                        if (lastSelectedItem==null){
                                            lastSelectedItem =(Item) CoinActor;
                                        }
                                        ((Item)CoinActor).setSelected(true, lastSelectedItem);
                                        if (lastSelectedItem.getSelectedDirection()==SelDirection.None){
                                            lastSelectedItem.setSelected(true,lastSelectedItem);
                                        }
                                        lastSelectedItem.setSelectedNext((Item)CoinActor);// change image after next selected
                                        lastSelectedItem=((Item)CoinActor);
                                    }

                                }

                            }
                        }
                    }
                }

            }
        });


     for(int i = 0;i<CellCount;i++){
            for(int j = 0;j<CellCount;j++){
                CreateNewItemForStart(j,i);

            }
        }



    }
    public void update(float dt)
    {

    }

    public ArrayList<Cell> removeSelectedItems(){
        ArrayList<Cell> arrayList = new ArrayList<>();
        if (firstSelectedItem==null)return arrayList;
        directionToFill = firstSelectedItem.getNext().findItemPos(firstSelectedItem);
        Item item1= firstSelectedItem;
        Item item2=null;
        while (item1.getNext()!=null){
            item2 = item1.getNext();
            arrayList.add(item1.getCell());
            item1.remove();
            item1=item2;
        }
        arrayList.add(item1.getCell());
        item1.remove();
        lastSelectedItem=null;
        firstSelectedItem=null;
        return arrayList;
    }

    public void FillRemovedCells(ArrayList<Cell> arrayList){
        ArrayList<Item> newItems = new ArrayList<>();
        ArrayList<Cell> newCells;

        Cell cell;
        int x0;
        int y0;
        int x;
        int y;
        cell = arrayList.get(0);
        x0=cell.getCol();
        y0=cell.getRow();

//Создаем змейку из существующих и новых элементов
        newCells = FindCellsToMove(cell,directionToFill);
        newItems = FindItemsToMove(newCells);
        AddNewItems(newCells,newItems,arrayList.size(),cell);

        for (int i=0; i<arrayList.size();i++){
            newItems.get(0).setCell(arrayList.get(i));
            newItems.get(0).addAction(Actions.after(Actions.moveTo(gameFieldX +newItems.get(0).getCol() * cellSize, gameFieldX + newItems.get(0).getRow() * cellSize,0.1f)));
            for(int j=1; j<newItems.size();j++){
               newItems.get(j).setCell(newCells.get(j-1));
                newItems.get(j).addAction(Actions.after(Actions.moveTo(gameFieldX +newItems.get(j).getCol() * cellSize, gameFieldX + newItems.get(j).getRow() * cellSize,0.1f)));
            }
           for(int j = 0;j<newCells.size();j++){
               newCells.get(j).setCol(newItems.get(j).getCol());
               newCells.get(j).setRow(newItems.get(j).getRow());
           }
            //Actions.forever( Actions.delay(1));
           // Action spin = Actions.rotateBy(30, 1);
            //item.addAction( Actions.forever(spin) );

        }


    }
    private ArrayList<Cell> FindCellsToMove(Cell firstCell,ItemPos itemPos){
        int count = 0;
        ArrayList<Cell> arrayList = new ArrayList<>();
        Cell cell;
        switch (itemPos){
            case Up: count= CellCount- firstCell.getRow()-1;
            break;
            case Down: count=  firstCell.getRow();
                break;
            case Right: count= CellCount- firstCell.getCol()-1;
                break;
            case Left: count=  firstCell.getCol();
                break;
        }
        for (int i = 0;i<count;i++){
            switch (itemPos){
                case Up: cell = new Cell(firstCell.getRow()+1+i,firstCell.getCol());
                arrayList.add(cell);
                    break;
                case Down: cell = new Cell(firstCell.getRow()-1-i,firstCell.getCol());
                    arrayList.add(cell);
                    break;
                case Right:cell = new Cell(firstCell.getRow(),firstCell.getCol()+1+i);
                    arrayList.add(cell);
                    break;
                case Left: cell = new Cell(firstCell.getRow(),firstCell.getCol()-1-i);
                    arrayList.add(cell);
                    break;
            }
        }
        return arrayList;
    }
    public ArrayList<Item> FindItemsToMove (ArrayList<Cell> arrayList){
        ArrayList<Item> arrayListItems = new ArrayList<>();
        for(int i = 0;i<arrayList.size();i++){
            for (Actor a : mainStage.getActors())
            {
                if ( Item.class.isAssignableFrom(a.getClass()) ){
                    if( (arrayList.get(i).getCol()==((Item)a).getCol())&&(arrayList.get(i).getRow()==((Item)a).getRow())){
                        arrayListItems.add( (Item) a );
                    }

                }

            }
        }
        return arrayListItems;
    }
    public void AddNewItems(ArrayList<Cell> arrayListCells,ArrayList<Item> arrayListItems,int count,Cell fCell){
        Cell cell;
        Cell firstCell;
        if(arrayListCells.size()>0) {
            firstCell = arrayListCells.get(arrayListCells.size() - 1);
        }else {
            firstCell = fCell;
        }
        for(int i = 0;i<count;i++){
            switch (directionToFill){
                case Up: cell = new Cell(firstCell.getRow()+1+i,firstCell.getCol());
                    arrayListCells.add(cell);
                   arrayListItems.add( CreateNewItem(cell.getRow(),cell.getCol()));
                    break;
                case Down: cell = new Cell(firstCell.getRow()-1-i,firstCell.getCol());
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItem(cell.getRow(),cell.getCol()));
                    break;
                case Right:cell = new Cell(firstCell.getRow(),firstCell.getCol()+1+i);
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItem(cell.getRow(),cell.getCol()));
                    break;
                case Left: cell = new Cell(firstCell.getRow(),firstCell.getCol()-1-i);
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItem(cell.getRow(),cell.getCol()));
                    break;
            }

        }
    }
    private Item CreateNewItem (int row,int col){
        Item item ;
        if (Math.random()>0.5){
            item = new Coin2(gameFieldX + col * cellSize, gameFieldX + row * cellSize, cellSize, cellSize, mainStage, row, col);
        }else {
           item= new Coin(gameFieldX + col * cellSize, gameFieldX + row * cellSize, cellSize, cellSize, mainStage, row, col);
        }
        return item;
    }
    private Item CreateNewItemForStart(int row,int col){
        Item item ;
        if (Math.random()>0.5){
            item = new Coin2(gameFieldX - cellSize, gameFieldX -cellSize, cellSize, cellSize, mainStage, row, col);
        }else {
            item= new Coin(gameFieldX - cellSize, gameFieldX -cellSize, cellSize, cellSize, mainStage, row, col);
        }
        //item.addAction(Actions.scaleTo(-1,-1,1));
        item.addAction(Actions.moveTo(gameFieldX + col * cellSize, gameFieldX + row * cellSize,1));
        //item.addAction(Actions.scaleTo(1,1,5));
        return item;
    }

    private void RestartLevel() {
        mainStage.clear();
        initialize();

    }
}
