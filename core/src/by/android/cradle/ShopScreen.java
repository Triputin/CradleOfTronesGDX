package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

import static by.android.cradle.BaseGame.skin;

public class ShopScreen extends BaseScreen {

private final int RowColCount=4;
private int padding=5;
private final int exchangeRate=10;
private ArrayList<Item> leftArray;
private ArrayList<Item> rightArray;


    public ShopScreen(CradleGame cradleGame) {

        super(cradleGame);
    }

    public void initialize()
    {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        int wh;
        if (w>h) {
            wh=h;
        }else{
            wh=w;
        }

        //Size of buttons line
        int buttonLineSize =  Math.round(wh*0.2f);
        //Size of the square
        int sizeSquare = wh-buttonLineSize*2;
        //Size of items
        final int itemSize = sizeSquare/4;
        //left square pos
        int leftSqX = (w-sizeSquare)/4;

        int leftSqY = sizeSquare;

        //left square pos
        int rightSqX = leftSqX + sizeSquare ;

        Label leftLabel = new Label(" ", BaseGame.labelStyle);
        leftLabel.setText("Resources available");
        leftLabel.setColor( Color.GOLDENROD );
        leftLabel.setPosition(leftSqX,wh*0.8f);
        leftLabel.setFontScale(1.0f);
        uiStage.addActor(leftLabel);

        Label rightLabel = new Label(" ", BaseGame.labelStyle);
        rightLabel.setText("Resources available for trade");
        rightLabel.setColor( Color.GOLDENROD );
        rightLabel.setPosition( leftSqX+sizeSquare,wh*0.8f);
        rightLabel.setFontScale(1.0f);
        uiStage.addActor(rightLabel);

        TextButton backButton = new TextButton( "   Back   ", BaseGame.textButtonStyle );
        backButton.setPosition(w*0.00f,h*0.88f);
        backButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                cradleGame.setActiveGameMapScreen();
                return true;
            }
        });

        uiStage.addActor(backButton);

        TextButton sellButton = new TextButton( "   Sell   ", BaseGame.textButtonStyle );
        sellButton.setPosition(w/2-sellButton.getWidth()/2,h*0.02f);
        sellButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                return true;
            }
        });

        uiStage.addActor(sellButton);

        final Label lblSum;
        lblSum=new Label("" +0, skin);
        lblSum.setFontScale(2.5f);
        //lblSum.setAlignment();
        lblSum.setPosition(w/2-lblSum.getWidth()/2,wh/4);
        uiStage.addActor(lblSum);

        final Slider slider = new Slider(0,10,1,false, BaseGame.skin);
        slider.setSize(wh/2,20);
        slider.setPosition(w/2 - slider.getWidth()/2,wh/6);
        slider.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                 lblSum.setText("" +(int) slider.getValue());
            }
        });
        uiStage.addActor(slider);

        leftArray = new ArrayList<>();

        leftArray.add(new Coin2(leftSqX, leftSqY, itemSize, itemSize, uiStage, 0, 0));
        leftArray.add(new Wood(leftSqX+itemSize+padding, leftSqY, itemSize, itemSize, uiStage, 0, 1));
        leftArray.add(new Bread(leftSqX+itemSize+padding+itemSize+padding, leftSqY, itemSize, itemSize, uiStage, 0, 2));

        rightArray = new ArrayList<>();
        rightArray.add(new Coin2(rightSqX, leftSqY, itemSize, itemSize, mainStage, 0, 0));
        rightArray.add(new Wood(rightSqX+itemSize+padding, leftSqY, itemSize, itemSize, mainStage, 0, 1));
        rightArray.add(new Bread(rightSqX+itemSize+padding+itemSize+padding, leftSqY, itemSize, itemSize, mainStage, 0, 2));

        uiStage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent) event;
                if (ie.getType().equals(InputEvent.Type.touchDown)) {

                    //Coin2
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(uiStage, "by.android.cradle.Coin2")) {
                        if (CoinActor.getBoundaryPolygon().contains(x, y)) {
                            ((Coin2) CoinActor).setSelected(true, null);
                            for (Item a:leftArray){
                                if(!a.equals(CoinActor)){
                                    a.setSelected(false,null);
                                }
                            }
                        }
                    }

                    //Wood
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(uiStage, "by.android.cradle.Wood")) {
                        if (CoinActor.getBoundaryPolygon().contains(x, y)) {
                            ((Wood) CoinActor).setSelected(true, null);
                            for (Item a:leftArray){
                                if(!a.equals(CoinActor)){
                                    a.setSelected(false,null);
                                }
                            }
                        }
                    }

                    //Bread
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(uiStage, "by.android.cradle.Bread")) {
                        if (CoinActor.getBoundaryPolygon().contains(x, y)) {
                            ((Bread) CoinActor).setSelected(true, null);
                            for (Item a:leftArray){
                                if(!a.equals(CoinActor)){
                                    a.setSelected(false,null);
                                }
                            }
                        }
                    }

                }

                return false;
            }

        });



        mainStage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent) event;
                if (ie.getType().equals(InputEvent.Type.touchDown)) {

                    //Coin2
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Coin2")) {
                        if (CoinActor.getBoundaryPolygon().contains(x, y)) {
                            ((Coin2) CoinActor).setSelected(true, null);
                            for (Item a:rightArray){
                                if(!a.equals(CoinActor)){
                                    a.setSelected(false,null);
                                }
                            }
                        }
                    }

                    //Wood
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Wood")) {
                        if (CoinActor.getBoundaryPolygon().contains(x, y)) {
                            ((Wood) CoinActor).setSelected(true, null);
                            for (Item a:rightArray){
                                if(!a.equals(CoinActor)){
                                    a.setSelected(false,null);
                                }
                            }
                        }
                    }

                    //Bread
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Bread")) {
                        if (CoinActor.getBoundaryPolygon().contains(x, y)) {
                            ((Bread) CoinActor).setSelected(true, null);
                            for (Item a:rightArray){
                                if(!a.equals(CoinActor)){
                                    a.setSelected(false,null);
                                }
                            }
                        }
                    }

                }

                return true;
            }

        });
    }

    public void update(float dt)
    {

    }


}
