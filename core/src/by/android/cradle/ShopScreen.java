package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
private final int exchangeRate=2;
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

        //Tiled texture
        Texture texture = new Texture(Gdx.files.internal("fon/tiled01.png"), true);
        texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.setRegion(0,0,texture.getWidth()*4,texture.getHeight()*4);
        Image image4 = new Image(textureRegion);
        image4.setSize(w,h);
        image4.setPosition(0,0);
        mainStage.addActor(image4);


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

        Label sLabel = new Label(" ", BaseGame.labelStyle);
        sLabel.setText("You will sell");
        sLabel.setColor( Color.GOLDENROD );
        sLabel.setPosition( leftSqX*0.8f,wh/3);
        sLabel.setFontScale(1.0f);
        uiStage.addActor(sLabel);

        Label gLabel = new Label(" ", BaseGame.labelStyle);
        gLabel.setText("You will get");
        gLabel.setColor( Color.GOLDENROD );
        gLabel.setPosition( leftSqX*2+sizeSquare,wh/3);
        gLabel.setFontScale(1.0f);
        uiStage.addActor(gLabel);

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



        final Label sellLabel;
        sellLabel=new Label("" +0, skin);
        sellLabel.setFontScale(2.5f);
        sellLabel.setPosition(w/3f-sellLabel.getWidth()/2,wh/3);
        uiStage.addActor(sellLabel);

        final Label buyLabel;
        buyLabel=new Label("" +0, skin);
        buyLabel.setFontScale(2.5f);
        buyLabel.setPosition(w/2-buyLabel.getWidth()/2,wh/3);
        uiStage.addActor(buyLabel);

        final Slider slider = new Slider(0,0,exchangeRate,false, BaseGame.skin);
        slider.setSize(wh/2,20);
        slider.setPosition(w/2 - slider.getWidth()/2,wh/5);
        slider.setValue(0);
        slider.getStyle().knob.setMinHeight(wh*0.1f);
        slider.getStyle().knob.setMinWidth(wh*0.08f);
        sellLabel.setX(slider.getX()*0.9f);
        buyLabel.setX(slider.getX()+slider.getWidth());
        gLabel.setX(buyLabel.getX()*1.05f+buyLabel.getWidth()+50);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                sellLabel.setText("" +(int) slider.getValue());
                buyLabel.setText("" +(int) slider.getValue()/exchangeRate);
            }
        });
        uiStage.addActor(slider);

        TextButton sellButton = new TextButton( "   Sell   ", BaseGame.textButtonStyle );
        sellButton.setPosition(w/2-sellButton.getWidth()/2,h*0.02f);
        sellButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                makeExchange((int)slider.getValue());
                slider.setValue(0);

                return true;
            }
        });

        uiStage.addActor(sellButton);

        //ShopItem sp = new ShopItem(0,0, uiStage,new Coin2(leftSqX, leftSqY, itemSize, itemSize, uiStage, 0, 0),30 );
        leftArray = new ArrayList<>();
        Item item = new Coin2(leftSqX, leftSqY, itemSize, itemSize, uiStage, 0, 0);
        item.setQtty(GameRes.Gold);
        item.setSelected(true,null);
        slider.setRange(0,(float)Math.floor((item.getQtty()*1.0f/exchangeRate))*exchangeRate);
        leftArray.add(item);
        item = new Wood(leftSqX+itemSize+padding, leftSqY, itemSize, itemSize, uiStage, 0, 1);
        item.setQtty(GameRes.Wood);
        leftArray.add(item);
        item = new Bread(leftSqX+itemSize+padding+itemSize+padding, leftSqY, itemSize, itemSize, uiStage, 0, 2);
        item.setQtty(GameRes.Bread);
        leftArray.add(item);

        rightArray = new ArrayList<>();
        rightArray.add(new Coin2(rightSqX, leftSqY, itemSize, itemSize, mainStage, 0, 0));
        item = new Wood(rightSqX+itemSize+padding, leftSqY, itemSize, itemSize, mainStage, 0, 1);
        rightArray.add(item);
        item.setSelected(true,null);
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
                            slider.setRange(0, (float)Math.floor(((Coin2) CoinActor).getQtty()*1.0f/exchangeRate)*exchangeRate);
                            slider.setValue(0);
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
                            slider.setRange(0,(float)Math.floor(((Wood) CoinActor).getQtty()*1.0f/exchangeRate)*exchangeRate);
                            slider.setValue(0);
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
                            slider.setRange(0,(float)Math.floor(((Bread) CoinActor).getQtty()*1.0f/exchangeRate)*exchangeRate);
                            slider.setValue(0);
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
                            slider.setValue(0);
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
                            slider.setValue(0);
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
                            slider.setValue(0);
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

    private void makeExchange(int sellQtty){
        String src="";
        String dest="";
        Item srcItem = leftArray.get(0);
        Item destItem = leftArray.get(0);
        for (Item a:leftArray){
            if (a.isSelected()){
                src=a.getClass().toString();
                srcItem = a;
            }
        }
        for (Item a:rightArray){
            if (a.isSelected()){
                dest=a.getClass().toString();
                destItem =a;
            }
        }

        for (Item a:leftArray){
            if( a.getClass().toString().equals(destItem.getClass().toString())){
                destItem=a;
            }
        }


        switch (src){
            case "class by.android.cradle.Coin2":
                GameRes.Gold -=sellQtty;
                srcItem.setQtty(GameRes.Gold);
                break;

            case "class by.android.cradle.Wood":
                GameRes.Wood -=sellQtty;
                srcItem.setQtty(GameRes.Wood);
                break;

            case "class by.android.cradle.Bread":
                GameRes.Bread -=sellQtty;
                srcItem.setQtty(GameRes.Bread);
                break;
        }


        switch (dest){
            case "class by.android.cradle.Coin2":
                GameRes.Gold +=sellQtty/exchangeRate;
                destItem.setQtty(GameRes.Gold);

                break;

            case "class by.android.cradle.Wood":
                GameRes.Wood +=sellQtty/exchangeRate;
                destItem.setQtty(GameRes.Wood);
                break;

            case "class by.android.cradle.Bread":
                GameRes.Bread +=sellQtty/exchangeRate;
                destItem.setQtty(GameRes.Bread);
                break;
        }

    }


}
