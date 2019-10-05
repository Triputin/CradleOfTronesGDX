package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class ScreenGamePlay extends BaseScreen {
    private final int CellSize = 128;
    private final int CellCount = 5;
    private GameField gameField;
    private float gameFieldX;
    private float gameFieldY;
    private boolean flag = false ;
    private Item lastSelectedItem;
    private Item firstSelectedItem;

    public void initialize()
    {
        BaseActor.setWorldBounds(CellSize*CellCount,CellSize*CellCount);
        gameFieldX=0;
        gameFieldY=0;
        gameField = new GameField(gameFieldX,gameFieldX,mainStage,CellSize*CellCount,CellSize*CellCount);

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
                if (Math.random()>0.5){
                    new Coin2(gameFieldX + i * CellSize, gameFieldX + j * CellSize, CellSize, CellSize, mainStage, j, i);
                }else {
                    new Coin(gameFieldX + i * CellSize, gameFieldX + j * CellSize, CellSize, CellSize, mainStage, j, i);
                }
            }
        }



    }
    public void update(float dt)
    {

    }

    public ArrayList<Cell> removeSelectedItems(){
        ArrayList<Cell> arrayList = new ArrayList<>();
        if (firstSelectedItem==null)return arrayList;
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
        Cell cell;
        int x0;
        int y0;
        int x;
        int y;
        cell = arrayList.get(0);
        x0=cell.getCol();
        y0=cell.getRow();

        for (int i=0; i<arrayList.size();i++){
            Item item;
            cell = arrayList.get(i);
            x=cell.getCol();
            y=cell.getRow();
            item = item=new Jem01(gameFieldX +x0 * CellSize, gameFieldX + y0 * CellSize, CellSize, CellSize, mainStage, x, y);
            Action spin = Actions.rotateBy(30, 1);
            item.addAction(Actions.moveTo(gameFieldX +x * CellSize, gameFieldX + y * CellSize,1));
            //item.addAction( Actions.forever(spin) );
        }


    }
}
