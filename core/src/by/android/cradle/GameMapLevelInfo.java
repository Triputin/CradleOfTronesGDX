package by.android.cradle;

import java.util.ArrayList;

public class GameMapLevelInfo {
    private ArrayList<Boolean> isMapLevelWinned;
    private CradleGame cradleGame;

    public GameMapLevelInfo(CradleGame cradleGame){
        this.cradleGame = cradleGame;
        isMapLevelWinned = new ArrayList<>();
        loadParams();
    }

    private void loadParams(){
        isMapLevelWinned.clear();
        for(int i =0; i<cradleGame.MaxGameMapLevel;i++) {
            isMapLevelWinned.add(cradleGame.getPrefs().getBoolean("GameMapLevelInfo"+(i+1), false));
        }

    }

    private void saveParams(){
        for(int i =0; i<isMapLevelWinned.size();i++){
            cradleGame.getPrefs().putBoolean("GameMapLevelInfo"+(i+1), isMapLevelWinned.get(i));
        }
        cradleGame.getPrefs().flush();
    }

    public void setMapLevelWinned(int mapLevel){
        isMapLevelWinned.set(mapLevel-1,true);
        cradleGame.getPrefs().putBoolean("GameMapLevelInfo"+mapLevel, true);
        cradleGame.getPrefs().flush();
        System.out.println("setMapLevelWinned="+mapLevel);
    }

    public boolean isGameMapLevelWinned(int gameMapLevel){
        return isMapLevelWinned.get(gameMapLevel-1);
    }

    public void resetMapLevelsWinned(){
        for(int i =0; i<isMapLevelWinned.size();i++){
            cradleGame.getPrefs().putBoolean("GameMapLevelInfo"+(i+1), false);
        }
        cradleGame.getPrefs().flush();
    }
}
