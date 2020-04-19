package by.android.cradle;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

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

    public static final int SOLAREFFECT_ANIMATION_ID = 1;

}
