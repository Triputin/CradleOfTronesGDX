package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


/*
 * Enables drag-and-drop functionality for actors.
 *
 */

public class DragAndDropActor extends BaseActor
{
    protected DragAndDropActor self;
    private float grabOffsetX;
    private float grabOffsetY;
    protected DropTargetActor dropTarget;
    private boolean draggable;
    private float startPositionX;
    private float startPositionY;

    public DragAndDropActor(float x, float y, Stage s)
    {
        super(x,y,s, Touchable.enabled);
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
                        float closestDistance =100;
                        for ( BaseActor actor : BaseActor.getList(self.getStage(), "by.android.cradle.DropTargetActor") ) {
                            DropTargetActor target = (DropTargetActor)actor;
                            if ( target.isTargetable() && self.overlaps(target) ) {
                                float currentDistance =
                                        Vector2.dst(self.getX(),self.getY(), target.getX(),target.getY());
                                // check if this target is even closer
                                if (currentDistance < closestDistance)
                                {
                                    self.setDropTarget(target);
                                    closestDistance = currentDistance; }
                            }
                        }

                        self.addAction( Actions.scaleTo(1.00f, 1.00f, 0.25f) );
                        self.onDrop();
                    }
                }
        );

    }

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

    public void onDragStart() {}
    public void onDrop() {

    }
}