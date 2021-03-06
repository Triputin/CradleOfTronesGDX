package by.android.cradle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ResultsActorForAttack extends BaseActor{
    private Label goldQuantityLabel;
    private Label woodQuantityLabel;
    private Label breadQuantityLabel;
    private Actor image;

    public ResultsActorForAttack(float x, float y, int width, int height, Stage s, Touchable touchable, BaseActor baseActor, CradleGame cradleGame, Label.LabelStyle labelStyle)
    {
        super(x,y,s, touchable, cradleGame);
        setHeight(height);
        setWidth(width);
        baseActor.loadTexture("fon_empty.png",(int) getWidth(), (int) getHeight());
        image = baseActor.AddImage("results_fo_attack.png",Math.round(x),Math.round(y), (int) getWidth(), (int) getHeight());

        goldQuantityLabel = new Label(" "+0, labelStyle);
        goldQuantityLabel.setColor( Color.GOLDENROD );
        goldQuantityLabel.setPosition( x+width*0.15f,y );
        //goldQuantityLabel.setFontScale(2f);
        baseActor.addActor(goldQuantityLabel);

        woodQuantityLabel = new Label(" "+0, labelStyle);
        woodQuantityLabel.setColor( Color.GOLDENROD );
        woodQuantityLabel.setPosition( x+width*0.45f,y );
        //woodQuantityLabel.setFontScale(2f);
        baseActor.addActor(woodQuantityLabel);

        breadQuantityLabel = new Label(" "+0, labelStyle);
        breadQuantityLabel.setColor( Color.GOLDENROD );
        breadQuantityLabel.setPosition( x+width*0.82f,y );
        //breadQuantityLabel.setFontScale(2f);
        baseActor.addActor(breadQuantityLabel);

    }


    public void UpdateRes(KingdomRes gameRes) {
        goldQuantityLabel.setText(" " + gameRes.Gold);
        woodQuantityLabel.setText(" " + gameRes.Wood);
        breadQuantityLabel.setText(" " + gameRes.Bread);

    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        goldQuantityLabel.setVisible(visible);
        woodQuantityLabel.setVisible(visible);
        breadQuantityLabel.setVisible(visible);
        image.setVisible(visible);
    }
}