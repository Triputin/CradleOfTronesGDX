package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class SandGlass extends BaseActor{
   // float stateTime;
private float totalDuration;

public SandGlass(float x, float y, Stage s, int width, int height ,float duration, CradleGame cradleGame)
        {
            super(x,y,s, Touchable.disabled, cradleGame);
            this.totalDuration = duration;
            System.out.println("SandGlass Duration = "+duration);
            String[] filenames =
                    {"sandglass/sandglass01.png", "sandglass/sandglass02.png", "sandglass/sandglass03.png",
                            "sandglass/sandglass04.png", "sandglass/sandglass05.png", "sandglass/sandglass06.png",
                            "sandglass/sandglass07.png", "sandglass/sandglass08.png", "sandglass/sandglass09.png",
                            "sandglass/sandglass10.png", "sandglass/sandglass11.png", "sandglass/sandglass12.png",
                            "sandglass/sandglass13.png", "sandglass/sandglass14.png"
                    };

            loadAnimationFromFiles(filenames, duration/14, false,  width,  height);

        }


/*
    public void setDuration(float duration) {
        getAnimation().setFrameDuration(duration/14);

    }

    public float getDuration() {
        return getAnimation().getFrameDuration();
    }
*/
}
