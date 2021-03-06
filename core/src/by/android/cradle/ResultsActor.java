package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ResultsActor extends BaseActor{
    private Label goldQuantityLabel;
    private Label woodQuantityLabel;
    private Label breadQuantityLabel;
    private Label scoreLabel;
    public ResultsActor(float x, float y, int width, int height, Stage s, Touchable touchable, BaseActor baseActor, CradleGame cradleGame)
    {
        super(x,y,s, touchable, cradleGame);
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        /*
        float fontScale = 1.0f;
        if (w>1000){
            fontScale = 3.0f;
        } else {
            fontScale = 1.4f;
        }
        */

        setHeight(height);
        setWidth(width);
        baseActor.loadTexture("fon_black.png",(int)getWidth(),(int)getHeight());
        baseActor.AddImage("results.png",Math.round(x),Math.round(y), (int) getWidth(), (int) getHeight());
        baseActor.AddImage("goldenframe02.png",Math.round(x),Math.round(y), (int) getWidth(), (int) getHeight());

        goldQuantityLabel = new Label(" "+0, BaseGame.labelStyle_Middle);
        goldQuantityLabel.setColor( Color.GOLDENROD );
        goldQuantityLabel.setPosition( x+width*0.1f,y+height*0.1f );
        //goldQuantityLabel.setFontScale(fontScale);
        baseActor.addActor(goldQuantityLabel);

        woodQuantityLabel = new Label(" "+0, BaseGame.labelStyle_Middle);
        woodQuantityLabel.setColor( Color.GOLDENROD );
        woodQuantityLabel.setPosition( x+width*0.32f,y+height*0.1f );
        //woodQuantityLabel.setFontScale(fontScale);
        baseActor.addActor(woodQuantityLabel);

        breadQuantityLabel = new Label(" "+0, BaseGame.labelStyle_Middle);
        breadQuantityLabel.setColor( Color.GOLDENROD );
        breadQuantityLabel.setPosition( x+width*0.58f,y+height*0.1f );
        //breadQuantityLabel.setFontScale(fontScale);
        baseActor.addActor(breadQuantityLabel);

        scoreLabel = new Label(" "+0, BaseGame.labelStyle_Middle);
        scoreLabel.setColor( Color.GOLDENROD );
        scoreLabel.setPosition( x+width*0.78f,y+height*0.1f );
        //scoreLabel.setFontScale(fontScale);
        baseActor.addActor(scoreLabel);
    }

    public void UpdateRes() {
        goldQuantityLabel.setText(" " + GameRes.Gold);
        woodQuantityLabel.setText(" " + GameRes.Wood);
        breadQuantityLabel.setText(" " + GameRes.Bread);
        scoreLabel.setText(" " + GameRes.Score);

    }

    public void UpdateRes(KingdomRes gameRes) {
        goldQuantityLabel.setText(" " + gameRes.Gold);
        woodQuantityLabel.setText(" " + gameRes.Wood);
        breadQuantityLabel.setText(" " + gameRes.Bread);

    }
}
