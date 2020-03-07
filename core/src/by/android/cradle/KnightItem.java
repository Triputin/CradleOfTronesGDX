package by.android.cradle;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

public class KnightItem extends Item {


    private KnightItemParams knightItemParams;
    private KnightScreen knightScreen;
    private BlackMarketScreen blackMarketScreen;

    //Drag and Drop
    protected KnightItem self;
    private float grabOffsetX;
    private float grabOffsetY;
    protected DropTargetActor dropTarget;
    private boolean draggable;
    private float startPositionX;
    private float startPositionY;


    public KnightItem(float x, float y, int width, int height, Stage s, int row, int col, CradleGame cradleGame,KnightItemParams knightItemParams,KnightScreen knightScreen,BlackMarketScreen blackMarketScreen ) {
        super(x, y, width, height, s, Touchable.enabled, row, col, cradleGame);
        this.knightScreen = knightScreen;
        this.blackMarketScreen = blackMarketScreen;

        // Очень важно, т.е. предок в лице DropTargetActor игнорирует все и ставит Disabled и события перестают доходить!!
        setTouchable(Touchable.enabled);
        setTargetable(false);
        self = this;
        draggable = true;

        addListener(
                new InputListener()
                {
                    public boolean touchDown(InputEvent event, float offsetX, float offsetY,
                                             int pointer, int button)
                    {
                        if ( !self.isDraggable() ) return false;
                        self.grabOffsetX = offsetX;
                        self.grabOffsetY = offsetY;
                        self.toFront();
                        self.startPositionX = self.getX();
                        self.startPositionY = self.getY();
                        self.addAction( Actions.scaleTo(1.1f, 1.1f, 0.25f) );
                        self.onDragStart();
                        return true;
                    }
                    public void touchDragged(InputEvent event, float offsetX, float offsetY,
                                             int pointer)
                    {
                        float deltaX = offsetX - self.grabOffsetX; float deltaY = offsetY - self.grabOffsetY;
                        self.moveBy(deltaX, deltaY);
                    }
                    public void touchUp(InputEvent event, float offsetX, float offsetY,
                                        int pointer, int button)
                    {
                        self.setDropTarget(null);
                        // keep track of distance to closest object
                        //float closestDistance = Float.MAX_VALUE;
                        float closestDistance = 100;
                        ArrayList<BaseActor> list = BaseActor.getList(self.getStage(), "by.android.cradle.DropTargetActor");
                        if (list!=null) {
                            for (BaseActor actor : list) {
                                DropTargetActor target = (DropTargetActor) actor;
                                if (target.isTargetable() && self.overlaps(target)){
                                    switch (target.getDropPlaceType()){
                                        case 1: // KnightScreen
                                            if (target.getTargetType()==self.knightItemParams.getKnightItemType().getValue()) {
                                                float currentDistance =
                                                        Vector2.dst(self.getX(), self.getY(), target.getX(), target.getY());
                                                // check if this target is even closer
                                                if (currentDistance < closestDistance) {
                                                    self.setDropTarget(target);
                                                    closestDistance = currentDistance;
                                                }
                                            }
                                            break;
                                        case 2:
                                            float currentDistance =
                                                    Vector2.dst(self.getX(), self.getY(), target.getX(), target.getY());
                                            // check if this target is even closer
                                            if (currentDistance < closestDistance) {
                                                self.setDropTarget(target);
                                                closestDistance = currentDistance;
                                            }
                                            break;

                                        case 3:

                                                    self.setDropTarget(target);


                                                break;
                                    }

                                }
                            }
                        }

                        self.addAction( Actions.scaleTo(1.00f, 1.00f, 0.25f) );
                        self.onDrop();

                    }
                }
        );


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
                        loadTexture("knightitems/boots01.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 2:
                        loadTexture("knightitems/boots02.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 3:
                        loadTexture("knightitems/boots03.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 4:
                        loadTexture("knightitems/boots01_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 5:
                        loadTexture("knightitems/boots02_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 6:
                        loadTexture("knightitems/boots03_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 7:
                        loadTexture("knightitems/boots01_top.png", (int) getWidth(), (int) getHeight());
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
                        loadTexture("knightitems/gloves01.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 2:
                        loadTexture("knightitems/gloves02.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 3:
                        loadTexture("knightitems/gloves03.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 4:
                        loadTexture("knightitems/gloves01_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 5:
                        loadTexture("knightitems/gloves02_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 6:
                        loadTexture("knightitems/gloves03_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 7:
                        loadTexture("knightitems/gloves01_top.png", (int) getWidth(), (int) getHeight());
                        break;

                }
                break;
            case Shield:
                switch (knightItemParams.getKnightItemSubType()){
                    case 1:
                        loadTexture("knightitems/shield01.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 2:
                        loadTexture("knightitems/shield02.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 3:
                        loadTexture("knightitems/shield03.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 4:
                        loadTexture("knightitems/shield01_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 5:
                        loadTexture("knightitems/shield02_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 6:
                        loadTexture("knightitems/shield03_gold.png", (int) getWidth(), (int) getHeight());
                        break;
                    case 7:
                        loadTexture("knightitems/shield01_top.png", (int) getWidth(), (int) getHeight());
                        break;

                }
                break;
        }


        //selectedBaseActor = new BaseActor(0,0,s,Touchable.disabled);
        //selectedBaseActor.AddImage("shield.png", 50,50,50, 50);

    }

    //Always locked so you can't select it in the field

    @Override
    public boolean isLocked(){
            return true;
    }


    public KnightItemParams getKnightItemParams() {
        return knightItemParams;
    }
/*
    @Override
    public void setSelected(boolean selected, Item prev) {
        if(selected){
            lockImage01 = AddImage("bread_frosen.png",0,0, (int) getWidth(), (int) getHeight());

        }else {
            if (lockImage01 !=null) {
                lockImage01.remove();
            }

        }

    }

*/

    public boolean hasDropTarget()
    { return (dropTarget != null); }

    public void setDropTarget(DropTargetActor dt) { dropTarget = dt; }

    public DropTargetActor getDropTarget()
    {  return dropTarget;  }

    public void act(float dt)
    {
        super.act(dt);
    }

    public void setDraggable(boolean d) { draggable = d; }
    public boolean isDraggable() {  return draggable;  }
    public void moveToActor(BaseActor other)
    {
        float x = other.getX() + (other.getWidth() - this.getWidth()) / 2; float y = other.getY() + (other.getHeight() - this.getHeight()) / 2; addAction( Actions.moveTo(x,y, 0.50f, Interpolation.pow3) );
    }
    public void moveToStart()
    {
        addAction( Actions.moveTo(startPositionX, startPositionY, 0.50f, Interpolation.pow3) );
    }

    public void onDragStart() {

        if (blackMarketScreen!=null){
            if (getKnightItemParams().getAddCellsQttyToDestroy()!=0) {
                blackMarketScreen.getMightLabel().setText("+" + String.valueOf(getKnightItemParams().getAddCellsQttyToDestroy()));
            }
            if(getKnightItemParams().getAddHealth()!=0) {
                blackMarketScreen.getLifeLabel().setText("+" + String.valueOf(getKnightItemParams().getAddHealth()));
            }
            if(getKnightItemParams().getAddRechargeWeaponTime()!=0) {
                blackMarketScreen.getSpeedLabel().setText("-" + String.valueOf(getKnightItemParams().getAddRechargeWeaponTime()));
            }
        }

    }

    public void onDrop() {

        if (blackMarketScreen!=null){
            blackMarketScreen.getMightLabel().setText("");
            blackMarketScreen.getLifeLabel().setText("");
            blackMarketScreen.getSpeedLabel().setText("");
        }

        if (!hasDropTarget()){
            moveToStart();
            return;
        }
        // check if place is in Knights screen (KnightActiveItemPlace)
        if(dropTarget.getDropPlaceType()==1){
            self.centerAtActor(dropTarget);
            knightScreen.moveToActiveItemParams(self);
            return;
        }
        // check if place is in black market screen (KnightItemShopPlace)
        if(dropTarget.getDropPlaceType()==2){

           int itemPrice = knightItemParams.getPrice();
            if(  GameRes.Gold > itemPrice) {
                //self.centerAtActor(dropTarget);
                KnightItemShopPlace knightItemShopPlace = (KnightItemShopPlace)dropTarget;
                cradleGame.setGameResGold(GameRes.Gold - itemPrice);
                cradleGame.getKnightParams().addKnightItemParams(self.getKnightItemParams());
                self.remove();
            } else {
                moveToStart();
                self.setDropTarget(null);
            }
            return;
        }

        // if dropped to slider in Knights screen
        if(dropTarget.getDropPlaceType()==3){
            knightScreen.moveToPassiveItemParams(self);
            return;
        }

    }

}
