package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class Kingdom extends BaseActor {

    private KingdomNames kingdomNames;
    private KingdomRes kingdomRes;
    private int protectionState;
    private Label protectionStateLabel;
    private Animation<TextureRegion> animation;
    private int flagSize;
    private BaseActor baseActor;
    private long timeOfLastGoldCollection;
    private Label timeStateLabel;
    private int levelOfKingdom;
    private Actor goldImage;
    private Sound coinSound;
    private int goldImageX;
    private int goldImageY;
    private int w;
    private int h;
    private final ResultsActor resultsActor;

    private int kingdomID; // Should be an unique number through the whole game

    private String[] filenames0 =
            { "flag_red/flag02.png", "flag_red/flag03.png",
                    "flag_red/flag04.png", "flag_red/flag05.png", "flag_red/flag06.png",
                    "flag_red/flag07.png", "flag_red/flag08.png", "flag_red/flag09.png",
                    "flag_red/flag10.png", "flag_red/flag11.png", "flag_red/flag12.png"
            };
    private String[] filenames1 =
            { "flag_yellow/flag02.png", "flag_yellow/flag03.png",
                    "flag_yellow/flag04.png", "flag_yellow/flag05.png", "flag_yellow/flag06.png",
                    "flag_yellow/flag07.png", "flag_yellow/flag08.png", "flag_yellow/flag09.png",
                    "flag_yellow/flag10.png", "flag_yellow/flag11.png", "flag_yellow/flag12.png"
            };
    private String[] filenames2 =
        { "flag_purple/flag02.png", "flag_purple/flag03.png",
                "flag_purple/flag04.png", "flag_purple/flag05.png", "flag_purple/flag06.png",
                "flag_purple/flag07.png", "flag_purple/flag08.png", "flag_purple/flag09.png",
                "flag_purple/flag10.png", "flag_purple/flag11.png", "flag_purple/flag12.png"
        };
    private String[] filenames3 =
            { "flag_orange/flag02.png", "flag_orange/flag03.png",
                    "flag_orange/flag04.png", "flag_orange/flag05.png", "flag_orange/flag06.png",
                    "flag_orange/flag07.png", "flag_orange/flag08.png", "flag_orange/flag09.png",
                    "flag_orange/flag10.png", "flag_orange/flag11.png", "flag_orange/flag12.png"
            };

    private String[] filenames4 =
            { "flag_green/flag02.png", "flag_green/flag03.png",
                    "flag_green/flag04.png", "flag_green/flag05.png", "flag_green/flag06.png",
                    "flag_green/flag07.png", "flag_green/flag08.png", "flag_green/flag09.png",
                    "flag_green/flag10.png", "flag_green/flag11.png", "flag_green/flag12.png"
            };
    private   String[] filenames5 =
            { "flag_brown/flag02.png", "flag_brown/flag03.png",
                    "flag_brown/flag04.png", "flag_brown/flag05.png", "flag_brown/flag06.png",
                    "flag_brown/flag07.png", "flag_brown/flag08.png", "flag_brown/flag09.png",
                    "flag_brown/flag10.png", "flag_brown/flag11.png", "flag_brown/flag12.png"
            };
    private String[] filenames6 =
            { "flag_blue/flag02.png", "flag_blue/flag03.png",
                    "flag_blue/flag04.png", "flag_blue/flag05.png", "flag_blue/flag06.png",
                    "flag_blue/flag07.png", "flag_blue/flag08.png", "flag_blue/flag09.png",
                    "flag_blue/flag10.png", "flag_blue/flag11.png", "flag_blue/flag12.png"
            };
    private String[] filenames00 =
            { "flag_red/flag02.png", "flag_red/flag03.png",
                    "flag_red/flag04.png", "flag_red/flag05.png", "flag_red/flag06.png",
                    "flag_red/flag07.png", "flag_red/flag08.png", "flag_red/flag09.png",
                    "flag_red/flag10.png", "flag_red/flag11.png", "flag_red/flag12.png"
            };
    private String[] filenames7 =
            { "flag_gray/flag02.png", "flag_gray/flag03.png",
                    "flag_gray/flag04.png", "flag_gray/flag05.png", "flag_gray/flag06.png",
                    "flag_gray/flag07.png", "flag_gray/flag08.png", "flag_gray/flag09.png",
                    "flag_gray/flag10.png", "flag_gray/flag11.png", "flag_gray/flag12.png"
            };

    public Kingdom(float x, float y, int width, int height, Stage s, Touchable touchable,KingdomNames kingdomNames, int kingdomID, final CradleGame  cradleGame,final ResultsActor resultsActor)
    {
        super(x,y,s, touchable,cradleGame);
        System.out.println("Kingdom constructor. Id="+kingdomID);
        this.resultsActor = resultsActor;
        flagSize = height;
        this.kingdomID = kingdomID;
        this.kingdomNames = kingdomNames;
        kingdomRes = new KingdomRes();
        protectionState = 1;
        setHeight(height);
        setWidth(width);
        setBoundaryPolygon(8);
        //coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/change_drop_on_wood.mp3"));
        coinSound = cradleGame.getCradleAssetManager().manager.get(Assets.SOUND_CHANGE_DROP_ON_WOOD);
        // Get screen size
         w = Gdx.graphics.getWidth();
         h = Gdx.graphics.getHeight();

        setUpKingdomFlagAndResources(width, height,s,kingdomNames);
        saveParams(); //useful for first run - remembers first run time
        final Action  completeAction = new Action(){
            public boolean act( float delta ) {
                goldImage.setVisible(false);
                goldImage.setPosition(goldImageX,goldImageY);
                cradleGame.setGameResGold(GameRes.Gold+getGoldForLevelOfKingdom());
                resultsActor.UpdateRes();
                saveParams();
                return true;
            }
        };
        addListener(
                new InputListener() {
                    public boolean touchDown(InputEvent event, float offsetX, float offsetY,
                                             int pointer, int button) {

                        long timeForLevelOfKingdom = getTimeForLevelOfKingdom();
                        long timeToShow = timeForLevelOfKingdom - (System.currentTimeMillis() - timeOfLastGoldCollection);
                        if ((timeToShow<=0) && (protectionState == 0)) {
                            timeOfLastGoldCollection = System.currentTimeMillis();
                            if(cradleGame.isSoundOn()){
                                coinSound.play(1f);
                            }
                            Action actions = sequence(Actions.moveTo(w*0.4f, h, 2.00f, Interpolation.bounceOut),completeAction,fadeIn(0.01f));
                            goldImage.addAction(actions);
                            goldImage.addAction(fadeOut(2.0f));

                        }

                        return  true;
                    }
                });

    }

    private void setUpKingdomFlagAndResources(int width, int height, Stage s,KingdomNames kingdomNames){
        String[] filenames;
        String flagBasementName;

        // Get screen size
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        switch (kingdomNames){
            case Kingdom_of_the_North: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames = filenames0;
                flagBasementName = "flag_red/flagbasement.png";
                protectionState=0;
                break;
            case Kingdom_of_the_Isles_and_Rivers: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames=filenames1;
                flagBasementName = "flag_yellow/flagbasement.png";
                protectionState=5;
                break;
            case Kingdom_of_the_Mountain_and_the_Vale: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames = filenames2;
                flagBasementName = "flag_purple/flagbasement.png";
                protectionState=7;
                break;
            case Kingdom_of_the_Reach: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames = filenames3;
                flagBasementName = "flag_orange/flagbasement.png";
                protectionState=5;
                break;
            case Kingdom_of_the_Rock: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames=filenames4;
                flagBasementName = "flag_green/flagbasement.png";
                protectionState=7;
                break;
            case Kingdom_of_the_Stormlands: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames = filenames5;
                flagBasementName = "flag_brown/flagbasement.png";
                protectionState=7;
                break;
            case Principality_of_Dorne: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames=filenames6;
                flagBasementName = "flag_blue/flagbasement.png";
                protectionState=5;
                break;

            default:
                kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames = filenames00;
                flagBasementName = "flag_red/flagbasement.png";
                protectionState=1;

        }
        getParams();
        animation = createAnimationFromFiles(filenames, 0.1f, true, flagSize,  flagSize);
        baseActor = new BaseActor((int) width/2f,(int) (height*0.9f),s,Touchable.enabled,cradleGame);
        baseActor.setAnimation(animation);
        addActor(baseActor);


        baseActor.AddImage(flagBasementName,0,0,flagSize,flagSize);
        baseActor.AddImage("kingdoms/winterfell.png", (int) (0-(width/2.8f)),(int)(0-(height*0.5f)),(int) width, (int) height);
        baseActor.AddImage("shield.png",0-10,(int)(0-(height*0.5f))+5,(int) width/2, (int) height/2);
        protectionStateLabel = new Label(""+protectionState, BaseGame.labelStyle);
        if (protectionState==0){
            protectionStateLabel.setText("");
        }
        protectionStateLabel.setColor( Color.GOLDENROD );
        protectionStateLabel.setPosition( (int) (int) ((width*0.05f)),(int)(0-(height*0.4f)));
        if (w>1000) {
            protectionStateLabel.setFontScale(2.0f);
        }
        else
        {
            protectionStateLabel.setFontScale(1.5f);
        }
        baseActor.addActor(protectionStateLabel);

        timeStateLabel = new Label("", BaseGame.labelStyle);
        timeStateLabel.setColor( Color.GOLDENROD );
        timeStateLabel.setPosition( (int) ((width*0.4f)),(int)(height*0.7f));
        if (w>1000){
            timeStateLabel.setFontScale(1.6f);
        }
        else{
            timeStateLabel.setFontScale(1.3f);
        }
        addActor(timeStateLabel);

        goldImageX = Math.round(getWidth()*0.3f);
        goldImageY = Math.round(getHeight()*0.4f);
        goldImage = AddImage("coin2.png",goldImageX,goldImageY,Math.round(getWidth()*0.9f),Math.round(getWidth()*0.9f));
        goldImage.setVisible(false);
    }

    public KingdomRes getKingdomResForAttack(){
        return kingdomRes;
    }

    public void decreaseProtection(){
        if (protectionState>0){
            protectionState--;
            protectionStateLabel.setText(""+protectionState);
            if (protectionState==0){
                addRedFlagAnimation();
                protectionStateLabel.setText("");
            }
        }
        saveParams();

    }

    private void addRedFlagAnimation(){
        animation = createAnimationFromFiles(filenames0, 0.1f, true, flagSize,  flagSize);
        if(baseActor!=null) {
            baseActor.setAnimation(animation);
            baseActor.AddImage("flag_red/flagbasement.png", 0, 0, flagSize, flagSize);
        }
    }

    public int getProtectionState() {
        return protectionState;
    }

public void resetProtectionState(int gameMapLevel) {
    switch (kingdomNames) {
        case Kingdom_of_the_North:
            kingdomRes.Bread = 10 * gameMapLevel;
            kingdomRes.Gold = 10 * gameMapLevel;
            kingdomRes.Wood = 10 * gameMapLevel;
            if(gameMapLevel==1){
            protectionState = 0;}
            else {
                protectionState = 5 + gameMapLevel;
            }
            break;
        case Kingdom_of_the_Isles_and_Rivers:
            kingdomRes.Bread = 10 * gameMapLevel;
            kingdomRes.Gold = 10 * gameMapLevel;
            kingdomRes.Wood = 10 * gameMapLevel;
            protectionState = 5+gameMapLevel;
            break;
        case Kingdom_of_the_Mountain_and_the_Vale:
            kingdomRes.Bread = 10 * gameMapLevel;
            kingdomRes.Gold = 25 * gameMapLevel;
            kingdomRes.Wood = 10 * gameMapLevel;
            protectionState = 7+gameMapLevel;
            break;
        case Kingdom_of_the_Reach:
            kingdomRes.Bread = 25 * gameMapLevel;
            kingdomRes.Gold = 10 * gameMapLevel;
            kingdomRes.Wood = 10 * gameMapLevel;
            protectionState = 5+gameMapLevel;
            break;
        case Kingdom_of_the_Rock:
            kingdomRes.Bread = 10 * gameMapLevel;
            kingdomRes.Gold = 10 * gameMapLevel;
            kingdomRes.Wood = 25 * gameMapLevel;
            protectionState = 7+gameMapLevel;
            break;
        case Kingdom_of_the_Stormlands:
            kingdomRes.Bread = 20 * gameMapLevel;
            kingdomRes.Gold = 20 * gameMapLevel;
            kingdomRes.Wood = 20 * gameMapLevel;
            protectionState = 7+gameMapLevel;
            break;
        case Principality_of_Dorne:
            kingdomRes.Bread = 5 * gameMapLevel;
            kingdomRes.Gold = 5 * gameMapLevel;
            kingdomRes.Wood = 5 * gameMapLevel;
            protectionState = 5+gameMapLevel;
            break;

        default:
            kingdomRes.Bread = 10 * gameMapLevel;
            kingdomRes.Gold = 10 * gameMapLevel;
            kingdomRes.Wood = 10 * gameMapLevel;
            protectionState = 1;

    }

    saveParams();
}

    public void setProtectionState(int protectionState) {
        if(protectionState>this.protectionState){
            resetFlag();
        }
        this.protectionState = protectionState;
        if(protectionStateLabel!=null) {
            protectionStateLabel.setText("" + protectionState);
        }
        if (protectionState==0){
            addRedFlagAnimation();
            if(protectionStateLabel!=null) {
            protectionStateLabel.setText("");
            }
        }

        saveParams();
    }

    public void resetFlag(){
        String[] filenames;
        String flagBasementName;

        switch (kingdomNames){
            case Kingdom_of_the_North: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                if(protectionState==0){
                    filenames = filenames0;
                    flagBasementName = "flag_red/flagbasement.png";
                }else{
                    filenames = filenames7;
                    flagBasementName = "flag_gray/flagbasement.png";
                }
                break;
            case Kingdom_of_the_Isles_and_Rivers: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames=filenames1;
                flagBasementName = "flag_yellow/flagbasement.png";
                break;
            case Kingdom_of_the_Mountain_and_the_Vale: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames = filenames2;
                flagBasementName = "flag_purple/flagbasement.png";
                break;
            case Kingdom_of_the_Reach: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames = filenames3;
                flagBasementName = "flag_orange/flagbasement.png";
                break;
            case Kingdom_of_the_Rock: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames=filenames4;
                flagBasementName = "flag_green/flagbasement.png";
                break;
            case Kingdom_of_the_Stormlands: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames = filenames5;
                flagBasementName = "flag_brown/flagbasement.png";
                break;
            case Principality_of_Dorne: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames=filenames6;
                flagBasementName = "flag_blue/flagbasement.png";
                break;

            default:
                kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                filenames = filenames00;
                flagBasementName = "flag_red/flagbasement.png";

        }

        if(protectionState==0){
            filenames = filenames0;
            flagBasementName = "flag_red/flagbasement.png";
        }

        animation = createAnimationFromFiles(filenames, 0.1f, true, flagSize,  flagSize);
        if (baseActor != null) {
            baseActor.setAnimation(animation);
            baseActor.AddImage(flagBasementName, 0, 0, flagSize, flagSize);
        }

    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (protectionState==0) {
            showTimeTillGoldCollection();
        }
    }

    private void showTimeTillGoldCollection(){

        long timeForLevelOfKingdom = getTimeForLevelOfKingdom();

        long timeToShow = timeForLevelOfKingdom - (System.currentTimeMillis() - timeOfLastGoldCollection);
        if (timeToShow>0) {
            if(timeToShow<60000) {
                timeStateLabel.setText(String.valueOf(Math.round(timeToShow / 1000f))+"s");
            }
            if((timeToShow>=60000) && (timeToShow<3600000)){
                timeStateLabel.setText(String.valueOf(Math.round(timeToShow / 60000f))+"m");
            }
            if((timeToShow>=3600000)){
                timeStateLabel.setText(String.valueOf(Math.round(timeToShow / 3600000f))+"h");
            }

            timeStateLabel.setX(baseActor.getWidth()*0.5f-timeStateLabel.getWidth()*0.5f);

        }
        else{
            timeStateLabel.setText("");
            goldImage.setVisible(true);

        }
    }

    private long getTimeForLevelOfKingdom(){
        long timeForLevelOfKingdom=10000;

        switch(levelOfKingdom){
            case 1:
                timeForLevelOfKingdom=1200000;
                break;

        }

        return timeForLevelOfKingdom;
    }


    private int getGoldForLevelOfKingdom(){
        int goldForLevelOfKingdom=10;

        switch(levelOfKingdom){
            case 1:
                goldForLevelOfKingdom=20;
                break;

        }

        return goldForLevelOfKingdom;
    }

    public void saveParams(){
        cradleGame.getPrefs().putLong("KingdomTime"+kingdomID,timeOfLastGoldCollection);
        cradleGame.getPrefs().putInteger("LevelOfKingdom"+kingdomID,levelOfKingdom);
        cradleGame.getPrefs().putInteger("KingdomProtectionState"+kingdomID,protectionState);
        cradleGame.getPrefs().flush();
        System.out.println("saveParams(): setProtectionState id="+kingdomID+ " = "+protectionState);
    }

    private void getParams(){
        timeOfLastGoldCollection = cradleGame.getPrefs().getLong("KingdomTime"+kingdomID,System.currentTimeMillis());
        levelOfKingdom = cradleGame.getPrefs().getInteger("LevelOfKingdom"+kingdomID,1);
        setProtectionState(cradleGame.getPrefs().getInteger("KingdomProtectionState"+kingdomID,5));
        System.out.println("getParams(): setProtectionState id="+kingdomID+ " = "+ cradleGame.getPrefs().getInteger("KingdomProtectionState"+kingdomID,5));
    }
}
