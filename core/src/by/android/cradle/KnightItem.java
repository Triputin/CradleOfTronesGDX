package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class KnightItem extends Item {

private KnightItemParams knightItemParams;

    public KnightItem(float x, float y, int width, int height, Stage s, int row, int col, CradleGame cradleGame,KnightItemParams knightItemParams ) {
        super(x, y, width, height, s, Touchable.disabled, row, col, cradleGame);
        setSelected(false, null);
        setBoundaryPolygon(8);
        this.knightItemParams = knightItemParams;
        switch(knightItemParams.getKnightItemType()){
            case Helmet:
                switch (knightItemParams.getKnightItemSubType()){
                    case 1:
                        loadTexture("knightitems/helmet01.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 2:
                        loadTexture("knightitems/helmet02.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 3:
                        loadTexture("knightitems/helmet03.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 4:
                        loadTexture("knightitems/helmet01_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 5:
                        loadTexture("knightitems/helmet02_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 6:
                        loadTexture("knightitems/helmet03_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 7:
                        loadTexture("knightitems/helmet01_top.png", (int) getWidth(), (int) getHeight());
                        break;
                }
                break;
            case Armor:
                switch (knightItemParams.getKnightItemSubType()){
                    case 1:
                        loadTexture("knightitems/armour01.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 2:
                        loadTexture("knightitems/armour02.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 3:
                        loadTexture("knightitems/armour03.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 4:
                        loadTexture("knightitems/armour01_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 5:
                        loadTexture("knightitems/armour02_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 6:
                        loadTexture("knightitems/armour03_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 7:
                        loadTexture("knightitems/armour01_top.png", (int) getWidth(), (int) getHeight());
                        break;
                }
                break;
            case Boots:
                switch (knightItemParams.getKnightItemSubType()){
                    case 1:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 2:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 3:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 4:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 5:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 6:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 7:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                }
                break;
            case Sword:
                switch (knightItemParams.getKnightItemSubType()){
                    case 1:
                        loadTexture("knightitems/sword01.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 2:
                        loadTexture("knightitems/sword02.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 3:
                        loadTexture("knightitems/sword03.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 4:
                        loadTexture("knightitems/sword01_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 5:
                        loadTexture("knightitems/sword02_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 6:
                        loadTexture("knightitems/sword03_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 7:
                        loadTexture("knightitems/sword01_top.png", (int) getWidth(), (int) getHeight());
                        break;
                }
                break;
            case Gloves:
                switch (knightItemParams.getKnightItemSubType()){
                    case 1:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 2:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 3:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 4:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 5:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 6:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 7:
                        loadTexture("knightitems/glove01_top.png", (int) getWidth(), (int) getHeight());
                        break;

                }
                break;
            case Shield:
                switch (knightItemParams.getKnightItemSubType()){
                    case 1:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 2:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 3:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 4:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 5:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 6:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 7:
                        loadTexture("gem01pressed.png", (int) getWidth(), (int) getHeight());
                        break;

                }
                break;
        }

    }

    //Always locked so you can't select it in the field
    @Override
    public boolean isLocked(){
            return true;
    }


}
