package by.android.cradle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;


public class Knight extends BaseActor {
    private Label lifesLabel;

    private String name;
    private KnightParams knightParams;
    private BaseActor healthActor;


    public Knight(float x, float y, int width, int height, Stage s, KnightParams knightParams, CradleGame cradleGame)
    {
        super(x,y,s, Touchable.enabled,cradleGame);
        setSize(width,height);
        setBoundaryPolygon(8);
        this.knightParams=knightParams;
        BaseActor baseActor = new BaseActor(0,0,s,Touchable.enabled,cradleGame);
        Animation animation;

        String[] filenames =
                { "hearts/heart01.png", "hearts/heart02.png",
                        "hearts/heart03.png", "hearts/heart04.png", "hearts/heart05.png",
                        "hearts/heart06.png", "hearts/heart07.png", "hearts/heart08.png",
                        "hearts/heart09.png", "hearts/heart10.png"
                };

        animation = createAnimationFromFiles(filenames, 1.0f, false,  Math.round(width*0.28f),Math.round(height*0.28f));
        healthActor = new BaseActor(width*0.54f,height*0.12f,s,Touchable.enabled,cradleGame);
        healthActor.setAnimation(animation);
        healthActor.setAnimationPaused(true);
        addActor(healthActor);
        showHealth();

        lifesLabel = new Label("  "+0, BaseGame.labelStyle);
        lifesLabel.setText(""+knightParams.getLifes());
        lifesLabel.setColor( Color.GOLDENROD );
        lifesLabel.setPosition( width*0.64f,height*0.21f);
        lifesLabel.setFontScale(2.0f);
        addActor(lifesLabel);


        switch (knightParams.getKnightType()){
            default:
                animation = loadTexture("knights/knight02_full.png", (int) getWidth(), (int) getHeight());
                this.name= "J.Lancaster";
                break;
            case 2:
                animation = loadTexture("knights/knight02_full.png", (int) getWidth(), (int) getHeight());
                this.name= "K.Targot";
                break;
            case 3:
                animation = loadTexture("knights/knight03_full.png", (int) getWidth(), (int) getHeight());
                this.name= "A.Star";
                break;
        }
        baseActor.setAnimation(animation);
        addActor(baseActor);

    }

    public int getRechargeWeaponTime() {
        return knightParams.getRechargeWeaponTime();
    }

    public void setRechargeWeaponTime(int rechargeWeaponTime) {
        knightParams.setRechargeWeaponTime(rechargeWeaponTime);
    }

    public int getCellsQttyToDestroy() {
        return knightParams.getFinalCellsQttyToDestroy();
    }


    public void doDamage(){
        System.out.println("doDamage called");
        knightParams.doDamage();
        lifesLabel.setText(String.valueOf(knightParams.getLifes()));
        showHealth();
    }

    public void addHealth(){
        knightParams.addHealth();
        showHealth();
    }

    private void showHealth(){
        int n = Math.round(10-10f*knightParams.getHealth()/knightParams.getCurrentHealthMaximum());
        healthActor.setElapsedTime(n);
        System.out.println("showHealth: setElapsedTime = "+ n);
        System.out.println("knightParams.getHealth()= " + knightParams.getHealth());
    }

    public void reDraw(){
        showHealth();
        lifesLabel.setText(String.valueOf(knightParams.getLifes()));
    }

    public void incLevel(){
        knightParams.incLevel();
    }

    public int getKnightLevel() {
        return knightParams.getKnightLevel();
    }

    public int getHealth() {
        return knightParams.getHealth();
    }

    public void setKnightParams(KnightParams knightParams) {
        this.knightParams = knightParams;
        showHealth();
    }

    public int calcKnightLevelAtScore(int score){
        float ff = score;
        return 1+(int)Math.sqrt(ff/1000f);
    }

    public void addKnightItem(KnightItem knightItem){
        KnightItemParams knightItemParams = knightItem.getKnightItemParams();
        KnightItemParams knightItemParams1 = new KnightItemParams(knightItemParams.getKnightItemType(),
                knightItemParams.getKnightItemSubType(),knightItemParams.getItemLevel(),
                knightItemParams.getAddHealth(),knightItemParams.getAddCellsQttyToDestroy(),
                knightItemParams.getAddRechargeWeaponTime());

        knightParams.addKnightItemParams(knightItemParams1);

    }

    public ArrayList<KnightItemParams> getActiveKnightItemParams(){
        return knightParams.getActiveKnightItemParamsArrayList();
    }

    public ArrayList<KnightItemParams> getPassiveKnightItemParams(){
        return knightParams.getPassiveKnightItemParamsArrayList();
    }

}
