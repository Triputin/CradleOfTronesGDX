package by.android.cradle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class BaseScreen implements Screen, InputProcessor
{
    protected Music[] musicArray  = new Music[2];
    protected Music instrumental;
    protected float audioVolume;
    protected Stage mainStage;
    protected Stage uiStage;
    protected Table uiTable;
    protected float aspectRatioOnStart;
    protected float aspectRatioKf;
    protected CradleGame cradleGame;
    protected IPlayServices ply;

    public BaseScreen(CradleGame cradleGame,IPlayServices ply)
    {
        this.aspectRatioOnStart = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
        mainStage = new Stage();
        uiStage = new Stage();

        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);
        this.cradleGame = cradleGame;
        this.ply=ply;

        initialize();
    }

    public void PlayMusic() {
        if (cradleGame.isMusicOn()){
        instrumental.play();
            System.out.println("instrumental.Play");
        } else{
            instrumental.pause();
        }

    }

    public void PauseMusic() {
        instrumental.pause();
    }
    public abstract void initialize();

    public abstract void update(float dt);

    // Gameloop:
    // (1) process input (discrete handled by listener; continuous in update)
    // (2) update game logic
    // (3) render the graphics
    public void render(float dt) 
    {
        // act methods
        uiStage.act(dt);
        mainStage.act(dt);

        // defined by user
        update(dt);

        // clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw the graphics
        mainStage.draw();
        uiStage.draw();
    }

    // methods required by Screen interface
    public void resize(int width, int height) {
        float aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
        aspectRatioKf = aspectRatio/aspectRatioOnStart;
        //mainStage.getViewport().update(Math.round(width*ratioKf), Math.round(height*ratioKf), true);
        //uiStage.getViewport().update(Math.round(width*ratioKf), Math.round(height*ratioKf), true);
        mainStage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);

    }

    public void pause()   {  }

    public void resume()  {  }

    public void dispose() {  }

    /**
     *  Called when this becomes the active screen in a Game.
     *  Set up InputMultiplexer here, in case screen is reactivated at a later time.
     */
    public void show()    
    {  
        InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);
    }

    /**
     *  Called when this is no longer the active screen in a Game.
     *  Screen class and Stages no longer process input.
     *  Other InputProcessors must be removed manually.
     */
    public void hide()    
    {  
        InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);
    }

    // methods required by InputProcessor interface
    public boolean keyDown(int keycode)
    {  return false;  }

    public boolean keyUp(int keycode)
    {  return false;  }

    public boolean keyTyped(char c) 
    {  return false;  }

    public boolean mouseMoved(int screenX, int screenY)
    {  return false;  }

    public boolean scrolled(int amount) 
    {  return false;  }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) 
    {  return false;  }

    public boolean touchDragged(int screenX, int screenY, int pointer) 
    {  return false;  }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) 
    {  return false;  }
}