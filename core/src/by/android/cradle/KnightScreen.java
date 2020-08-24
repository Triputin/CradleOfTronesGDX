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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class KnightScreen extends BaseScreen {

    private final int RowColCount = 5;
    private int cellSize;
    private int padding = 5;
    private Knight knight;
    private Weapon weapon;
    private final float sellItemKf = 0.25f;

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

    private KnightItemShopPlace knightItemSellPlace;

    public KnightScreen(CradleGame cradleGame, IPlayServices ply) {

        super(cradleGame, ply);
    }


    public void initialize() {
        knightActiveItemsArrayList = new ArrayList<>();
        knightPassiveItemsArrayList = new ArrayList<>();

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        /*
        float fontScale = 1.0f;
        if (w>1000){
            fontScale = 2.8f;
        } else {
            fontScale = 2.0f;
        }
        */

        int wh;
        if (w > h) {
            wh = h;
        } else {
            wh = w;
        }
        cellSize = wh / RowColCount;

        //fon
        BaseActor hall = new BaseActor(0, 0, mainStage, Touchable.disabled,cradleGame);
        hall.loadTexture("hall01.png", w, h);


        //Size of lines
        int lineX = Math.round(w * 0.53f);
        int lineY = Math.round(h * 0.82f);
        int lineSize = Math.round(h * 0.16f);
        int lineSizeW = Math.round(w * 0.47f);

        // Might
        BaseActor might = new BaseActor(lineX, lineY, mainStage, Touchable.disabled,cradleGame);
        might.setSize(lineSizeW, lineSize);
        might.loadTexture("knights/plate01.png", lineSizeW, lineSize);
        might.AddImage("goldenframe02.png",0,0,lineSizeW, lineSize);

        BaseActor mightBadge = new BaseActor(0, 0, mainStage, Touchable.disabled,cradleGame);
        mightBadge.setSize(lineSize * 0.88f, lineSize * 0.88f);
        mightBadge.loadTexture("knights/sword_badge.png", Math.round(mightBadge.getWidth()), Math.round(mightBadge.getHeight()));
        mightBadge.setPosition(mightBadge.getWidth() * 0.3f, mightBadge.getWidth() * 0.05f);
        might.addActor(mightBadge);

        mightLabel = new Label(" ", BaseGame.labelStyle_Middle);
        String s = String.valueOf(cradleGame.getKnightParams().getCellsQttyToDestroy());
        mightLabel.setText(s);
        mightLabel.setColor(Color.GOLDENROD);
        mightLabel.setPosition(lineX + lineSizeW / 2.0f, lineY + might.getHeight() * 0.2f);
        //mightLabel.setFontScale(fontScale);
        mainStage.addActor(mightLabel);


        // Life
        BaseActor life = new BaseActor(lineX, lineY - lineSize - 10, mainStage, Touchable.disabled,cradleGame);
        life.setSize(lineSizeW, lineSize);
        life.loadTexture("knights/plate01.png", lineSizeW, lineSize);
        life.AddImage("goldenframe02.png",0,0,lineSizeW, lineSize);

        BaseActor lifeBadge = new BaseActor(0, 0, mainStage, Touchable.disabled,cradleGame);
        lifeBadge.setSize(lineSize * 0.95f, lineSize * 0.95f);
        lifeBadge.loadTexture("knights/heart.png", Math.round(lifeBadge.getWidth()), Math.round(lifeBadge.getHeight()));
        lifeBadge.setPosition(lifeBadge.getWidth() * 0.3f, lifeBadge.getWidth() * 0.05f);
        life.addActor(lifeBadge);

        lifeLabel = new Label(" ", BaseGame.labelStyle_Middle);
        s = String.valueOf(cradleGame.getKnightParams().getHealth());
        String sc = cradleGame.getLanguageStrings().get("of");
        s = s + " " + sc + " " + String.valueOf(cradleGame.getKnightParams().getCurrentHealthMaximum());
        lifeLabel.setText(s);
        lifeLabel.setColor(Color.GOLDENROD);
        lifeLabel.setPosition(lineX + lineSizeW / 3.2f, lineY - lineSize -10 + life.getHeight() * 0.2f);
        //lifeLabel.setFontScale(fontScale);
        mainStage.addActor(lifeLabel);

        // Speed
        BaseActor speedActor = new BaseActor(lineX, lineY - lineSize*2 - 20, mainStage, Touchable.disabled,cradleGame);
        speedActor.setSize(lineSizeW, lineSize);
        speedActor.loadTexture("knights/plate01.png", lineSizeW, lineSize);
        speedActor.AddImage("goldenframe02.png",0,0,lineSizeW, lineSize);

        BaseActor speedBadge = new BaseActor(0, 0, mainStage, Touchable.disabled,cradleGame);
        speedBadge.setSize(lineSize * 0.88f, lineSize * 0.88f);
        speedBadge.loadTexture("knightitems/speed_badge.png", Math.round(speedBadge.getWidth()), Math.round(speedBadge.getHeight()));
        speedBadge.setPosition(speedBadge.getWidth() * 0.3f, speedBadge.getWidth() * 0.05f);
        speedActor.addActor(speedBadge);

        speedLabel = new Label(" ", BaseGame.labelStyle_Middle);
        s = String.valueOf(cradleGame.getKnightParams().getRechargeWeaponTime());
        speedLabel.setText(s);
        speedLabel.setColor(Color.GOLDENROD);
        speedLabel.setPosition(lineX + lineSizeW / 2.0f, lineY - lineSize*2 - 20 + speedActor.getHeight() * 0.2f);
        //speedLabel.setFontScale(fontScale);
        mainStage.addActor(speedLabel);





        int knSize = Math.round(h * 0.4f);
        int wpSize = Math.round(h * 0.1f);
        if (knight != null) {
            knight.remove();
        }
        knight = new Knight(-knSize * 0.1f, h - knSize + knSize * 0.1f, knSize, knSize, mainStage, cradleGame.getKnightParams(),cradleGame);

        if (weapon != null) {
            weapon.remove();
        }
        weapon = new Weapon(knSize * 0.585f, h - knSize * 0.39f, wpSize, wpSize, mainStage, cradleGame, knight);
        weapon.setDraggable(false);

        //show active items places
        int ItemPlaceSize = Math.round(cellSize*1.18f);
        int itemPlaceLeftSpace = Math.round(w*0.1f);
        knightActiveItemPlaceHelmet = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize,cellSize+ItemPlaceSize*2, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Helmet,cradleGame);
        knightActiveItemPlaceArmor = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize,cellSize+ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Armor,cradleGame);
        knightActiveItemPlaceGloves = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize*2,cellSize+ItemPlaceSize*2, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Gloves,cradleGame);
        knightActiveItemPlaceShield = new KnightActiveItemPlace(  itemPlaceLeftSpace,cellSize+ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Shield,cradleGame);
        knightActiveItemPlaceSword = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize*2,cellSize+ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Sword,cradleGame);
        knightActiveItemPlaceBoots = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize,cellSize, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Boots,cradleGame);


        Label activeItemsLabel = new Label(" ", BaseGame.labelStyle_Small);
        String str = cradleGame.getLanguageStrings().get("activeitems");
        activeItemsLabel.setText(str);
        activeItemsLabel.setColor( Color.GOLDENROD );
        activeItemsLabel.setPosition(itemPlaceLeftSpace+ItemPlaceSize,wh*0.93f);
        /*
        if (w>1000){
            activeItemsLabel.setFontScale(1.7f);
        } else {
            activeItemsLabel.setFontScale(1.0f);
        }
        */

        mainStage.addActor(activeItemsLabel);

        Label inventoryLabel = new Label(" ", BaseGame.labelStyle_Small);
        str = cradleGame.getLanguageStrings().get("inventory");
        inventoryLabel.setText(str);
        inventoryLabel.setColor( Color.GOLDENROD );
        inventoryLabel.setPosition(w*0.05f,cellSize);
        /*
        if (w>1000){
            inventoryLabel.setFontScale(1.7f);
        } else {
            inventoryLabel.setFontScale(1.0f);
        }
        */

        mainStage.addActor(inventoryLabel);

        //Back button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture(Gdx.files.internal("back_button02.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        Button backButton = new Button(buttonStyle);
        backButton.setSize(h * 0.22f, h * 0.22f);
        backButton.setPosition(w*0.00f, h*0.24f);
        uiStage.addActor(backButton);

        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent) event;
                if (ie.getType().equals(InputEvent.Type.touchDown)) {
                    cradleGame.setActiveGameMapScreen(false,0);
                    return true;
                }
                return false;
            }
        });

        //Slider
        knightItemsSlider = new KnightItemsSlider(0, h * 0.00f, w, cellSize,
                mainStage, Touchable.enabled, knight, cradleGame,this);

        knightItemsSlider.showKnightItems(knight);
        knightItemsSlider.setBoundaryPolygon(8);


        knightItemSellPlace = new KnightItemShopPlace(itemPlaceLeftSpace+ItemPlaceSize*4,cellSize, ItemPlaceSize, ItemPlaceSize, mainStage,null,false,cradleGame);
        knightItemSellPlace.setDropPlaceType(4);
        Label knightItemSellPlaceLabel = new Label(" ", BaseGame.labelStyle_SuperSmall);
        str = cradleGame.getLanguageStrings().get("PutItemHereToSell");
        knightItemSellPlaceLabel.setText(str);
        knightItemSellPlaceLabel.setColor( Color.GOLDENROD );
        knightItemSellPlaceLabel.setWrap(true);
        knightItemSellPlaceLabel.setFontScale(0.7f);


        knightItemSellPlaceLabel.setAlignment(Align.center);
        knightItemSellPlaceLabel.setPosition(knightItemSellPlace.getX()+knightItemSellPlace.getWidth()*0.15f,knightItemSellPlace.getY()+knightItemSellPlace.getHeight()*0.5f);
        knightItemSellPlaceLabel.setWidth(knightItemSellPlace.getWidth()*0.7f);
        mainStage.addActor(knightItemSellPlaceLabel);

        //Sell Button
        String ms = cradleGame.getLanguageStrings().get("sell");
        TextButton sellButton = new TextButton( ms, BaseGame.textButtonStyle );
        if (w<1000) {
            sellButton.setWidth(sellButton.getWidth()*0.8f);
            sellButton.setHeight(sellButton.getHeight()*0.8f);
        }
        sellButton.setPosition(knightItemSellPlaceLabel.getX()+knightItemSellPlaceLabel.getWidth()*1.5f,knightItemSellPlace.getY()+knightItemSellPlace.getHeight()*0.3f);
        mainStage.addActor(sellButton);
        sellButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent) event;
                if (ie.getType().equals(InputEvent.Type.touchDown)) {
                    KnightItem knightItem = knightItemSellPlace.getKnightItem();
                    if (knightItem!=null){
                        if(knightItem.overlaps(knightItemSellPlace)) {
                            int itemPrice = Math.round(knightItem.getKnightItemParams().getPrice() * sellItemKf);
                            cradleGame.setGameResGold(GameRes.Gold + itemPrice);
                            cradleGame.getKnightParams().moveToPassiveItemParams(knightItem.getKnightItemParams());
                            cradleGame.getKnightParams().removeKnightItemParams(knightItemSellPlace.getKnightItem().getKnightItemParams());
                            knightItem.remove();
                            knightItemSellPlace.setKnightItem(null);
                            SetParams(cradleGame.getKnightParams());
                        }
                    }
                    return true;
                }
                return false;
            }
        });
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
        SetParams(cradleGame.getKnightParams());
    }


    public void moveToPassiveItemParams(KnightItem knightItem){
        cradleGame.getKnightParams().moveToPassiveItemParams(knightItem.getKnightItemParams());
        //knightItemsSlider.showKnightItems(knight);
        SetParams(cradleGame.getKnightParams());
    }

}

