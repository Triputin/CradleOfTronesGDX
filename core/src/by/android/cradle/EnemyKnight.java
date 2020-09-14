package by.android.cradle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class EnemyKnight extends BaseActor {

    private BaseActor healthKnightActor;
    private Label curKnightHealthLabel;
    private BaseActor healthEnemyKnightActor;
    private Label curEnemyHealthLabel;
    private KnightParams knightParams; //Player Knight params
    private KnightParamsForAttack curKnightParams; // Current Player Knight params
    private KnightParamsForAttack enemyKnightParamsForAttack; //Enemy Knight params
    private KnightParamsForAttack curEnemyKnightParamsForAttack; // Current enemy knight params

    public EnemyKnight(float x, float y, Stage s, int width, int height , final CradleGame cradleGame, KnightParams knightParams) {
        super(x, y, s, Touchable.enabled, cradleGame);
        this.setSize(width, height);
        setBoundaryPolygon(8);
        this.knightParams=knightParams;
        curKnightParams = new KnightParamsForAttack(knightParams.getCurrentHealthMaximum());
        enemyKnightParamsForAttack = new KnightParamsForAttack(1);
        curEnemyKnightParamsForAttack = new KnightParamsForAttack(1);

        AddImage("fon_white2.png", Math.round(width * 0.02f), Math.round(height * 0.02f), Math.round(width * .98f), Math.round(height * 0.98f));
        AddImage("knights/knight_01_in_frame.png",0,0,width/2,height);
        AddImage("knights/knight_enemy01_in_frame.png",width/2,0,width/2,height);

        String[] filenames =
                { "hearts/heart01.png", "hearts/heart02.png",
                        "hearts/heart03.png", "hearts/heart04.png", "hearts/heart05.png",
                        "hearts/heart06.png", "hearts/heart07.png", "hearts/heart08.png",
                        "hearts/heart09.png", "hearts/heart10.png"
                };

        Animation animation;
        int heartSize = Math.round(width*0.25f);
        animation = createAnimationFromFiles(filenames, 1.0f, false,  heartSize, heartSize);
        healthKnightActor = new BaseActor((width/2f - heartSize)*0.5f,heartSize*0.05f,s,Touchable.enabled,cradleGame);
        healthKnightActor.setAnimation(animation);
        healthKnightActor.setAnimationPaused(true);
        addActor(healthKnightActor);

        Animation animation2;
        animation2 = createAnimationFromFiles(filenames, 1.0f, false,  heartSize, heartSize);
        healthEnemyKnightActor = new BaseActor(width/2f+(width/2f - heartSize)*0.5f,heartSize*0.05f,s,Touchable.enabled,cradleGame);
        healthEnemyKnightActor.setAnimation(animation2);
        healthEnemyKnightActor.setAnimationPaused(true);
        addActor(healthEnemyKnightActor);





        curKnightHealthLabel = new Label(""+1000, BaseGame.labelStyle);
        //curKnightHealthLabel.setText(""+curKnightParams.HealthPoints);
        curKnightHealthLabel.setColor( Color.GOLDENROD );
        curKnightHealthLabel.setFontScale(2.0f);
        curKnightHealthLabel.setPosition( healthKnightActor.getX()+healthKnightActor.getWidth()*0.5f-curKnightHealthLabel.getWidth()*0.5f,healthKnightActor.getY()+healthKnightActor.getHeight()*0.5f-curKnightHealthLabel.getHeight()*0.5f);
        addActor(curKnightHealthLabel);

        curEnemyHealthLabel = new Label(""+100, BaseGame.labelStyle);
        //curEnemyHealthLabel.setText(""+curEnemyKnightParamsForAttack.HealthPoints);
        curEnemyHealthLabel.setColor( Color.GOLDENROD );
        curEnemyHealthLabel.setFontScale(2.0f);
        curEnemyHealthLabel.setPosition( healthEnemyKnightActor.getX()+healthEnemyKnightActor.getWidth()*0.5f-curEnemyHealthLabel.getWidth()*0.5f,healthEnemyKnightActor.getY()+healthEnemyKnightActor.getHeight()*0.5f-curEnemyHealthLabel.getHeight()*0.5f);
        addActor(curEnemyHealthLabel);

        showHealth();

    }


    public void doDamage(int damagePoints){

        curEnemyKnightParamsForAttack.HealthPoints-=damagePoints;
        if (curEnemyKnightParamsForAttack.HealthPoints<0){
            curEnemyKnightParamsForAttack.HealthPoints=0;
        }

        showHealth();
    }

    public void doDamageToHero(int damagePoints){

        curKnightParams.HealthPoints-=damagePoints;
        if (curKnightParams.HealthPoints<0){
            curKnightParams.HealthPoints=0;
        }

        showHealth();
    }

    private void showHealth(){
        int n = Math.round(10-10f*curKnightParams.HealthPoints/knightParams.getCurrentHealthMaximum());
        healthKnightActor.setElapsedTime(n);
        n = Math.round(10-10f*curEnemyKnightParamsForAttack.HealthPoints/enemyKnightParamsForAttack.HealthPoints);
        healthEnemyKnightActor.setElapsedTime(n);

        curKnightHealthLabel.setText(""+curKnightParams.HealthPoints);
        curEnemyHealthLabel.setText(""+curEnemyKnightParamsForAttack.HealthPoints);
        //System.out.println("showHealth: setElapsedTime = "+ n);
        //System.out.println("knightParams.getHealth()= " + knightParams.getHealth());
    }

    public void setKnightParamsForAttack(int healthPoints){
        enemyKnightParamsForAttack.HealthPoints = healthPoints;
        curEnemyKnightParamsForAttack.HealthPoints = healthPoints;
        curKnightParams.HealthPoints = knightParams.getCurrentHealthMaximum();
        showHealth();
    }

    public KnightParamsForAttack getCurKnightParams() {
        return curKnightParams;
    }

    public KnightParamsForAttack getCurEnemyKnightParamsForAttack() {
        return curEnemyKnightParamsForAttack;
    }


}
