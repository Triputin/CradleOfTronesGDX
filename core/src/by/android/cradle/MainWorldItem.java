package by.android.cradle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MainWorldItem extends BaseActor {

    private Label label;
    private int gameMapLevel;
    private Actor clouds;
    private Actor lock;


    public MainWorldItem(int x, int y, int width, int height , Stage s, final CradleGame cradleGame, final int gameMapLevel)
    {
        super(x,y,s, Touchable.enabled,cradleGame);
        setWidth(width);
        setHeight(height);
        setBoundaryPolygon(8);
        this.gameMapLevel = gameMapLevel;
        setUpPicture(gameMapLevel);

        String st = cradleGame.getLanguageStrings().get("level");
        label = new Label(st+" "+gameMapLevel, BaseGame.labelStyle);
        label.setColor( Color.GOLDENROD );
        label.setFontScale(1.5f);
        label.setPosition(getWidth()/2 - label.getWidth()*0.7f, getHeight() * 0.6f- label.getHeight()*0.5f);
        addActor(label);


        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    if (gameMapLevel<=cradleGame.getMaxOpenedMapLevel()) {
                        cradleGame.setGameMapLevel(gameMapLevel);
                        cradleGame.setActiveGameMapScreen(false, gameMapLevel);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void setUpPicture(int gameMapLevel){
        switch(gameMapLevel){
            case 1:
                AddImage("maps/worldmap01_s.png",0,0 ,Math.round(getWidth()),Math.round( getHeight()));
                break;
            case 2:
                AddImage("maps/worldmap02_s.png",0,0, Math.round(getWidth()),Math.round( getHeight()));
                break;
            case 3:
                AddImage("maps/worldmap03_s.png",0,0, Math.round(getWidth()),Math.round( getHeight()));
                break;
            case 4:
                AddImage("maps/worldmap04_s.png",0,0, Math.round(getWidth()),Math.round( getHeight()));
                break;

        }

        if (gameMapLevel>cradleGame.getMaxOpenedMapLevel()){
            clouds = AddImage("maps/clouds.png",0,0, Math.round(getWidth()),Math.round( getHeight()));
            int lockSize = Math.round( getHeight()*0.3f);
            lock = AddImage("maps/lock.png",Math.round(getWidth()*0.5f-lockSize*0.5f),Math.round( getHeight()*0.2f), lockSize,lockSize);

        }
    }


}
