package by.android.cradle;

import java.util.ArrayList;
import java.util.List;


public class GameNode {

    public Cell cell;
    public Cell previousCell;
    public ArrayList<GameNode> childs ;

    public GameNode(Cell cell, Cell previousCell)
    {
        this.cell = cell;
        this.previousCell = previousCell;
        childs = new ArrayList<>();
    }

    public ArrayList<Cell> GetSolution()
    {
        ArrayList<ArrayList<Cell>> arrayLists = GetAllBranches();
        ArrayList<Cell> arrayList= GetLongestBranch(arrayLists);
        return arrayList;
    }

private ArrayList<ArrayList<Cell>> GetAllBranches(){
    ArrayList<ArrayList<Cell>> arrayLists = new ArrayList<>();

    //for (int i=0; i<childs.size();i++){
        ArrayList<Cell> arrayListnew = new ArrayList<>();
        arrayListnew.add(cell);
        //arrayListnew.add(childs.get(i).cell);
        arrayLists.add(arrayListnew);
        GetAllBranchesForChild(arrayLists,this);
    //}

return arrayLists;
}

private void GetAllBranchesForChild(ArrayList<ArrayList<Cell>> arrayLists,GameNode gameNode){
    ArrayList<Cell> arrayList = arrayLists.get(arrayLists.size()-1);
   // arrayList.add(cell);
    for (int i=0; i<gameNode.childs.size();i++){
        ArrayList<Cell> arrayListnew = new ArrayList<>();
        for(int j=0;j<arrayList.size();j++){
            arrayListnew.add(new Cell(arrayList.get(j)));
        }
        arrayListnew.add(gameNode.childs.get(i).cell);
        arrayLists.add(arrayListnew);
        GetAllBranchesForChild(arrayLists,gameNode.childs.get(i));
    }

}

private ArrayList<Cell> GetLongestBranch(ArrayList<ArrayList<Cell>> arrayLists){
    ArrayList<Cell> arrayList = new ArrayList<>();
    int longestIndex=0;
    for (int i=0; i<arrayLists.size();i++){
        if(arrayLists.get(i).size()>longestIndex){
            longestIndex=i;
        }
    }
    return arrayLists.get(longestIndex);
}

public boolean isInNodes(Cell cell){
if (this.cell.isEqual(cell)){
    return true;
}
for(int i=0; i<childs.size();i++){
    if(childs.get(i).isInNodes(cell)){
                return true;
    }
}

return false;

}

}