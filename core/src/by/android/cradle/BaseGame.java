package by.android.cradle;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    public static LabelStyle labelStyle_SuperSmall; // BitmapFont + Color
    public static LabelStyle labelStyle_Small; // BitmapFont + Color
    public static LabelStyle labelStyle_Middle; // BitmapFont + Color
    public static LabelStyle labelStyle_Big; // BitmapFont + Color

    public static TextButtonStyle textButtonStyle; // NPD + BitmapFont + Color
    public static TextButtonStyle textButtonStyleCheck; // for check boxes
    public static Skin skin;
    public static Skin dialogSkin;

    public static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";


    protected CradleAssetManager cradleAssetManager;

    /**
     *  Called when game is initialized; stores global reference to game object.
     */
    public BaseGame()
    {        
        game = this;
        cradleAssetManager = new CradleAssetManager();
    }

    /**
     *  Called when game is initialized, 
     *  after Gdx.input and other objects have been initialized.
     */
    public void create() 
    {        
        // prepare for multiple classes/stages/actors to receive discrete input
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor( im );

        //Gdx.input.setCatchKey(Input.Keys.BACK, true); //to catch the back key so it is not passed on to the operating system
        //Gdx.input.setCatchKey(Input.Keys.MENU, true);

    }

    /**
     *  Used to switch screens while game is running.
     *  Method is static to simplify usage.
     */
    public static void setActiveScreen(BaseScreen s)
    {
        game.setScreen(s);
    }

    public CradleAssetManager getCradleAssetManager() {
        return cradleAssetManager;
    }

}