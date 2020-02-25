package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class KnightScreen extends BaseScreen {

    private final int RowColCount = 5;
    private int cellSize;
    private int padding = 5;
    private Knight knight;
    private Weapon weapon;

    private Label mightLabel;
    private Label lifeLabel;
    private Label speedLabel;

    private ArrayList<KnightItem> knightActiveItemsArrayList;
    private ArrayList<KnightItem> knightPassiveItemsArrayList;

    private KnightItemsSlider knightItemsSlider;

    public KnightActiveItemPlace knightActiveItemPlaceHelmet;
    public KnightActiveItemPlace knightActiveItemPlaceArmor;
    public KnightActiveItemPlace knightActiveItemPlaceGloves;
    public KnightActiveItemPlace knightActiveItemPlaceShield;
    public KnightActiveItemPlace knightActiveItemPlaceSword;
    public KnightActiveItemPlace knightActiveItemPlaceBoots;

    public KnightScreen(CradleGame cradleGame, IPlayServices ply) {

        super(cradleGame, ply);
    }


    public void initialize() {
        knightActiveItemsArrayList = new ArrayList<>();
        knightPassiveItemsArrayList = new ArrayList<>();

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        int wh;
        if (w > h) {
            wh = h;
        } else {
            wh = w;
        }
        cellSize = wh / RowColCount;

        //fon
        BaseActor hall = new BaseActor(0, 0, mainStage, Touchable.disabled);
        hall.loadTexture("hall01.png", w, h);


        //Size of lines
        int lineX = Math.round(w * 0.49f);
        int lineY = Math.round(h * 0.6f);
        int lineSize = Math.round(h * 0.18f);
        int lineSizeW = Math.round(w * 0.51f);

        // Might
        BaseActor might = new BaseActor(lineX, lineY, mainStage, Touchable.disabled);
        might.setSize(lineSizeW, lineSize);
        might.loadTexture("knights/plate01.png", lineSizeW, lineSize);

        BaseActor mightBadge = new BaseActor(0, 0, mainStage, Touchable.disabled);
        mightBadge.setSize(lineSize * 0.95f, lineSize * 0.95f);
        mightBadge.loadTexture("knights/sword_badge.png", Math.round(mightBadge.getWidth()), Math.round(mightBadge.getHeight()));
        mightBadge.setPosition(mightBadge.getWidth() * 0.3f, mightBadge.getWidth() * 0.05f);
        might.addActor(mightBadge);

        mightLabel = new Label(" ", BaseGame.labelStyle);
        String s = String.valueOf(cradleGame.getKnightParams().getCellsQttyToDestroy());
        mightLabel.setText(s);
        mightLabel.setColor(Color.GOLDENROD);
        mightLabel.setPosition(lineX + lineSizeW / 1.8f, lineY + might.getHeight() * 0.4f);
        mightLabel.setFontScale(3.0f);
        mainStage.addActor(mightLabel);


        // Life
        BaseActor life = new BaseActor(lineX, lineY - lineSize - 10, mainStage, Touchable.disabled);
        life.setSize(lineSizeW, lineSize);
        life.loadTexture("knights/plate01.png", lineSizeW, lineSize);

        BaseActor lifeBadge = new BaseActor(0, 0, mainStage, Touchable.disabled);
        lifeBadge.setSize(lineSize * 0.95f, lineSize * 0.95f);
        lifeBadge.loadTexture("knights/heart.png", Math.round(lifeBadge.getWidth()), Math.round(lifeBadge.getHeight()));
        lifeBadge.setPosition(lifeBadge.getWidth() * 0.3f, lifeBadge.getWidth() * 0.05f);
        life.addActor(lifeBadge);

        lifeLabel = new Label(" ", BaseGame.labelStyle);
        s = String.valueOf(cradleGame.getKnightParams().getHealth());
        String sc = cradleGame.getLanguageStrings().get("of");
        s = s + " " + sc + " " + String.valueOf(cradleGame.getKnightParams().getCurrentHealthMaximum());
        lifeLabel.setText(s);
        lifeLabel.setColor(Color.GOLDENROD);
        lifeLabel.setPosition(lineX + lineSizeW / 2.9f, lineY - lineSize -10 + life.getHeight() * 0.4f);
        lifeLabel.setFontScale(3.0f);
        mainStage.addActor(lifeLabel);

        // Speed
        BaseActor speedActor = new BaseActor(lineX, lineY - lineSize*2 - 20, mainStage, Touchable.disabled);
        speedActor.setSize(lineSizeW, lineSize);
        speedActor.loadTexture("knights/plate01.png", lineSizeW, lineSize);

        BaseActor speedBadge = new BaseActor(0, 0, mainStage, Touchable.disabled);
        speedBadge.setSize(lineSize * 0.95f, lineSize * 0.95f);
        speedBadge.loadTexture("knightitems/speed_badge.png", Math.round(speedBadge.getWidth()), Math.round(speedBadge.getHeight()));
        speedBadge.setPosition(speedBadge.getWidth() * 0.3f, speedBadge.getWidth() * 0.05f);
        speedActor.addActor(speedBadge);

        speedLabel = new Label(" ", BaseGame.labelStyle);
        s = String.valueOf(cradleGame.getKnightParams().getRechargeWeaponTime());
        speedLabel.setText(s);
        speedLabel.setColor(Color.GOLDENROD);
        speedLabel.setPosition(lineX + lineSizeW / 1.8f, lineY - lineSize*2 - 20 + speedActor.getHeight() * 0.4f);
        speedLabel.setFontScale(3.0f);
        mainStage.addActor(speedLabel);





        int knSize = Math.round(h * 0.4f);
        int wpSize = Math.round(h * 0.1f);
        if (knight != null) {
            knight.remove();
        }
        knight = new Knight(-knSize * 0.1f, h - knSize + knSize * 0.1f, knSize, knSize, mainStage, cradleGame.getKnightParams());

        if (weapon != null) {
            weapon.remove();
        }
        weapon = new Weapon(knSize * 0.585f, h - knSize * 0.39f, wpSize, wpSize, mainStage, cradleGame, knight);
        weapon.setDraggable(false);

        //show active items places
        int ItemPlaceSize = Math.round(cellSize*1.18f);
        int itemPlaceLeftSpace = Math.round(w*0.1f);
        knightActiveItemPlaceHelmet = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize,cellSize+ItemPlaceSize*2, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Helmet);
        knightActiveItemPlaceArmor = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize,cellSize+ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Armor);
        knightActiveItemPlaceGloves = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize*2,cellSize+ItemPlaceSize*2, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Gloves);
        knightActiveItemPlaceShield = new KnightActiveItemPlace(  itemPlaceLeftSpace,cellSize+ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Shield);
        knightActiveItemPlaceSword = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize*2,cellSize+ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Sword);
        knightActiveItemPlaceBoots = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize,cellSize, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Boots);


        Label activeItemsLabel = new Label(" ", BaseGame.labelStyle);
        String str = cradleGame.getLanguageStrings().get("activeitems");
        activeItemsLabel.setText(str);
        activeItemsLabel.setColor( Color.GOLDENROD );
        activeItemsLabel.setPosition(itemPlaceLeftSpace+ItemPlaceSize,wh*0.93f);
        activeItemsLabel.setFontScale(1.2f);
        mainStage.addActor(activeItemsLabel);

        Label inventoryLabel = new Label(" ", BaseGame.labelStyle);
        str = cradleGame.getLanguageStrings().get("inventory");
        inventoryLabel.setText(str);
        inventoryLabel.setColor( Color.GOLDENROD );
        inventoryLabel.setPosition(w*0.05f,cellSize);
        inventoryLabel.setFontScale(1.2f);
        mainStage.addActor(inventoryLabel);

        //Back button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture(Gdx.files.internal("back_button02.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        Button backButton = new Button(buttonStyle);
        backButton.setSize(h * 0.2f, h * 0.2f);
        backButton.setPosition(w - backButton.getWidth() + 10, h - backButton.getHeight() - 5);
        uiStage.addActor(backButton);

        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent) event;
                if (ie.getType().equals(InputEvent.Type.touchDown)) {
                    cradleGame.setActiveGameMapScreen(false);
                    return true;
                }
                return false;
            }
        });

        //Slider
        knightItemsSlider = new KnightItemsSlider(0, h * 0.00f, w, cellSize,
                mainStage, Touchable.enabled, knight, cradleGame,this);

        knightItemsSlider.showKnightItems(knight);

    }


    public void update(float dt) {

    }

    public void SetParams(KnightParams knightParams) {


        //Might
        String s = String.valueOf(cradleGame.getKnightParams().getCellsQttyToDestroy());
        mightLabel.setText(s);

        //life
        s = String.valueOf(cradleGame.getKnightParams().getHealth());
        String sc = cradleGame.getLanguageStrings().get("of");
        s = s + " " + sc + " " + String.valueOf(cradleGame.getKnightParams().getCurrentHealthMaximum());
        lifeLabel.setText(s);


        //Speed
        s = String.valueOf(cradleGame.getKnightParams().getRechargeWeaponTime());
        speedLabel.setText(s);


        //Knight
        knight.setKnightParams(knightParams);

        knightItemsSlider.showKnightItems(knight);
    }


    public void moveToActiveItemParams(KnightItem knightItem){
        cradleGame.getKnightParams().moveToActiveItemParams(knightItem.getKnightItemParams());
        //knightItemsSlider.showKnightItems(knight);
        SetParams(cradleGame.getKnightParams());
    }


}

