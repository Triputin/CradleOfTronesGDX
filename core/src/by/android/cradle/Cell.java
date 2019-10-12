package by.android.cradle;

public class Cell {
    private int Row;
    private int Col;

    public Cell(int row, int col){
        Row = row;
        Col = col;
    }
    public Cell(Cell cell){
        this.Row =cell.getRow();
        this.Col = cell.getCol();
    }
    public int getRow() {
        return Row;
    }

    public void setRow(int row) {
        Row = row;
    }

    public int getCol() {
        return Col;
    }

    public void setCol(int col) {
        Col = col;
    }

    public boolean isEqual(Cell o) {
        if( (o.getRow()==this.getRow())&& o.getCol()==this.getCol()){
            return true;
        }else return false;

    }
}
