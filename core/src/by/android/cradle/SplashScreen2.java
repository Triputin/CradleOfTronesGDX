package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


// Minimal dependencies alows to start asap.
public class SplashScreen2 implements Screen {
    private SpriteBatch batch;
    private Texture ttrSplash;
    public CustomLabel progressLabel;
    private Stage mainStage;
    public  Label.LabelStyle labelStyle; // BitmapFont + Color
    public SplashScreen2() {
        super();
        batch = new SpriteBatch();
        ttrSplash = new Texture("3waystudio_splash.png");
        mainStage = new Stage();
        createLabelStyle();
        progressLabel = new CustomLabel("0%",labelStyle);
        progressLabel.setPosition(Gdx.graphics.getWidth()/2f-progressLabel.getWidth()/2f,10);
        Image image = new Image(ttrSplash);
        image.setHeight(Gdx.graphics.getHeight());
        image.setWidth(Gdx.graphics.getWidth());
        mainStage.addActor(image);
        mainStage.addActor(progressLabel);
    }

    public void createLabelStyle(){

        // parameters for generating a custom bitmap font
        FreeTypeFontGenerator fontGenerator =
                new FreeTypeFontGenerator(Gdx.files.internal("opensans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = 24;
        fontParameters.characters = BaseGame.FONT_CHARACTERS;
        fontParameters.color = new Color(0.8f,0.8f,0.8f,1);
        fontParameters.borderWidth = 2;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParameters.magFilter = Texture.TextureFilter.Linear;

        BitmapFont customFont = fontGenerator.generateFont(fontParameters);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = customFont;
    }

    @Override
    public void render(float delta) {

        mainStage.act(delta);
        //progressLabel.setText(progressLabel.getText());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //batch.begin();
        //batch.draw(ttrSplash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainStage.draw();
        //batch.end();

    }

    @Override
    public void hide() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void show() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void dispose() {
        ttrSplash.dispose();
        batch.dispose();
    }
}