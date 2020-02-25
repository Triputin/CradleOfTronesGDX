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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class KnightItemsSlider extends BaseActor {
    private ArrayList<KnightItem> knightPassiveItemsArrayList;
    private Stage stage;
    private Knight knight;
    private int cellSize;
    private CradleGame cradleGame;
    private int sliderPos;
    private int qttyOfVisibleItems;
    KnightScreen knightScreen;


    public KnightItemsSlider(float x, float y, int width, int height, Stage s, Touchable touchable, Knight knight, CradleGame cradleGame, KnightScreen knightScreen) {
        super(x, y, s, touchable);
        this.stage = s;
        this.knight = knight;
        this.cradleGame = cradleGame;
        this.sliderPos = 0;
        this.knightScreen = knightScreen;

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

        float w = getWidth();
        float h = getHeight();
        float buttonSize = h*0.8f;
        qttyOfVisibleItems = (int) Math.floor((w - cellSize*2)/h);

        //Next Button
        Button.ButtonStyle buttonStyleN = new Button.ButtonStyle();
        Texture buttonTexN = new Texture( Gdx.files.internal("forward_button.png") );
        TextureRegion buttonRegionN =  new TextureRegion(buttonTexN);
        buttonStyleN.up = new TextureRegionDrawable( buttonRegionN );

        Button nextButton = new Button( buttonStyleN );
        nextButton.setSize(buttonSize,buttonSize);
        nextButton.setPosition(w-buttonSize,h*0.1f);
        addActor(nextButton);

        nextButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                incrSliderPos();
                return true;
            }
        });

        //Prev Button
        Button.ButtonStyle buttonStyleP = new Button.ButtonStyle();
        Texture buttonTexP = new Texture( Gdx.files.internal("backward_button.png") );
        TextureRegion buttonRegionP =  new TextureRegion(buttonTexP);
        buttonStyleP.up = new TextureRegionDrawable( buttonRegionP );

        Button prevButton = new Button( buttonStyleP );
        prevButton.setSize(buttonSize,buttonSize);
        prevButton.setPosition(0,h*0.1f);
        addActor(prevButton);

        prevButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                decrSliderPos();
                return true;
            }
        });

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

        knightPassiveItemsArrayList.clear();

        //create passive items
        if (knight!=null) {
            ArrayList<KnightItemParams> passiveKnightItemParams = knight.getPassiveKnightItemParams();
            int x = Math.round(getX()+cellSize);
            int y = Math.round(getY());
            int i = 0;

            //Create Items
            for (KnightItemParams knightItemParams : passiveKnightItemParams) {
                KnightItem knightItem = new KnightItem(-cellSize, y, cellSize, cellSize, stage, 0, i, cradleGame, knightItemParams,knightScreen);
                knightPassiveItemsArrayList.add(knightItem);
                i++;
            }

            //Show Items
            setSliderPos(0);
        }


        //show active items
        KnightItem knightItem;
        if (knight!=null) {
            ArrayList<KnightItemParams> activeKnightItemParams = knight.getActiveKnightItemParams();
            for (KnightItemParams knightItemParams: activeKnightItemParams){
                knightItem = new KnightItem(-cellSize, 0, cellSize, cellSize, stage, 0, 0, cradleGame, knightItemParams,knightScreen);
                switch(knightItemParams.getKnightItemType()){
                    case Shield:
                        knightItem.centerAtActor(knightScreen.knightActiveItemPlaceShield);
                        knightScreen.knightActiveItemPlaceShield.setKnightItem(knightItem);
                        break;
                    case Helmet:
                        knightItem.centerAtActor(knightScreen.knightActiveItemPlaceHelmet);
                        knightScreen.knightActiveItemPlaceHelmet.setKnightItem(knightItem);
                        break;
                    case Armor:
                        knightItem.centerAtActor(knightScreen.knightActiveItemPlaceArmor);
                        knightScreen.knightActiveItemPlaceArmor.setKnightItem(knightItem);
                        break;
                    case Boots:
                        knightItem.centerAtActor(knightScreen.knightActiveItemPlaceBoots);
                        knightScreen.knightActiveItemPlaceBoots.setKnightItem(knightItem);
                        break;
                    case Sword:
                        knightItem.centerAtActor(knightScreen.knightActiveItemPlaceSword);
                        knightScreen.knightActiveItemPlaceSword.setKnightItem(knightItem);
                        break;
                    case Gloves:
                        knightItem.centerAtActor(knightScreen.knightActiveItemPlaceGloves);
                        knightScreen.knightActiveItemPlaceGloves.setKnightItem(knightItem);
                        break;
                }
            }

        }


    }


    public ArrayList<KnightItem> getKnightPassiveItemsArrayList(){
        return knightPassiveItemsArrayList;
    }

    public void setSliderPos(int pos){
        int y = Math.round(getY());
        sliderPos = pos;
        int qt = 0;
        for (int j=0;j<knightPassiveItemsArrayList.size();j++){
            if ((j>=sliderPos)&& (qt<qttyOfVisibleItems)) {
                knightPassiveItemsArrayList.get(j).setPosition(cellSize + qt * cellSize, y);
                qt++;
            } else {
                knightPassiveItemsArrayList.get(j).setPosition(-cellSize , y);
            }
        }
    }

    public void decrSliderPos() {
        if (sliderPos > 0) {
            setSliderPos(sliderPos - 1);
        }
    }

    public void incrSliderPos() {
        int qty = knightPassiveItemsArrayList.size();

        if ((sliderPos+qttyOfVisibleItems)<qty) {
            setSliderPos(sliderPos + 1);
        }
    }

}
