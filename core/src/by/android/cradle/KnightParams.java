package by.android.cradle;

import com.badlogic.gdx.Preferences;
import java.util.ArrayList;

public class KnightParams {
    private final int MAX_Cell_QTTY_TO_DESTROY = 9;
    private final int FULL_HEALTH = 100; // default fo restart and start
    private int lifes;
    private int health;
    private int currentHealthMaximum; // Can be raised up with Knight levels
    private int knightType;
    private int rechargeWeaponTime;
    private float cellsQttyToDestroy; // qtty cells from leftdown cell to destroy including start cell
    private int knightLevel;

    private ArrayList<KnightItemParams> activeKnightItemParamsArrayList; // set of Knight's active accessories
    private ArrayList<KnightItemParams> passiveKnightItemParamsArrayList; // set of Knight's passive accessories

    private Preferences prefs;

    public KnightParams (Preferences prefs){
        this.prefs = prefs;
        activeKnightItemParamsArrayList = new ArrayList<>();
        passiveKnightItemParamsArrayList = new ArrayList<>();
        load();
    }

    private void load(){
        lifes = prefs.getInteger("lifes", 3);
        health = prefs.getInteger("health", FULL_HEALTH);
        knightType =  prefs.getInteger("knighttype", 1);
        rechargeWeaponTime =  prefs.getInteger("rechargeWeaponTime", 20);
        cellsQttyToDestroy =  prefs.getFloat("cellsQttyToDestroy", 1);
        knightLevel =  prefs.getInteger("knightLevel", 1);
        currentHealthMaximum =  prefs.getInteger("currentHealthMaximum", FULL_HEALTH);

        int qttyOfActiveKnightItemParams = prefs.getInteger("qttyOfActiveKnightItemParams", 0);
        activeKnightItemParamsArrayList.clear();
        for (int i=0; i<qttyOfActiveKnightItemParams;i++){
            KnightItemParams knightItemParams = new KnightItemParams(KnightItemType.Helmet,1,1,
                    0,0,0);

            knightItemParams.load(prefs,i);
            activeKnightItemParamsArrayList.add(knightItemParams);
        }

        int qttyOfPassiveKnightItemParams = prefs.getInteger("qttyOfPassiveKnightItemParams", 0);
        passiveKnightItemParamsArrayList.clear();
        for (int i=0; i<qttyOfPassiveKnightItemParams;i++){
            KnightItemParams knightItemParams = new KnightItemParams(KnightItemType.Helmet,1,1,
                    0,0,0);

            knightItemParams.load(prefs,i+100);
            passiveKnightItemParamsArrayList.add(knightItemParams);
        }

    }

    public void save(){
        prefs.putInteger("lifes",lifes);
        prefs.putInteger("health",health);
        prefs.putInteger("knighttype",knightType);
        prefs.putInteger("rechargeWeaponTime",rechargeWeaponTime);
        prefs.putFloat("cellsQttyToDestroy",cellsQttyToDestroy);
        prefs.putInteger("knightLevel",knightLevel);
        prefs.putInteger("currentHealthMaximum",currentHealthMaximum);
        prefs.putInteger("qttyOfActiveKnightItemParams",activeKnightItemParamsArrayList.size());
        prefs.putInteger("qttyOfPassiveKnightItemParams",passiveKnightItemParamsArrayList.size());
        int i=0;
        for(KnightItemParams knightItemParams: activeKnightItemParamsArrayList){
            knightItemParams.save(prefs,i);
            i++;
        }
        i=100;
        for(KnightItemParams knightItemParams: passiveKnightItemParamsArrayList){
            knightItemParams.save(prefs,i);
            i++;
        }
        prefs.flush();
    }

    public void reset(){
        moveAllItemsToPassive();
        lifes=3;
        health = FULL_HEALTH;
        knightType =1;
        rechargeWeaponTime = 20;
        cellsQttyToDestroy = 1;
        knightLevel = 1;
        setCurrentHealthMaximum(health);
        save();
    }

    public void moveAllItemsToPassive(){
        for(KnightItemParams knightItemParams: activeKnightItemParamsArrayList){
            passiveKnightItemParamsArrayList.add(knightItemParams);
            activeKnightItemParamsArrayList.remove(knightItemParams);
        }
    }


    public void addKnightItemParams(KnightItemParams knightItemParams){
        passiveKnightItemParamsArrayList.add(knightItemParams);
    }

    public void removeKnightItemParams(KnightItemParams knightItemParams){
        passiveKnightItemParamsArrayList.remove(knightItemParams);
    }

    public void moveToActiveItemParams(KnightItemParams knightItemParams){
        // move to passive from active item of the same type
        KnightItemType knightItemType = knightItemParams.getKnightItemType();
        for(KnightItemParams knightItemParams1: activeKnightItemParamsArrayList){
            if(knightItemParams1.getKnightItemType()==knightItemType){
                passiveKnightItemParamsArrayList.add(knightItemParams1);
                removeFromActiveItemParams(knightItemParams1);
            }
        }

        //add to active
        addToActiveItemParams(knightItemParams);
    }

    public void addToActiveItemParams(KnightItemParams knightItemParams) {
        activeKnightItemParamsArrayList.add(knightItemParams);
        setCurrentHealthMaximum(getCurrentHealthMaximum() + knightItemParams.getAddHealth());
        setCellsQttyToDestroy(getCellsQttyToDestroy() + knightItemParams.getAddCellsQttyToDestroy());
        setRechargeWeaponTime(getRechargeWeaponTime() - knightItemParams.getAddRechargeWeaponTime());
    }

    public void removeFromActiveItemParams(KnightItemParams knightItemParams){
        activeKnightItemParamsArrayList.remove(knightItemParams);
        setCurrentHealthMaximum(getCurrentHealthMaximum() - knightItemParams.getAddHealth());
        setCellsQttyToDestroy(getCellsQttyToDestroy() - knightItemParams.getAddCellsQttyToDestroy());
        setRechargeWeaponTime(getRechargeWeaponTime() + knightItemParams.getAddRechargeWeaponTime());
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getKnightType() {
        return knightType;
    }

    public void setKnightType(int knightType) {
        this.knightType = knightType;
    }

    public int getRechargeWeaponTime() {
        return rechargeWeaponTime;
    }

    public void setRechargeWeaponTime(int rechargeWeaponTime) {
        this.rechargeWeaponTime = rechargeWeaponTime;
    }

    public float getCellsQttyToDestroy() {
        return cellsQttyToDestroy;
    }

    public int getFinalCellsQttyToDestroy() {
        return Math.round(cellsQttyToDestroy);
    }

    public void setCellsQttyToDestroy(float cellsQttyToDestroy) {
        if (cellsQttyToDestroy<MAX_Cell_QTTY_TO_DESTROY){
            this.cellsQttyToDestroy = cellsQttyToDestroy;
        } else {
            this.cellsQttyToDestroy=MAX_Cell_QTTY_TO_DESTROY;
        }
    }

    public void incCellsQttyToDestroy() {
        if (cellsQttyToDestroy<(MAX_Cell_QTTY_TO_DESTROY-1)){
            cellsQttyToDestroy++;
        } else {
            this.cellsQttyToDestroy=MAX_Cell_QTTY_TO_DESTROY;
        }
    }

    public void doDamage(){
        health -=10;
        if (health<=0){
            lifes--;
            health=currentHealthMaximum;
        }
    }

    public void addHealth(){
        if (health<currentHealthMaximum){
            health +=2;
        }
    }

    public int getLifes() {
        return lifes;
    }

    public void incLevel(){
       knightLevel++;
        incCellsQttyToDestroy();
    }

    public int getKnightLevel() {
        return knightLevel;
    }

    public int getCurrentHealthMaximum() {
        return currentHealthMaximum;
    }

    public void setCurrentHealthMaximum(int currentHealthMaximum) {
        this.currentHealthMaximum = currentHealthMaximum;
    }

    public void CheckKnightLevelAtScore(int score){
        float ff = score;
        setKnightLevel(1+(int)Math.sqrt(ff/1000f));
    }

    public void setKnightLevel(int knightLevel) {
        this.knightLevel = knightLevel;
        this.cellsQttyToDestroy = knightLevel;
    }

    public ArrayList<KnightItemParams> getActiveKnightItemParamsArrayList() {
        return activeKnightItemParamsArrayList;
    }

    public ArrayList<KnightItemParams> getPassiveKnightItemParamsArrayList() {
        return passiveKnightItemParamsArrayList;
    }
}
