package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MessageActor01 extends BaseActor {
    private final KingdomRes kingdomRes;
    private ResultsActorForAttack resultsActorForAttack;
    private AttackType attackType;
    Kingdom attackedKingdom;

    public MessageActor01(float x, float y, Stage s, int width, int height , final CradleGame cradleGame)
    {
        super(x,y,s, Touchable.enabled);
        attackType = AttackType.AttackKingdom;
        this.kingdomRes = new KingdomRes();
        loadTexture( "goldenframe.png",width,height );
        AddImage("fon_white.png",Math.round(width*0.075f),Math.round(height*0.1f),Math.round(width*0.85f), Math.round(height*0.8f));

        resultsActorForAttack = new ResultsActorForAttack(x+width*0.1f,(int)(y+height/2),(int)(width-width*0.2f),(int)(height/2.5),s,Touchable.disabled,this);

        //Attack Button
        String ms = cradleGame.getLanguageStrings().get("attack");
        TextButton attackButton = new TextButton( ms, BaseGame.textButtonStyle );
        attackButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                if (!GameRes.isResEnough(kingdomRes)) {

                    return false;}
                if (attackType == AttackType.AttackKingdom) {
                    GameRes.Gold -= kingdomRes.Gold;
                    GameRes.Wood -= kingdomRes.Wood;
                    GameRes.Bread -= kingdomRes.Bread;
                }
                cradleGame.setActivescreenGamePlay(attackType,attackedKingdom);
                //cradleGame.getScreenGamePlay().UpdateRes();
                //cradleGame.getScreenGamePlay().StartNewLevel();
                return true;
            }
        });
        attackButton.setPosition(Math.round(width*0.08f),20);
        addActor(attackButton);

        //Cancel Button
        ms = cradleGame.getLanguageStrings().get("cancel");
        TextButton cancelButton = new TextButton( ms, BaseGame.textButtonStyle );
        cancelButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                cradleGame.setActiveGameMapScreen();
                return true;
            }
        });
        cancelButton.setPosition(Math.round(width*0.52f),20);
        addActor(cancelButton);

    }

    public void SetRes(KingdomRes kingdomRes){
        this.kingdomRes.Bread = kingdomRes.Bread;
        this.kingdomRes.Gold = kingdomRes.Gold;
        this.kingdomRes.Wood = kingdomRes.Wood;
        resultsActorForAttack.UpdateRes(kingdomRes);
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }
    public Kingdom getAttackedKingdom() {
        return attackedKingdom;
    }

    public void setAttackedKingdom(Kingdom attackedKingdom) {
        this.attackedKingdom = attackedKingdom;
    }

}
