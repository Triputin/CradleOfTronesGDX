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

    private ArrayList<KnightItem> knightActiveItemsArrayList;
    private ArrayList<KnightItem> knightPassiveItemsArrayList;

    private KnightItemsSlider knightItemsSlider;

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
        int lineY = Math.round(h * 0.52f);
        int lineSize = Math.round(h * 0.25f);
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
        mightLabel.setPosition(lineX + lineSizeW / 1.7f, lineY + might.getHeight() * 0.4f);
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
        lifeLabel.setPosition(lineX + lineSizeW / 2.5f, lineY - lineSize + life.getHeight() * 0.4f);
        lifeLabel.setFontScale(3.0f);
        mainStage.addActor(lifeLabel);

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

        int ItemPlaceSize = Math.round(cellSize*1.3f);
        int itemPlaceLeftSpace = Math.round(w*0.05f);
        KnightActiveItemPlace knightActiveItemPlace1 = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize,ItemPlaceSize*2, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Helmet);
        KnightActiveItemPlace knightActiveItemPlace2 = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize,ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Armor);
        KnightActiveItemPlace knightActiveItemPlace3 = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize*2,ItemPlaceSize*2, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Gloves);
        KnightActiveItemPlace knightActiveItemPlace4 = new KnightActiveItemPlace(itemPlaceLeftSpace,ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Shield);
        KnightActiveItemPlace knightActiveItemPlace5 = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize*2,ItemPlaceSize, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Sword);
        KnightActiveItemPlace knightActiveItemPlace6 = new KnightActiveItemPlace(itemPlaceLeftSpace+ItemPlaceSize,0, ItemPlaceSize, ItemPlaceSize, mainStage,KnightItemType.Boots);



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
        knightItemsSlider = new KnightItemsSlider(lineX, h * 0.00f, w-ItemPlaceSize*3, cellSize,
                mainStage, Touchable.enabled, knight, cradleGame);

        knightItemsSlider.showKnightItems(knight);

        mainStage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent) event;
                if (ie.getType().equals(InputEvent.Type.touchDown)) {

                    //KnightItems
                    /*
                   for (KnightItem knightItem : knightItemsSlider.getKnightPassiveItemsArrayList()) {
                        if (knightItem.getBoundaryPolygon().contains(x, y)) {

                            //knightItem.setSelected(!knightItem.isSelected());

                        }
                    }
                    */
                }

                return true;
            }

/*
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent) event;
                if (ie.getType().equals(InputEvent.Type.touchUp)) {

                    //KnightItems
                    for (KnightItem knightItem : knightItemsSlider.getKnightPassiveItemsArrayList()) {
                        if (knightItem.getBoundaryPolygon().contains(x, y)) {

                            knightItem.setSelected(false);

                        }
                    }
                }

            }
*/


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

        //Knight
        knight.setKnightParams(knightParams);

        knightItemsSlider.showKnightItems(knight);
    }





}

