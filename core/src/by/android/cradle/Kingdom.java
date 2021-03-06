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
    private BaseActor baseActorFlag;
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
    private AttackTargetInfo attackTargetInfo;


    private int kingdomID; // Should be an unique number through the whole game


    public Kingdom(float x, float y, int width, int height, Stage s, Touchable touchable,KingdomNames kingdomNames, int kingdomID, final CradleGame  cradleGame,final ResultsActor resultsActor)
    {
        super(x,y,s, touchable,cradleGame);
        System.out.println("Kingdom constructor. Id="+kingdomID);
        this.resultsActor = resultsActor;
        attackTargetInfo = new AttackTargetInfo(kingdomID);
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
                            cradleGame.getPly().logEvent("20", "Kingdom gold collected", "Kingdom gold collected");
                            if (!cradleGame.isKingdomGoldCollected()) {
                                cradleGame.getPrefs().putBoolean("iskingdomgoldcollected", true);
                                cradleGame.getPrefs().flush();
                                //cradleGame.getScreenGamePlay().HideGoldArrow();
                            }
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

        //Get Planned res values
        KingdomRes kR = getKingdomPlannedRes(kingdomID);
        kingdomRes.Gold = kR.Gold;
        kingdomRes.Wood = kR.Wood;
        kingdomRes.Bread = kR.Bread;

        baseActor = new BaseActor((int) width/2f,(int) (height*0.9f),s,Touchable.enabled,cradleGame);
        baseActorFlag = new BaseActor((int) width/1.6f,(int) (height*1.47f),s,Touchable.enabled,cradleGame);

        switch (kingdomNames){
            case Kingdom_of_the_North:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGRED_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTRED_ANIMATION_ID));
                break;
            case Kingdom_of_the_Isles_and_Rivers:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGYELLOW_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTYELLOW_ANIMATION_ID));
                break;
            case Kingdom_of_the_Mountain_and_the_Vale:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGPURPLE_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTPURPLE_ANIMATION_ID));
                break;
            case Kingdom_of_the_Reach:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGYELLOW_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTYELLOW_ANIMATION_ID));
                break;
            case Kingdom_of_the_Rock:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGGREEN_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTGREEN_ANIMATION_ID));
                break;
            case Kingdom_of_the_Stormlands:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBROWN_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTBROWN_ANIMATION_ID));
                break;
            case Principality_of_Dorne:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBLUE_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTBLUE_ANIMATION_ID));
                break;
            case Kingdom_Main01:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGORANGE_ANIMATION_ID));
                baseActorFlag.setPosition( width/1.75f,height*1.28f);
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTORANGE_ANIMATION_ID));
                break;
            default:
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTRED_ANIMATION_ID));
                protectionState=1;

        }
        getParams();
        //System.out.println("setUpKingdomFlagAndResources: kingdomID="+kingdomID);
        //System.out.println("setUpKingdomFlagAndResources: protectionState="+protectionState);
        // заменить цвет флага если уровень защиты = 0
        if (protectionState==0){
            //filenames = filenames0;
            baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGRED_ANIMATION_ID));
            //flagBasementName = "flag_red/flagbasement.png";
            baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTRED_ANIMATION_ID));

        }

        addActor(baseActorFlag);
        addActor(baseActor);
        switch (kingdomNames) {
            case Kingdom_Main01:
                baseActor.addActor(cradleGame.getCradleAssetManager().AddImage(Assets.CASTLE_MAIN01, (int) (0 - (width / 2.8f)), (int) (0 - (height * 0.5f)), (int) width, (int) height));
                break;
            default:
                //baseActor.AddImage("kingdoms/winterfell.png", (int) (0-(width/2.8f)),(int)(0-(height*0.5f)),(int) width, (int) height);
                baseActor.addActor(cradleGame.getCradleAssetManager().AddImage(Assets.CASTLE_BASE01, (int) (0 - (width / 2.8f)), (int) (0 - (height * 0.5f)), (int) width, (int) height));

        }

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
        //increase res with lowering protection state
        KingdomRes kr = new KingdomRes();
        int dif = getKingdomPlannedProtectionState(kingdomID) - protectionState;
        kr.Bread = kingdomRes.Bread+10*dif;
        kr.Gold = kingdomRes.Gold+10*dif;
        kr.Wood = kingdomRes.Wood+10*dif;
        return kr;

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

        if(baseActor!=null) {
            baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGRED_ANIMATION_ID));
            baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTRED_ANIMATION_ID));
        }
    }

    public int getProtectionState() {
        return protectionState;
    }

public void resetProtectionState(int gameMapLevel) {
    this.kingdomRes = getKingdomPlannedRes(this.kingdomID);
    this.protectionState = getKingdomPlannedProtectionState(this.kingdomID);
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
            case Kingdom_of_the_North:
                if(protectionState==0){
                    baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGRED_ANIMATION_ID));
                    baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTRED_ANIMATION_ID));
                }else{
                    baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGGRAY_ANIMATION_ID));
                    baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTGRAY_ANIMATION_ID));
                }
                break;
            case Kingdom_of_the_Isles_and_Rivers:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGYELLOW_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTYELLOW_ANIMATION_ID));
                break;
            case Kingdom_of_the_Mountain_and_the_Vale:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGPURPLE_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTPURPLE_ANIMATION_ID));
                break;
            case Kingdom_of_the_Reach:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGYELLOW_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTYELLOW_ANIMATION_ID));
                break;
            case Kingdom_of_the_Rock:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGGREEN_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTGREEN_ANIMATION_ID));
                break;
            case Kingdom_of_the_Stormlands:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBROWN_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTBROWN_ANIMATION_ID));
                break;
            case Principality_of_Dorne:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBLUE_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTBLUE_ANIMATION_ID));
                break;
            case Kingdom_Main01:
                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGORANGE_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTORANGE_ANIMATION_ID));
                break;
            default:

                baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGRED_ANIMATION_ID));
                baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTRED_ANIMATION_ID));

        }

        if(protectionState==0){
            baseActorFlag.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGRED_ANIMATION_ID));
            baseActor.setAnimation(cradleGame.getCradleAssetManager().getAnimation(Assets.FLAGBASEMENTRED_ANIMATION_ID));
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

        cradleGame.getPrefs().putInteger("KingdomGoldParams"+kingdomID,this.kingdomRes.Gold);
        cradleGame.getPrefs().putInteger("KingdomBreadParams"+kingdomID,this.kingdomRes.Bread);
        cradleGame.getPrefs().putInteger("KingdomWoodParams"+kingdomID,this.kingdomRes.Wood);

        cradleGame.getPrefs().flush();
        System.out.println("saveParams(): setProtectionState id="+kingdomID+ " = "+protectionState);
    }

    private void getParams(){
        timeOfLastGoldCollection = cradleGame.getPrefs().getLong("KingdomTime"+kingdomID,System.currentTimeMillis());
        levelOfKingdom = cradleGame.getPrefs().getInteger("LevelOfKingdom"+kingdomID,1);
        setProtectionState(cradleGame.getPrefs().getInteger("KingdomProtectionState"+kingdomID,5));

        kingdomRes.Gold = cradleGame.getPrefs().getInteger("KingdomGoldParams"+kingdomID,1);
        kingdomRes.Bread = cradleGame.getPrefs().getInteger("KingdomBreadParams"+kingdomID,1);
        kingdomRes.Wood = cradleGame.getPrefs().getInteger("KingdomWoodParams"+kingdomID,1);


        System.out.println("getParams(): setProtectionState id="+kingdomID+ " = "+ cradleGame.getPrefs().getInteger("KingdomProtectionState"+kingdomID,5));
    }


    public static int getKingdomPlannedProtectionState(int Id){
        int res=5;
        switch(Id){
            //level 1
            case 0: res=0;break;
            case 1: res=5;break;
            case 2: res=7;break;
            case 3: res=5;break;
            case 4: res=7;break;
            case 5: res=5;break;
            case 6: res=7;break;
            case 7: res=9;break;
            //level 2
            case 10: res=6;break;
            case 11: res=8;break;
            case 12: res=6;break;
            case 13: res=8;break;
            case 14: res=6;break;
            case 15: res=8;break;
            case 16: res=6;break;
            case 17: res=9;break;
            // level 3
            case 20: res=7;break;
            case 21: res=9;break;
            case 22: res=7;break;
            case 23: res=9;break;
            case 24: res=7;break;
            case 25: res=9;break;
            case 26: res=7;break;
            case 27: res=9;break;
            // level 4
            case 30: res=9;break;
            case 31: res=9;break;
            case 32: res=8;break;
            case 33: res=9;break;
            case 34: res=8;break;
            case 35: res=9;break;
            case 36: res=9;break;
            case 37: res=10;break;
        }
        return res;
    }

    public static KingdomRes getKingdomPlannedRes(int Id){
        KingdomRes kingdomRes=new KingdomRes();

        switch(Id){
            //level 1
            case 0:
                kingdomRes.Gold = 10;
                kingdomRes.Bread = 10;
                kingdomRes.Wood = 10;
            break;
            case 1:
                kingdomRes.Gold = 15;
                kingdomRes.Bread = 10;
                kingdomRes.Wood = 15;
                break;
            case 2:
                kingdomRes.Gold = 10;
                kingdomRes.Bread = 25;
                kingdomRes.Wood = 10;
                break;
            case 3:
                kingdomRes.Gold = 20;
                kingdomRes.Bread = 10;
                kingdomRes.Wood = 10;
                break;
            case 4:
                kingdomRes.Gold = 10;
                kingdomRes.Bread = 10;
                kingdomRes.Wood = 20;
                break;
            case 5:
                kingdomRes.Gold = 20;
                kingdomRes.Bread = 20;
                kingdomRes.Wood = 20;
                break;
            case 6:
                kingdomRes.Gold = 5;
                kingdomRes.Bread = 5;
                kingdomRes.Wood = 5;
                break;
            case 7:
                kingdomRes.Gold = 30;
                kingdomRes.Bread = 30;
                kingdomRes.Wood = 30;
                break;

            //level 2
            case 10:
                kingdomRes.Gold = 20;
                kingdomRes.Bread = 20;
                kingdomRes.Wood = 20;
                break;
            case 11:
                kingdomRes.Gold = 30;
                kingdomRes.Bread = 20;
                kingdomRes.Wood = 30;
                break;
            case 12:
                kingdomRes.Gold = 10;
                kingdomRes.Bread = 35;
                kingdomRes.Wood = 10;
                break;
            case 13:
                kingdomRes.Gold = 40;
                kingdomRes.Bread = 10;
                kingdomRes.Wood = 10;
                break;
            case 14:
                kingdomRes.Gold = 10;
                kingdomRes.Bread = 10;
                kingdomRes.Wood = 40;
                break;
            case 15:
                kingdomRes.Gold = 30;
                kingdomRes.Bread = 30;
                kingdomRes.Wood = 30;
                break;
            case 16:
                kingdomRes.Gold = 10;
                kingdomRes.Bread = 10;
                kingdomRes.Wood = 10;
                break;
            case 17:
                kingdomRes.Gold = 50;
                kingdomRes.Bread = 50;
                kingdomRes.Wood = 50;
                break;

            // level 3
            case 20:
                kingdomRes.Gold = 30;
                kingdomRes.Bread = 30;
                kingdomRes.Wood = 30;
                break;
            case 21:
                kingdomRes.Gold = 50;
                kingdomRes.Bread = 20;
                kingdomRes.Wood = 50;
                break;
            case 22:
                kingdomRes.Gold = 20;
                kingdomRes.Bread = 50;
                kingdomRes.Wood = 20;
                break;
            case 23:
                kingdomRes.Gold = 80;
                kingdomRes.Bread = 20;
                kingdomRes.Wood = 20;
                break;
            case 24:
                kingdomRes.Gold = 20;
                kingdomRes.Bread = 20;
                kingdomRes.Wood = 60;
                break;
            case 25:
                kingdomRes.Gold = 50;
                kingdomRes.Bread = 50;
                kingdomRes.Wood = 50;
                break;
            case 26:
                kingdomRes.Gold = 20;
                kingdomRes.Bread = 20;
                kingdomRes.Wood = 20;
                break;
            case 27:
                kingdomRes.Gold = 70;
                kingdomRes.Bread = 70;
                kingdomRes.Wood = 70;
                break;
            // level 4
            case 30:
                kingdomRes.Gold = 40;
                kingdomRes.Bread = 40;
                kingdomRes.Wood = 40;
                break;
            case 31:
                kingdomRes.Gold = 70;
                kingdomRes.Bread = 30;
                kingdomRes.Wood = 70;
                break;
            case 32:
                kingdomRes.Gold = 30;
                kingdomRes.Bread = 80;
                kingdomRes.Wood = 30;
                break;
            case 33:
                kingdomRes.Gold = 90;
                kingdomRes.Bread = 40;
                kingdomRes.Wood = 30;
                break;
            case 34:
                kingdomRes.Gold = 30;
                kingdomRes.Bread = 30;
                kingdomRes.Wood = 90;
                break;
            case 35:
                kingdomRes.Gold = 80;
                kingdomRes.Bread = 80;
                kingdomRes.Wood = 80;
                break;
            case 36:
                kingdomRes.Gold = 30;
                kingdomRes.Bread = 30;
                kingdomRes.Wood = 30;
                break;
            case 37:
                kingdomRes.Gold = 100;
                kingdomRes.Bread = 100;
                kingdomRes.Wood = 100;
                break;
                default:
                    kingdomRes.Gold = 10;
                    kingdomRes.Bread = 10;
                    kingdomRes.Wood = 10;
                    break;
        }

        return kingdomRes;
    }

    public KingdomNames getKingdomNames() {
        return kingdomNames;
    }

    public AttackTargetInfo getAttackTargetInfo() {
        return attackTargetInfo;
    }
}
