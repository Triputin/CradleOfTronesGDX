package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class CradleAssetManager  {

    public final AssetManager manager = new AssetManager();
    private HashMap<Integer,Animation<TextureRegion>> hashMapAnimations = new HashMap<>(); //holds animations
   // private HashMap<Integer,Actor> hashMapActors = new HashMap<>(); //holds Actors (Images...) doesn't work, because more than one actor is needed

    //Load textures from files, but load finished either after  finishLoading() or update() true
    public void loadAssets(){

        loadImages();
        loadMusicAndSounds();

    }

    private void loadMusicAndSounds(){
        manager.load(Assets.MUSIC_2_HEARTS);
        manager.load(Assets.MUSIC_NEW_LAND);
        manager.load(Assets.MUSIC_SOUNDMAP02);
        manager.load(Assets.SOUND_GLASS_WINDOW_BREAKING);
        manager.load(Assets.SOUND_CHANGE_DROP_ON_WOOD);

    }

    private void loadImages(){
        //Items
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
        manager.load(Assets.LOCKED_ITEM_LEVEL01);
        manager.load(Assets.LOCKED_ITEM_LEVEL02);
        //Arrows
        manager.load(Assets.ArrowToEast);
        manager.load(Assets.ArrowToWest);
        manager.load(Assets.HorizontalLine);
        manager.load(Assets.ArrowToNorth);
        manager.load(Assets.ArrowToSouth);
        manager.load(Assets.VerticalLine);
        manager.load(Assets.UpToLeft);
        manager.load(Assets.UpToRight);
        manager.load(Assets.DownToLeft);
        manager.load(Assets.DownToRight);
        //Game cells
        manager.load(Assets.GAMECELL);
        manager.load(Assets.GAMECELL_LOCK01);
        manager.load(Assets.GAMECELL_LOCK02);
        //Game maps
        manager.load(Assets.WORLDMAP01);
        manager.load(Assets.WORLDMAP02);
        manager.load(Assets.WORLDMAP03);
        manager.load(Assets.WORLDMAP04);

    }

    public void finishLoading(){
        manager.finishLoading();
    }

    //Scale textures up to screen resolution and prepare animation sets for Actors
    public void prepareAnimations(CradleGame cradleGame){

        //Animations
        hashMapAnimations.put(Assets.SOLAREFFECT_ANIMATION_ID,loadAnimationFromAssetsAtlas(Assets.SOLAREFFECT_ATLAS,1,5,0.2f, true,  cradleGame.getCellSize(),  cradleGame.getCellSize()));

        hashMapAnimations.put(Assets.COIN01_ANIMATION_ID,loadAnimationFromAssets(Assets.COIN01,cradleGame.getCellSize(),  cradleGame.getCellSize()));
        hashMapAnimations.put(Assets.COIN01_PRESSED_ANIMATION_ID,loadAnimationFromAssets(Assets.COIN01_PRESSED,cradleGame.getCellSize(),  cradleGame.getCellSize()));

        hashMapAnimations.put(Assets.COIN02_ANIMATION_ID,loadAnimationFromAssets(Assets.COIN02,cradleGame.getCellSize(),  cradleGame.getCellSize()));
        hashMapAnimations.put(Assets.COIN02_PRESSED_ANIMATION_ID,loadAnimationFromAssets(Assets.COIN02_PRESSED,cradleGame.getCellSize(),  cradleGame.getCellSize()));

        hashMapAnimations.put(Assets.BREAD_ANIMATION_ID,loadAnimationFromAssets(Assets.BREAD,cradleGame.getCellSize(),  cradleGame.getCellSize()));
        hashMapAnimations.put(Assets.BREAD_PRESSED_ANIMATION_ID,loadAnimationFromAssets(Assets.BREAD_PRESSED,cradleGame.getCellSize(),  cradleGame.getCellSize()));

        hashMapAnimations.put(Assets.WOOD_ANIMATION_ID,loadAnimationFromAssets(Assets.WOOD,cradleGame.getCellSize(),  cradleGame.getCellSize()));
        hashMapAnimations.put(Assets.WOOD_PRESSED_ANIMATION_ID,loadAnimationFromAssets(Assets.WOOD_PRESSED,cradleGame.getCellSize(),  cradleGame.getCellSize()));

        hashMapAnimations.put(Assets.JEM01_ANIMATION_ID,loadAnimationFromAssets(Assets.JEM01,cradleGame.getCellSize(),  cradleGame.getCellSize()));
        hashMapAnimations.put(Assets.JEM01_PRESSED_ANIMATION_ID,loadAnimationFromAssets(Assets.JEM01_PRESSED,cradleGame.getCellSize(),  cradleGame.getCellSize()));

        hashMapAnimations.put(Assets.GAMECELL_ANIMATION_ID,loadAnimationFromAssets(Assets.GAMECELL,cradleGame.getCellSize(),  cradleGame.getCellSize()));
        hashMapAnimations.put(Assets.GAMECELL_LOCK01_ANIMATION_ID,loadAnimationFromAssets(Assets.GAMECELL_LOCK01,cradleGame.getCellSize(),  cradleGame.getCellSize()));
        hashMapAnimations.put(Assets.GAMECELL_LOCK02_ANIMATION_ID,loadAnimationFromAssets(Assets.GAMECELL_LOCK02,cradleGame.getCellSize(),  cradleGame.getCellSize()));

        hashMapAnimations.put(Assets.WORLDMAP01_ANIMATION_ID,loadAnimationFromAssets(Assets.WORLDMAP01,cradleGame.getW(),  cradleGame.getH()));
        hashMapAnimations.put(Assets.WORLDMAP02_ANIMATION_ID,loadAnimationFromAssets(Assets.WORLDMAP02,cradleGame.getW(),  cradleGame.getH()));
        hashMapAnimations.put(Assets.WORLDMAP03_ANIMATION_ID,loadAnimationFromAssets(Assets.WORLDMAP03,cradleGame.getW(),  cradleGame.getH()));
        hashMapAnimations.put(Assets.WORLDMAP04_ANIMATION_ID,loadAnimationFromAssets(Assets.WORLDMAP04,cradleGame.getW(),  cradleGame.getH()));

        //Actors (Images) // Don't work, if actor the same it works equally everywhere
        //hashMapActors.put(Assets.LOCKED_ITEM_LEVEL01_ANIMATION_ID,AddImage(Assets.LOCKED_ITEM_LEVEL01,0,0,cradleGame.getCellSize(),  cradleGame.getCellSize()));
        // hashMapActors.put(Assets.LOCKED_ITEM_LEVEL02_ANIMATION_ID,AddImage(Assets.LOCKED_ITEM_LEVEL02,0,0,cradleGame.getCellSize(),  cradleGame.getCellSize()));


    }

    public  Animation<TextureRegion> getAnimation(int animationId){
        return hashMapAnimations.get(animationId);
    }

    /* // Don't work, if actor the same it works equally everywhere
    public  Actor getActor(int animationId){
        return hashMapActors.get(animationId);
    }
    */

    public void dispose(){
        manager.dispose();
    }



    //Scale textures up to screen resolution
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

    //Scale textures up to screen resolution
    public Animation<TextureRegion> loadAnimationFromAssets(AssetDescriptor<Texture> assetDescriptor, int width, int height)
    {
        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        Texture texture200 =  manager.get(assetDescriptor);
        TextureData textureData200 = texture200.getTextureData();
        if (!textureData200.isPrepared()) {
            textureData200.prepare();
        }
        Pixmap pixmap200 = textureData200.consumePixmap();

        Pixmap pixmap100 = new Pixmap(width, height, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        Texture texture = new Texture(pixmap100);
        texture.setFilter( Texture.TextureFilter.Linear, Texture.TextureFilter.Linear );
        pixmap200.dispose();
        pixmap100.dispose();
        texture200.dispose();


        TextureRegion textureRegion=new TextureRegion( texture );
        textureArray.add(textureRegion);
        Animation<TextureRegion> anim = new Animation<TextureRegion>(1, textureArray);
        anim.setPlayMode(Animation.PlayMode.LOOP);
        return anim;
    }

    //Scale textures up to screen resolution
    public Image AddImage(AssetDescriptor<Texture> assetDescriptor,int x, int y, int width, int height)
    {

        Texture texture200 =  manager.get(assetDescriptor);
        TextureData textureData200 = texture200.getTextureData();
        if (!textureData200.isPrepared()) {
            textureData200.prepare();
        }
        Pixmap pixmap200 = textureData200.consumePixmap();

        Pixmap pixmap100 = new Pixmap(width, height, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        Texture texture = new Texture(pixmap100);
        texture.setFilter( Texture.TextureFilter.Linear, Texture.TextureFilter.Linear );
        pixmap200.dispose();
        pixmap100.dispose();
        texture200.dispose();


        TextureRegion textureRegion=new TextureRegion( texture );

        TextureRegion imgTextureRegion = new TextureRegion(texture);
        //imgTextureRegion.setRegion(0,0,getWidth(),getHeight());
        imgTextureRegion.setRegion(x,y,width,height);
        TextureRegionDrawable imgTextureRegionDrawable = new TextureRegionDrawable(imgTextureRegion);
        Image img = new Image(texture);
        //img.setDrawable(imgTextureRegionDrawable);
        img.setSize(width,height);
        img.setPosition(x, y);
        //addActor(img);
        //texture.dispose(); // it influences to the image! so image holds pointer to texture.
        return img;

    }

}

