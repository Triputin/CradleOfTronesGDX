package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LevelOfHardnessDialogBox extends DialogBox {
    Button easyButton;
    Button middleButton;
    Button hardButton;

    public LevelOfHardnessDialogBox(float x, float y, Stage s, int width, int height, CradleGame cradleGame1) {
        super(x, y, s, width, height, cradleGame1);


        String ms = cradleGame1.getLanguageStrings().get("easy");
        easyButton = new TextButton( ms, BaseGame.textButtonStyleCheck );
        easyButton.setPosition(Math.round(getWidth()/4-easyButton.getWidth()/2),height*0.5f);
        addActor(easyButton);

        ms = cradleGame1.getLanguageStrings().get("middle");
        middleButton = new TextButton( ms, BaseGame.textButtonStyleCheck );
        middleButton.setPosition(Math.round(getWidth()/4*2-middleButton.getWidth()/2),height*0.5f);
        addActor(middleButton);
        middleButton.setChecked(true);

        ms = cradleGame1.getLanguageStrings().get("hard");
        hardButton = new TextButton( ms, BaseGame.textButtonStyleCheck );
        hardButton.setPosition(Math.round(getWidth()/4*3-hardButton.getWidth()/2),height*0.5f);
        addActor(hardButton);

        //initalize stage and all your buttons
        ButtonGroup buttonGroup = new ButtonGroup(easyButton, middleButton, hardButton);
        //next set the max and min amount to be checked
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setMinCheckCount(1);

        ms = cradleGame1.getLanguageStrings().get("choosedifficultylevel");
        setText(ms);
        alignTop();

    }

public int getSelectedDifficultyLevel(){

        int diflvl = 2;
     if(easyButton.isChecked()){diflvl=1;}
     if(middleButton.isChecked()){diflvl=2;}
     if(hardButton.isChecked()){diflvl=3;}
        return diflvl;
}

}
