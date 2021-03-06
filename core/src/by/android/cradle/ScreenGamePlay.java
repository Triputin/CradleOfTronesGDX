package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;

import static by.android.cradle.AttackTypeInfo.SingleTimeClearUp;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class ScreenGamePlay extends BaseScreen {
   // Game properties to save
   private int gameLevel;


    // Game constant params
    private final float SoundEffectsVolume = 0.1f;
    private int LevelDuration = 60; // Time of every level (HourGlass)
    private final int TIME_BEFORE_SHOW_SOLUTION = 8000;
    private final int TIME_BEFORE_SHOW_ENEMY_STEP = 1000;
    private int cellSize;
    private int CellCount;
    private GameField gameField;
    private float gameFieldX;
    private float gameFieldY;
    private boolean flag = false ; //indicates that user has already started selecting items
    //private boolean isEndOfLevelAnimation=false; // indicates that end of level animation is playing
    private Item lastSelectedItem;
    private Item firstSelectedItem;
    private ItemPos directionToFill;
    private ResultsActor resultsActor;
    private Label gameLevelLabel;
    private AttackTarget attackTarget;
    private AttackTargetSteps attackTargetSteps;
    private AttackTargetDialog attackTargetDialog;

    private ScreenGamePlay screenGamePlay;
    private SandGlass sandGlass;
    private Sound explosionSound;
    private Label messageLabel;
    private boolean isPaused; //indicates that screen is not active
    private AttackType attackType;
    private KingdomRes kingdomRes; //res to win
    private Kingdom attackedKingdom;
    private BaseActor hall;
    private float score_during_attack;
    private DialogBox_EndLevel dialogBox_endLevel;
    private boolean isEndLevelDialogActive;
    private DialogBox_NotEnoughSteps dialogBox_notEnoughSteps;
    private int QttyRewardedVideoWatched;
    private KingdomRes resultAttack;

    private Label timeBombQttyLabel;
    private Label squareBomb1QttyLabel;
    private Label squareBomb2QttyLabel;

    private Bombs_Frame bombs_frame;

    private Long timeLastSelectionEnded;
    public Knight knight;
    public Weapon weapon;
    private ArrowUpActor arrowUpActor;

    private EnemyKnight enemyKnight;
    private boolean enemyTurn;// true => enemy makes its turn

    public ScreenGamePlay(CradleGame cradleGame,IPlayServices ply) {
        super(cradleGame,ply);
    }

    public void initialize()
    {
        kingdomRes = new KingdomRes();
        resultAttack = new KingdomRes();
        attackType = AttackType.AttackKingdom;
        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setFontScale(4);
        messageLabel.setVisible(false);
        uiTable.add(messageLabel).expandY();
        score_during_attack=0;
        enemyTurn=false;
        isEndLevelDialogActive=false;
        //explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/glass_windows_breaking.mp3"));
        explosionSound = cradleGame.getCradleAssetManager().manager.get(Assets.SOUND_GLASS_WINDOW_BREAKING);
        timeLastSelectionEnded= TimeUtils.millis();;
        screenGamePlay = this;

        // Get screen size
        int w = cradleGame.getW();
        int h = cradleGame.getH();
        int resHeight = cradleGame.getResHeight();
        CellCount = cradleGame.getCellCount();


        float bombs_frame_sizeX= cradleGame.getBombs_frame_sizeX();
        float bombs_frame_sizeY= cradleGame.getBombs_frame_sizeY();
        timeBombQttyLabel = new Label(" ", BaseGame.labelStyle_Middle);
        timeBombQttyLabel.setColor( Color.GOLDENROD );
        timeBombQttyLabel.setPosition( bombs_frame_sizeX*0.7f, bombs_frame_sizeY * 0.67f);
        uiStage.addActor(timeBombQttyLabel);

        squareBomb1QttyLabel = new Label(" ", BaseGame.labelStyle_Middle);
        squareBomb1QttyLabel.setColor( Color.GOLDENROD );
        squareBomb1QttyLabel.setPosition( bombs_frame_sizeX*0.7f, bombs_frame_sizeY * 0.35f);
        uiStage.addActor(squareBomb1QttyLabel);

        squareBomb2QttyLabel = new Label(" ", BaseGame.labelStyle_Middle);
        squareBomb2QttyLabel.setColor( Color.GOLDENROD );
        squareBomb2QttyLabel.setPosition( bombs_frame_sizeX*0.7f, bombs_frame_sizeY* 0.03f);
        uiStage.addActor(squareBomb2QttyLabel);

        hall = new BaseActor(0,0, mainStage, Touchable.disabled,cradleGame);
        //hall.loadTexture( "hall01.png",w,h );
        if(cradleGame.getGameMapLevel()==1){
        hall.loadTexture( "game_of_thrones_locations4.jpg",w,h );}
        else{
            if(cradleGame.getGameMapLevel()==2) {
                hall.loadTexture("castle/castlelevel02.png", w, h);
            }else {
                if(cradleGame.getGameMapLevel()==4) {
                    hall.loadTexture("castle/castlelevel04.png", w, h);
                }else {
                    hall.loadTexture("castle/castlelevel03.png", w, h);
                }
            }
        }

        int dialogSize = Math.round(h*0.95f);
        dialogBox_endLevel = new DialogBox_EndLevel(w/2-dialogSize/2,h/2-dialogSize/2,uiStage,dialogSize,dialogSize,cradleGame);
        dialogBox_endLevel.setVisible(false);

        dialogBox_notEnoughSteps = new DialogBox_NotEnoughSteps(w/2-dialogSize/2,h/2-dialogSize/2,uiStage,dialogSize,dialogSize,cradleGame);
        dialogBox_notEnoughSteps.setVisible(false);
        QttyRewardedVideoWatched = 0;


        cellSize = cradleGame.getCellSize();
        h=h-70; //place for top menu items
        if (w<h) {
            h=w;
        } else w=h;


        BaseActor.setWorldBounds(cellSize*CellCount,cellSize*CellCount);
        int gameFieldWidth = cellSize*CellCount;
        gameFieldX=(Gdx.graphics.getWidth()-gameFieldWidth)/2;
        gameFieldY=0;

        gameField = new GameField(gameFieldX,gameFieldY,mainStage,gameFieldWidth,gameFieldWidth,CellCount,1,cradleGame);


        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        BaseActor baseResultsActor = new BaseActor(gameFieldX,h-resHeight,mainStage,Touchable.disabled,cradleGame);
        baseResultsActor.setWidth( w-gameFieldX);
        baseResultsActor.setHeight(resHeight);
        resultsActor = new ResultsActor(0,0,(int) (w-gameFieldX),resHeight,mainStage,Touchable.disabled,baseResultsActor,cradleGame);

        /*
        BaseActor baseResultsActor = new BaseActor(gameFieldX*0.9f,h,uiStage,Touchable.disabled);
        baseResultsActor.setWidth(cellSize*CellCount*1.2f); // don't work
        baseResultsActor.setHeight(70);
        resultsActor = new ResultsActor(0,0,cellSize*CellCount,70,uiStage,Touchable.disabled,baseResultsActor);
   */

        //DrawResults(h, gameFieldWidth);
        UpdateRes();
        //SandGlass placement
        float x = gameFieldX+cellSize*CellCount+10;
        float y = gameFieldY+gameFieldWidth*0.05f;
        int sw = (int)(Gdx.graphics.getWidth()-x);
        if (sw<0){
            sw=20; //временно!
        }
        int sandglassduration = getLevelDuration();
        if (gameLevel>20) {
            sandglassduration = getLevelDuration() + gameLevel*2 - 20;
            if (sandglassduration > 400) sandglassduration = 400;
        }

        sandGlass = new SandGlass(x,0,uiStage,sw, Math.round( gameFieldWidth*0.8f), sandglassduration,cradleGame);
        //Game level
        //gameLevel = 1 ;
        gameLevelLabel = new Label("  "+0, BaseGame.labelStyle_SuperSmall);
        gameLevelLabel.setText(""+gameLevel);
        gameLevelLabel.setColor( Color.GOLDENROD );
        gameLevelLabel.setPosition( sandGlass.getX()+sandGlass.getWidth()*0.4f,10 );
        //gameLevelLabel.setFontScale(2.0f);
        //uiStage.addActor(gameLevelLabel);







        //Attack target info
        float targetSizeX = (cradleGame.getW()-(gameField.getX()+gameField.getWidth()))*0.95f;
        float targetSizeY = targetSizeX*0.5f;
        float targetPosX = cradleGame.getW()- (cradleGame.getW()-(gameField.getX()+gameField.getWidth()))*0.5f-targetSizeX*0.5f;
        float targetPosY = cradleGame.getH()*0.875f-targetSizeY;
        attackTarget = new AttackTarget(targetPosX,targetPosY,uiStage,Math.round(targetSizeX),Math.round(targetSizeY),cradleGame);
        attackTargetSteps = new AttackTargetSteps(targetPosX,targetPosY-targetSizeY,uiStage,Math.round(targetSizeX),Math.round(targetSizeY),cradleGame);

        // Attack target is shown before attack
        int AttackTargetDialogSizeX = Math.round(w*0.4f);
        int AttackTargetDialogSizeY = Math.round(h*0.4f);
        attackTargetDialog = new AttackTargetDialog(w/2-AttackTargetDialogSizeX/2,h/2-AttackTargetDialogSizeY/2,uiStage,AttackTargetDialogSizeX,AttackTargetDialogSizeY,cradleGame);

        //EnemyKnight
        float enemySizeX = (cradleGame.getW()-(gameField.getX()+gameField.getWidth()))*0.95f;
        float enemySizeY = enemySizeX*0.94f;
        float enemyPosX = cradleGame.getW()- (cradleGame.getW()-(gameField.getX()+gameField.getWidth()))*0.5f-targetSizeX*0.5f;
        float enemyPosY = 0;//cradleGame.getH()*0.875f-targetSizeY;
        enemyKnight = new EnemyKnight(targetPosX,enemyPosY,mainStage,Math.round(enemySizeX), Math.round(enemySizeY),cradleGame,cradleGame.getKnightParams());


        /*
        //Test video ad button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("undo.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button testVideoAdButton = new Button( buttonStyle );
        testVideoAdButton.setColor( Color.CYAN );
        testVideoAdButton.setPosition(250,250);
        uiStage.addActor(testVideoAdButton);

        testVideoAdButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {

                    if(cradleGame.getiGoogleServices().hasVideoLoaded()) {
                        cradleGame.getiGoogleServices().showRewardedVideoAd();
                    }
                }
                return false;
            }
        });

*/

/*

        //Restart button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("undo.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button restartButton = new Button( buttonStyle );
        restartButton.setColor( Color.CYAN );
        restartButton.setPosition(50,350);
        uiStage.addActor(restartButton);

        restartButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    RestartLevel();
                }
                return false;
            }
        });
*/
 /*       //FindSolution Button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTex = new Texture( Gdx.files.internal("undo.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );
        Button testButton = new Button( buttonStyle );
        testButton.setPosition(50,100);
        uiStage.addActor(testButton);

        testButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    clearAllSelections();
                    SelectSolution(gameField.FindSolutions(CellCount,mainStage));
                }
                return false;
            }
        });

*/

        //gameField.boundToWorld();
        gameField.setTouchable(Touchable.enabled);

        gameField.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isPaused) return true;
                if (enemyTurn) return true;

                flag = true;

                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ){

                    clearAllSelections();
                    timeLastSelectionEnded = TimeUtils.millis();
                        //Coin
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Coin")){
                        if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldY)){
                            if(((Coin)CoinActor).isLocked()){
                                return true;
                            }
                            if (lastSelectedItem==null){
                                lastSelectedItem =(Coin) CoinActor;
                                firstSelectedItem= lastSelectedItem;
                            }

                            ((Coin)CoinActor).setSelected(true, lastSelectedItem);
                            lastSelectedItem =(Coin) CoinActor;
                        }
                    }
                    //Coin2
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Coin2")){
                        if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldY)){
                            if(((Coin2)CoinActor).isLocked()){
                                return true;
                            }
                            if (lastSelectedItem==null){
                                lastSelectedItem =(Coin2) CoinActor;
                                firstSelectedItem= lastSelectedItem;
                            }
                            ((Coin2)CoinActor).setSelected(true, lastSelectedItem);
                            lastSelectedItem =(Coin2) CoinActor;
                        }
                    }

                    //Wood
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Wood")){
                        if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldY)){
                            if(((Wood)CoinActor).isLocked()){
                                return true;
                            }
                            if (lastSelectedItem==null){
                                lastSelectedItem =(Wood) CoinActor;
                                firstSelectedItem= lastSelectedItem;
                            }
                            ((Wood)CoinActor).setSelected(true, lastSelectedItem);
                            lastSelectedItem =(Wood) CoinActor;
                        }
                    }

                    //Bread
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Bread")){
                        if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldY)){
                            if(((Bread)CoinActor).isLocked()){
                                return true;
                            }
                            if (lastSelectedItem==null){
                                lastSelectedItem =(Bread) CoinActor;
                                firstSelectedItem= lastSelectedItem;
                            }
                            ((Bread)CoinActor).setSelected(true, lastSelectedItem);
                            lastSelectedItem =(Bread) CoinActor;
                        }
                    }
                    //Jem01
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Jem01")){
                        if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldY)){
                            if(((Jem01)CoinActor).isLocked()){
                                return true;
                            }
                            if (lastSelectedItem==null){
                                lastSelectedItem =(Jem01) CoinActor;
                                firstSelectedItem= lastSelectedItem;
                            }
                            ((Jem01)CoinActor).setSelected(true, lastSelectedItem);
                            lastSelectedItem =(Jem01) CoinActor;
                        }
                    }
                }

                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    flag = false;
                    InputEvent ie = (InputEvent)event;
                timeLastSelectionEnded = TimeUtils.millis();
                if (lastSelectedItem==null) return;
                    String className = lastSelectedItem.getClass().getName();
                    if ( ie.getType().equals(InputEvent.Type.touchUp) ){

                        if (firstSelectedItem.getCountOfSelectedItems()>=3) {
                            FillRemovedCells(removeSelectedItems());
                        }else{
                            clearAllSelections();
                        }
                        lastSelectedItem = null;
                        firstSelectedItem = null;
                    }
                    if( isWin()){
                        //isEndOfLevelAnimation=true;
                        WinMessageAndNewLevelCreate();

                    }

            }
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y){

               return false;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer){
                InputEvent ie = (InputEvent)event;
                if (lastSelectedItem==null) return;
                if(flag){
                    String className = firstSelectedItem.getClass().getName();
                    if ( ie.getType().equals(InputEvent.Type.touchDragged)){

                        for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, className)) {
                            if (CoinActor.getBoundaryPolygon().contains(x + gameFieldX, y + gameFieldY)) {
                                if (lastSelectedItem.isNear(((Item) CoinActor).getRow(), ((Item) CoinActor).getCol())) {
                                    if ((!((Item) CoinActor).isSelected())&&(!((Item) CoinActor).isLocked())) {
                                        if (lastSelectedItem == null) {
                                            lastSelectedItem = (Item) CoinActor;
                                        }
                                        ((Item) CoinActor).setSelected(true, lastSelectedItem);
                                        if (lastSelectedItem.getSelectedDirection() == SelDirection.None) {
                                            lastSelectedItem.setSelected(true, lastSelectedItem);
                                        }
                                        lastSelectedItem.setSelectedNext((Item) CoinActor);// change image after next selected
                                        lastSelectedItem = ((Item) CoinActor);
                                    } else{
                                        //if user selects back
                                        if (!((Item) CoinActor).isLocked()){
                                           if( ((Item) CoinActor).getNext()!=null){
                                               ((Item) CoinActor).getNext().setSelected(false,null);
                                               ((Item) CoinActor).setSelectedNext(null);
                                               lastSelectedItem = ((Item) CoinActor);
                                           }
                                        }
                                    }

                                }
                            }
                        }
                        // Do the same for Coin
                        for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Coin")) {
                            if (CoinActor.getBoundaryPolygon().contains(x + gameFieldX, y + gameFieldY)) {
                                if (lastSelectedItem.isNear(((Item) CoinActor).getRow(), ((Item) CoinActor).getCol())) {
                                    if ((!((Item) CoinActor).isSelected())&&(!((Item) CoinActor).isLocked())) {
                                                if (lastSelectedItem == null) {
                                                    lastSelectedItem = (Item) CoinActor;
                                                }
                                                ((Item) CoinActor).setSelected(true, lastSelectedItem);
                                                if (lastSelectedItem.getSelectedDirection() == SelDirection.None) {
                                                    lastSelectedItem.setSelected(true, lastSelectedItem);
                                                }
                                                lastSelectedItem.setSelectedNext((Item) CoinActor);// change image after next selected
                                                lastSelectedItem = ((Item) CoinActor);
                                    }else{
                                        //if user selects back
                                        if (!((Item) CoinActor).isLocked()){
                                            if( ((Item) CoinActor).getNext()!=null){
                                                ((Item) CoinActor).getNext().setSelected(false,null);
                                                ((Item) CoinActor).setSelectedNext(null);
                                                lastSelectedItem = ((Item) CoinActor);
                                            }
                                        }
                                    }

                                }

                            }
                        }


                    }
                }

            }
        });


     GenerateLevel(1);


    }

    //Called when new attack started
    public void StartNewLevel(){

        ply.logLevelStartEvent(gameLevel);
        QttyRewardedVideoWatched = 0;
        enemyTurn=false;
        isEndLevelDialogActive=false;
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        if (attackedKingdom!=null) {
            gameField.GenerateLevel(gameLevel, CellCount, attackedKingdom.getAttackTargetInfo().attackTypeInfo);
        } else { //Arena
            gameField.GenerateLevel(gameLevel, CellCount, SingleTimeClearUp);
        }
        GenerateLevel(gameLevel);
        sandGlass.remove();
        //SandGlass recreation for restarting of animation
        float x = gameFieldX+cellSize*CellCount+10;
        float y = gameFieldY+cellSize*CellCount*0.05f;;
        int sw = (int)(Gdx.graphics.getWidth()-x);
        int sandglassduration = getLevelDuration();
        if (gameLevel>20) {
            sandglassduration = getLevelDuration() + gameLevel*2 - 20;
            if (sandglassduration > 400) sandglassduration = 400;
        }
        sandGlass = new SandGlass(x,0,uiStage,sw,Math.round( cellSize*CellCount*0.8f), sandglassduration,cradleGame);

        //Attack target info
        float targetSizeX = (cradleGame.getW()-(gameField.getX()+gameField.getWidth()))*0.95f;
        float targetSizeY = targetSizeX*0.5f;
        float targetPosX = cradleGame.getW()- (cradleGame.getW()-(gameField.getX()+gameField.getWidth()))*0.5f-targetSizeX*0.5f;
        float targetPosY = cradleGame.getH()*0.875f-targetSizeY;
        attackTarget.remove();
        attackTarget = new AttackTarget(targetPosX,targetPosY,uiStage,Math.round(targetSizeX),Math.round(targetSizeY),cradleGame);
        //attackTarget.setAttackQtty(cradleGame.getAttackQtty());
        if (attackedKingdom!=null) {
            attackTarget.setAttackTypeAndQtty(attackedKingdom.getAttackTargetInfo(), cradleGame.getAttackQtty());
            attackTargetDialog.setAttackTypeAndQtty(attackedKingdom.getAttackTargetInfo(), cradleGame.getAttackQtty());
        } else{
            AttackTargetInfo atf = new AttackTargetInfo(0);
            attackTarget.setAttackTypeAndQtty(atf, cradleGame.getAttackQtty());
            attackTargetDialog.setAttackTypeAndQtty(atf, cradleGame.getAttackQtty());
        }
        isPaused=false;

        attackTargetDialog.setVisible(false);


        //Attack target Moves left
        attackTargetSteps.remove();
        attackTargetSteps = new AttackTargetSteps(targetPosX,targetPosY-targetSizeY,uiStage,Math.round(targetSizeX),Math.round(targetSizeY),cradleGame);
        if (attackedKingdom!=null) { //null if Arena attacked
            attackTargetSteps.setAttackStepsQtty(attackedKingdom.getAttackTargetInfo().stepsQtty);


            switch (attackedKingdom.getAttackTargetInfo().attackTypeInfo) {
                case SingleTimeClearUp:
                    sandGlass.setVisible(true);
                    attackTargetSteps.setVisible(false);
                    enemyKnight.setVisible(false);
                    break;
                case SingleTimeResources:
                    sandGlass.setVisible(true);
                    attackTargetSteps.setVisible(false);
                    enemyKnight.setVisible(false);
                    break;
                case SingleClearUp:
                    sandGlass.setVisible(false);
                    attackTargetSteps.setVisible(true);
                    enemyKnight.setVisible(false);
                    break;
                case SingleResources:
                    sandGlass.setVisible(false);
                    attackTargetSteps.setVisible(true);
                    enemyKnight.setVisible(false);
                    break;
                case DoubleClearUp:
                    sandGlass.setVisible(false);
                    attackTargetSteps.setVisible(true);
                    enemyKnight.setVisible(false);
                    break;
                case DoubleResources:
                    sandGlass.setVisible(false);
                    attackTargetSteps.setVisible(false);
                    enemyKnight.setVisible(false);
                    break;
                case DoubleFight:
                    sandGlass.setVisible(false);
                    attackTargetSteps.setVisible(false);
                    enemyKnight.setVisible(true);
                    enemyKnight.setKnightParamsForAttack(attackedKingdom.getAttackTargetInfo().knightParamsForAttack.HealthPoints);
                    break;
                default:
                    sandGlass.setVisible(true);
                    attackTargetSteps.setVisible(false);
                    enemyKnight.setVisible(false);
            }

        } else { // Arena
            sandGlass.setVisible(true);
            attackTargetSteps.setVisible(false);
            enemyKnight.setVisible(false);
        }

        int knSize = Math.round(h*0.4f);
        int wpSize = Math.round(h*0.1f);
        if (knight!=null){knight.remove();}
        if(weapon!=null){weapon.remove();}
        if(cradleGame.isAdMobViewCreated()) {
            knight = new Knight(-knSize * 0.1f, h - knSize * 0.57f, knSize, knSize, mainStage, cradleGame.getKnightParams(), cradleGame);
            weapon = new Weapon(knSize * 0.585f, h - knSize * 0.07f, wpSize, wpSize, mainStage, cradleGame, knight);
        }else{
            knight = new Knight(-knSize * 0.1f, h - knSize + knSize * 0.15f, knSize, knSize, mainStage, cradleGame.getKnightParams(), cradleGame);
            weapon = new Weapon(knSize*0.585f,h-knSize*0.34f,wpSize,wpSize,mainStage,cradleGame,knight);
        }

        if (!cradleGame.isWeaponUsed()){
            //Message size and pos
            arrowUpActor = new ArrowUpActor(knSize*0.3f, h-knSize*2.0f,knSize*0.7f, knSize*1.6f,uiStage, cradleGame);
            //arrow pos
            arrowUpActor.startAnimation(knSize*0.6f,h-knSize*0.5f,knSize*0.6f,h-knSize*0.4f);
        }


        //BombsFrame
        if (bombs_frame!=null){
            bombs_frame.remove();
        }
        bombs_frame = new Bombs_Frame(0,0,cradleGame.getBombs_frame_sizeX(),cradleGame.getBombs_frame_sizeY(),mainStage,cradleGame);

        float bombPos = cradleGame.getBombs_frame_sizeX()*0.5f-cradleGame.getBombSize()*0.5f;
        //Bombs
        for (int i = 0; i < GameRes.TimeBomb; i++) {
            new TimeBomb(bombPos, cradleGame.getBombs_frame_sizeY() * 0.71f, cradleGame.getBombSize(), cradleGame.getBombSize(), mainStage, Touchable.enabled, sandGlass, 60, cradleGame);
        }
        for (int i=0;i<GameRes.SquareBomb1;i++) {
            new SquareBomb(bombPos,cradleGame.getBombs_frame_sizeY() * 0.38f,cradleGame.getBombSize(),cradleGame.getBombSize(),mainStage,Touchable.enabled,1,this,cradleGame);
        }
        for (int i=0;i<GameRes.SquareBomb2;i++) {
            new SquareBomb(bombPos,h*0.04f,cradleGame.getBombSize(),cradleGame.getBombSize(),mainStage,Touchable.enabled,2,this,cradleGame);
        }

        if (GameRes.TimeBomb>0) {
            timeBombQttyLabel.setText("" + GameRes.TimeBomb);
        } else {
            timeBombQttyLabel.setText("");
        }

        if (GameRes.SquareBomb1>0) {
            squareBomb1QttyLabel.setText("" + GameRes.SquareBomb1);
        } else {
            squareBomb1QttyLabel.setText("");
        }

        if (GameRes.SquareBomb2>0) {
            squareBomb2QttyLabel.setText("" + GameRes.SquareBomb2);
        } else {
            squareBomb2QttyLabel.setText("");
        }

        timeLastSelectionEnded = TimeUtils.millis();

        final Action  completeAction = new Action(){
            public boolean act( float delta ) {
                // Do your stuff
                return true;
            }
        };

        attackTargetDialog.showForTime(3,completeAction);

    }

    public void LoseLevel(){
        //System.out.println("LoseLevel()");
        //GdxLog.print("LoseLevel():","Called");
        System.out.println("LoseLevel called");
        isPaused=true; // block timer in update
        score_during_attack=0;
        Action actions;
        //Fon
        /*
        BaseActor fon = new BaseActor(0,0,uiStage,Touchable.disabled);
        fon.loadTexture("fon_orange.png",cellSize*CellCount,cellSize*CellCount);
        fon.setX(gameFieldX);
        fon.setY(gameFieldY);
        fon.centerAtPosition(gameFieldX+cellSize*CellCount/2,gameFieldY+cellSize*CellCount/2);
        fon.setOpacity(80);
        actions = sequence(fadeIn(0.5f), Actions.delay(3) ,fadeOut(1f));
        fon.addAction(actions);
        */

        Action completeAction = new Action(){
            public boolean act( float delta ) {

                //Show dialog
                dialogBox_endLevel.setResults(0,resultAttack.Gold,resultAttack.Wood,resultAttack.Bread);

                InputListener inputListener3 =new InputListener() {
                    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                        if (!(e instanceof InputEvent))
                            return false;

                        if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                            return false;
                        knight.doDamage();
                        collectAllKnightItems();
                        ply.logLevelEndEvent(gameLevel,"attack is lost");
                        cradleGame.setActiveGameMapScreen(false,0);
                        setEndLevelDialogActive(false);
                        return true;
                    }
                };

                InputListener inputListenerWatchVideo = new InputListener() {
                    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                        if (!(e instanceof InputEvent))
                            return false;

                        if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                            return false;


                        if(cradleGame.getiGoogleServices().hasVideoLoaded()) {
                            cradleGame.getiGoogleServices().showRewardedVideoAd();
                        }

                        //isPaused=false;
                        return true;
                    }
                };
                QttyRewardedVideoWatched=0;
                dialogBox_endLevel.showWithOkButton(inputListener3, inputListenerWatchVideo);
                return true;
            }
        };

        actions = sequence(fadeIn(0.5f), Actions.delay(3) ,fadeOut(1f), completeAction);
        String s = cradleGame.getLanguageStrings().get("you_lose");
        messageLabel.setText(s);
        messageLabel.setColor(Color.RED);
        messageLabel.setVisible(true);
        messageLabel.addAction(actions);

    }

    public void update(float dt)
    {

        if(isEndLevelDialogActive) {
            return;
        }


        InputListener inputListenerCancel =new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                dialogBox_notEnoughSteps.setVisible(false);
                LoseLevel();
                return true;
            }
        };
        InputListener inputListenerWatchVideo = new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                dialogBox_notEnoughSteps.setVisible(false);

                if(cradleGame.getiGoogleServices().hasVideoLoaded()) {
                    cradleGame.getiGoogleServices().showRewardedVideoAd();
                }

                //isPaused=false;
                return true;
            }
        };

        //Check if game has time limit
        if (sandGlass.isVisible()&& sandGlass.isAnimationFinished()&&!isPaused) {
            if ((attackedKingdom==null)||(QttyRewardedVideoWatched>0)) {LoseLevel(); return;}

            isPaused=true; // block timer in update
            dialogBox_notEnoughSteps.showWithOkButton(inputListenerWatchVideo,inputListenerCancel,attackedKingdom.getAttackTargetInfo().attackTypeInfo);
        }

        //Check if game has steps limit
        if (!sandGlass.isVisible()&&!isPaused&&(attackTargetSteps.getAttackStepsQtty()<=0)&&(attackedKingdom.getAttackTargetInfo().attackTypeInfo!=AttackTypeInfo.DoubleFight)){
            if ((attackedKingdom==null)||(QttyRewardedVideoWatched>0)) {LoseLevel(); return;}
            isPaused=true; // block timer in update
            System.out.println("attackTargetSteps.getAttackStepsQtty()="+attackTargetSteps.getAttackStepsQtty());
            dialogBox_notEnoughSteps.showWithOkButton(inputListenerWatchVideo,inputListenerCancel,attackedKingdom.getAttackTargetInfo().attackTypeInfo);
        }

        //Check if game has doublefight type
        if (!sandGlass.isVisible()&&!isPaused&&(attackedKingdom.getAttackTargetInfo().attackTypeInfo==AttackTypeInfo.DoubleFight)) {
            if (enemyKnight.getCurKnightParams().HealthPoints <= 0) {
                LoseLevel();
                return;
            }

        }

            if(!isPaused) {

            long diffInMillis = TimeUtils.timeSinceMillis(timeLastSelectionEnded);
            if ((diffInMillis > TIME_BEFORE_SHOW_SOLUTION) && (!flag)&& (!enemyTurn)) {
                    clearAllSelections();
                    SelectSolution(gameField.FindSolutions(CellCount, mainStage));
                    timeLastSelectionEnded = TimeUtils.millis();
            }

                // Make the enemy step
                if ((diffInMillis > TIME_BEFORE_SHOW_ENEMY_STEP) && (!flag) && (enemyTurn)) {
                    clearAllSelections();
                    SelectSolution(gameField.FindSolutions(CellCount, mainStage));
                    timeLastSelectionEnded = TimeUtils.millis();
                    // make enemy's turn and switch turn if it's figth type of game
                    if (attackedKingdom!=null){
                        if((attackedKingdom.getAttackTargetInfo().attackTypeInfo==AttackTypeInfo.DoubleFight)&&enemyTurn){
                            if (firstSelectedItem!=null) {
                                if (firstSelectedItem.getCountOfSelectedItems() >= 2) {
                                    FillRemovedCells(removeSelectedItems());
                                    enemyTurn = false;
                                    return;
                                }
                            }
                        }
                    }
                }

        }


    }

    // used for weapon
    public void RemoveAndFillCells(int centreRow, int centreCol, int cellQttyToRemove){
        Cell startCell = new Cell(centreRow,centreCol);
        Item startItem = gameField.GetItemAtCell(startCell, mainStage);
        firstSelectedItem = startItem;
        Item prevItem = null;
        Item curItem = null;
        int cellToRemoveRemainded = cellQttyToRemove;
        Cell cell = new Cell(0,0);


        //Select all possible items -------------
        while (true) {
            //Select starting item
            curItem = startItem;
            curItem.setSelected(true, prevItem);
            cellToRemoveRemainded--;
            if (cellToRemoveRemainded == 0) {
                break;
            }

            //check cell right
            prevItem = curItem;
            if (startItem.getCol()<(CellCount-1)) {
                cell.setRow(startItem.getRow());
                cell.setCol(startItem.getCol()+1);
                curItem = gameField.GetItemAtCell(cell, mainStage);
                curItem.setSelected(true,prevItem);
                prevItem.setSelectedNext(curItem);
            }
            cellToRemoveRemainded--; // at any case count even if cell unreachable
            if (cellToRemoveRemainded == 0) {
                break;
            }

            //check cell 2xright
            prevItem = curItem;
            if (startItem.getCol()<(CellCount-2)) {
                cell.setRow(startItem.getRow());
                cell.setCol(startItem.getCol()+2);
                curItem = gameField.GetItemAtCell(cell, mainStage);
                curItem.setSelected(true,prevItem);
                prevItem.setSelectedNext(curItem);
            }
            cellToRemoveRemainded--; // at any case count even if cell unreachable
            if (cellToRemoveRemainded == 0) {
                break;
            }

            //check cell up
            prevItem = curItem;
            if (startItem.getRow()<(CellCount-1)) {
                cell.setRow(startItem.getRow()+1);
                cell.setCol(startItem.getCol());
                curItem = gameField.GetItemAtCell(cell, mainStage);
                curItem.setSelected(true,prevItem);
                prevItem.setSelectedNext(curItem);
            }
            cellToRemoveRemainded--; // at any case count even if cell unreachable
            if (cellToRemoveRemainded == 0) {
                break;
            }
            //check cell upright
            prevItem = curItem;
            if ((startItem.getRow()<(CellCount-1)) && (startItem.getCol()<(CellCount-1))) {
                cell.setRow(startItem.getRow()+1);
                cell.setCol(startItem.getCol()+1);
                curItem = gameField.GetItemAtCell(cell, mainStage);
                curItem.setSelected(true,prevItem);
                prevItem.setSelectedNext(curItem);
            }
            cellToRemoveRemainded--; // at any case count even if cell unreachable
            if (cellToRemoveRemainded == 0) {
                break;
            }
            //check cell up 2xright
            prevItem = curItem;
            if ((startItem.getRow()<(CellCount-1)) && (startItem.getCol()<(CellCount-2))) {
                cell.setRow(startItem.getRow()+1);
                cell.setCol(startItem.getCol()+2);
                curItem = gameField.GetItemAtCell(cell, mainStage);
                curItem.setSelected(true,prevItem);
                prevItem.setSelectedNext(curItem);
            }
            cellToRemoveRemainded--; // at any case count even if cell unreachable
            if (cellToRemoveRemainded == 0) {
                break;
            }

            //check cell 2xUp
            prevItem = curItem;
            if (startItem.getRow()<(CellCount-2)) {
                cell.setRow(startItem.getRow()+2);
                cell.setCol(startItem.getCol());
                curItem = gameField.GetItemAtCell(cell, mainStage);
                curItem.setSelected(true,prevItem);
                prevItem.setSelectedNext(curItem);
            }
            cellToRemoveRemainded--; // at any case count even if cell unreachable
            if (cellToRemoveRemainded == 0) {
                break;
            }

            //check cell 2xup right
            prevItem = curItem;
            if ((startItem.getRow()<(CellCount-2)) && (startItem.getCol()<(CellCount-1))) {
                cell.setRow(startItem.getRow()+2);
                cell.setCol(startItem.getCol()+1);
                curItem = gameField.GetItemAtCell(cell, mainStage);
                curItem.setSelected(true,prevItem);
                prevItem.setSelectedNext(curItem);
            }
            cellToRemoveRemainded--; // at any case count even if cell unreachable
            if (cellToRemoveRemainded == 0) {
                break;
            }

            //check cell 2xup 2xright
            prevItem = curItem;
            if ((startItem.getRow()<(CellCount-2)) && (startItem.getCol()<(CellCount-2))) {
                cell.setRow(startItem.getRow()+2);
                cell.setCol(startItem.getCol()+2);
                curItem = gameField.GetItemAtCell(cell, mainStage);
                curItem.setSelected(true,prevItem);
                prevItem.setSelectedNext(curItem);
            }
            cellToRemoveRemainded--; // at any case count even if cell unreachable
            if (cellToRemoveRemainded == 0) {
                break;
            }

        } // end of switch which selected all items to remove

        FillRemovedCells(removeSelectedItems());
        lastSelectedItem = null;
        firstSelectedItem = null;



        if( isWin()){
            //isEndOfLevelAnimation=true;
            WinMessageAndNewLevelCreate();

        }


    }

    //used for square bomb
    public void RemoveAndFillSquare(int centreRow, int centreCol, int squareSize){

    //Find leftdown cell coordinates and actual size of square
    Cell leftDownCell = new Cell(0,0);
    int sqVerticalSize;
    int sqHorizontalSize;
    if ((centreRow-squareSize)>=0) {leftDownCell.setRow(centreRow-squareSize);} else {leftDownCell.setRow(0);}
    if ((centreCol-squareSize)>=0) {leftDownCell.setCol(centreCol-squareSize);} else {leftDownCell.setCol(0);}
    if((centreRow+squareSize)<CellCount){sqVerticalSize=centreRow+squareSize+1-leftDownCell.getRow();}else{sqVerticalSize=CellCount-leftDownCell.getRow();}
    if((centreCol+squareSize)<CellCount){sqHorizontalSize=centreCol+squareSize+1-leftDownCell.getCol();}else{sqHorizontalSize=CellCount-leftDownCell.getCol();}

    // Select items inside of square
    Cell cell = new Cell(0,0);
    Item prevItem = null;
    Item curItem = null;
        firstSelectedItem = gameField.GetItemAtCell(leftDownCell, mainStage);
        prevItem = firstSelectedItem;
        for (int i = 0; i < sqHorizontalSize; i++) {
            for (int j = 0; j < sqVerticalSize; j++) {
                cell.setRow(leftDownCell.getRow() + j);
                cell.setCol(leftDownCell.getCol() + i);
                curItem = gameField.GetItemAtCell(cell, mainStage);
                curItem.setSelected(true, prevItem);
                prevItem.setSelectedNext(curItem);
                prevItem = curItem;

            }
        }

    FillRemovedCells(removeSelectedItems());
        timeLastSelectionEnded = TimeUtils.millis();

           // ((Item)CoinActor).setSelected(false,null);

    lastSelectedItem = null;
    firstSelectedItem = null;

    if( isWin()){
        //isEndOfLevelAnimation=true;
        WinMessageAndNewLevelCreate();

    }

}

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public BaseActor getHall() {
        return hall;
    }

    // select randomly or with hardeness level for enemy turn
    public void SelectSolution(ArrayList<SortableArrayListOfItems> arrayLists){
        if (flag){return;}

        if(arrayLists.size()<1){
        // We need to restart level because no steps remains...
            //RestartLevel(); // Don't work right moves elements outside and hides the Knight
            return;

        }

        int sol=0;
        ArrayList<Item> arrayList;
        if(!enemyTurn) {
            //Choose solution randomly
            sol = (int) Math.round(Math.random() * (arrayLists.size() - 1));
            if (sol > (arrayLists.size() - 1)) {
                sol = arrayLists.size() - 1;
            }

            arrayList = arrayLists.get(sol);
        }else{
            //Choose solution according to difficulty level
            Collections.sort(arrayLists);
            //choose solution depending on difficultyLevel
            //hard - top solution
            //middle - 0.75 solution
            //easy - 0.5 solution
            switch(cradleGame.getDifficultyLevel()){
                case 1:
                    sol = Math.round(0.85f*(arrayLists.size()-1));
                    break;
                case 2:
                    sol = Math.round(0.95f*(arrayLists.size()-1));
                    break;
                default:
                    sol = arrayLists.size() - 1;

            }

            if (sol > (arrayLists.size() - 1)) {
                sol = arrayLists.size() - 1;
            }
            arrayList = (ArrayList<Item>) arrayLists.get(sol);

            //for debug
            /*
            System.out.println("Sol choosed = "+sol);
            for(int i=0; i<arrayLists.size();i++){
                System.out.println("Sol"+i+" size"+arrayLists.get(i).size());
            }
            */

        }


        //System.out.println("cradleGame.getDifficultyLevel() = "+cradleGame.getDifficultyLevel());
        if ((arrayList.size()<3)&&(!enemyTurn)){
            //System.out.println("solution size <3");
            return;}
        if ((arrayList.size()<2)&&(enemyTurn)){
            //System.out.println("solution size <2");
            return;}

        arrayList.get(0).setSelected(true,null);
        firstSelectedItem=arrayList.get(0);
        arrayList.get(0).setSelectedNext(arrayList.get(1));
        for (int i=1;i<arrayList.size()-1;i++){
            arrayList.get(i).setSelected(true,arrayList.get(i-1));
            arrayList.get(i).setSelectedNext(arrayList.get(i+1));
        }
        arrayList.get(arrayList.size()-1).setSelected(true,arrayList.get(arrayList.size()-2));
    }



    public void clearAllSelections(){
        ArrayList<Item> arrayList = gameField.GetAllItems();

        for(Item item: arrayList) {
            if (item.isSelected()) {
                item.setSelected(false, null);
            }
        }

    }

    public ArrayList<Cell> removeSelectedItems(){
        ArrayList<Cell> arrayList = new ArrayList<>();
        Item item1;
        Item item2;
        String className;
        int resCollected=0;

        if (firstSelectedItem==null)return arrayList;
        if (firstSelectedItem.getNext()!=null) {
            directionToFill = firstSelectedItem.getNext().findItemPos(firstSelectedItem);
        }else{
            directionToFill = ItemPos.Down;
        }
            item1 = firstSelectedItem;
            item2 = null;

            //unlock near items for just one level
            ArrayList<Item> itemArrayList = new ArrayList<>();
            ArrayList<Item> itemArrayList2;
            // fill array with all items around all item without duplicates
            while (item1.getNext() != null) {
                itemArrayList2 = GetNearItem(item1);
                for (int i = 0; i < itemArrayList2.size(); i++) {
                    if (!itemArrayList.contains(itemArrayList2.get(i))) {
                        // System.out.println("NearItems: row="+itemArrayList2.get(i).getRow() +" col="+itemArrayList2.get(i).getCol());
                        itemArrayList.add(itemArrayList2.get(i));
                    }
                }
                item1 = item1.getNext();
            }

            // add near items of last cell without duplicates
            itemArrayList2 = GetNearItem(item1);
            for (int i = 0; i < itemArrayList2.size(); i++) {
                if (!itemArrayList.contains(itemArrayList2.get(i))) {
                    // System.out.println("NearItems: row="+itemArrayList2.get(i).getRow() +" col="+itemArrayList2.get(i).getCol());
                    itemArrayList.add(itemArrayList2.get(i));
                }
            }

            // unlock items on for one level
            for (int i = 0; i < itemArrayList.size(); i++) {
                if (itemArrayList.get(i).getLockLevel()>0) {
                    if (itemArrayList.get(i).UnlockForOneLevel() == 0) {
                    }
                }
            }


        //remove items
        item1= firstSelectedItem;
        item2=null;
        int countofitemremoved=0;
        while (item1.getNext()!=null){
            item2 = item1.getNext();
            arrayList.add(item1.getCell());
            className = item1.getClass().getName();
            resCollected += IncreaseRes(className);
            //Explosion
            ExplosionEffect boom = new ExplosionEffect();
            boom.centerAtActor( item1 );
            boom.start();
            //Explosion sound
            if(cradleGame.isSoundOn()){
            explosionSound.play(SoundEffectsVolume);
            }

            mainStage.addActor(boom);

            // Lowered cradleGame.setAttackQtty -1 !!!
            gameField.changeGameCell(item1.getCell());


            item1.remove();
            item1=item2;
            countofitemremoved++;
        }

        arrayList.add(item1.getCell());
        className = item1.getClass().getName();
        resCollected += IncreaseRes(className);
        countofitemremoved++;
        if (!enemyTurn) {
            ShowCollectedResCount(item1.getX(), item1.getY(), resCollected);
        }
        else {
            ShowCollectedResCount(item1.getX(), item1.getY(), countofitemremoved);
        }
        //Explosion
        ExplosionEffect boom = new ExplosionEffect();
        boom.centerAtActor( item1 );
        boom.start();
        //Explosion sound
        if(cradleGame.isSoundOn()){
            explosionSound.play(SoundEffectsVolume);
        }
        mainStage.addActor(boom);

        // Lowered cradleGame.setAttackQtty -1 !!!
        gameField.changeGameCell(item1.getCell());

        item1.remove();
        lastSelectedItem=null;
        firstSelectedItem=null;
        //attackTarget.setAttackQtty(cradleGame.getAttackQtty());

        if (attackedKingdom!=null) {
            attackTarget.setAttackTypeAndQtty(getAttackTargetLeftInfo(), cradleGame.getAttackQtty());
            //Do damage to enemy knight if needed
            if (attackedKingdom.getAttackTargetInfo().attackTypeInfo==AttackTypeInfo.DoubleFight){

                if (!enemyTurn) {
                    enemyKnight.doDamage(resCollected);
                }else
                {
                    enemyKnight.doDamageToHero(countofitemremoved);
                }

            }
        } else{
            AttackTargetInfo atf = new AttackTargetInfo(0);
            attackTarget.setAttackTypeAndQtty(atf, cradleGame.getAttackQtty());
        }

        attackTargetSteps.setAttackStepsQtty(attackTargetSteps.getAttackStepsQtty()-1);

        return arrayList;
    }

    public AttackTargetInfo getAttackTargetLeftInfo(){
        AttackTargetInfo atf = new AttackTargetInfo(0);
        if (attackedKingdom!=null) {
            atf = new AttackTargetInfo(attackedKingdom.getAttackTargetInfo());
        }

        atf.kingdomRes.Wood -= resultAttack.Wood;
        if (atf.kingdomRes.Wood<0) {atf.kingdomRes.Wood = 0;}
        atf.kingdomRes.Bread -= resultAttack.Bread;
        if (atf.kingdomRes.Bread<0) {atf.kingdomRes.Bread = 0;}
        atf.kingdomRes.Gold -= resultAttack.Gold;
        if (atf.kingdomRes.Gold<0) {atf.kingdomRes.Gold = 0;}
        atf.stepsQtty = cradleGame.getAttackQtty();
        return atf;
    }

    public boolean isWin(){
        boolean win=true;
        AttackTargetInfo atf = getAttackTargetLeftInfo();
        if (atf.kingdomRes.Wood>0) win = false;
        if (atf.kingdomRes.Gold>0) win = false;
        if (atf.kingdomRes.Bread>0) win = false;
        switch (atf.attackTypeInfo){
            case SingleTimeClearUp:
                win=gameField.CheckWin();
                break;
            case SingleTimeResources:
                break;
            case SingleClearUp:
                win=gameField.CheckWin();
                break;
            case SingleResources:
                break;
            case DoubleClearUp:
                win=gameField.CheckWin();
                break;
            case DoubleResources:
                break;
            case DoubleFight:
                if (enemyKnight.getCurEnemyKnightParamsForAttack().HealthPoints <= 0) {
                    win=true;
                } else {
                    win = false;
                }
                break;
            default:
                win=gameField.CheckWin();
        }
        return win;

    }

    public void FillRemovedCells(ArrayList<Cell> arrayList){
        ArrayList<Item> newItems = new ArrayList<>();
        ArrayList<Cell> newCells;

        Cell cell;
        int x0;
        int y0;
        int x;
        int y;
        cell = arrayList.get(0);
        x0=cell.getCol();
        y0=cell.getRow();


        newCells = FindCellsToMove(cell,directionToFill);

        // Если True то Крюк найден, поэтому просто удалить и заполнить ячейки без движения змейки
        if(checkForDublicates(newCells, arrayList)){

            newCells.clear();
        }
        else {
        newItems = FindItemsToMove(newCells);
        }

        AddNewItems(newCells,newItems,arrayList.size(),cell);


        //Создаем змейку из существующих и новых элементов
        for (int i=0; i<arrayList.size();i++){
            newItems.get(0).setCell(arrayList.get(i));
            newItems.get(0).addAction(Actions.after(Actions.moveTo(gameFieldX +newItems.get(0).getCol() * cellSize, gameFieldY + newItems.get(0).getRow() * cellSize,0.1f)));
            for(int j=1; j<newItems.size();j++){
               newItems.get(j).setCell(newCells.get(j-1));
                newItems.get(j).addAction(Actions.after(Actions.moveTo(gameFieldX +newItems.get(j).getCol() * cellSize, gameFieldY + newItems.get(j).getRow() * cellSize,0.1f)));
            }
           for(int j = 0;j<newCells.size();j++){
               newCells.get(j).setCol(newItems.get(j).getCol());
               newCells.get(j).setRow(newItems.get(j).getRow());
           }
            //Actions.forever( Actions.delay(1));
           // Action spin = Actions.rotateBy(30, 1);
            //item.addAction( Actions.forever(spin) );

        }

        // switch turn if it's fight type of game
        if (attackedKingdom!=null){
            if(attackedKingdom.getAttackTargetInfo().attackTypeInfo==AttackTypeInfo.DoubleFight){
                enemyTurn=!enemyTurn;
                //System.out.println("enemyTurn="+enemyTurn);
            }
        }
    }


    private ArrayList<Cell> FindCellsToMove(Cell firstCell,ItemPos itemPos){
        int count = 0;
        ArrayList<Cell> arrayList = new ArrayList<>();
        Cell cell;
        switch (itemPos){
            case Up: count= CellCount- firstCell.getRow()-1;
            break;
            case Down: count=  firstCell.getRow();
                break;
            case Right: count= CellCount- firstCell.getCol()-1;
                break;
            case Left: count=  firstCell.getCol();
                break;
        }
        for (int i = 0;i<count;i++){
            switch (itemPos){
                case Up: cell = new Cell(firstCell.getRow()+1+i,firstCell.getCol());
                arrayList.add(cell);
                    break;
                case Down: cell = new Cell(firstCell.getRow()-1-i,firstCell.getCol());
                    arrayList.add(cell);
                    break;
                case Right:cell = new Cell(firstCell.getRow(),firstCell.getCol()+1+i);
                    arrayList.add(cell);
                    break;
                case Left: cell = new Cell(firstCell.getRow(),firstCell.getCol()-1-i);
                    arrayList.add(cell);
                    break;
            }
        }
        return arrayList;
    }
    public ArrayList<Item> FindItemsToMove (ArrayList<Cell> arrayList){
        ArrayList<Item> arrayListItems = new ArrayList<>();
        for(int i = 0;i<arrayList.size();i++){
            for (Actor a : mainStage.getActors())
            {
                if ( Item.class.isAssignableFrom(a.getClass()) ){
                    if( (arrayList.get(i).getCol()==((Item)a).getCol())&&(arrayList.get(i).getRow()==((Item)a).getRow())){
                        arrayListItems.add( (Item) a );
                        //System.out.println("Item added: Col="+((Item)a).getCol() +" Row="+ ((Item)a).getRow());
                    }

                }

            }
        }
        return arrayListItems;
    }

    //Check if the same item exists in both arrays
    private boolean checkForDublicates(ArrayList<Cell> arrayListMove, ArrayList<Cell> arrayListDel){
        for (int i=0;i<arrayListMove.size();i++){
            for (int j=0;j<arrayListDel.size();j++){
                if (arrayListDel.get(j).isEqual(arrayListMove.get(i))){
                    return true;
                }
            }
        }
        return false;
    }

    public void AddNewItems(ArrayList<Cell> arrayListCells,ArrayList<Item> arrayListItems,int count,Cell fCell){
        Cell cell;
        Cell firstCell;
        boolean isKnightItemPossible;
        double rnd = Math.random();
        if ((count>=7) && (rnd<0.3)){
            isKnightItemPossible = true;
        }else {
            isKnightItemPossible = false;
        }
        if(arrayListCells.size()>0) {
            firstCell = arrayListCells.get(arrayListCells.size() - 1);
        }else {
            firstCell = fCell;
        }
        for(int i = 0;i<count;i++){
            switch (directionToFill){
                case Up: cell = new Cell(firstCell.getRow()+1+i,firstCell.getCol());
                    arrayListCells.add(cell);
                   arrayListItems.add( CreateNewItemToFill(gameLevel,cell.getRow(),cell.getCol(),isKnightItemPossible));
                    break;
                case Down: cell = new Cell(firstCell.getRow()-1-i,firstCell.getCol());
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItemToFill(gameLevel,cell.getRow(),cell.getCol(),isKnightItemPossible));
                    break;
                case Right:cell = new Cell(firstCell.getRow(),firstCell.getCol()+1+i);
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItemToFill(gameLevel,cell.getRow(),cell.getCol(),isKnightItemPossible));
                    break;
                case Left: cell = new Cell(firstCell.getRow(),firstCell.getCol()-1-i);
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItemToFill(gameLevel,cell.getRow(),cell.getCol(),isKnightItemPossible));
                    break;
            }

        }
    }

    private Item CreateNewItem (int levelnumber,int row,int col){
        Item item;
        float x = gameFieldX - cellSize;
        float y = gameFieldY - cellSize;

        item = getRandomItem(x,y,row,col,levelnumber);
        return item;
    }

    private Item CreateNewItemToFill(int levelnumber, int row, int col, boolean isKnightItemPossible){
        Item item ;
        float x = gameFieldX + col * cellSize;
        float y = gameFieldY + row * cellSize;
        double rnd = Math.random();

        if (isKnightItemPossible) {
            if (rnd < 0.03f) {
                item = getRandomKnightItem(x, y, row, col, levelnumber);
                return item;
            }
        }


        item = getRandomItem(x,y,row,col,levelnumber);
        return item;
    }

    public Item getRandomItem(float x, float y, int row,int col, int levelnumber){
        Item item;

        double rnd = Math.random();

        if (rnd<0.25){
            return new Coin2(x, y, cellSize, cellSize, mainStage, row, col,cradleGame);
        }
        if ((rnd>=0.25) && (rnd<0.5)){
            return new Wood(x, y, cellSize, cellSize, mainStage, row, col,cradleGame);
        }
        if ((rnd>=0.5) && (rnd<0.75)){
            return new Bread(x, y, cellSize, cellSize, mainStage, row, col,cradleGame);
        }

        float lvl = levelnumber;
        if (lvl>100){lvl=100;}

        rnd = Math.random();

        int lvlH =cradleGame.getDifficultyLevel();
        switch (lvlH) {
            case 1:
                if ((rnd - lvl / 400) > 0.3) {
                    return new Coin(x, y, cellSize, cellSize, mainStage, row, col,cradleGame);
                }
                return new Jem01(x, y, cellSize, cellSize, mainStage, row, col,cradleGame);

            case 2:
                if ((rnd - lvl / 400) > 0.5) {
                    return new Coin(x, y, cellSize, cellSize, mainStage, row, col,cradleGame);
                }
                return new Jem01(x, y, cellSize, cellSize, mainStage, row, col,cradleGame);

            default:
            if ((rnd - lvl / 400) > 0.7) {
                return new Coin(x, y, cellSize, cellSize, mainStage, row, col,cradleGame);
            }
            return new Jem01(x, y, cellSize, cellSize, mainStage, row, col,cradleGame);

        }

        /*
        if (Math.random()<0.25){
            item = new Coin2(x, y, cellSize, cellSize, mainStage, row, col);
        }else {
            //lower coins with rising of level
            if (Math.random()<(0.15-levelnumber/200)) {
                item = new Coin(x, y, cellSize, cellSize, mainStage, row, col);
            }
            else{
                if(Math.random()<0.3){
                    item = new Wood(x, y, cellSize, cellSize, mainStage, row, col);
                }else{
                    if (Math.random()<0.9-levelnumber/100) {
                        item = new Bread(x, y, cellSize, cellSize, mainStage, row, col);
                    }else{
                        item = new Jem01(x, y, cellSize, cellSize, mainStage, row, col);
                    }
                }

            }
        }
        */

    }

    public KnightItem getRandomKnightItem(float x, float y, int row,int col, int levelnumber) {
        KnightItem knightItem;
        KnightItemParams knightItemParams;
        KnightItemType knightItemType = KnightItemType.Sword;

        int itemMight = 0;
        int itemHealth = 0;
        int itemSpeed = 0;

        double rnd = Math.random();
        //Choose KnightItemType
        if (rnd<0.15f) {
            knightItemType = KnightItemType.Sword;
            itemMight=1;
        } else
        if (rnd<0.3f) {
            knightItemType = KnightItemType.Helmet;
            itemHealth=1;
        } else
        if (rnd<0.45f) {
            knightItemType = KnightItemType.Armor;
            itemHealth=1;
        } else
        if (rnd<0.6f) {
            knightItemType = KnightItemType.Boots;
            itemSpeed=1;
        } else
        if (rnd<0.75f) {
            knightItemType = KnightItemType.Gloves;
            itemSpeed=1;
        } else
         {
            knightItemType = KnightItemType.Shield;
            itemMight=1;
        }



            //Choose KnightItem
        if (rnd<0.6f){
            knightItemParams = new KnightItemParams(knightItemType,1,1, 5*itemHealth,0.1f*itemMight,1*itemSpeed);
            knightItem = new KnightItem(x, y, cellSize, cellSize, mainStage, row, col,cradleGame,knightItemParams,null,null);
            return  knightItem;
        }else if (rnd<0.8f){
            knightItemParams = new KnightItemParams(knightItemType,2,1,10*itemHealth,0.2f*itemMight,2*itemSpeed);
            knightItem = new KnightItem(x, y, cellSize, cellSize, mainStage, row, col,cradleGame,knightItemParams,null,null);
            return  knightItem;
        } else {
            knightItemParams = new KnightItemParams(knightItemType,3,1,15*itemHealth,0.3f*itemMight,3*itemSpeed);
            knightItem = new KnightItem(x, y, cellSize, cellSize, mainStage, row, col,cradleGame,knightItemParams,null,null);
            return  knightItem;
        }

        /*

        if (rnd<0.35f){
            knightItemParams = new KnightItemParams(knightItemType,4,2,20*itemHealth,0.5f*itemMight,4*itemSpeed);
            knightItem = new KnightItem(x, y, cellSize, cellSize, mainStage, row, col,cradleGame,knightItemParams,null);
            return  knightItem;
        }
        if (rnd<0.39f){
            knightItemParams = new KnightItemParams(knightItemType,5,2,25*itemHealth,0.6f*itemMight,5*itemSpeed);
            knightItem = new KnightItem(x, y, cellSize, cellSize, mainStage, row, col,cradleGame,knightItemParams,null);
            return  knightItem;
        }
        if (rnd<0.42f){
            knightItemParams = new KnightItemParams(knightItemType,6,2,30*itemHealth,0.7f*itemMight,6*itemSpeed);
            knightItem = new KnightItem(x, y, cellSize, cellSize, mainStage, row, col,cradleGame,knightItemParams,null);
            return  knightItem;
        }



        knightItemParams = new KnightItemParams(knightItemType,1,1,5*itemHealth,0.1f*itemMight,1*itemSpeed);
        knightItem = new KnightItem(x, y, cellSize, cellSize, mainStage, row, col,cradleGame,knightItemParams,null);
        return  knightItem;
*/

    }

    // Don't work right moves elements outside and hides the Knight
    //Don't see silotions with coin
    /*
    private void RestartLevel() {
        mainStage.clear();
        uiStage.clear();
        initialize();

    }
    */


    public int IncreaseRes(String className){

        int resCollectedCount=0; //indicates if res was collected
        if (enemyTurn) return resCollectedCount; // if enemy collected res? than forget it

        switch (className)
        {
            case "by.android.cradle.Coin2":
                cradleGame.setGameResGold(GameRes.Gold+1);
                resultAttack.Gold++;
                IncrementScoreDuringAttack(); //temporarily increase score. Finally applied after win game
                resCollectedCount++;
            break;
            case "by.android.cradle.Wood":
                cradleGame.setGameResWood(GameRes.Wood+1);
                resultAttack.Wood++;
                IncrementScoreDuringAttack(); //temporarily increase score. Finally applied after win game
                resCollectedCount++;
                break;
            case "by.android.cradle.Bread":
                cradleGame.setGameResBread(GameRes.Bread+1);
                resultAttack.Bread++;
                IncrementScoreDuringAttack(); //temporarily increase score. Finally applied after win game
                resCollectedCount++;
                break;

        }


        resultsActor.UpdateRes();
        return resCollectedCount;
    }

    private void IncrementScoreDuringAttack(){
        switch (cradleGame.getDifficultyLevel()){
            case 1:
                score_during_attack = score_during_attack +0.25f;
                break;
            case 2:
                score_during_attack = score_during_attack +0.5f;
                break;
            case 3:
                score_during_attack = score_during_attack + 1.0f;
                break;

        }
    }

    public void UpdateRes() {
        resultsActor.UpdateRes();
    }

    public void resize (int width, int height) {

    }


    public void GenerateLevel(int levelnumber){
        //System.out.println("GenerateLevel() = "+levelnumber);
        int countOfLockedItems=0;
        int gameMapLevel = cradleGame.getGameMapLevel();
        ArrayList<Item> arrayListItems = new ArrayList<>();
                for (Actor a : mainStage.getActors())
                {
                    if ( Item.class.isAssignableFrom(a.getClass()) ){
                        arrayListItems.add((Item)a);
                    }

                }

                for(int i =0;i<arrayListItems.size();i++){
                    arrayListItems.get(i).remove();
                    arrayListItems.get(i).clear();
                }

                //clear Bombs


        ArrayList<DragAndDropActor> arrayListBombs = new ArrayList<>();
        for (Actor a : mainStage.getActors())
        {
            if ( DragAndDropActor.class.isAssignableFrom(a.getClass()) ){
                arrayListBombs.add((DragAndDropActor)a);
            }

        }

        for(int i =0;i<arrayListBombs.size();i++){
            arrayListBombs.get(i).remove();
        }


        float lvl = levelnumber;
        if (lvl>300){lvl=300;}

        Item item;

        int maxLockCount=12;
        if(cradleGame.getDifficultyLevel()==1){
            maxLockCount=7;
        }
        if(cradleGame.getDifficultyLevel()==2){
            maxLockCount=9;
        }

        for(int i = 0;i<CellCount;i++){
            for(int j = 0;j<CellCount;j++){

                item = CreateNewItem (gameLevel, i,j);
                //item.addAction(Actions.scaleTo(-1,-1,1));
                item.addAction(Actions.moveTo(gameFieldX + j * cellSize, gameFieldY + i * cellSize,1));
                //item.addAction(Actions.scaleTo(1,1,5));

                if(Math.random()>(0.9-lvl/450)){
                    if ((countOfLockedItems<(3*gameMapLevel+1)) && (countOfLockedItems<maxLockCount)) {
                        countOfLockedItems++;
                        if (Math.random() > (1.0 - lvl / 350)) {
                            item.setLockLevel(2);
                        } else {
                            item.setLockLevel(1);
                        }
                    }
                }

            }
        }

        //Clear counters results of attack
        score_during_attack=0;
        resultAttack.Clear();

    }

    public void WinMessageAndNewLevelCreate(){
        isPaused=true; // block timer in update
        //Fon
        BaseActor fon = new BaseActor(0,0,uiStage,Touchable.disabled,cradleGame);
        fon.loadTexture("fon_orange.png",cellSize*CellCount,cellSize*CellCount);
        fon.setX(gameFieldX);
        fon.setY(gameFieldY);
        //fon.centerAtPosition(gameFieldX+cellSize*CellCount/2,gameFieldY+cellSize*CellCount/2);
        //fon.setOpacity(80);
        Action actions = sequence(fadeIn(0.5f), Actions.delay(3) ,fadeOut(1f));
        fon.addAction(actions);

        //Win message
       /*
        BaseActor youWinMessage = new BaseActor(0,0,uiStage,Touchable.disabled);
        String localeString= java.util.Locale.getDefault().getLanguage().toString();
        if (localeString=="ru"){
            youWinMessage.loadTexture("you-win_ru.png", 424, 114);
        }else {
            youWinMessage.loadTexture("you-win.png", 424, 114);
        }
        youWinMessage.centerAtPosition(gameFieldX+cellSize*CellCount/2,gameFieldY+cellSize*CellCount/2);
        youWinMessage.setOpacity(0);
        //youWinMessage.addAction( Actions.delay(1) );
*/

        Action completeAction = new Action(){
            public boolean act( float delta ) {
                // Do your stuff
                if (attackType == AttackType.AttackArena) {
                    GameRes.Gold -= kingdomRes.Gold;
                    GameRes.Wood -= kingdomRes.Wood;
                    GameRes.Bread -= kingdomRes.Bread;
                }

                knight.addHealth();
                System.out.println("knight health"+knight.getHealth());
                if (attackedKingdom!=null){
                    attackedKingdom.decreaseProtection();
                    ply.logLevelEndEvent(gameLevel,"attack is won");
                    setGameLevel(gameLevel+1);
                    ply.logLevelUpEvent(gameLevel);
                    if (attackedKingdom.getProtectionState() == 0){
                        //ply.logLevelUpEvent("2", "Protection state of Kingdom lowered to 0", "Kingdom captured");
                    }
                }



                dialogBox_endLevel.setResults(Math.round(score_during_attack),resultAttack.Gold,resultAttack.Wood,resultAttack.Bread);

                 InputListener inputListener2 =new InputListener() {
                    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                        if (!(e instanceof InputEvent))
                            return false;

                        if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                            return false;

                        //Check if level rising is needed for hero.
                        boolean isHeroLevelNeedsShow;
                        if (knight.calcKnightLevelAtScore(GameRes.Score+Math.round(score_during_attack))>knight.getKnightLevel()){
                            knight.incLevel();
                            isHeroLevelNeedsShow = true;
                        }else {
                            isHeroLevelNeedsShow = false;
                        }

                        GameRes.Score=GameRes.Score+Math.round(score_during_attack); // increase score if win
                        collectAllKnightItems();
                        setEndLevelDialogActive(false);
                        cradleGame.setActiveGameMapScreen(isHeroLevelNeedsShow,0);

                        return true;
                    }
                };

                InputListener inputListenerWatchVideo = new InputListener() {
                    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                        if (!(e instanceof InputEvent))
                            return false;

                        if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                            return false;


                        if(cradleGame.getiGoogleServices().hasVideoLoaded()) {
                            cradleGame.getiGoogleServices().showRewardedVideoAd();
                        }

                        //isPaused=false;
                        return true;
                    }
                };

                QttyRewardedVideoWatched=0;
                dialogBox_endLevel.showWithOkButton(inputListener2,inputListenerWatchVideo);

                return true;
            }
        };

        actions = sequence(fadeIn(0.5f), Actions.delay(3) ,fadeOut(1f), completeAction);
        //youWinMessage.addAction( actions );

        String s = cradleGame.getLanguageStrings().get("you_win");
        messageLabel.setText(s);
        messageLabel.setColor(Color.GOLD);
        messageLabel.setVisible(true);
        messageLabel.addAction(actions);



        //Explosion
        ExplosionEffect2 boom1 = new ExplosionEffect2();
        boom1.setX(messageLabel.getX());
        boom1.setY(messageLabel.getY());
        boom1.start();
        mainStage.addActor(boom1);
        ExplosionEffect2 boom2 = new ExplosionEffect2();
        boom2.centerAtActor( messageLabel );
        boom2.start();
        mainStage.addActor(boom2);
        ExplosionEffect2 boom3 = new ExplosionEffect2();
        boom3.setX(messageLabel.getX()+messageLabel.getWidth());
        boom3.setY(messageLabel.getY());
        boom3.start();
        mainStage.addActor(boom3);

    }



    // set res for add after winnning on Arena
    public void SetRes(KingdomRes kingdomRes){
        this.kingdomRes.Bread = kingdomRes.Bread;
        this.kingdomRes.Gold = kingdomRes.Gold;
        this.kingdomRes.Wood = kingdomRes.Wood;
    }

    public Kingdom getAttackedKingdom() {
        return attackedKingdom;
    }

    public void setAttackedKingdom(Kingdom attackedKingdom) {
        this.attackedKingdom = attackedKingdom;
    }


    public ArrayList<Item> GetNearItem(Item item){
        ArrayList<Item> ai = new ArrayList<>();
        Cell itemCell1 = new Cell(item.getCell());
        Cell itemCell2 = new Cell(item.getCell());
       // System.out.println("GetNearItem: row="+itemCell1.getRow() +" col="+itemCell1.getCol());
        Item item2;
        // up
        if (itemCell2.getRow()>0){
            itemCell2.setRow(itemCell2.getRow()-1);
            item2=gameField.GetItemAtCell(itemCell2,mainStage);
            if(item2!=null){
                ai.add(item2);
            }

        }

        //right
        itemCell2.setRow(itemCell1.getRow());
        itemCell2.setCol(itemCell1.getCol());
        if (itemCell2.getCol()<(CellCount-1)){
            itemCell2.setCol(itemCell2.getCol()+1);
            item2=gameField.GetItemAtCell(itemCell2,mainStage);
            if(item2!=null){
                ai.add(item2);
            }
        }

        //down
        itemCell2.setRow(itemCell1.getRow());
        itemCell2.setCol(itemCell1.getCol());
        if (itemCell2.getRow()<(CellCount-1)){
            itemCell2.setRow(itemCell2.getRow()+1);
            item2=gameField.GetItemAtCell(itemCell2,mainStage);
            if(item2!=null){
                ai.add(item2);
            }
        }
        //left
        itemCell2.setRow(itemCell1.getRow());
        itemCell2.setCol(itemCell1.getCol());
        if (itemCell2.getCol()>0){
            itemCell2.setCol(itemCell2.getCol()-1);
            item2=gameField.GetItemAtCell(itemCell2,mainStage);
            if(item2!=null){
                ai.add(item2);
            }
        }


        return ai;
    }

    public int getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
        this.gameLevelLabel.setText(""+gameLevel);
    }

    //For Debug purposes
    public void printNumberOfActors()
    {
        ArrayList<Actor> list = new ArrayList<Actor>();
        Stage stage= mainStage;

        for (Actor a : stage.getActors())
        {
                list.add( a );
        }
        //System.out.println("MainStage: Actors="+list.size());
        GdxLog.d("printNumberOfActors():","MainStage: Actors=%d",list.size());
        list = new ArrayList<Actor>();
        stage= uiStage;

        for (Actor a : stage.getActors())
        {
            list.add( a );
        }
        //System.out.println("uiStage: Actors="+list.size());
        GdxLog.d("printNumberOfActors():","uiStage: Actors=%d",list.size());

    }

    public void HideDialog (){
        dialogBox_endLevel.setVisible(false);

    }

    private int getLevelDuration() {
        int ld = LevelDuration;
        switch (cradleGame.getDifficultyLevel()) {
            case 1:
                ld += 20;
                break;
            case 2:
                ld += 10;
                break;
            case 3:
                break;

        }
        return ld;
    }

    public void setTimeBombQttyLabelText(String text){
        timeBombQttyLabel.setText(text);
    }
    public void setSquareBomb1QttyLabelText(String text){
        squareBomb1QttyLabel.setText(text);
    }
    public void setSquareBomb2QttyLabelText(String text){
        squareBomb2QttyLabel.setText(text);
    }

    public void collectAllKnightItems(){
        KnightItem knightItem;
        for (by.android.cradle.BaseActor baseActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.KnightItem")) {
            knightItem = (KnightItem) baseActor;
            knight.addKnightItem(knightItem);
        }
    }


    public void ShowCollectedResCount(float x, float y, int count){
        if (count==0) return;
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        String s = String.valueOf(count);
        final Label resCollectedLabel = new Label(s, BaseGame.labelStyle);
        if (w>1000) {
            resCollectedLabel.setFontScale(3);
        }
        else{
            resCollectedLabel.setFontScale(2);
        }
        resCollectedLabel.setColor(Color.GOLD);
        resCollectedLabel.setAlignment(Align.center);
        resCollectedLabel.setPosition(x,y);
        uiStage.addActor(resCollectedLabel);


        Action completeAction = new Action(){
            public boolean act( float delta ) {
                // Do your stuff
                resCollectedLabel.remove();

                return true;
            }
        };


        Action actions = sequence(Actions.moveTo(x, y+cellSize, 1.50f, Interpolation.circleOut),fadeOut(0.5f),completeAction);
        resCollectedLabel.addAction(actions);

    }



    public void HideArrowUp(){

        if( arrowUpActor!=null) {
            arrowUpActor.setVisible(false);
        }
    }


    public void useRewardForVideo(){
        QttyRewardedVideoWatched++;  //Controls if video was already used by player
        System.out.println("useRewardForVideo called");
        if (isEndLevelDialogActive){
            dialogBox_endLevel.setResults(Math.round(score_during_attack)*(QttyRewardedVideoWatched+1),resultAttack.Gold*(QttyRewardedVideoWatched+1),resultAttack.Wood*(QttyRewardedVideoWatched+1),resultAttack.Bread*(QttyRewardedVideoWatched+1));
            GameRes.Score+=Math.round(score_during_attack);
            GameRes.Gold += resultAttack.Gold;
            GameRes.Wood += resultAttack.Wood;
            GameRes.Bread += resultAttack.Bread;
            if (QttyRewardedVideoWatched>1){
                dialogBox_endLevel.hideWatchAdButton();
                QttyRewardedVideoWatched=0;
            }
            return;
        }

        if(attackedKingdom==null){ //Arena
            sandGlass.setElapsedTime(sandGlass.getAnimationDuration()-20);
            return;
        }

        switch(attackedKingdom.getAttackTargetInfo().attackTypeInfo){
            case SingleTimeClearUp:
            case SingleTimeResources:
                //sandGlass.setElapsedTime(sandGlass.getElapsedTime()-20);
                sandGlass.setElapsedTime(sandGlass.getAnimationDuration()-20);
                return;
                //break;
            default:
                attackTargetSteps.setAttackStepsQtty(attackTargetSteps.getAttackStepsQtty()+5);
        }

        if(!isEndLevelDialogActive) {
            isPaused=false;
        }
    }

    public void NouseRewardForVideo(){

        if(!isEndLevelDialogActive) {
            isPaused=false;
        }
    }


    public boolean isEndLevelDialogActive() {
        return isEndLevelDialogActive;
    }

    public void setEndLevelDialogActive(boolean endLevelDialogActive) {
        isEndLevelDialogActive = endLevelDialogActive;
    }
}
