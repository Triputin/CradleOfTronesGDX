package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class GameCell extends BaseActor {
    private Cell cell;
    private int lockLevel = 0;
    public GameCell(float x, float y, int width, int height, Stage s, Touchable touchable, int row, int col, CradleGame cradleGame)
    {
        super(x,y,s, touchable, cradleGame);
        cell = new Cell(row,col);
        setHeight(height);
        setWidth(width);
        //loadTexture("gamecell.png", (int) getWidth(), (int) getHeight());
        setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.GAMECELL_ANIMATION_ID));
    }

    public int getLockLevel() {
        return lockLevel;
    }

    public void setLockLevel(int lockLevel) {
        this.lockLevel = lockLevel;
        if(lockLevel == 0) {
            //loadTexture("gamecell.png", (int) getWidth(), (int) getHeight());
            setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.GAMECELL_ANIMATION_ID));
        }else {
            if (lockLevel == 1){
                //loadTexture("gamecellock01.png", (int) getWidth(), (int) getHeight());
                setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.GAMECELL_LOCK01_ANIMATION_ID));
            } else {
                //loadTexture("gamecellock02.png", (int) getWidth(), (int) getHeight());
                setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.GAMECELL_LOCK02_ANIMATION_ID));
            }

        }
    }

    public void lowerLockLevel(){
        this.lockLevel--;
        if(this.lockLevel<0){
            this.lockLevel =0;
        } else
        {
            setLockLevel(this.lockLevel);
        }

    }

    public Cell getCell() {
        return cell;
    }

    public void unLock(Cell cell){
        if(cell.isEqual(this.cell)){
            this.lowerLockLevel();
            //loadTexture("gamecell.png", (int) getWidth(), (int) getHeight());
        }

    }
}
