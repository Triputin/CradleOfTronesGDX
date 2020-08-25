package by.android.cradle;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class EnemyKnight extends BaseActor {

    private BaseActor healthKnightActor;
    private BaseActor healthEnemyKnightActor;
    private KnightParams knightParams;

    public EnemyKnight(float x, float y, Stage s, int width, int height , final CradleGame cradleGame, KnightParams knightParams) {
        super(x, y, s, Touchable.enabled, cradleGame);
        this.setSize(width, height);
        setBoundaryPolygon(8);
        this.knightParams=knightParams;
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


        showHealth();

    }

    public void reDraw(){
        showHealth();

    }

    private void showHealth(){
        int n = Math.round(10-10f*knightParams.getHealth()/knightParams.getCurrentHealthMaximum());
        healthKnightActor.setElapsedTime(n);
        healthEnemyKnightActor.setElapsedTime(n);
        //System.out.println("showHealth: setElapsedTime = "+ n);
        //System.out.println("knightParams.getHealth()= " + knightParams.getHealth());
    }

}
