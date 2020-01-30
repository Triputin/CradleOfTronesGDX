package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Knight extends BaseActor {
    int health;
    String name;
    KnightType knightType;
    int rechargeWeaponTime;

    public Knight(float x, float y, int width, int height, Stage s, KnightType knightType)
    {
        super(x,y,s, Touchable.enabled);
        setSize(width,height);
        setBoundaryPolygon(8);
        health=100;

        this.knightType = knightType;
        switch (knightType){
            case Lancaster:
                loadTexture("knights/knight01_full.png", (int) getWidth(), (int) getHeight());
                this.name= "J.Lancaster";
                rechargeWeaponTime=20;
                break;
            case Targot:
                loadTexture("knights/knight01_full.png", (int) getWidth(), (int) getHeight());
                this.name= "K.Targot";
                rechargeWeaponTime=20;
                break;
            case Star:
                loadTexture("knights/knight01_full.png", (int) getWidth(), (int) getHeight());
                this.name= "A.Star";
                rechargeWeaponTime=20;
                break;
        }
    }

    public int getRechargeWeaponTime() {
        return rechargeWeaponTime;
    }

    public void setRechargeWeaponTime(int rechargeWeaponTime) {
        this.rechargeWeaponTime = rechargeWeaponTime;
    }
}
