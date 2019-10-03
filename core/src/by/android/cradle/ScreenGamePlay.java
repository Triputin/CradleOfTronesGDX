package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ScreenGamePlay extends BaseScreen {
    private final int CellSize = 128;
    private final int CellCount = 5;
    private GameField gameField;
    private float gameFieldX;
    private float gameFieldY;
    private boolean flag = false ;
    private Item lastSelectedItem;
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
                            }
                            ((Coin)CoinActor).setSelected(true, lastSelectedItem.findDirection(lastSelectedItem,(Item)CoinActor));
                            lastSelectedItem =(Coin) CoinActor;
                        }
                    }
                    //Coin2
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Coin2")){
                        if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldX)){
                            if (lastSelectedItem==null){
                                lastSelectedItem =(Coin2) CoinActor;
                            }
                            ((Coin2)CoinActor).setSelected(true, lastSelectedItem.findDirection(lastSelectedItem,(Item)CoinActor));
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
                    String className = lastSelectedItem.getClass().getName();
                    if ( ie.getType().equals(InputEvent.Type.touchUp) ){
                        for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, className)){
                                ((Item)CoinActor).setSelected(false,SelDirection.None);

                        }
                        lastSelectedItem=null;
                    }
            }
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y){

               return false;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer){
                InputEvent ie = (InputEvent)event;
                String className = lastSelectedItem.getClass().getName();
                if(flag){
                    if ( ie.getType().equals(InputEvent.Type.touchDragged)){

                        for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, className)){
                            if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldX)){
                                if(lastSelectedItem.isNear(((Item)CoinActor).getRow(),((Item)CoinActor).getCol())){
                                    if(!((Item)CoinActor).isSelected()){
                                        if (lastSelectedItem==null){
                                            lastSelectedItem =(Item) CoinActor;
                                        }
                                        ((Item)CoinActor).setSelected(true, lastSelectedItem.findDirection(lastSelectedItem,(Item)CoinActor));
                                        if (lastSelectedItem.getSelectedDirection()==SelDirection.None){
                                            lastSelectedItem.setSelected(true,lastSelectedItem.findDirection(lastSelectedItem,(Item)CoinActor));
                                        }
                                        SelDirection d1 = lastSelectedItem.getSelectedDirection();
                                        SelDirection d2 = ((Item)CoinActor).getSelectedDirection();
                                        if (d1!=d2){

                                        }
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
}
