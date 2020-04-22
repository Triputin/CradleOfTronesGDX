package by.android.cradle;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

//1. Loads Textures from files  AssetDescriptor used for that
//2. Prepare (scale) textures and makes animations depending from size of screen (it doesn't allow resize screen during the game)
// int _ANIMATION_ID used for that

public class Assets {
    private Assets(){}

    //Bread
    public static final AssetDescriptor<Texture> BREAD =
            new AssetDescriptor<Texture>("bread.png", Texture.class);
    public static final AssetDescriptor<Texture> BREAD_PRESSED =
            new AssetDescriptor<Texture>("breadpressed.png", Texture.class);
    //JEM01
    public static final AssetDescriptor<Texture> JEM01 =
            new AssetDescriptor<Texture>("gem01.png", Texture.class);
    public static final AssetDescriptor<Texture> JEM01_PRESSED =
            new AssetDescriptor<Texture>("gem01pressed.png", Texture.class);
    //COIN01
    public static final AssetDescriptor<Texture> COIN01 =
            new AssetDescriptor<Texture>("coin1.png", Texture.class);
    public static final AssetDescriptor<Texture> COIN01_PRESSED =
            new AssetDescriptor<Texture>("coin1pressed.png", Texture.class);
    //COIN02
    public static final AssetDescriptor<Texture> COIN02 =
            new AssetDescriptor<Texture>("coin2.png", Texture.class);
    public static final AssetDescriptor<Texture> COIN02_PRESSED =
            new AssetDescriptor<Texture>("coin2pressed.png", Texture.class);
    //Wood
    public static final AssetDescriptor<Texture> WOOD =
            new AssetDescriptor<Texture>("wood.png", Texture.class);
    public static final AssetDescriptor<Texture> WOOD_PRESSED =
            new AssetDescriptor<Texture>("woodpressed.png", Texture.class);

    //solareffectatlas
    public static final AssetDescriptor<TextureAtlas> SOLAREFFECT_ATLAS =
            new AssetDescriptor<TextureAtlas>("solareffect_atlas/solareffectatlas.atlas", TextureAtlas.class);

    //Locked Item
    public static final AssetDescriptor<Texture> LOCKED_ITEM_LEVEL01 =
            new AssetDescriptor<Texture>("lockitem01.png", Texture.class);
    public static final AssetDescriptor<Texture> LOCKED_ITEM_LEVEL02 =
            new AssetDescriptor<Texture>("lockitem02.png", Texture.class);

    //Arrows
    public static final AssetDescriptor<Texture> ArrowToEast =
            new AssetDescriptor<Texture>("arrowtoeast.png", Texture.class);
    public static final AssetDescriptor<Texture> ArrowToWest =
            new AssetDescriptor<Texture>("arrowtowest.png", Texture.class);
    public static final AssetDescriptor<Texture> HorizontalLine =
            new AssetDescriptor<Texture>("linehoriz.png", Texture.class);
    public static final AssetDescriptor<Texture> ArrowToNorth =
            new AssetDescriptor<Texture>("arrowtonorth.png", Texture.class);
    public static final AssetDescriptor<Texture> ArrowToSouth =
            new AssetDescriptor<Texture>("arrowtosouth.png", Texture.class);
    public static final AssetDescriptor<Texture> VerticalLine =
            new AssetDescriptor<Texture>("linevert.png", Texture.class);
    public static final AssetDescriptor<Texture> UpToLeft =
            new AssetDescriptor<Texture>("LineCorner03.png", Texture.class);
    public static final AssetDescriptor<Texture> UpToRight =
            new AssetDescriptor<Texture>("LineCorner04.png", Texture.class);
    public static final AssetDescriptor<Texture> DownToLeft =
            new AssetDescriptor<Texture>("LineCorner02.png", Texture.class);
    public static final AssetDescriptor<Texture> DownToRight =
            new AssetDescriptor<Texture>("LineCorner01.png", Texture.class);
    //Game cells
    public static final AssetDescriptor<Texture> GAMECELL =
            new AssetDescriptor<Texture>("gamecell.png", Texture.class);
    public static final AssetDescriptor<Texture> GAMECELL_LOCK01 =
            new AssetDescriptor<Texture>("gamecellock01.png", Texture.class);
    public static final AssetDescriptor<Texture> GAMECELL_LOCK02 =
            new AssetDescriptor<Texture>("gamecellock02.png", Texture.class);

    //Game maps
    public static final AssetDescriptor<Texture> WORLDMAP01 =
            new AssetDescriptor<Texture>("maps/worldmap01.png", Texture.class);
    public static final AssetDescriptor<Texture> WORLDMAP02 =
            new AssetDescriptor<Texture>("maps/worldmap02.png", Texture.class);
    public static final AssetDescriptor<Texture> WORLDMAP03 =
            new AssetDescriptor<Texture>("maps/worldmap03.png", Texture.class);
    public static final AssetDescriptor<Texture> WORLDMAP04 =
            new AssetDescriptor<Texture>("maps/worldmap04.png", Texture.class);

    //Sounds and music
    public static final AssetDescriptor<Music> MUSIC_2_HEARTS =
            new AssetDescriptor<Music>("sounds/2_hearts.mp3", Music.class);
    public static final AssetDescriptor<Music> MUSIC_NEW_LAND =
            new AssetDescriptor<Music>("sounds/new_land.mp3", Music.class);
    public static final AssetDescriptor<Music> MUSIC_SOUNDMAP02 =
            new AssetDescriptor<Music>("sounds/soundmap02.mp3", Music.class);
    public static final AssetDescriptor<Sound> SOUND_GLASS_WINDOW_BREAKING =
            new AssetDescriptor<Sound>("sounds/glass_windows_breaking.mp3", Sound.class);
    public static final AssetDescriptor<Sound> SOUND_CHANGE_DROP_ON_WOOD =
            new AssetDescriptor<Sound>("sounds/change_drop_on_wood.mp3", Sound.class);



    //animations IDs
    public static final int SOLAREFFECT_ANIMATION_ID = 1;
    public static final int COIN01_ANIMATION_ID = 2;
    public static final int COIN01_PRESSED_ANIMATION_ID = 3;
    public static final int COIN01_FROZEN_ANIMATION_ID = 4;

    public static final int LOCKED_ITEM_LEVEL01_ANIMATION_ID = 5;
    public static final int LOCKED_ITEM_LEVEL02_ANIMATION_ID = 6;

    public static final int COIN02_ANIMATION_ID = 7;
    public static final int COIN02_PRESSED_ANIMATION_ID = 8;
    public static final int COIN02_FROZEN_ANIMATION_ID = 9;

    public static final int BREAD_ANIMATION_ID = 10;
    public static final int BREAD_PRESSED_ANIMATION_ID = 11;
    public static final int BREAD_FROZEN_ANIMATION_ID = 12;

    public static final int WOOD_ANIMATION_ID = 13;
    public static final int WOOD_PRESSED_ANIMATION_ID = 14;
    public static final int WOOD_FROZEN_ANIMATION_ID = 15;

    public static final int JEM01_ANIMATION_ID = 16;
    public static final int JEM01_PRESSED_ANIMATION_ID = 17;
    public static final int JEM01_FROZEN_ANIMATION_ID = 18;

    public static final int GAMECELL_ANIMATION_ID = 19;
    public static final int GAMECELL_LOCK01_ANIMATION_ID = 20;
    public static final int GAMECELL_LOCK02_ANIMATION_ID = 21;

    public static final int WORLDMAP01_ANIMATION_ID = 22;
    public static final int WORLDMAP02_ANIMATION_ID = 23;
    public static final int WORLDMAP03_ANIMATION_ID = 24;
    public static final int WORLDMAP04_ANIMATION_ID = 25;

}
