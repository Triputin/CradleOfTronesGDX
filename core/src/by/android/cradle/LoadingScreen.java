package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class LoadingScreen implements Screen {

    private Stage stage;
    private CradleGame cradleGame;
    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;

    private float startX, endX;
    private float percent;

    private Actor loadingBar;
    private int LoadingBarPlaceSize = 100;

    //private Texture ttrSplash;
    private Image image3W;

    private float loadingPercentFase2=0;
    private int loadingPhase=0;

    private int w;
    private int h;

    public LoadingScreen(CradleGame cradleGame) {
        super();
        this.cradleGame=cradleGame;

        // Initialize the stage where we will place everything
        stage = new Stage();
        // Get screen size
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();


    }

    @Override
    public void show() {
        // Tell the manager to load assets for the loading screen
        //cradleGame.getCradleAssetManager().manager.load("data/loading.pack", TextureAtlas.class);
        // Wait until they are finished loading
        //cradleGame.getCradleAssetManager().manager.finishLoading();



        // Get our textureatlas from the manager
        //TextureAtlas atlas = cradleGame.getCradleAssetManager().manager.get("data/loading.pack", TextureAtlas.class);

        // Grab the regions from the atlas and create some images
        //logo = new Image(atlas.findRegion("libgdx-logo"));
        Texture ttrSplash = new Texture("data/loading/3waystudio_splash01.png");
        image3W = new Image(ttrSplash);
        //image3W.setHeight(Gdx.graphics.getHeight()-LoadingBarPlaceSize);
        //image3W.setWidth(Gdx.graphics.getHeight()-LoadingBarPlaceSize);

        //loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingFrame = new Image(new Texture("data/loading/loading_frame.png"));

        //loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        loadingBarHidden = new Image(new Texture("data/loading/loading_bar_hidden.png"));
        //screenBg = new Image(atlas.findRegion("screen-bg"));
        screenBg = new Image(new Texture("data/loading/loading_screen_bg.png"));

        //loadingBg = new Image(atlas.findRegion("loading-frame-bg"));
        loadingBg = new Image(new Texture("data/loading/loading_frame_bg.png"));

        // Add the loading bar animation
        //Animation<TextureRegion>  anim = new Animation(0.05f, atlas.findRegions("loading-bar-anim") );
        //anim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        //loadingBar = new LoadingBar(anim);

        // Or if you only need a static bar, you can do
        //loadingBar = new Image(atlas.findRegion("loading-bar1"));
        loadingBar = new Image(new Texture("data/loading/loading_bar_bw.png"));

        // Add all the actors to the stage
        stage.addActor(screenBg);
        stage.addActor(image3W);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);

        //stage.addActor(logo);

        // Add everything to be loaded, for instance:
        cradleGame.getCradleAssetManager().loadAssets();


    }

    @Override
    public void resize(int width, int height) {
        // Set our screen to always be XXX x 480 in size
        //width = 480 * width / height;
        //height = 480;
        //stage.getViewport().update(width, height, true);


        // Make the background fill the screen
        screenBg.setSize(w, h);

        // Place the logo in the middle of the screen and 100 px up
        image3W.setHeight(h-LoadingBarPlaceSize);
        image3W.setWidth(h-LoadingBarPlaceSize);
        image3W.setX((w - image3W.getWidth()) / 2);
        image3W.setY(LoadingBarPlaceSize);

        // Place the loading frame in the middle of the screen
        loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY(30);

        // Place the loading bar at the same spot as the frame, adjusted a few px
        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 5);

        // Place the image that will hide the bar on top of the bar, adjusted a few px
        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);
        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 440;

        // The rest of the hidden bar
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
    }

    @Override
    public void render(float delta) {
        // clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (cradleGame.getCradleAssetManager().manager.update()&& percent>0.99f&& (loadingPhase==4)) { // Load some, will return true if done loading
            long ct = System.currentTimeMillis();
            loadingPhase=5;
            cradleGame.setScreen(cradleGame.getMenuScreen());
            System.out.println("set Menu Screen time : " +(System.currentTimeMillis()-ct));
        }

        // Interpolate the percentage to make it more smooth
        percent = Interpolation.linear.apply(percent, cradleGame.getCradleAssetManager().manager.getProgress()*0.3f+loadingPercentFase2, 0.1f);


        if (cradleGame.getCradleAssetManager().manager.update() && (loadingPhase==0)) { // Load some, will return true if done loading
            System.out.println("Loading... prepareAnimations");
            long ct = System.currentTimeMillis();
            cradleGame.getCradleAssetManager().prepareAnimations(cradleGame);
            System.out.println("prepareAnimations time : " +(System.currentTimeMillis()-ct));
            loadingPercentFase2 =0.2f;
            loadingPhase=1;

        }

        if (cradleGame.getCradleAssetManager().manager.update() && (percent>0.49f)&&(loadingPhase==1)) { // Load some, will return true if done loading

            loadingPercentFase2 =0.3f;
            loadingPhase=2;
            System.out.println("Loading... Init01");
            cradleGame.Init01();

        }

        if (cradleGame.getCradleAssetManager().manager.update() && (percent>0.59f)&&(loadingPhase==2)) { // Load some, will return true if done loading

            cradleGame.getCradleAssetManager().prepareAnimations(cradleGame);
            loadingPercentFase2 =0.4f;
            loadingPhase=3;
            System.out.println("Loading... Init02");
            cradleGame.Init02();

        }

        if (cradleGame.getCradleAssetManager().manager.update() && (percent>0.69f)&&(loadingPhase==3)) { // Load some, will return true if done loading

            cradleGame.getCradleAssetManager().prepareAnimations(cradleGame);
            loadingPercentFase2 =0.7f;
            loadingPhase=4;
            System.out.println("Loading... Init03");
            cradleGame.Init03();

        }

        // Update positions (and size) to match the percentage
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();

        // Show the loading screen
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        // Dispose the loading assets as we no longer need them
       // cradleGame.getCradleAssetManager().manager.unload("data/loading.pack");
    }


    public void pause()   {  }

    public void resume()  {  }

    public void dispose() {  }

}