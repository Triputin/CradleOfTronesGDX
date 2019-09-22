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
    private GameField gameField;
    private float gameFieldX;
    private float gameFieldY;
    private boolean flag = false ;
    private Item lastSelectedItem;
    public void initialize()
    {
        BaseActor.setWorldBounds(800,600);
        gameFieldX=100;
        gameFieldY=100;
        gameField = new GameField(gameFieldX,gameFieldX,mainStage,256,256);
        gameField.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                flag = true;

                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ){
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Coin")){
                        if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldX)){
                            ((Coin)CoinActor).setSelected(true);
                            lastSelectedItem =(Coin) CoinActor;
                        }
                    }
                }

                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    flag = false;
                    InputEvent ie = (InputEvent)event;
                    if ( ie.getType().equals(InputEvent.Type.touchUp) ){
                        for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Coin")){
                                ((Coin)CoinActor).setSelected(false);

                        }
                    }
            }
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y){

               return false;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer){
                InputEvent ie = (InputEvent)event;
                if(flag){

                    if ( ie.getType().equals(InputEvent.Type.touchDragged)&&flag ){
                        for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Coin")){
                            if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldX)){
                                if(lastSelectedItem.isNear(((Coin)CoinActor).getRow(),((Coin)CoinActor).getCol())){
                                    if(!((Coin)CoinActor).isSelected()){
                                        ((Coin)CoinActor).setSelected(true);
                                        lastSelectedItem=((Coin)CoinActor);
                                    }

                                }

                            }
                        }
                    }
                }

            }
        });

        gameField.setHeight(256);
        gameField.setWidth(256);
       // gameField.setBounds(100,100,256,256);
        gameField.setTouchable(Touchable.enabled);
     for(int i = 0;i<4;i++){
            for(int j = 0;j<4;j++){
                new Coin(gameFieldX+i*64,gameFieldX+j*64, mainStage,j,i);
            }
        }



    }
    public void update(float dt)
    {

    }
}
