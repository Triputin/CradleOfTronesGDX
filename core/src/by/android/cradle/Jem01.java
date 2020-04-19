package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Jem01 extends Item {

    public Jem01(float x, float y, int width, int height, Stage s, int row, int col, CradleGame cradleGame) {
        super(x, y, width, height, s, Touchable.disabled, row, col, cradleGame);
        setSelected(false, null);
        setBoundaryPolygon(8);
    }

    @Override
    public void setSelected(boolean selected, Item prev) {
        if (selected) {
            //loadTextureFromAssets(Assets.JEM01_PRESSED, (int) getWidth(), (int) getHeight());
            setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.JEM01_PRESSED_ANIMATION_ID));
        } else {
            //loadTextureFromAssets(Assets.JEM01, (int) getWidth(), (int) getHeight());
            setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.JEM01_ANIMATION_ID));
        }

        super.setSelected(selected, prev);
    }

    @Override
    public void setLockLevel(int lockLevel) {
        if (cradleGame.getGameMapLevel()!=4){
            super.setLockLevel(lockLevel);
        } else {
            switch (lockLevel){

                case 1:
                    if (lockImage01==null){
                        lockImage01 = AddImage("gem01_frosen.png",0,0, (int) getWidth(), (int) getHeight());
                    }
                    if (this.lockLevel ==0){
                        addActor(lockImage01);
                    }
                    if (this.lockLevel == 2){
                        lockImage02.remove();
                    }
                    break;
                case 2:
                    if (lockImage01==null){
                        lockImage01 = AddImage("gem01_frosen.png",0,0, (int) getWidth(), (int) getHeight());
                    }
                    if (lockImage02==null){
                        lockImage02 = AddImage("lockitem02.png",0,0, (int) getWidth(), (int) getHeight());
                    }
                    if (this.lockLevel ==0){
                        addActor(lockImage01);
                        addActor(lockImage02);
                    }
                    if (this.lockLevel ==1){
                        addActor(lockImage02);
                    }
                    if (this.lockLevel == 2){
                        lockImage02.remove();
                    }
                    break;

                case 0:
                    if (this.lockLevel == 1){
                        lockImage01.remove();
                    }
                    if (this.lockLevel == 2){
                        lockImage02.remove();
                    }
                    break;


            }

            this.lockLevel = lockLevel;
        }
    }
}
