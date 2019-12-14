package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MessageActor01 extends BaseActor {
    private KingdomRes kingdomRes;

    public MessageActor01(float x, float y, Stage s, int width, int height , final CradleGame cradleGame)
    {
        super(x,y,s, Touchable.enabled);
        kingdomRes = new KingdomRes();

        // BaseActor frame = new BaseActor(0,0, s, Touchable.disabled);
        loadTexture( "goldenframe.png",width,height );
        AddImage("fon_white.png",Math.round(width*0.075f),Math.round(height*0.1f),Math.round(width*0.85f), Math.round(height*0.8f));


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
        attackButton.setPosition(Math.round(width*0.1f),20);
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
        cancelButton.setPosition(Math.round(width*0.62f),20);
        addActor(cancelButton);

    }

    public void SetRes(KingdomRes kingdomRes){
        this.kingdomRes.Bread = kingdomRes.Bread;
        this.kingdomRes.Gold = kingdomRes.Gold;
        this.kingdomRes.Wood = kingdomRes.Wood;

    }

    private void AddImage(String name,int x, int y, int width, int height){
        Pixmap pixmap200;
        pixmap200 = new Pixmap(Gdx.files.internal("fon_white.png"));

        // Изменяем размер загружаемой картинки из файла на заданный
        Pixmap pixmap100 = new Pixmap((int) getWidth(), (int) getHeight(), pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        Texture texture = new Texture(pixmap100);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pixmap200.dispose();
        pixmap100.dispose();
        TextureRegion imgTextureRegion = new TextureRegion(texture);
        //imgTextureRegion.setRegion(0,0,getWidth(),getHeight());
        imgTextureRegion.setRegion(x,y,width,height);
        TextureRegionDrawable imgTextureRegionDrawable = new TextureRegionDrawable(imgTextureRegion);
        Image img = new Image(texture);
        //img.setDrawable(imgTextureRegionDrawable);
        img.setSize(width,height);
        img.setPosition(x, y);
        addActor(img);

    }

}
