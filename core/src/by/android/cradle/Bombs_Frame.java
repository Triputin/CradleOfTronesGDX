package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Bombs_Frame extends BaseActor {

    private Actor lock01;
    private Actor lock02;
    private Actor lock03;
    private Actor frame;

    public Bombs_Frame(float x, float y, int width, int height, Stage s, CradleGame cradleGame) {
        super(x, y, s, Touchable.enabled, cradleGame);
        setSize(width, height);
        setBoundaryPolygon(8);


        int bomb_lock_size = cradleGame.getBomb_lock_size();
        int bomb_lockX = Math.round(width*0.06f);

        lock01 = cradleGame.getCradleAssetManager().AddImage(Assets.BOMB_LOCK,bomb_lockX,Math.round(bomb_lock_size*1.65f),bomb_lock_size,bomb_lock_size);
        lock02 = cradleGame.getCradleAssetManager().AddImage(Assets.BOMB_LOCK,bomb_lockX,Math.round(bomb_lock_size*0.84f),bomb_lock_size,bomb_lock_size);
        lock03 = cradleGame.getCradleAssetManager().AddImage(Assets.BOMB_LOCK,bomb_lockX,0,bomb_lock_size,bomb_lock_size);
        frame = cradleGame.getCradleAssetManager().AddImage(Assets.BOMBS_FRAME,0,0,width,height);

        addActor(lock01);
        addActor(lock02);
        addActor(lock03);
        addActor(frame);

        //setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.BOMBS_FRAME_ANIMATION_ID));

        int maxOpenedMapLevel = cradleGame.getMaxOpenedMapLevel();

        switch(maxOpenedMapLevel){

            case 1:
                lock01.setVisible(true);
                lock02.setVisible(true);
                lock03.setVisible(true);
                break;
            case 2:
                lock01.setVisible(false);
                lock02.setVisible(true);
                lock03.setVisible(true);
                break;
            case 3:
                lock01.setVisible(false);
                lock02.setVisible(false);
                lock03.setVisible(true);
            break;
            default:
                lock01.setVisible(false);
                lock02.setVisible(false);
                lock03.setVisible(false);
        }


    }


}
