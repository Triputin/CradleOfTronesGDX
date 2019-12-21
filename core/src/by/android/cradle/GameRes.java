package by.android.cradle;

public class GameRes {
    public static int Gold=0;
    public static int Wood=0;
    public static int Bread=0;

    public static boolean isResEnough(KingdomRes kingdomRes){
        if (kingdomRes.Gold>Gold) return false;
        if (kingdomRes.Bread>Bread) return false;
        if (kingdomRes.Wood>Wood) return false;
        return true;
    }
}
