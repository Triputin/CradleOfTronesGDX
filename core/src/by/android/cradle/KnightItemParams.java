package by.android.cradle;


import com.badlogic.gdx.Preferences;

public class KnightItemParams {
    private int itemLevel;
    private int addHealth;
    private float addCellsQttyToDestroy;
    private int addRechargeWeaponTime;
    private KnightItemType knightItemType; // for choosing right place at Hero
    private int knightItemSubType;         // Type among simular items


    public KnightItemParams (KnightItemType knightItemType,int knightItemSubType, int itemLevel, int addHealth, float addCellsQttyToDestroy,int addRechargeWeaponTime){
        this.addHealth = addHealth;
        this.addCellsQttyToDestroy = addCellsQttyToDestroy;
        this.addRechargeWeaponTime = addRechargeWeaponTime;
        this.itemLevel = itemLevel;
        this.knightItemType = knightItemType;
        this.knightItemSubType = knightItemSubType;
    }

    public void save(Preferences preferences, int itemNumber){
        preferences.putInteger("itemLevel"+itemNumber, itemLevel);
        preferences.putInteger("addHealth"+itemNumber, addHealth);
        preferences.putFloat("addCellsQttyToDestroy"+itemNumber, addCellsQttyToDestroy);
        preferences.putInteger("addRechargeWeaponTime"+itemNumber, addRechargeWeaponTime);
        preferences.putInteger("knightItemType"+itemNumber, knightItemType.getValue());
        preferences.putInteger("knightItemSubType"+itemNumber, knightItemSubType);
    }

    public void load(Preferences preferences, int itemNumber){
        itemLevel = preferences.getInteger("itemLevel"+itemNumber,1);
        addHealth = preferences.getInteger("addHealth"+itemNumber, 0);
        addCellsQttyToDestroy = preferences.getFloat("addCellsQttyToDestroy"+itemNumber, 0);
        addRechargeWeaponTime = preferences.getInteger("addRechargeWeaponTime"+itemNumber, 0);
        knightItemType = KnightItemType.valueOf(preferences.getInteger("knightItemType"+itemNumber, 1));
        knightItemSubType = preferences.getInteger("knightItemSubType"+itemNumber, 1);
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

    public int getAddHealth() {
        return addHealth;
    }

    public void setAddHealth(int addHealth) {
        this.addHealth = addHealth;
    }

    public float getAddCellsQttyToDestroy() {
        return addCellsQttyToDestroy;
    }

    public void setAddCellsQttyToDestroy(float addCellsQttyToDestroy) {
        this.addCellsQttyToDestroy = addCellsQttyToDestroy;
    }

    public int getAddRechargeWeaponTime() {
        return addRechargeWeaponTime;
    }

    public void setAddRechargeWeaponTime(int addRechargeWeaponTime) {
        this.addRechargeWeaponTime = addRechargeWeaponTime;
    }

    public KnightItemType getKnightItemType() {
        return knightItemType;
    }

    public void setKnightItemType(KnightItemType knightItemType) {
        this.knightItemType = knightItemType;
    }

    public int getKnightItemSubType() {
        return knightItemSubType;
    }

    public void setKnightItemSubType(int knightItemSubType) {
        this.knightItemSubType = knightItemSubType;
    }
}
