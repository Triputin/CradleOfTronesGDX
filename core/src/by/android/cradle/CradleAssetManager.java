package by.android.cradle;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class CradleAssetManager  {

    public final AssetManager manager = new AssetManager();
    private HashMap<Integer,Animation<TextureRegion>> hashMapAnimations = new HashMap<>();


    public void loadAssets(){
        manager.load(Assets.BREAD);
        manager.load(Assets.BREAD_PRESSED);
        manager.load(Assets.COIN01);
        manager.load(Assets.COIN01_PRESSED);
        manager.load(Assets.COIN02);
        manager.load(Assets.COIN02_PRESSED);
        manager.load(Assets.JEM01);
        manager.load(Assets.JEM01_PRESSED);
        manager.load(Assets.WOOD);
        manager.load(Assets.WOOD_PRESSED);
        manager.load(Assets.SOLAREFFECT_ATLAS);

    }

    public void finishLoading(){
        manager.finishLoading();
    }


    public void prepareAnimations(CradleGame cradleGame){

        hashMapAnimations.put(Assets.SOLAREFFECT_ANIMATION_ID,loadAnimationFromAssetsAtlas(Assets.SOLAREFFECT_ATLAS,1,5,0.2f, true,  cradleGame.getCellSize(),  cradleGame.getCellSize()));
    }

    public  Animation<TextureRegion> getAnimation(int animationId){

        return hashMapAnimations.get(animationId);
    }

    public void dispose(){
        manager.dispose();
    }




    public Animation<TextureRegion> loadAnimationFromAssetsAtlas(AssetDescriptor<TextureAtlas> assetDescriptor, int rows, int cols, float frameDuration, boolean loop, int width, int height){
        TextureAtlas textureAtlas = manager.get(assetDescriptor);
        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        Array<TextureAtlas.AtlasRegion> atlasRegions = textureAtlas.getRegions();
        Texture texture = atlasRegions.get(0).getTexture();
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++){
                Pixmap pixmap200 = extractPixmapFromTextureRegion(temp[r][c]);
                Pixmap pixmap100 = new Pixmap(width, height, pixmap200.getFormat());
                pixmap100.drawPixmap(pixmap200,
                        0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                        0, 0, pixmap100.getWidth(), pixmap100.getHeight()
                );
                Texture texture2 = new Texture(pixmap100);
                texture2.setFilter( Texture.TextureFilter.Linear, Texture.TextureFilter.Linear );
                pixmap200.dispose();
                pixmap100.dispose();

                TextureRegion textureRegion=new TextureRegion( texture2 );
                textureArray.add(textureRegion);
            }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);

        return anim;

    }



    private Pixmap extractPixmapFromTextureRegion(TextureRegion textureRegion)
    {
        TextureData textureData = textureRegion.getTexture().getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }
        Pixmap pixmap = new Pixmap(
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight(),
                textureData.getFormat()
        );
        pixmap.drawPixmap(
                textureData.consumePixmap(), // The other Pixmap
                0, // The target x-coordinate (top left corner)
                0, // The target y-coordinate (top left corner)
                textureRegion.getRegionX(), // The source x-coordinate (top left corner)
                textureRegion.getRegionY(), // The source y-coordinate (top left corner)
                textureRegion.getRegionWidth(), // The width of the area from the other Pixmap in pixels
                textureRegion.getRegionHeight() // The height of the area from the other Pixmap in pixels
        );
        return pixmap;
    }
}

