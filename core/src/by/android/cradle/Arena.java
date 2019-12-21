package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Arena extends BaseActor {

    public Arena(float x, float y, int width, int height, Stage s, Touchable touchable) {
        super(x, y, s, touchable);
        loadTexture("arena.png", width, height);
    }

    public KingdomRes GetWinningRes(int level){
        KingdomRes kingdomRes = new KingdomRes();
        kingdomRes.Wood=0;
        kingdomRes.Gold=0;
        kingdomRes.Bread=0;
        return kingdomRes;
    }


}


