package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.awt.geom.GeneralPath;

public class TimeBomb extends DragAndDropActor {
    private SandGlass sandGlass;
    private float bombTime;

    public TimeBomb(float x, float y, int width, int height, Stage s, Touchable touchable,SandGlass sandGlass, float bombTime, CradleGame cradleGame) {
        super(x, y, s,cradleGame);
        setHeight(height);
        setWidth(width);
        this.bombTime = bombTime;
        //loadTexture("timebomb.png", (int) getWidth(), (int) getHeight());
        setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.BOMBTIME_ANIMATION_ID));
        //AddImage("timebomb.png", 0,0,(int) getWidth(), (int) getHeight());
        this.sandGlass = sandGlass;
    }

    public void onDrop() {
        if (hasDropTarget()){
            System.out.println("Time bomb");
            //sandGlass.setDuration(10);
            if (sandGlass.getElapsedTime()>bombTime){
            sandGlass.setElapsedTime(sandGlass.getElapsedTime()-60);
            }else {
                sandGlass.setElapsedTime(0);
            }
            self.remove();
            GameRes.TimeBomb--;
            cradleGame.getScreenGamePlay().setTimeBombQttyLabelText(String.valueOf(GameRes.TimeBomb));
        }
    }


}
