package by.android.cradle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ResultsActor extends BaseActor{
    private Label goldQuantityLabel;
    private Label woodQuantityLabel;
    private Label breadQuantityLabel;
    public ResultsActor(float x, float y, int width, int height, Stage s, Touchable touchable, BaseActor baseActor)
    {
        super(x,y,s, touchable);
        setHeight(height);
        setWidth(width);
        baseActor.AddImage("results.png",Math.round(x),Math.round(y), (int) getWidth(), (int) getHeight());

        goldQuantityLabel = new Label(" "+0, BaseGame.labelStyle);
        goldQuantityLabel.setColor( Color.GOLDENROD );
        goldQuantityLabel.setPosition( x+width*0.16f,y+20 );
        goldQuantityLabel.setFontScale(2f);
        baseActor.addActor(goldQuantityLabel);

        woodQuantityLabel = new Label(" "+0, BaseGame.labelStyle);
        woodQuantityLabel.setColor( Color.GOLDENROD );
        woodQuantityLabel.setPosition( x+width*0.47f,y+20 );
        woodQuantityLabel.setFontScale(2f);
        baseActor.addActor(woodQuantityLabel);

        breadQuantityLabel = new Label(" "+0, BaseGame.labelStyle);
        breadQuantityLabel.setColor( Color.GOLDENROD );
        breadQuantityLabel.setPosition( x+width*0.85f,y+20 );
        breadQuantityLabel.setFontScale(2f);
        baseActor.addActor(breadQuantityLabel);
    }

    public void UpdateRes() {
        goldQuantityLabel.setText(" " + GameRes.Gold);
        woodQuantityLabel.setText(" " + GameRes.Wood);
        breadQuantityLabel.setText(" " + GameRes.Bread);


    }

    public void UpdateRes(KingdomRes gameRes) {
        goldQuantityLabel.setText(" " + gameRes.Gold);
        woodQuantityLabel.setText(" " + gameRes.Wood);
        breadQuantityLabel.setText(" " + gameRes.Bread);

    }
}
