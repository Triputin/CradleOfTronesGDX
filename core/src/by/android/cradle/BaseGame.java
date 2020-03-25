package by.android.cradle;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 *  Created when program is launched; 
 *  manages the screens that appear during the game.
 */
public abstract class BaseGame extends Game
{
    /**
     *  Stores reference to game; used when calling setActiveScreen method.
     */
    protected static BaseGame game;

    public static LabelStyle labelStyle; // BitmapFont + Color
    public static TextButtonStyle textButtonStyle; // NPD + BitmapFont + Color
    public static TextButtonStyle textButtonStyleCheck; // for check boxes
    public static Skin skin;
    public static Skin dialogSkin;

    private static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

    /**
     *  Called when game is initialized; stores global reference to game object.
     */
    public BaseGame() 
    {        
        game = this;
    }

    /**
     *  Called when game is initialized, 
     *  after Gdx.input and other objects have been initialized.
     */
    public void create() 
    {        
        // prepare for multiple classes/stages/actors to receive discrete input
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor( im );

        // parameters for generating a custom bitmap font
        FreeTypeFontGenerator fontGenerator = 
            new FreeTypeFontGenerator(Gdx.files.internal("opensans.ttf"));
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 24;
        fontParameters.characters = FONT_CHARACTERS;
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 2;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = TextureFilter.Linear;
        fontParameters.magFilter = TextureFilter.Linear;

        BitmapFont customFont = fontGenerator.generateFont(fontParameters);

        labelStyle = new LabelStyle();
        labelStyle.font = customFont;
        
        textButtonStyle = new TextButtonStyle();

        Texture   buttonTex   = new Texture( Gdx.files.internal("goldbutton.png") );
        NinePatch buttonPatch = new NinePatch(buttonTex, 14,14,24,24);
        textButtonStyle.up    = new NinePatchDrawable( buttonPatch );

        Texture   buttonTex2   = new Texture( Gdx.files.internal("goldbutton_pressed.png") );
        NinePatch buttonPatch2 = new NinePatch(buttonTex2, 14,14,24,24);
        textButtonStyle.down    = new NinePatchDrawable( buttonPatch2 );

        textButtonStyle.font      = customFont;
        textButtonStyle.fontColor = Color.GRAY;

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        if (w>1000){
            fontParameters.size = 40;
        } else {
            fontParameters.size = 24;
        }
        BitmapFont customFontCheck = fontGenerator.generateFont(fontParameters);
        textButtonStyleCheck = new TextButtonStyle();

        Texture   buttonTexCheck   = new Texture( Gdx.files.internal("button01_normal_s.png") );
        NinePatch buttonPatchCheck = new NinePatch(buttonTexCheck, 14,14,14,14);
        textButtonStyleCheck.up    = new NinePatchDrawable( buttonPatchCheck );

        Texture   buttonTexCheck3= new Texture( Gdx.files.internal("button01_checked_s.png") );
        NinePatch buttonPatchCheck3 = new NinePatch(buttonTexCheck3, 14,14,24,24);
        textButtonStyleCheck.checked = new NinePatchDrawable( buttonPatchCheck3 );

        textButtonStyleCheck.font      = customFontCheck;
        textButtonStyleCheck.fontColor = Color.GRAY;


        //Don't work!!!!! Dialog crashes!
        // This is the Skin that we'll use to design our dialog
        dialogSkin = new Skin();
        // The only mandatory resource required for a Dialog is the WindowStyle
        Window.WindowStyle ws = new Window.WindowStyle();
        ws.titleFontColor = Color.GOLD;
        ws.titleFont = customFont;
        ws.stageBackground = new Image(new Texture( Gdx.files.internal("goldbutton.png") )).getDrawable();

        TextureRegion texture_region = new TextureRegion(new Texture( Gdx.files.internal("goldbutton.png") ));
        Sprite sprite = new Sprite(texture_region);
        NinePatch np = new NinePatch(sprite, 15, 15, 15, 15);
        NinePatchDrawable npd = new NinePatchDrawable(np);
        // We're using the 9patch drawable as the dialog element background
        ws.background = npd;
        // This WindowStyle needs to be set as the default style in the skin
        dialogSkin.add("default", ws);

    }

    /**
     *  Used to switch screens while game is running.
     *  Method is static to simplify usage.
     */
    public static void setActiveScreen(BaseScreen s)
    {
        game.setScreen(s);
    }

}