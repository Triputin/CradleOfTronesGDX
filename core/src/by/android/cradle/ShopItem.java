package by.android.cradle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

// don't used because of no image
public class ShopItem extends BaseActor {

    private int qtty;
    private Item item;
    private Label label;

    public ShopItem(int x, int y, Stage s, Item item, int qtty)
    {
        super(x,y,s, Touchable.enabled);
        this.item = item;
        this.qtty = qtty;

        this.addActor(item);

        label = new Label(""+qtty, BaseGame.labelStyle);
        //label.setText("Resources available for trade");
        label.setColor( Color.GOLDENROD );
        label.setPosition( +item.getWidth()/2,item.getHeight()*0.05f);
        label.setFontScale(0.5f);
        //s.addActor(label);
        this.addActor(label);

    }


    public void setSelected(boolean selected){
        item.setSelected(selected, null);
    }

}
