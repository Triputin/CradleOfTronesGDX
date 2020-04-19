package by.android.cradle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

// don't used because of no image
public class ShopItem extends BaseActor {

    private int qtty;
    private boolean showQtty;
    boolean isSelected;
    private Label label;
    private String normalStateString;
    private String selectedStateString;
    private String itemType;
    private int itemCost;

    public ShopItem(int x, int y, int width, int height , Stage s, int qtty, String normalStateString, String selectedStateString, String itemType, int itemCost, CradleGame cradleGame)
    {
        super(x,y,s,Touchable.enabled,cradleGame);
        setWidth(width);
        setHeight(height);

        this.qtty = qtty;
        showQtty=true;
        this.normalStateString = normalStateString;
        this.selectedStateString = selectedStateString;
        this.itemType = itemType;
        this.itemCost = itemCost;
        label = new Label(""+qtty, BaseGame.labelStyle);
        //label.setText("Resources available for trade");
        label.setColor( Color.GOLDENROD );
        label.setPosition( +getWidth()/2-label.getWidth()/2,0);
        label.setFontScale(1.1f);
        this.addActor(label);
        label.setVisible(false);
        setSelected(false);
    }


    public void setSelected(boolean selected) {
        if(selected){
            loadTexture(selectedStateString, (int) getWidth(), (int) getWidth());
        }else {
            loadTexture(normalStateString, (int) getWidth(), (int) getWidth());
        }

        isSelected = selected;

    }

    public boolean isSelected(){
        return isSelected;
    }

    public boolean isShowQtty() {
        return showQtty;
    }

    public void setShowQtty(boolean showQtty) {
        this.showQtty = showQtty;
        label.setVisible(showQtty);
    }


    public int getQtty() {
        return qtty;
    }

    public void setQtty(int qtty) {
        this.qtty = qtty;
        label.setText(""+qtty);
        label.setPosition( +getWidth()/2-label.getWidth()/2,0);
    }

    public float getItemCost() {
        return itemCost;
    }

    public void setItemCost(int itemCost) {
        this.itemCost = itemCost;
    }

    public String getItemType() {
        return itemType;
    }
}
