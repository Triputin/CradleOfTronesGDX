package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ShopScreen extends BaseScreen {

private final int RowColCount=4;

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
        int sizeSquare = wh-buttonLineSize;

        //left square pos
        int leftSqX = (w-sizeSquare)/4;

        Label leftLabel = new Label(" ", BaseGame.labelStyle);
        leftLabel.setText("Resources available");
        leftLabel.setColor( Color.GOLDENROD );
        leftLabel.setPosition(leftSqX,10 );
        leftLabel.setFontScale(2.0f);
        uiStage.addActor(leftLabel);

        Label rightLabel = new Label(" ", BaseGame.labelStyle);
        rightLabel.setText("Resources available for trade");
        rightLabel.setColor( Color.GOLDENROD );
        rightLabel.setPosition( leftSqX,10);
        rightLabel.setFontScale(2.0f);
        uiStage.addActor(rightLabel);

    }

    public void update(float dt)
    {

    }


}
