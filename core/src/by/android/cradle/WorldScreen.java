package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class WorldScreen extends BaseScreen {

    private ArrayList<MainWorldItem> mainWorldItemArrayList;
    private BaseActor fon;
    private int scrollPos;
    private final int ItemsQttyPerScreen = 3;

    public WorldScreen(CradleGame cradleGame, IPlayServices ply) {
        super(cradleGame, ply);
    }


    public void initialize() {

        //instrumental = Gdx.audio.newMusic(Gdx.files.internal("sounds/2_hearts.mp3"));
        instrumental = cradleGame.getCradleAssetManager().manager.get(Assets.MUSIC_2_HEARTS);
        audioVolume = 0.7f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        //instrumental.play();
        scrollPos = 0;

        final int w = Gdx.graphics.getWidth();
        final int h = Gdx.graphics.getHeight();



        //Tiled texture
        Texture texture = new Texture(Gdx.files.internal("fon/tiled01.png"), true);
        texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.setRegion(0,0,texture.getWidth()*4,texture.getHeight()*4);
        Image image4 = new Image(textureRegion);
        image4.setSize(w,h);
        image4.setPosition(0,0);
        mainStage.addActor(image4);


        //Items
        mainWorldItemArrayList = new ArrayList<>();
        initializeMap();



        //Menu button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTex = new Texture( Gdx.files.internal("back_button02.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button backButton = new Button( buttonStyle );
        backButton.setSize(h*0.221f,h*0.22f);
        backButton.setPosition(5,h - backButton.getHeight()-10);
        uiStage.addActor(backButton);

        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    instrumental.pause();
                    cradleGame.setActiveGameMapScreen(false,0);
                    cradleGame.getGameMapScreen().UpdateRes();
                    return true;
                }
                return false;
            }
        });


        //Next Button
        Button.ButtonStyle buttonStyleN = new Button.ButtonStyle();
        Texture buttonTexN = new Texture( Gdx.files.internal("forward_button.png") );
        TextureRegion buttonRegionN =  new TextureRegion(buttonTexN);
        buttonStyleN.up = new TextureRegionDrawable( buttonRegionN );

        Button nextButton = new Button( buttonStyleN );
        nextButton.setSize(h*0.2f,h*0.2f);
        nextButton.setPosition(w-10-nextButton.getWidth(),h*0.02f);
        uiStage.addActor(nextButton);

        nextButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                moveForward();

                return true;
            }
        });

        //Prev Button
        Button.ButtonStyle buttonStyleP = new Button.ButtonStyle();
        Texture buttonTexP = new Texture( Gdx.files.internal("backward_button.png") );
        TextureRegion buttonRegionP =  new TextureRegion(buttonTexP);
        buttonStyleP.up = new TextureRegionDrawable( buttonRegionP );

        Button prevButton = new Button( buttonStyleP );
        prevButton.setSize(h*0.2f,h*0.2f);
        prevButton.setPosition(10,h*0.02f);
        uiStage.addActor(prevButton);

        prevButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                moveBackward();
                return true;
            }
        });


    }


    public void update(float dt)
    {

    }


    public void initializeMap(){

        for (int i=0; i<mainWorldItemArrayList.size();i++){
            mainWorldItemArrayList.get(i).remove();
        }
        mainWorldItemArrayList.clear();
        if (fon!=null){
            fon.clear();
            fon.remove();
        }

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        int itemSizeH = Math.round(h*0.5f);
        int itemSizeW = w/ItemsQttyPerScreen;
        int itemPosition = Math.round(h*0.5f);
        for (int i=0; i<cradleGame.MaxGameMapLevel;i++){
            mainWorldItemArrayList.add(new MainWorldItem(i*itemSizeW, Math.round(itemPosition - itemSizeH*0.5f),itemSizeW,itemSizeH,mainStage,cradleGame,i+1));
        }


        //Fon
        fon = new BaseActor(0, 0, uiStage, Touchable.disabled,cradleGame);
        fon.loadTexture("maps/main_world_frame01.png", w, itemSizeH);
        fon.setPosition(0,itemPosition-fon.getHeight()*0.5f);
        uiStage.addActor(fon);
    }

    private void moveForward(){
        MainWorldItem mainWorldItem;
        if (cradleGame.getMaxGameMapLevel()>(scrollPos+ItemsQttyPerScreen)){
            scrollPos++;
            for (int i=0; i<mainWorldItemArrayList.size();i++){
                mainWorldItem = mainWorldItemArrayList.get(i);
                mainWorldItem.setX((i-scrollPos)*mainWorldItem.getWidth());
            }
        }
    }


    private void moveBackward(){
        MainWorldItem mainWorldItem;
        if (scrollPos>0){
            scrollPos--;
            for (int i=0; i<mainWorldItemArrayList.size();i++){
                mainWorldItem = mainWorldItemArrayList.get(i);
                mainWorldItem.setX((i-scrollPos)*mainWorldItem.getWidth());
            }
        }
    }
}