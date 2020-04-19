package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class Weapon extends DragAndDropActor {
    float X;
    float Y;
    Knight knight;


    public Weapon(float x, float y, int width, int height, Stage s, CradleGame cradleGame, Knight knight) {
        super(x, y, s,cradleGame);
        this.X=x;
        this.Y=y;
        setHeight(height);
        setWidth(width);
        loadTexture("knights/mace01.png", (int) getWidth(), (int) getHeight());
        //AddImage("mace01.png", 0,0,(int) getWidth(), (int) getHeight());
        this.knight = knight;
    }

    public void onDrop() {
        if (hasDropTarget()){
            System.out.println("mace");

            //self.remove();
            Action actions;
            actions = sequence(fadeOut(0.01f),Actions.moveTo(-500,-500,0.05f),Actions.delay(knight.getRechargeWeaponTime()),Actions.moveTo(X,Y,0.1f), fadeIn(0.5f));
            self.addAction(actions);
            //self.addAction(Actions.moveTo(X,Y,0.5f));


            if (hasDropTarget()) {
                System.out.println("mace has target");
                Item item = (Item) dropTarget;
                System.out.println("Drop target item row "+ item.getRow()+" col "+item.getCol());
                //cradleGame.getScreenGamePlay().RemoveAndFillSquare(item.getRow(),item.getCol(),squareSize);
                cradleGame.getScreenGamePlay().RemoveAndFillCells(item.getRow(),item.getCol(),knight.getCellsQttyToDestroy());
            }
        }
    }

}
