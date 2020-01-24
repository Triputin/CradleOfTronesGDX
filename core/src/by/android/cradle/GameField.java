package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class GameField extends BaseActor {
    private ArrayList<GameCell> gameCells ;
    protected CradleGame cradleGame;

    public GameField(float x, float y, Stage s,float width,float height,int cellsCount, int gameLevel,CradleGame cradleGame)
    {
        super(x,y,s,Touchable.enabled);
        this.cradleGame = cradleGame;
        gameCells = new ArrayList<>();
        int cellSizeX = (int)width/cellsCount;
        int cellSizeY = (int)height/cellsCount;
        //loadTexture("game_of_thrones_locations4.jpg", (int) width,(int) height);
        loadTexture("fon_white.png", (int) width,(int) height);
        setHeight(height);
        setWidth(width);
        GameCell gameCell;

        for (int i = 0;i<cellsCount;i++){
            for (int j = 0;j<cellsCount;j++){
                gameCell = new GameCell(x+i*cellSizeX,y+j*cellSizeY,cellSizeX,cellSizeY,s, Touchable.disabled,j,i);
                gameCells.add(gameCell);
            }
        }

        GenerateLevel(gameLevel,cellsCount);
       //this.setColor(255,255,255,255);



        // repeated texture
        /*Texture imgTexture = new Texture(Gdx.files.internal("assets/marble.jpg"));
        imgTexture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        TextureRegion imgTextureRegion = new TextureRegion(imgTexture);
        imgTextureRegion.setRegion(0,0,128*4,128*4);

        TextureRegionDrawable imgTextureRegionDrawable = new TextureRegionDrawable(imgTextureRegion);
        Image img = new Image();
        img.setDrawable(imgTextureRegionDrawable);
        img.setSize(width,height);
        img.setPosition(0,0);
        addActor(img);
        img.setBounds(0,0,width,height);
        img.setTouchable(Touchable.enabled);
        setTouchable(Touchable.enabled);
        setBounds(x,y,width,height);
        */


    }
/*
    @Override
    public Actor hit (float x, float y, boolean touchable) {
        if (touchable && !this.isTouchable()) return null;
        return x >= 0 && x < this.getWidth() && y >= 0 && y < this.getHeight() ? this : null;
    }
*/

// Find solution for all Items on the field
public ArrayList<ArrayList<Item>> FindSolutions(int cellsCount, Stage stage){

    ArrayList<ArrayList<Item>> arraySolutions = new ArrayList<>();
    ArrayList<Item> arrayList;
    Cell cell = new Cell(0,0);
    for (int i =0; i< cellsCount;i++){
       for (int j =0; j< cellsCount;j++){
            cell.setCol(j);
            cell.setRow(i);
            arrayList = GetSoluton(cell, stage, cellsCount);
            if (arrayList.size()>2){arraySolutions.add(arrayList);}

       }
    }


    //for debug only
    PrintSolutions(arraySolutions);


    return arraySolutions;
}

public Item GetItemAtCell(Cell cell, Stage stage){
    Item item =null;
    for (Actor a : stage.getActors())
    {
        if ( Item.class.isAssignableFrom(a.getClass()) ){
            if( (cell.getCol()==((Item)a).getCol())&&(cell.getRow()==((Item)a).getRow())){
                item= (Item) a;
            }

        }
    }

    //System.out.println("GetItemAtCell: row="+cell.getRow()+" col="+cell.getCol());
    //if (item==null) {System.out.println("GetItemAtCell: item=null");}
    return item;

}


public ArrayList<Item> GetSoluton (Cell cell, Stage stage, int cellCount){
    ArrayList<Item> arrayList= new ArrayList<>();
    Item item1 = GetItemAtCell(cell, stage);
    Item item2;
    String className1 = item1.getClass().getName();
    String className2;

    GameNode root = new GameNode(cell, null);
   //Check left
    if (cell.getCol()>0){
        item2 = GetItemAtCell(new Cell(cell.getRow(),cell.getCol()-1), stage);
        className2 = item2.getClass().getName();
        if ((className1 == className2) && (!item2.isLocked())){
            root.childs.add(new GameNode(item2.getCell(),item1.getCell()));
        }
    }
    //Check right
    if (cell.getCol()<(cellCount-2)){
        item2 = GetItemAtCell(new Cell(cell.getRow(),cell.getCol()+1), stage);
        className2 = item2.getClass().getName();
        if ((className1 == className2) && (!item2.isLocked())){
            root.childs.add(new GameNode(item2.getCell(),item1.getCell()));
        }
    }
    //Check up
    if (cell.getRow()<(cellCount-2)){
        item2 = GetItemAtCell(new Cell(cell.getRow()+1,cell.getCol()), stage);
        className2 = item2.getClass().getName();
        if ((className1 == className2) && (!item2.isLocked())){
            root.childs.add(new GameNode(item2.getCell(),item1.getCell()));
        }
    }
    //Check down
    if (cell.getRow()>0){
        item2 = GetItemAtCell(new Cell(cell.getRow()-1,cell.getCol()), stage);
        className2 = item2.getClass().getName();
        if ((className1 == className2) && (!item2.isLocked())){
            root.childs.add(new GameNode(item2.getCell(),item1.getCell()));
        }
    }
for (int i=0; i<root.childs.size();i++){
    FillNodes(cell,root.childs.get(i),stage,cellCount,root);
}

ArrayList<Cell> array1= root.GetSolution();
for(int i=0; i<array1.size();i++){
    Cell newcell = new Cell(array1.get(i));
    arrayList.add(GetItemAtCell(newcell,stage));
}
    return  arrayList;
}

private void FillNodes(Cell prevcell, GameNode gameNode,Stage stage, int cellCount, GameNode root){
    Cell cell = gameNode.cell;
    Item item1 = GetItemAtCell(cell, stage);
    Item item2;
    String className1 = item1.getClass().getName();
    String className2;

    //GameNode root = new GameNode(cell, prevcell);
    //Check left
    if (cell.getCol()>0){
        item2 = GetItemAtCell(new Cell(cell.getRow(),cell.getCol()-1), stage);
        className2 = item2.getClass().getName();
        if ((className1 == className2)&&(!root.isInNodes(item2.getCell()))){
            gameNode.childs.add(new GameNode(item2.getCell(),item1.getCell()));
        }
    }
    //Check right
    if (cell.getCol()<(cellCount-2)){
        item2 = GetItemAtCell(new Cell(cell.getRow(),cell.getCol()+1), stage);
        className2 = item2.getClass().getName();
        if ((className1 == className2)&&(!root.isInNodes(item2.getCell()))){
            gameNode.childs.add(new GameNode(item2.getCell(),item1.getCell()));
        }
    }
    //Check up
    if (cell.getRow()<(cellCount-2)){
        item2 = GetItemAtCell(new Cell(cell.getRow()+1,cell.getCol()), stage);
        className2 = item2.getClass().getName();
        if ((className1 == className2)&&(!root.isInNodes(item2.getCell()))){
            gameNode.childs.add(new GameNode(item2.getCell(),item1.getCell()));
        }
    }
    //Check down
    if (cell.getRow()>0){
        item2 = GetItemAtCell(new Cell(cell.getRow()-1,cell.getCol()), stage);
        className2 = item2.getClass().getName();
        if ((className1 == className2)&&(!root.isInNodes(item2.getCell()))){
            gameNode.childs.add(new GameNode(item2.getCell(),item1.getCell()));
        }
    }

    for (int i=0; i<gameNode.childs.size();i++){
        FillNodes(cell,gameNode.childs.get(i),stage,cellCount,root);
    }
}

public void PrintSolutions(ArrayList<ArrayList<Item>> arrayLists){
    ArrayList<Item> arrayList;
    for(int i=0; i<arrayLists.size();i++){
        arrayList = arrayLists.get(i);
        //System.out.println("Solution "+i+":");
        for (int j=0; j<arrayList.size();j++){
            //System.out.println(j+":  Col: "+arrayList.get(j).getCell().getCol()+" Row: "+arrayList.get(j).getCell().getRow());
        }
    }

}
public void changeGameCell(Cell cell){
    for(int i = 0;i<gameCells.size();i++){
        if(gameCells.get(i).getCell().isEqual(cell)){
            gameCells.get(i).unLock(cell);
        }
    }
}

    public void GenerateLevel(int levelnumber,  int CellCount) {
        //Generate cells

        int  gmplvl = cradleGame.getGameMapLevel();

            for (int i = 0; i < CellCount; i++) {
                for (int j = 0; j < CellCount; j++) {

                    switch (gmplvl){
                        case 1:
                            if (Math.random() < 0.5 && (j > CellCount / 4) && (j < CellCount / 1.5) && (i > CellCount / 4) && (i < CellCount / 1.5)) {
                                gameCells.get(i * CellCount + j).setLockLevel(1);
                            }
                            break;
                        case 2:
                            if (Math.random() < 0.08) {

                                gameCells.get(i * CellCount + j).setLockLevel(1);

                            }
                            break;
                        case 3:
                            if (Math.random() < 0.15) {
                                if (Math.random() < 0.7){
                                    gameCells.get(i * CellCount + j).setLockLevel(1);
                                } else {
                                    gameCells.get(i * CellCount + j).setLockLevel(2);
                                }

                            }
                            break;

                            default:
                                if (Math.random() < 0.15) {
                                    if (Math.random() < 0.5){
                                        gameCells.get(i * CellCount + j).setLockLevel(1);
                                    } else {
                                        gameCells.get(i * CellCount + j).setLockLevel(2);
                                    }

                                }
                    }


                }
            }

    }

    public  boolean CheckWin(){
    for (int i = 0;i<gameCells.size();i++){
        if(gameCells.get(i).getLockLevel()!=0){
            return false;
        }
    }
    return true;
    }
}
