package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class DropTargetActor extends BaseActor
{
    private boolean targetable;
    private int dropPlaceType;
    public DropTargetActor(float x, float y, Stage s, CradleGame cradleGame)
    {
        super(x,y,s, Touchable.disabled, cradleGame);
        targetable = true;
    }

    public void setTargetable(boolean t)
    {
        targetable = t;
    }

    public boolean isTargetable()
    {
        return targetable;
    }

    public int getTargetType(){
        return 0;
    }

    public KnightItem getTargetKnightItem(){
        return null;
    }

    public int getDropPlaceType() {
        return dropPlaceType;
    }

    public void setDropPlaceType(int dropPlaceType) {
        this.dropPlaceType = dropPlaceType;
    }
}