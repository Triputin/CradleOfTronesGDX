package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MessageActor01 extends BaseActor {
    private KingdomRes kingdomRes;

    public MessageActor01(float x, float y, Stage s, int width, int height , final CradleGame cradleGame)
    {
        super(x,y,s, Touchable.enabled);
        kingdomRes = new KingdomRes();

        // BaseActor frame = new BaseActor(0,0, s, Touchable.disabled);
        loadTexture( "goldenframe.png",width,height );
        //loadTexture("fon_white.png",  width,height);



        //Attack Button
        TextButton attackButton = new TextButton( "Attack", BaseGame.textButtonStyle );
        attackButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                GameRes.Gold -= kingdomRes.Gold;
                GameRes.Wood -= kingdomRes.Wood;
                GameRes.Bread -= kingdomRes.Bread;
                cradleGame.setActivescreenGamePlay();
                cradleGame.getScreenGamePlay().UpdateRes();
                cradleGame.getScreenGamePlay().StartNewLevel(1);
                return true;
            }
        });
        attackButton.setPosition(50,20);
        addActor(attackButton);

        //Cancel Button
        TextButton cancelButton = new TextButton( "Cancel", BaseGame.textButtonStyle );
        cancelButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                cradleGame.setActiveGameMapScreen();
                return true;
            }
        });
        cancelButton.setPosition(250,20);
        addActor(cancelButton);


        //Fon
        //BaseActor fon = new BaseActor(x,y,s,Touchable.disabled);
        //fon.loadTexture("fon_white.png",width,height);
        //fon.setOpacity(70);


    }

    public void SetRes(KingdomRes kingdomRes){
        this.kingdomRes.Bread = kingdomRes.Bread;
        this.kingdomRes.Gold = kingdomRes.Gold;
        this.kingdomRes.Wood = kingdomRes.Wood;

    }

}
