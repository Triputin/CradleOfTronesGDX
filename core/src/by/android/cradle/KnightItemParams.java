package by.android.cradle;


import com.badlogic.gdx.Preferences;

public class KnightItemParams {
    private int itemLevel;
    private int addHealth;
    private float addCellsQttyToDestroy;
    private int addRechargeWeaponTime;
    private KnightItemType knightItemType; // for choosing right place at Hero
    private int knightItemSubType;         // Type among simular items
    private int price;

    public KnightItemParams (KnightItemType knightItemType,int knightItemSubType, int itemLevel, int addHealth, float addCellsQttyToDestroy,int addRechargeWeaponTime){
        this.addHealth = addHealth;
        this.addCellsQttyToDestroy = addCellsQttyToDestroy;
        this.addRechargeWeaponTime = addRechargeWeaponTime;
        this.itemLevel = itemLevel;
        this.knightItemType = knightItemType;
        this.knightItemSubType = knightItemSubType;
        this.price = KnightItemParams.getKnightItemPrice(itemLevel, knightItemSubType);

    }

    public void save(Preferences preferences, int itemNumber){
        preferences.putInteger("itemLevel"+itemNumber, itemLevel);
        preferences.putInteger("addHealth"+itemNumber, addHealth);
        preferences.putFloat("addCellsQttyToDestroy"+itemNumber, addCellsQttyToDestroy);
        preferences.putInteger("addRechargeWeaponTime"+itemNumber, addRechargeWeaponTime);
        preferences.putInteger("knightItemType"+itemNumber, knightItemType.getValue());
        preferences.putInteger("knightItemSubType"+itemNumber, knightItemSubType);
        preferences.putInteger("knightItemPrice"+itemNumber, price);
    }

    public void load(Preferences preferences, int itemNumber){
        itemLevel = preferences.getInteger("itemLevel"+itemNumber,1);
        addHealth = preferences.getInteger("addHealth"+itemNumber, 0);
        addCellsQttyToDestroy = preferences.getFloat("addCellsQttyToDestroy"+itemNumber, 0);
        addRechargeWeaponTime = preferences.getInteger("addRechargeWeaponTime"+itemNumber, 0);
        knightItemType = KnightItemType.valueOf(preferences.getInteger("knightItemType"+itemNumber, 1));
        knightItemSubType = preferences.getInteger("knightItemSubType"+itemNumber, 1);
        price = preferences.getInteger("knightItemPrice"+itemNumber, 0);
        if (price == 0){
            price = KnightItemParams.getKnightItemPrice(itemLevel,knightItemSubType);
        }
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

    public static KnightItemParams generateRandomKnightItemParamsForMarket(){
        float rnd = (float) Math.random();
        int itemMight = 0;
        int itemHealth = 0;
        int itemSpeed = 0;
        KnightItemParams knightItemParams = null;
        KnightItemType knightItemType = KnightItemType.Sword;

            rnd = (float) Math.random();
            //Choose KnightItemType
            if (rnd<0.15f) {
                knightItemType = KnightItemType.Sword;
                itemMight=1;
            } else
            if (rnd<0.3f) {
                knightItemType = KnightItemType.Helmet;
                itemHealth=1;
            } else
            if (rnd<0.45f) {
                knightItemType = KnightItemType.Armor;
                itemHealth=1;
            } else
            if (rnd<0.6f) {
                knightItemType = KnightItemType.Boots;
                itemSpeed=1;
            } else
            if (rnd<0.75f) {
                knightItemType = KnightItemType.Gloves;
                itemSpeed=1;
            } else
            {
                knightItemType = KnightItemType.Shield;
                itemMight=1;
            }



        //Choose KnightItemParams
        if (rnd<0.1f){
            knightItemParams = new KnightItemParams(knightItemType,1,1, 5*itemHealth,0.1f*itemMight,1*itemSpeed);
        }else if (rnd<0.2f){
            knightItemParams = new KnightItemParams(knightItemType,2,1,10*itemHealth,0.2f*itemMight,2*itemSpeed);

        } else if (rnd<0.3f){
            knightItemParams = new KnightItemParams(knightItemType,3,1,15*itemHealth,0.3f*itemMight,3*itemSpeed);

        } else if (rnd<0.35f){
            knightItemParams = new KnightItemParams(knightItemType,4,2,20*itemHealth,0.5f*itemMight,4*itemSpeed);
        } else if (rnd<0.4f) {
            knightItemParams = new KnightItemParams(knightItemType, 5, 2, 25 * itemHealth, 0.6f * itemMight, 5 * itemSpeed);

        } else if (rnd<0.45f){
            knightItemParams = new KnightItemParams(knightItemType,6,2,30*itemHealth,0.7f*itemMight,6*itemSpeed);

        } else if (rnd<0.5f){
            knightItemParams = new KnightItemParams(knightItemType,7,3,50*itemHealth,2.0f*itemMight,10*itemSpeed);

        }


        if(knightItemParams == null) {
            knightItemParams = new KnightItemParams(knightItemType, 1, 1, 5 * itemHealth, 0.1f * itemMight, 1 * itemSpeed);
        }

        return knightItemParams;

    }

    private static int getKnightItemPrice(int itemLevel, int itemSubLevel){

        int pr = 1000;
        switch(itemLevel){
            case 1: pr=500*itemSubLevel;
            break;
            case 2: pr = 1000*(itemSubLevel-3);
            break;
            case 3: pr = 5000*(itemSubLevel-6);
            break;

        }
        return pr;

    }

    public int getPrice() {
        return price;
    }
}
