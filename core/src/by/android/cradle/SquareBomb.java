package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.TimeUtils;


public class SquareBomb extends DragAndDropActor {
    private int squareSize;// qtty cells around of center 1 = 9 cells, 2 = 25 cells
    private ScreenGamePlay screenGamePlay;

    public SquareBomb(float x, float y, int width, int height, Stage s, Touchable touchable, int squareSize,ScreenGamePlay screenGamePlay,CradleGame cradleGame) {
        super(x, y, s, cradleGame);
        setHeight(height);
        setWidth(width);
        this.squareSize = squareSize;
        this.screenGamePlay = screenGamePlay;
        if (squareSize==1) {
            //loadTexture("squarebomb01.png", (int) getWidth(), (int) getHeight());
            setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.BOMBSQUARE01_ANIMATION_ID));
        } else {
            //loadTexture("squarebomb02.png", (int) getWidth(), (int) getHeight());
            setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.BOMBSQUARE02_ANIMATION_ID));
        }

//setColor(100,0,0,0);

    }

    public void onDrop() {
        if (hasDropTarget()) {
            System.out.println("Square bomb");
            Item item = (Item) dropTarget;
            System.out.println("Drop target item row "+ item.getRow()+" col "+item.getCol());
            self.remove();
            screenGamePlay.RemoveAndFillSquare(item.getRow(),item.getCol(),squareSize);

            if (squareSize==1) {
                GameRes.SquareBomb1--;
                screenGamePlay.setSquareBomb1QttyLabelText(String.valueOf(GameRes.SquareBomb1));
            }else{
                GameRes.SquareBomb2--;
                screenGamePlay.setSquareBomb2QttyLabelText(String.valueOf(GameRes.SquareBomb2));
            }

        }
    }


}

