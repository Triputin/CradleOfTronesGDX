package by.android.cradle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class KnightItemShopPlace extends DropTargetActor {

    KnightItem knightItem;
    Label priceLabel;

    public KnightItemShopPlace(float x, float y, int width, int height, Stage s, KnightItemType knightItemType, boolean showImage)
    {
        super(x,y,s);
        setHeight(height);
        setWidth(width);
        setBoundaryPolygon(8);
        AddImage("knightitems/frame01.png",0,0,Math.round(getWidth()),Math.round(getHeight()));

        priceLabel = new Label("5000", BaseGame.labelStyle);
        priceLabel.setColor(Color.GOLDENROD);
        priceLabel.setFontScale(1.5f);
        priceLabel.setPosition(getWidth()/2 - priceLabel.getWidth()*0.7f, getHeight() * 0.01f);
        addActor(priceLabel);

        if (showImage) {
            int sizeOfSign = Math.round(getWidth() * 0.2f);
            AddImage("coin2.png", Math.round(priceLabel.getX() - sizeOfSign), Math.round(priceLabel.getY()), sizeOfSign, sizeOfSign);
        }

    }



    @Override
    public KnightItem getTargetKnightItem(){
        return knightItem;
    }

    public void setKnightItem(KnightItem knightItem){
        this.knightItem = knightItem;
        if (knightItem != null) {
            priceLabel.setText(knightItem.getKnightItemParams().getPrice());
        }else{
            priceLabel.setText("");
        }


    }

    public KnightItem getKnightItem() {
        return knightItem;
    }
}

