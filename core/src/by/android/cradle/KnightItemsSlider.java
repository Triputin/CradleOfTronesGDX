package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

public class KnightItemsSlider extends BaseActor {
    private ArrayList<KnightItem> knightPassiveItemsArrayList;
    private Stage stage;
    private Knight knight;
    private int cellSize;
    private CradleGame cradleGame;


    public KnightItemsSlider(float x, float y, int width, int height, Stage s, Touchable touchable, Knight knight, CradleGame cradleGame) {
        super(x, y, s, touchable);
        this.stage = s;
        this.knight = knight;
        this.cradleGame = cradleGame;

        setHeight(height);
        setWidth(width);
        this.cellSize = height;


        //Tiled texture
        Texture texture = new Texture(Gdx.files.internal("fon/tiled01.png"), true);
        texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.setRegion(0,0,texture.getWidth()*4,texture.getHeight()*4);
        Image image4 = new Image(textureRegion);
        image4.setSize(width,height);
        image4.setPosition(0,0);
        addActor(image4);

        knightPassiveItemsArrayList = new ArrayList<>();


    }

    public void showKnightItems(Knight knight){
        this.knight = knight;
        //clear Items
        ArrayList<Item> arrayListItems = new ArrayList<>();
        for (Actor a : stage.getActors())
        {
            if ( Item.class.isAssignableFrom(a.getClass()) ){
                arrayListItems.add((Item)a);
            }

        }

        for(int i =0;i<arrayListItems.size();i++){
            arrayListItems.get(i).remove();
        }

        //show items
        if (knight!=null) {
            ArrayList<KnightItemParams> passiveKnightItemParams = knight.getPassiveKnightItemParams();
            int x = Math.round(getX());
            int y = Math.round(getY());
            int i = 0;
            for (KnightItemParams knightItemParams : passiveKnightItemParams) {
                KnightItem knightItem = new KnightItem(x + cellSize * i, y, cellSize, cellSize, stage, 0, i, cradleGame, knightItemParams);
                knightPassiveItemsArrayList.add(knightItem);
                //addActor(knightItem);
                i++;
            }
        }

    }


    public ArrayList<KnightItem> getKnightPassiveItemsArrayList(){
        return knightPassiveItemsArrayList;
    }
}
