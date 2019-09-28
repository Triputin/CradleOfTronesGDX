package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameField extends BaseActor {
    public GameField(float x, float y, Stage s,float width,float height)
    {
        super(x,y,s,Touchable.enabled);
       //this.setColor(255,255,255,255);
       loadTexture("assets/game_of_thrones_locations4.jpg", (int) width,(int) height);
        setHeight(height);
        setWidth(width);

        // repeated texture
        /*Texture imgTexture = new Texture(Gdx.files.internal("assets/marble.jpg"));
        imgTexture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        TextureRegion imgTextureRegion = new TextureRegion(imgTexture);
        imgTextureRegion.setRegion(0,0,128*4,128*4);

        TextureRegionDrawable imgTextureRegionDrawable = new TextureRegionDrawable(imgTextureRegion);
        Image img = new Image();
        img.setDrawable(imgTextureRegionDrawable);
        img.setSize(width,height);
        img.setPosition(0,0);
        addActor(img);
        img.setBounds(0,0,width,height);
        img.setTouchable(Touchable.enabled);
        setTouchable(Touchable.enabled);
        setBounds(x,y,width,height);
        */


    }
/*
    @Override
    public Actor hit (float x, float y, boolean touchable) {
        if (touchable && !this.isTouchable()) return null;
        return x >= 0 && x < this.getWidth() && y >= 0 && y < this.getHeight() ? this : null;
    }
*/

}
