package by.android.cradle;

import java.util.ArrayList;

public class SortableArrayListOfItems extends ArrayList<Item> implements Comparable<ArrayList<Item>>{

    @Override
    public int compareTo(ArrayList<Item> another) {
        if (this.size()<another.size()){
            return -1;
        }else{
            return 1;
        }
    }
}
