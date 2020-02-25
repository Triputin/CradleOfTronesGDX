package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class KnightActiveItemPlace extends DropTargetActor{
    KnightItemType knightItemType;
    KnightItem knightItem;

    public KnightActiveItemPlace(float x, float y, int width, int height, Stage s, KnightItemType knightItemType)
    {
        super(x,y,s);
        this.knightItemType = knightItemType;
        setHeight(height);
        setWidth(width);
        setBoundaryPolygon(8);
        AddImage("knightitems/frame01.png",0,0,Math.round(getWidth()),Math.round(getHeight()));
        switch(knightItemType){
            case Gloves:
                AddImage("knightitems/gloves_shadow.png",Math.round(getWidth()*0.15f),Math.round(getHeight()*0.15f),Math.round(getWidth()*0.7f),Math.round(getHeight()*0.7f));
                break;
            case Sword:
                AddImage("knightitems/sword_shadow.png",Math.round(getWidth()*0.15f),Math.round(getHeight()*0.15f),Math.round(getWidth()*0.7f),Math.round(getHeight()*0.7f));
                break;
            case Boots:
                AddImage("knightitems/boots_shadow.png",Math.round(getWidth()*0.15f),Math.round(getHeight()*0.15f),Math.round(getWidth()*0.7f),Math.round(getHeight()*0.7f));
                break;
            case Armor:
                AddImage("knightitems/armour_shadow.png",Math.round(getWidth()*0.15f),Math.round(getHeight()*0.15f),Math.round(getWidth()*0.7f),Math.round(getHeight()*0.7f));
                break;
            case Helmet:
                AddImage("knightitems/helmet_shadow.png",Math.round(getWidth()*0.15f),Math.round(getHeight()*0.15f),Math.round(getWidth()*0.7f),Math.round(getHeight()*0.7f));
                break;
            case Shield:
                AddImage("knightitems/shield_shadow.png",Math.round(getWidth()*0.15f),Math.round(getHeight()*0.15f),Math.round(getWidth()*0.7f),Math.round(getHeight()*0.7f));
                break;
        }
    }

    @Override
    public int getTargetType(){
        return knightItemType.getValue();
    }

    @Override
    public KnightItem getTargetKnightItem(){
        return knightItem;
    }

    public void setKnightItem(KnightItem knightItem){
        this.knightItem = knightItem;
    }

    public KnightItem getKnightItem() {
        return knightItem;
    }
}
