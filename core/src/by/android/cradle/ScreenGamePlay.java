package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class ScreenGamePlay extends BaseScreen {
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
    private GameRes gameRes;
    private Label goldQuantityLabel;
    private Label woodQuantityLabel;
    private Label breadQuantityLabel;
    private int gameLevel;
    private ScreenGamePlay screenGamePlay;
    private SandGlass sandGlass;

    public void initialize()
    {
        screenGamePlay = this;
        gameRes = new GameRes();
        GameRes.Bread=100;
        GameRes.Wood=100;
        GameRes.Gold=50;

        gameLevel = 1 ;

        // Get screen size
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        h=h-70; //place for top menu items
        if (w<h) {
            h=w;
        } else w=h;
        cellSize = w/CellCount;

        BaseActor.setWorldBounds(cellSize*CellCount,cellSize*CellCount);
        gameFieldX=(Gdx.graphics.getWidth()-w)/2;
        gameFieldY=0;
        gameField = new GameField(gameFieldX,gameFieldY,mainStage,cellSize*CellCount,cellSize*CellCount,CellCount,1);

        new ResultsActor(gameFieldX,h,cellSize*CellCount,70,uiStage,Touchable.disabled);
        //
        goldQuantityLabel = new Label(" "+0, BaseGame.labelStyle);
        goldQuantityLabel.setColor( Color.GOLDENROD );
        goldQuantityLabel.setPosition( gameFieldX+100,h+5 );
        goldQuantityLabel.setFontScale(0.9f);
        uiStage.addActor(goldQuantityLabel);

        woodQuantityLabel = new Label(" "+0, BaseGame.labelStyle);
        woodQuantityLabel.setColor( Color.GOLDENROD );
        woodQuantityLabel.setPosition( gameFieldX+350,h+5 );
        woodQuantityLabel.setFontScale(0.9f);
        uiStage.addActor(woodQuantityLabel);

        breadQuantityLabel = new Label(" "+0, BaseGame.labelStyle);
        breadQuantityLabel.setColor( Color.GOLDENROD );
        breadQuantityLabel.setPosition( gameFieldX+600,h+5 );
        breadQuantityLabel.setFontScale(0.9f);
        uiStage.addActor(breadQuantityLabel);

        //SandGlass placement
        float x = gameFieldX+cellSize*CellCount+10;
        float y =gameFieldY+200;
        int sw = (int)(Gdx.graphics.getWidth()-x);
        sandGlass = new SandGlass(x,y,uiStage,sw,sw*2, 20);

        //Gamemap Button
        TextButton mapButton = new TextButton( "GameMap", BaseGame.textButtonStyle );
        mapButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                CradleGame.setActiveScreen(new GameMapScreen(screenGamePlay));
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


        //gameField.boundToWorld();
        gameField.setTouchable(Touchable.enabled);




        gameField.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                flag = true;

                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ){

                        //Coin
                    for (by.android.cradle.BaseActor CoinActor : by.android.cradle.BaseActor.getList(mainStage, "by.android.cradle.Coin")){
                        if(CoinActor.getBoundaryPolygon().contains(x+gameFieldX,y+gameFieldY)){
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
                            if (lastSelectedItem==null){
                                lastSelectedItem =(Bread) CoinActor;
                                firstSelectedItem= lastSelectedItem;
                            }
                            ((Bread)CoinActor).setSelected(true, lastSelectedItem);
                            lastSelectedItem =(Bread) CoinActor;
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
                        gameLevel++;
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
                                    if (!((Item) CoinActor).isSelected()) {
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
                                    if (!((Item) CoinActor).isSelected()) {
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
        if (sandGlass.isAnimationFinished()) {
            LooseLevel();
        }
    }

    public ArrayList<Cell> removeSelectedItems(){
        ArrayList<Cell> arrayList = new ArrayList<>();
        if (firstSelectedItem==null)return arrayList;
        directionToFill = firstSelectedItem.getNext().findItemPos(firstSelectedItem);
        String className;
        Item item1= firstSelectedItem;
        Item item2=null;
        while (item1.getNext()!=null){
            item2 = item1.getNext();
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
                   arrayListItems.add( CreateNewItem(cell.getRow(),cell.getCol()));
                    break;
                case Down: cell = new Cell(firstCell.getRow()-1-i,firstCell.getCol());
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItem(cell.getRow(),cell.getCol()));
                    break;
                case Right:cell = new Cell(firstCell.getRow(),firstCell.getCol()+1+i);
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItem(cell.getRow(),cell.getCol()));
                    break;
                case Left: cell = new Cell(firstCell.getRow(),firstCell.getCol()-1-i);
                    arrayListCells.add(cell);
                    arrayListItems.add( CreateNewItem(cell.getRow(),cell.getCol()));
                    break;
            }

        }
    }
    private Item CreateNewItem (int row,int col){
        Item item ;
        if (Math.random()<0.25){
            item = new Coin2(gameFieldX + col * cellSize, gameFieldY + row * cellSize, cellSize, cellSize, mainStage, row, col);
        }else {
            if (Math.random()<0.35) {
                item= new Coin(gameFieldX + col * cellSize, gameFieldY + row * cellSize, cellSize, cellSize, mainStage, row, col);
            }else{
                if (Math.random()<0.6) {
                    item= new Bread(gameFieldX + col * cellSize, gameFieldY + row * cellSize, cellSize, cellSize, mainStage, row, col);
                }else{
                    item= new Wood(gameFieldX + col * cellSize, gameFieldY + row * cellSize, cellSize, cellSize, mainStage, row, col);
                }
            }

        }
        return item;
    }

    private void RestartLevel() {
        mainStage.clear();
        uiStage.clear();
        initialize();

    }

    public void IncreaseRes(String className){

        switch (className)
        {
            case "by.android.cradle.Coin2": gameRes.Gold++;
                goldQuantityLabel.setText(" "+gameRes.Gold);
            break;
            case "by.android.cradle.Wood": gameRes.Wood++;
                woodQuantityLabel.setText(" "+gameRes.Wood);
                break;
            case "by.android.cradle.Bread": gameRes.Bread++;
                breadQuantityLabel.setText(" "+gameRes.Bread);
                break;

        }

    }


    public void UpdateRes() {

        goldQuantityLabel.setText(" " + gameRes.Gold);
        woodQuantityLabel.setText(" " + gameRes.Wood);
        breadQuantityLabel.setText(" " + gameRes.Bread);


    }

    public void resize (int width, int height) {
        mainStage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);
    }


    public void GenerateLevel(int levelnumber){

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

                if (Math.random()<0.25){
                    item = new Coin2(gameFieldX - cellSize, gameFieldY -cellSize, cellSize, cellSize, mainStage, i, j);
                }else {
                    if (Math.random()<0.5) {
                        item = new Coin(gameFieldX - cellSize, gameFieldY - cellSize, cellSize, cellSize, mainStage, i, j);
                    }
                    else{
                        if(Math.random()<0.75){
                            item = new Wood(gameFieldX - cellSize, gameFieldY - cellSize, cellSize, cellSize, mainStage, i, j);
                        }else{
                            item = new Bread(gameFieldX - cellSize, gameFieldY - cellSize, cellSize, cellSize, mainStage, i, j);
                        }

                    }
                }
                //item.addAction(Actions.scaleTo(-1,-1,1));
                item.addAction(Actions.moveTo(gameFieldX + j * cellSize, gameFieldY + i * cellSize,1));
                //item.addAction(Actions.scaleTo(1,1,5));

            }
        }
    }

    public void WinMessageAndNewLevelCreate(){
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

                StartNewLevel(gameLevel);
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

    public void StartNewLevel(int gameLevel){
        gameField.GenerateLevel(gameLevel,CellCount);
        GenerateLevel(gameLevel);
        //sandGlass.Restart(180);
        sandGlass.remove();
        //SandGlass recreation for restarting of animation
        float x = gameFieldX+cellSize*CellCount+10;
        float y = gameFieldY+200;
        int sw = (int)(Gdx.graphics.getWidth()-x);
        sandGlass = new SandGlass(x,y,uiStage,sw,sw*2, 20);
    }

    public void LooseLevel(){
        CradleGame.setActiveScreen(new GameMapScreen(screenGamePlay));

    }

}
