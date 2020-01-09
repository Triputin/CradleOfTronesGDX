package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.Set;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class ScreenGamePlay extends BaseScreen {
   // Game properties to save
   private int gameLevel;


    // Game constant params
    private int LevelDuration = 120; // Time of every level (HourGlass)
    private int cellSize;
    private final int CellCount = 7;
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

    private ScreenGamePlay screenGamePlay;
    private SandGlass sandGlass;
    private Sound explosionSound;
    private Label messageLabel;
    private boolean isPaused; //indicates that screen is not active
    private AttackType attackType;
    private KingdomRes kingdomRes; //res to win
    private Kingdom attackedKingdom;



    public ScreenGamePlay(CradleGame cradleGame) {
        super(cradleGame);
    }

    public void initialize()
    {
        kingdomRes = new KingdomRes();
        attackType = AttackType.AttackKingdom;
        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setFontScale(4);
        messageLabel.setVisible(false);
        uiTable.add(messageLabel).expandY();

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/glass_windows_breaking.mp3"));

        screenGamePlay = this;

        // Get screen size
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();


        BaseActor hall = new BaseActor(0,0, mainStage, Touchable.disabled);
        //hall.loadTexture( "hall01.png",w,h );

        hall.loadTexture( "game_of_thrones_locations4.jpg",w,h );
        h=h-70; //place for top menu items
        if (w<h) {
            h=w;
        } else w=h;
        cellSize = w/CellCount;

        BaseActor.setWorldBounds(cellSize*CellCount,cellSize*CellCount);
        gameFieldX=(Gdx.graphics.getWidth()-w)/2;
        gameFieldY=0;
        int gameFieldWidth = cellSize*CellCount;
        gameField = new GameField(gameFieldX,gameFieldY,mainStage,gameFieldWidth,gameFieldWidth,CellCount,1);

        BaseActor baseResultsActor = new BaseActor(gameFieldX,h,uiStage,Touchable.disabled);
        baseResultsActor.setWidth(cellSize*CellCount);
        baseResultsActor.setHeight(70);
        resultsActor = new ResultsActor(0,0,cellSize*CellCount,70,uiStage,Touchable.disabled,baseResultsActor);
        //DrawResults(h, gameFieldWidth);
        UpdateRes();
        //SandGlass placement
        float x = gameFieldX+cellSize*CellCount+10;
        float y = gameFieldY+gameFieldWidth*0.15f;
        int sw = (int)(Gdx.graphics.getWidth()-x);
        sandGlass = new SandGlass(x,y,uiStage,sw, Math.round( gameFieldWidth*0.7f), LevelDuration);
        //Game level
        gameLevel = 1 ;
        gameLevelLabel = new Label(" "+0, BaseGame.labelStyle);
        gameLevelLabel.setText(""+gameLevel);
        gameLevelLabel.setColor( Color.GOLDENROD );
        gameLevelLabel.setPosition( x+sandGlass.getWidth()/2-10,y );
        gameLevelLabel.setFontScale(0.5f);
        uiStage.addActor(gameLevelLabel);



/*
        //Gamemap Button
        TextButton mapButton = new TextButton( "GameMap", BaseGame.textButtonStyle );
        mapButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                //CradleGame.setActiveScreen(new GameMapScreen(screenGamePlay));
                cradleGame.setActiveGameMapScreen();
                return true;
            }
        });
        mapButton.setPosition(0,200);
        uiStage.addActor(mapButton);

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

        //FindSolution Button
        Button testButton = new Button( buttonStyle );
        testButton.setPosition(50,450);
        uiStage.addActor(testButton);

        testButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
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

                flag = true;

                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ){

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
                if (lastSelectedItem==null) return;
                    String className = lastSelectedItem.getClass().getName();
                    if ( ie.getType().equals(InputEvent.Type.touchUp) ){

                        if (firstSelectedItem.getCountOfSelectedItems()>=3) {
                            FillRemovedCells(removeSelectedItems());
                        }else{
                            for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, className)){
                                ((Item)CoinActor).setSelected(false,null);
                            }
                        }
                        lastSelectedItem = null;
                        firstSelectedItem = null;
                    }
                    if( gameField.CheckWin()){
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


    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }



    public void SelectSolution(ArrayList<ArrayList<Item>> arrayLists){
        if (flag){return;}

        if(arrayLists.size()<1){
        // We need to restart level because no steps remains...
            RestartLevel();
            return;

        }

        //Choose solution randomly
        int sol = (int) Math.round(Math.random()*(arrayLists.size()-1));
        if (sol>(arrayLists.size()-1)){
            sol = arrayLists.size()-1;
        }
        ArrayList<Item> arrayList = arrayLists.get(sol);
        if (arrayList.size()<3){return;}
        arrayList.get(0).setSelected(true,null);
        arrayList.get(0).setSelectedNext(arrayList.get(1));
        for (int i=1;i<arrayList.size()-1;i++){
            arrayList.get(i).setSelected(true,arrayList.get(i-1));
            arrayList.get(i).setSelectedNext(arrayList.get(i+1));
        }
        arrayList.get(arrayList.size()-1).setSelected(true,arrayList.get(arrayList.size()-2));
    }

    public void update(float dt)
    {

        if (sandGlass.isAnimationFinished()&&!isPaused) {
            LoseLevel();
        }
    }

    public ArrayList<Cell> removeSelectedItems(){
        ArrayList<Cell> arrayList = new ArrayList<>();
        if (firstSelectedItem==null)return arrayList;
        directionToFill = firstSelectedItem.getNext().findItemPos(firstSelectedItem);
        String className;
        Item item1= firstSelectedItem;
        Item item2=null;

        //unlock near items for just one level
        ArrayList<Item> itemArrayList = new ArrayList<>();
        ArrayList<Item> itemArrayList2;
        Item it;
        while (item1.getNext()!=null){
            itemArrayList2 = GetNearItem(item1);
            for(int i=0;i<itemArrayList2.size();i++){
                if(!itemArrayList.contains(itemArrayList2.get(i))){
                   // System.out.println("NearItems: row="+itemArrayList2.get(i).getRow() +" col="+itemArrayList2.get(i).getCol());
                    itemArrayList.add(itemArrayList2.get(i));
                }
            }
            item1=item1.getNext();
        }
        itemArrayList2 = GetNearItem(item1);
        for(int i=0;i<itemArrayList2.size();i++){
            if(!itemArrayList.contains(itemArrayList2.get(i))){
                // System.out.println("NearItems: row="+itemArrayList2.get(i).getRow() +" col="+itemArrayList2.get(i).getCol());
                itemArrayList.add(itemArrayList2.get(i));
            }
        }
        for(int i=0;i<itemArrayList.size();i++){
            itemArrayList.get(i).UnlockForOneLevel();
        }


        //remove items
        item1= firstSelectedItem;
        item2=null;
        while (item1.getNext()!=null){
            item2 = item1.getNext();
            arrayList.add(item1.getCell());
            className = item1.getClass().getName();
            IncreaseRes(className);
            //Explosion
            ExplosionEffect boom = new ExplosionEffect();
            boom.centerAtActor( item1 );
            boom.start();
            //Explosion sound
            explosionSound.play(1f);

            mainStage.addActor(boom);
            gameField.changeGameCell(item1.getCell());


            item1.remove();
            item1=item2;
        }
        arrayList.add(item1.getCell());
        className = item1.getClass().getName();
        IncreaseRes(className);
        //Explosion
        ExplosionEffect boom = new ExplosionEffect();
        boom.centerAtActor( item1 );
        boom.start();
        mainStage.addActor(boom);

        gameField.changeGameCell(item1.getCell());

        item1.remove();
        lastSelectedItem=null;
        firstSelectedItem=null;
        return arrayList;
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

//Создаем змейку из существующих и новых элементов
        newCells = FindCellsToMove(cell,directionToFill);
        newItems = FindItemsToMove(newCells);
        AddNewItems(newCells,newItems,arrayList.size(),cell);

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
                    }

                }

            }
        }
        return arrayListItems;
    }
    public void AddNewItems(ArrayList<Cell> arrayListCells,ArrayList<Item> arrayListItems,int count,Cell fCell){
        Cell cell;
        Cell firstCell;
        if(arrayListCells.size()>0) {
            firstCell = arrayListCells.get(arrayListCells.size() - 1);
        }else {
            firstCell = fCell;
        }
        for(int i = 0;i<count;i++){
            switch (directionToFill){
                case Up: cell = new Cell(firstCell.getRow()+1+i,firstCell.getCol());
                    arrayListCells.add(cell);
                   arrayListItems.add( CreateNewItemToFill(gameLevel,cell.getRow(),cell.getCol()));
                    break;
                case Down: cell = new Cell(firstCell.getRow()-1-i,firstCell.getCol());
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItemToFill(gameLevel,cell.getRow(),cell.getCol()));
                    break;
                case Right:cell = new Cell(firstCell.getRow(),firstCell.getCol()+1+i);
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItemToFill(gameLevel,cell.getRow(),cell.getCol()));
                    break;
                case Left: cell = new Cell(firstCell.getRow(),firstCell.getCol()-1-i);
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItemToFill(gameLevel,cell.getRow(),cell.getCol()));
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

    private Item CreateNewItemToFill(int levelnumber, int row,int col){
        Item item ;
        float x = gameFieldX + col * cellSize;
        float y = gameFieldY + row * cellSize;

        item = getRandomItem(x,y,row,col,levelnumber);
        return item;
    }

    public Item getRandomItem(float x, float y, int row,int col, int levelnumber){
        Item item;
        double rnd = Math.random();

        if (rnd<0.25){
            return new Coin2(x, y, cellSize, cellSize, mainStage, row, col);
        }
        if ((rnd>=0.25) && (rnd<0.5)){
            return new Wood(x, y, cellSize, cellSize, mainStage, row, col);
        }
        if ((rnd>=0.5) && (rnd<0.75)){
            return new Bread(x, y, cellSize, cellSize, mainStage, row, col);
        }

        int lvl = levelnumber;
        if (lvl>100){lvl=100;}

        rnd = Math.random();

        if ((rnd-lvl/200)>0.5){
            return new Coin(x, y, cellSize, cellSize, mainStage, row, col);
        }

        return new Jem01(x, y, cellSize, cellSize, mainStage, row, col);


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

    private void RestartLevel() {
        mainStage.clear();
        uiStage.clear();
        initialize();

    }

    public void IncreaseRes(String className){

        switch (className)
        {
            case "by.android.cradle.Coin2": cradleGame.setGameResGold(GameRes.Gold+1);
            break;
            case "by.android.cradle.Wood": cradleGame.setGameResWood(GameRes.Wood+1);
                break;
            case "by.android.cradle.Bread": cradleGame.setGameResBread(GameRes.Bread+1);
                break;

        }
        resultsActor.UpdateRes();
    }


    public void UpdateRes() {
        resultsActor.UpdateRes();
    }

    public void resize (int width, int height) {
        mainStage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);
    }


    public void GenerateLevel(int levelnumber){
        System.out.println("GenerateLevel() = "+levelnumber);
            ArrayList<Item> arrayListItems = new ArrayList<>();
                for (Actor a : mainStage.getActors())
                {
                    if ( Item.class.isAssignableFrom(a.getClass()) ){
                        arrayListItems.add((Item)a);
                    }

                }

                for(int i =0;i<arrayListItems.size();i++){
                    arrayListItems.get(i).remove();
                }


        Item item;
        for(int i = 0;i<CellCount;i++){
            for(int j = 0;j<CellCount;j++){

                item = CreateNewItem (gameLevel, i,j);
                //item.addAction(Actions.scaleTo(-1,-1,1));
                item.addAction(Actions.moveTo(gameFieldX + j * cellSize, gameFieldY + i * cellSize,1));
                //item.addAction(Actions.scaleTo(1,1,5));

                if(Math.random()>(0.9-levelnumber/100)){
                    if (Math.random()>(0.5-levelnumber/100)) {item.setLockLevel(2);}
                    else {item.setLockLevel(1);
                    }
                }

            }
        }
    }

    public void WinMessageAndNewLevelCreate(){
        isPaused=true; // block timer in update
        //Fon
        BaseActor fon = new BaseActor(0,0,uiStage,Touchable.disabled);
        fon.loadTexture("fon_orange.png",cellSize*CellCount,cellSize*CellCount);
        fon.setX(gameFieldX);
        fon.setY(gameFieldY);
        //fon.centerAtPosition(gameFieldX+cellSize*CellCount/2,gameFieldY+cellSize*CellCount/2);
        //fon.setOpacity(80);
        Action actions = sequence(fadeIn(0.5f), Actions.delay(3) ,fadeOut(1f));
        fon.addAction(actions);

        //Win message
        BaseActor youWinMessage = new BaseActor(0,0,uiStage,Touchable.disabled);
        youWinMessage.loadTexture("you-win.png",424,114);
        youWinMessage.centerAtPosition(gameFieldX+cellSize*CellCount/2,gameFieldY+cellSize*CellCount/2);
        youWinMessage.setOpacity(0);
        //youWinMessage.addAction( Actions.delay(1) );

        Action completeAction = new Action(){
            public boolean act( float delta ) {
                // Do your stuff
                if (attackType == AttackType.AttackArena) {
                    GameRes.Gold -= kingdomRes.Gold;
                    GameRes.Wood -= kingdomRes.Wood;
                    GameRes.Bread -= kingdomRes.Bread;
                }
                if (attackedKingdom!=null){
                    attackedKingdom.decreaseProtection();
                    setGameLevel(gameLevel+1);
                }
                cradleGame.setActiveGameMapScreen();
                return true;
            }
        };

        actions = sequence(fadeIn(0.5f), Actions.delay(3) ,fadeOut(1f), completeAction);
        youWinMessage.addAction( actions );
        //Explosion
        ExplosionEffect2 boom1 = new ExplosionEffect2();
        boom1.setX(youWinMessage.getX());
        boom1.setY(youWinMessage.getY());
        boom1.start();
        mainStage.addActor(boom1);
        ExplosionEffect2 boom2 = new ExplosionEffect2();
        boom2.centerAtActor( youWinMessage );
        boom2.start();
        mainStage.addActor(boom2);
        ExplosionEffect2 boom3 = new ExplosionEffect2();
        boom3.setX(youWinMessage.getX()+youWinMessage.getWidth());
        boom3.setY(youWinMessage.getY());
        boom3.start();
        mainStage.addActor(boom3);



    }

    public void StartNewLevel(){
        gameField.GenerateLevel(gameLevel,CellCount);
        GenerateLevel(gameLevel);
        sandGlass.remove();
        //SandGlass recreation for restarting of animation
        float x = gameFieldX+cellSize*CellCount+10;
        float y = gameFieldY+100;
        int sw = (int)(Gdx.graphics.getWidth()-x);
        sandGlass = new SandGlass(x,y,uiStage,sw,sw*2, LevelDuration);
        isPaused=false;
    }

    public void LoseLevel(){
        System.out.println("LoseLevel()");
        isPaused=true; // block timer in update
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
                // Do your stuff

                cradleGame.setActiveGameMapScreen();
                return true;
            }
        };

        actions = sequence(fadeIn(0.5f), Actions.delay(3) ,fadeOut(1f), completeAction);
        messageLabel.setText("You Lose");
        messageLabel.setColor(Color.RED);
        messageLabel.setVisible(true);
        messageLabel.addAction(actions);

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
        System.out.println("MainStage: Actors="+list.size());

        list = new ArrayList<Actor>();
        stage= uiStage;

        for (Actor a : stage.getActors())
        {
            list.add( a );
        }
        System.out.println("uiStage: Actors="+list.size());

    }


}
