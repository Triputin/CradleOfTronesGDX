package by.android.cradle;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Kingdom extends BaseActor {

    private KingdomNames kingdomNames;
    private KingdomRes kingdomRes;
    private int protectionState;

    public Kingdom(float x, float y, int width, int height, Stage s, Touchable touchable,KingdomNames kingdomNames)
    {
        super(x,y,s, touchable);
        int flagSize = height;

        this.kingdomNames = kingdomNames;
        kingdomRes = new KingdomRes();
        protectionState = 5;


       // Animation<TextureRegion> animation= loadAnimationFromSheet("kingdoms/flaganimation01.png", 2, 3, 0.1f, true,50,50);

        Animation<TextureRegion> animation;
        BaseActor baseActor;
        switch (kingdomNames){
            case Kingdom_of_the_North: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                String[] filenames =
                        { "flag_red/flag02.png", "flag_red/flag03.png",
                                "flag_red/flag04.png", "flag_red/flag05.png", "flag_red/flag06.png",
                                "flag_red/flag07.png", "flag_red/flag08.png", "flag_red/flag09.png",
                                "flag_red/flag10.png", "flag_red/flag11.png", "flag_red/flag12.png"
                        };

                animation = loadAnimationFromFiles(filenames, 0.1f, true,  flagSize,  flagSize);
                baseActor = new BaseActor((int) width/2.8f,(int) (height*0.5f),s,Touchable.enabled);
                baseActor.setAnimation(animation);
                addActor(baseActor);
                baseActor = new BaseActor((int) width/2.8f,(int) (height*0.5f),s,Touchable.enabled);
                animation = loadTexture("flag_red/flagbasement.png", (int) getWidth(), (int) getHeight());
                baseActor.setAnimation(animation);
                addActor(baseActor);

                loadTexture("kingdoms/winterfell.png", width, height);
                break;
            case Kingdom_of_the_Isles_and_Rivers: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                String[] filenames1 =
                        { "flag_yellow/flag02.png", "flag_yellow/flag03.png",
                                "flag_yellow/flag04.png", "flag_yellow/flag05.png", "flag_yellow/flag06.png",
                                "flag_yellow/flag07.png", "flag_yellow/flag08.png", "flag_yellow/flag09.png",
                                "flag_yellow/flag10.png", "flag_yellow/flag11.png", "flag_yellow/flag12.png"
                        };

                animation = loadAnimationFromFiles(filenames1, 0.1f, true,  flagSize,  flagSize);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                baseActor.setAnimation(animation);
                addActor(baseActor);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                animation = loadTexture("flag_yellow/flagbasement.png", (int) getWidth(), (int) getHeight());
                baseActor.setAnimation(animation);
                addActor(baseActor);

                loadTexture("kingdoms/winterfell.png", (int) getWidth(), (int) getHeight());
                break;
            case Kingdom_of_the_Mountain_and_the_Vale: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                String[] filenames2 =
                        { "flag_purple/flag02.png", "flag_purple/flag03.png",
                                "flag_purple/flag04.png", "flag_purple/flag05.png", "flag_purple/flag06.png",
                                "flag_purple/flag07.png", "flag_purple/flag08.png", "flag_purple/flag09.png",
                                "flag_purple/flag10.png", "flag_purple/flag11.png", "flag_purple/flag12.png"
                        };

                animation = loadAnimationFromFiles(filenames2, 0.1f, true,  flagSize,  flagSize);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                baseActor.setAnimation(animation);
                addActor(baseActor);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                animation = loadTexture("flag_purple/flagbasement.png", (int) getWidth(), (int) getHeight());
                baseActor.setAnimation(animation);
                addActor(baseActor);

                loadTexture("kingdoms/winterfell.png", (int) getWidth(), (int) getHeight());
                break;
            case Kingdom_of_the_Reach: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                String[] filenames3 =
                        { "flag_orange/flag02.png", "flag_orange/flag03.png",
                                "flag_orange/flag04.png", "flag_orange/flag05.png", "flag_orange/flag06.png",
                                "flag_orange/flag07.png", "flag_orange/flag08.png", "flag_orange/flag09.png",
                                "flag_orange/flag10.png", "flag_orange/flag11.png", "flag_orange/flag12.png"
                        };

                animation = loadAnimationFromFiles(filenames3, 0.1f, true,  flagSize,  flagSize);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                baseActor.setAnimation(animation);
                addActor(baseActor);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                animation = loadTexture("flag_orange/flagbasement.png", (int) getWidth(), (int) getHeight());
                baseActor.setAnimation(animation);
                addActor(baseActor);

                loadTexture("kingdoms/winterfell.png", (int) getWidth(), (int) getHeight());
                break;
            case Kingdom_of_the_Rock: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                String[] filenames4 =
                        { "flag_green/flag02.png", "flag_green/flag03.png",
                                "flag_green/flag04.png", "flag_green/flag05.png", "flag_green/flag06.png",
                                "flag_green/flag07.png", "flag_green/flag08.png", "flag_green/flag09.png",
                                "flag_green/flag10.png", "flag_green/flag11.png", "flag_green/flag12.png"
                        };

                animation = loadAnimationFromFiles(filenames4, 0.1f, true,  flagSize,  flagSize);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                baseActor.setAnimation(animation);
                addActor(baseActor);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                animation = loadTexture("flag_green/flagbasement.png", (int) getWidth(), (int) getHeight());
                baseActor.setAnimation(animation);
                addActor(baseActor);

                loadTexture("kingdoms/winterfell.png", (int) getWidth(), (int) getHeight());
                break;
            case Kingdom_of_the_Stormlands: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                String[] filenames5 =
                        { "flag_brown/flag02.png", "flag_brown/flag03.png",
                                "flag_brown/flag04.png", "flag_brown/flag05.png", "flag_brown/flag06.png",
                                "flag_brown/flag07.png", "flag_brown/flag08.png", "flag_brown/flag09.png",
                                "flag_brown/flag10.png", "flag_brown/flag11.png", "flag_brown/flag12.png"
                        };

                animation = loadAnimationFromFiles(filenames5, 0.1f, true,  flagSize,  flagSize);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                baseActor.setAnimation(animation);
                addActor(baseActor);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                animation = loadTexture("flag_brown/flagbasement.png", (int) getWidth(), (int) getHeight());
                baseActor.setAnimation(animation);
                addActor(baseActor);

                loadTexture("kingdoms/winterfell.png", (int) getWidth(), (int) getHeight());
                break;
            case Principality_of_Dorne: kingdomRes.Bread = 10;
                kingdomRes.Gold = 10;
                kingdomRes.Wood = 10;
                String[] filenames6 =
                        { "flag_blue/flag02.png", "flag_blue/flag03.png",
                                "flag_blue/flag04.png", "flag_blue/flag05.png", "flag_blue/flag06.png",
                                "flag_blue/flag07.png", "flag_blue/flag08.png", "flag_blue/flag09.png",
                                "flag_blue/flag10.png", "flag_blue/flag11.png", "flag_blue/flag12.png"
                        };

                animation = loadAnimationFromFiles(filenames6, 0.1f, true,  flagSize,  flagSize);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                baseActor.setAnimation(animation);
                addActor(baseActor);
                baseActor = new BaseActor((int) getWidth()/2.8f,(int) (getHeight()*0.5f),s,Touchable.enabled);
                animation = loadTexture("flag_blue/flagbasement.png", (int) getWidth(), (int) getHeight());
                baseActor.setAnimation(animation);
                addActor(baseActor);

                loadTexture("kingdoms/winterfell.png", (int) getWidth(), (int) getHeight());
                break;

        }
    }

    public KingdomRes getKingdomResForAttack(){


        return kingdomRes;
    }

}
