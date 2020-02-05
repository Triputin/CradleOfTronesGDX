package by.android.cradle;

import com.badlogic.gdx.Preferences;

public class KnightParams {
    private final int MAX_Cell_QTTY_TO_DESTROY = 9;
    private final int FULL_HEALTH = 100;
    private int lifes;
    private int health;
    private int knightType;
    private int rechargeWeaponTime;
    private int cellsQttyToDestroy; // qtty cells from leftdown cell to destroy including start cell
    private int knightLevel;

    private Preferences prefs;

    public KnightParams (Preferences prefs){
        this.prefs = prefs;
        load();
    }

    private void load(){
        lifes = prefs.getInteger("lifes", 3);
        health = prefs.getInteger("health", FULL_HEALTH);
        knightType =  prefs.getInteger("knighttype", 1);
        rechargeWeaponTime =  prefs.getInteger("rechargeWeaponTime", 20);
        cellsQttyToDestroy =  prefs.getInteger("cellsQttyToDestroy", 1);
        knightLevel =  prefs.getInteger("knightLevel", 1);
    }

    public void save(){
        prefs.putInteger("lifes",lifes);
        prefs.putInteger("health",health);
        prefs.putInteger("knighttype",knightType);
        prefs.putInteger("rechargeWeaponTime",rechargeWeaponTime);
        prefs.putInteger("cellsQttyToDestroy",cellsQttyToDestroy);
        prefs.putInteger("knightLevel",knightLevel);
        prefs.flush();
    }

    public void reset(){
        lifes=3;
        prefs.putInteger("lifes",lifes);
        health = FULL_HEALTH;
        prefs.putInteger("health",health);
        knightType =1;
        prefs.putInteger("knighttype",knightType);
        rechargeWeaponTime = 20;
        prefs.putInteger("rechargeWeaponTime",rechargeWeaponTime);
        cellsQttyToDestroy = 1;
        prefs.putInteger("cellsQttyToDestroy",cellsQttyToDestroy);
        knightLevel = 1;
        prefs.putInteger("knightLevel",knightLevel);
        prefs.flush();
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

    public int getCellsQttyToDestroy() {
        return cellsQttyToDestroy;
    }

    public void setCellsQttyToDestroy(int cellsQttyToDestroy) {
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
            health=FULL_HEALTH;
        }
    }

    public void addHealth(){
        if (health<FULL_HEALTH){
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
}
