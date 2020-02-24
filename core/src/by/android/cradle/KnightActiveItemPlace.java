package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class KnightActiveItemPlace extends DropTargetActor{
    KnightItemType knightItemType;

    public KnightActiveItemPlace(float x, float y, int width, int height, Stage s, KnightItemType knightItemType)
    {
        super(x,y,s);
        this.knightItemType = knightItemType;
        setHeight(height);
        setWidth(width);
        setBoundaryPolygon(8);
        AddImage("knightitems/frame01.png",0,0,Math.round(getWidth()),Math.round(getHeight()));
    }

    @Override
    public int getTargetType(){
        return knightItemType.getValue();
    }

}
