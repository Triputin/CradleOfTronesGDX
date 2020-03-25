package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;


public class BlackMarketScreen extends BaseScreen {

    private final int RowColCount = 5;
    private int cellSize;

    private KnightItemShopPlace knightItemShopPlace01;
    private KnightItemShopPlace knightItemShopPlace02;
    private KnightItemShopPlace knightItemShopPlace03;
    private KnightItemShopPlace knightItemShopPlace04;
    private KnightItemShopPlace knightItemShopPlace05;
    private KnightItemShopPlace knightItemShopPlace06;
    private KnightItemShopPlace knightItemShopPlace07;
    private KnightItemShopPlace knightItemShopPlace08;
    private KnightItemShopPlace knightItemShopPlace09;
    private KnightItemShopPlace knightItemShopPlace10;

    private Label mightLabel;
    private Label lifeLabel;
    private Label speedLabel;


    public BlackMarketScreen(CradleGame cradleGame, IPlayServices ply) {
        super(cradleGame, ply);
    }


    public void initialize() {

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        float fontScale = 1.0f;
        float fontScale2 = 1.0f;
        if (w>1000){
            fontScale = 1.5f;
            fontScale2 = 3.0f;
        } else {
            fontScale = 0.8f;
            fontScale2 = 2.0f;
        }

        int wh;
        if (w > h) {
            wh = h;
        } else {
            wh = w;
        }
        cellSize = wh / RowColCount;


        //fon
        BaseActor hall = new BaseActor(0, 0, mainStage, Touchable.disabled);
        hall.loadTexture("blackmarketfon.png", w, h);

        Label label = new Label(" ", BaseGame.labelStyle);
        String s = cradleGame.getLanguageStrings().get("moveheretobuy");
        label.setText(s);
        label.setColor(Color.GOLDENROD);
        label.setFontScale(fontScale);
        label.setPosition(w*0.65f , h * 0.35f);
        mainStage.addActor(label);

        Label label2 = new Label(" ", BaseGame.labelStyle);
        String s2 = cradleGame.getLanguageStrings().get("presstofindoutitemparameters");
        label2.setText(s2);
        label2.setColor(Color.GOLDENROD);
        label2.setFontScale(fontScale);
        label2.setPosition(w*0.03f , h * 0.93f);
        mainStage.addActor(label2);

        //show active items places and items
        ArrayList<KnightItemParams> knightItemParamsArrayList = cradleGame.getKnightItemsParamsDailyArrayList();

        int ItemPlaceSize = Math.round(cellSize*1.18f);
        int itemPlaceLeftSpace = Math.round(w*0.05f);
        knightItemShopPlace01 = new KnightItemShopPlace(itemPlaceLeftSpace,cellSize+ItemPlaceSize*2, ItemPlaceSize, ItemPlaceSize, mainStage,null,true);
        KnightItem knightItem = new KnightItem(knightItemShopPlace01.getX(), knightItemShopPlace01.getY(), cellSize, cellSize, mainStage, 0, 0,cradleGame,knightItemParamsArrayList.get(0),null,this);
        knightItem.centerAtActor(knightItemShopPlace01);
        knightItemShopPlace01.setKnightItem(knightItem);
        knightItemShopPlace02 = new KnightItemShopPlace(itemPlaceLeftSpace+ItemPlaceSize,cellSize+ItemPlaceSize*2, ItemPlaceSize, ItemPlaceSize, mainStage,null,true);
        knightItem = new KnightItem(knightItemShopPlace02.getX(), knightItemShopPlace02.getY(), cellSize, cellSize, mainStage, 0, 0,cradleGame,knightItemParamsArrayList.get(1),null,this);
        knightItem.centerAtActor(knightItemShopPlace02);
        knightItemShopPlace02.setKnightItem(knightItem);
        knightItemShopPlace03 = new KnightItemShopPlace(itemPlaceLeftSpace+ItemPlaceSize*2,cellSize+ItemPlaceSize*2, ItemPlaceSize, ItemPlaceSize, mainStage,null,true);
        knightItem = new KnightItem(knightItemShopPlace03.getX(), knightItemShopPlace03.getY(), cellSize, cellSize, mainStage, 0, 0,cradleGame,knightItemParamsArrayList.get(2),null,this);
        knightItem.centerAtActor(knightItemShopPlace03);
        knightItemShopPlace03.setKnightItem(knightItem);
        knightItemShopPlace04 = new KnightItemShopPlace(itemPlaceLeftSpace,cellSize+ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,null,true);
        knightItem = new KnightItem(knightItemShopPlace04.getX(), knightItemShopPlace04.getY(), cellSize, cellSize, mainStage, 0, 0,cradleGame,knightItemParamsArrayList.get(3),null,this);
        knightItem.centerAtActor(knightItemShopPlace04);
        knightItemShopPlace04.setKnightItem(knightItem);
        knightItemShopPlace05 = new KnightItemShopPlace(itemPlaceLeftSpace+ItemPlaceSize,cellSize+ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,null,true);
        knightItem = new KnightItem(knightItemShopPlace05.getX(), knightItemShopPlace05.getY(), cellSize, cellSize, mainStage, 0, 0,cradleGame,knightItemParamsArrayList.get(4),null,this);
        knightItem.centerAtActor(knightItemShopPlace05);
        knightItemShopPlace05.setKnightItem(knightItem);
        knightItemShopPlace06 = new KnightItemShopPlace(itemPlaceLeftSpace+ItemPlaceSize*2,cellSize+ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,null,true);
        knightItem = new KnightItem(knightItemShopPlace06.getX(), knightItemShopPlace06.getY(), cellSize, cellSize, mainStage, 0, 0,cradleGame,knightItemParamsArrayList.get(5),null,this);
        knightItem.centerAtActor(knightItemShopPlace06);
        knightItemShopPlace06.setKnightItem(knightItem);
        knightItemShopPlace07 = new KnightItemShopPlace(itemPlaceLeftSpace,cellSize+ItemPlaceSize*0, ItemPlaceSize, ItemPlaceSize, mainStage,null,true);
        knightItem = new KnightItem(knightItemShopPlace07.getX(), knightItemShopPlace07.getY(), cellSize, cellSize, mainStage, 0, 0,cradleGame,knightItemParamsArrayList.get(6),null,this);
        knightItem.centerAtActor(knightItemShopPlace07);
        knightItemShopPlace07.setKnightItem(knightItem);
        knightItemShopPlace08 = new KnightItemShopPlace(itemPlaceLeftSpace+ItemPlaceSize,cellSize+ItemPlaceSize*0, ItemPlaceSize, ItemPlaceSize, mainStage,null,true);
        knightItem = new KnightItem(knightItemShopPlace08.getX(), knightItemShopPlace08.getY(), cellSize, cellSize, mainStage, 0, 0,cradleGame,knightItemParamsArrayList.get(7),null,this);
        knightItem.centerAtActor(knightItemShopPlace08);
        knightItemShopPlace08.setKnightItem(knightItem);
        knightItemShopPlace09 = new KnightItemShopPlace(itemPlaceLeftSpace+ItemPlaceSize*2,cellSize+ItemPlaceSize*0, ItemPlaceSize, ItemPlaceSize, mainStage,null,true);
        knightItem = new KnightItem(knightItemShopPlace09.getX(), knightItemShopPlace09.getY(), cellSize, cellSize, mainStage, 0, 0,cradleGame,knightItemParamsArrayList.get(8),null,this);
        knightItem.centerAtActor(knightItemShopPlace09);
        knightItemShopPlace09.setKnightItem(knightItem);

        //Place for bying
        knightItemShopPlace10 = new KnightItemShopPlace(itemPlaceLeftSpace+ItemPlaceSize*5,cellSize+ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,null,false);
        knightItemShopPlace10.setKnightItem(null);
        knightItemShopPlace10.setDropPlaceType(2);


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
                    cradleGame.setActiveGameMapScreen(false,0);
                    return true;
                }
                return false;
            }
        });



        //Size of lines
        int lineX = itemPlaceLeftSpace;
        int lineY = Math.round(h * 0.01f);
        int lineSize = Math.round(h * 0.18f);
        int lineSizeW = Math.round(w * 0.3f);

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
        s = "";
        mightLabel.setText(s);
        mightLabel.setColor(Color.GOLDENROD);
        mightLabel.setPosition(lineX + lineSizeW / 1.8f, lineY + might.getHeight() * 0.4f);
        mightLabel.setFontScale(fontScale2);
        mainStage.addActor(mightLabel);


        // Life
        BaseActor life = new BaseActor(lineX+lineSizeW+10, lineY , mainStage, Touchable.disabled);
        life.setSize(lineSizeW, lineSize);
        life.loadTexture("knights/plate01.png", lineSizeW, lineSize);

        BaseActor lifeBadge = new BaseActor(0, 0, mainStage, Touchable.disabled);
        lifeBadge.setSize(lineSize * 0.95f, lineSize * 0.95f);
        lifeBadge.loadTexture("knights/heart.png", Math.round(lifeBadge.getWidth()), Math.round(lifeBadge.getHeight()));
        lifeBadge.setPosition(lifeBadge.getWidth() * 0.3f, lifeBadge.getWidth() * 0.05f);
        life.addActor(lifeBadge);

        lifeLabel = new Label(" ", BaseGame.labelStyle);
        s = "";
        lifeLabel.setText(s);
        lifeLabel.setColor(Color.GOLDENROD);
        lifeLabel.setPosition(lineX+lineSizeW+10 + lineSizeW / 1.8f, lineY  + life.getHeight() * 0.4f);
        lifeLabel.setFontScale(fontScale2);
        mainStage.addActor(lifeLabel);

        // Speed
        BaseActor speedActor = new BaseActor(lineX+lineSizeW*2+20, lineY , mainStage, Touchable.disabled);
        speedActor.setSize(lineSizeW, lineSize);
        speedActor.loadTexture("knights/plate01.png", lineSizeW, lineSize);

        BaseActor speedBadge = new BaseActor(0, 0, mainStage, Touchable.disabled);
        speedBadge.setSize(lineSize * 0.95f, lineSize * 0.95f);
        speedBadge.loadTexture("knightitems/speed_badge.png", Math.round(speedBadge.getWidth()), Math.round(speedBadge.getHeight()));
        speedBadge.setPosition(speedBadge.getWidth() * 0.3f, speedBadge.getWidth() * 0.05f);
        speedActor.addActor(speedBadge);

        speedLabel = new Label(" ", BaseGame.labelStyle);
        s = "";
        speedLabel.setText(s);
        speedLabel.setColor(Color.GOLDENROD);
        speedLabel.setPosition(lineX+lineSizeW*2+20 + lineSizeW / 1.8f, lineY  + speedActor.getHeight() * 0.4f);
        speedLabel.setFontScale(fontScale2);
        mainStage.addActor(speedLabel);


/*
        mainStage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent) event;
                if (ie.getType().equals(InputEvent.Type.touchDown)) {
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(uiStage, "by.android.cradle.KnightItem")) {
                        if (CoinActor.getBoundaryPolygon().contains(x, y)) {
                            KnightItem shopItem1=(KnightItem) CoinActor;
                            if (shopItem1.getTouchable()==Touchable.disabled){return false;} //
                            if(lastSelectedItem!=null){
                                lastSelectedItem.setSelected(false,null);
                            }
                            lastSelectedItem =shopItem1;
                            shopItem1.setSelected(true,null);

                            }
                        }
                    }

                return false;
            }

        });
*/



    }

    public void update(float dt) {

    }

    public Label getMightLabel() {
        return mightLabel;
    }

    public Label getLifeLabel() {
        return lifeLabel;
    }

    public Label getSpeedLabel() {
        return speedLabel;
    }
}
