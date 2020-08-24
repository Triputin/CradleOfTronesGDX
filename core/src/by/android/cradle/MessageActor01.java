package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import static by.android.cradle.KingdomNames.Kingdom_Main01;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class MessageActor01 extends BaseActor {
    private final KingdomRes kingdomRes;
    private ResultsActorForAttack resultsActorForAttack;
    private AttackType attackType;
    private Kingdom attackedKingdom;
    private TextButton attackButton;
    private TextButton cancelButton;
    private Label labelAttackCost;
    private Label labelResNotEnough01;
    private Label labelResNotEnough02;
    private Label labelmainkingdomattackisforbidden;
    private Button shopButton;
    private Button arenaButton;

    public MessageActor01(float x, float y, Stage s, int width, int height , final CradleGame cradleGame)
    {
        super(x,y,s, Touchable.enabled,cradleGame);
        attackType = AttackType.AttackKingdom;
        this.kingdomRes = new KingdomRes();
        loadTexture( "goldenframe04.png",width,height );
        AddImage("fon_white.png",Math.round(width*0.02f),Math.round(height*0.02f),Math.round(width*0.96f), Math.round(height*0.96f));

        BaseActor baseActorRes = new BaseActor(x,y,getStage(),Touchable.disabled,cradleGame);
        resultsActorForAttack = new ResultsActorForAttack(x+width*0.1f,(int)(y+height/2),(int)(width*0.8f),(int)(height*0.25f),s,Touchable.disabled,baseActorRes,cradleGame,BaseGame.labelStyle_Middle);
        this.addActor(baseActorRes);

        String ms = cradleGame.getLanguageStrings().get("attackcost");
        labelAttackCost = new Label(ms, BaseGame.labelStyle_Small);
        labelAttackCost.setWrap(true);
        labelAttackCost.setAlignment( Align.center );
        labelAttackCost.setPosition( Math.round(width*0.06f), Math.round(height*0.77f) );
        labelAttackCost.setWidth( width*0.9f );
        labelAttackCost.setColor(Color.GOLD);
        this.addActor(labelAttackCost);

        ms = cradleGame.getLanguageStrings().get("noresourecesforattack01");
        labelResNotEnough01 = new Label(ms, BaseGame.labelStyle_SuperSmall);
        labelResNotEnough01.setWrap(true);
        labelResNotEnough01.setAlignment( Align.center );
        labelResNotEnough01.setPosition( Math.round(width*0.06f), Math.round(height*0.77f) );
        labelResNotEnough01.setWidth( width*0.9f );
        labelResNotEnough01.setColor(Color.GOLD);
        this.addActor(labelResNotEnough01);
        labelResNotEnough01.setVisible(false);


        ms = cradleGame.getLanguageStrings().get("noresourecesforattack02");
        labelResNotEnough02 = new Label(ms, BaseGame.labelStyle_SuperSmall);
        labelResNotEnough02.setWrap(true);
        labelResNotEnough02.setAlignment( Align.center );
        labelResNotEnough02.setPosition( Math.round(width*0.06f), Math.round(height*0.25f) );
        labelResNotEnough02.setWidth( width*0.9f );
        labelResNotEnough02.setColor(Color.GOLD);
        this.addActor(labelResNotEnough02);
        labelResNotEnough02.setVisible(false);

        ms = cradleGame.getLanguageStrings().get("mainkingdomattackisforbidden");
        labelmainkingdomattackisforbidden = new Label(ms, BaseGame.labelStyle_SuperSmall);
        labelmainkingdomattackisforbidden.setWrap(true);
        labelmainkingdomattackisforbidden.setAlignment( Align.center );
        labelmainkingdomattackisforbidden.setPosition( Math.round(width*0.06f), Math.round(height*0.6f) );
        labelmainkingdomattackisforbidden.setWidth( width*0.9f );
        labelmainkingdomattackisforbidden.setColor(Color.GOLD);
        this.addActor(labelmainkingdomattackisforbidden);
        labelmainkingdomattackisforbidden.setVisible(false);


        //Shop button
        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();
        Texture buttonTex2 = new Texture( Gdx.files.internal("shop_button.png") );
        TextureRegion buttonRegion2 =  new TextureRegion(buttonTex2);
        buttonStyle2.up = new TextureRegionDrawable( buttonRegion2 );
        shopButton = new Button( buttonStyle2 );
        shopButton.setSize(Math.round(height*0.25f),Math.round(height*0.25f));
        float sx1 = width-shopButton.getWidth()*1.5f;
        float sy1 = Math.round(height*0.01f);
        shopButton.setPosition(sx1, sy1);
        float sx2 = sx1;
        float sy2 = sy1+shopButton.getWidth()*0.15f;
        addActor(shopButton);
        shopButton.setVisible(false);
        Action actionS = Actions.forever(sequence( Actions.moveTo(sx2,sy2,0.6f, Interpolation.smooth),Actions.delay(0.02f), Actions.moveTo(sx1,sy1,0.6f, Interpolation.smooth)));
        shopButton.addAction(Actions.forever(actionS));

        shopButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    cradleGame.setActiveShopScreen();
                    return true;
                }
                return false;
            }
        });

        //Arena button
        Button.ButtonStyle buttonStyle3 = new Button.ButtonStyle();
        Texture buttonTex3 = new Texture( Gdx.files.internal("arena.png") );
        TextureRegion buttonRegion3 =  new TextureRegion(buttonTex3);
        buttonStyle3.up = new TextureRegionDrawable( buttonRegion3 );
        arenaButton = new Button( buttonStyle3 );
        arenaButton.setSize(Math.round(height*0.22f),Math.round(height*0.22f));
        float ax1 = arenaButton.getWidth()*0.9f;
        float ay1 = Math.round(height*0.05f);
        arenaButton.setPosition(ax1,ay1);
        float ax2 = ax1;
        float ay2 = ay1+arenaButton.getWidth()*0.15f;
        addActor(arenaButton);
        arenaButton.setVisible(false);
        Action action = Actions.forever(sequence( Actions.moveTo(ax2,ay2,0.6f, Interpolation.smooth),Actions.delay(0.02f), Actions.moveTo(ax1,ay1,0.6f, Interpolation.smooth)));
        arenaButton.addAction(Actions.forever(action));

        arenaButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputEvent ie = (InputEvent)event;
                if ( ie.getType().equals(InputEvent.Type.touchDown) ) {
                    KingdomRes kingdomRes = new KingdomRes();
                    kingdomRes.Wood=0;
                    kingdomRes.Gold=0;
                    kingdomRes.Bread=0;
                    setAttackType(AttackType.AttackArena);
                    setAttackedKingdom(null);
                    cradleGame.setActivescreenGamePlay(attackType, attackedKingdom);
                    return true;
                }
                return false;
            }
        });



        //Attack Button
        ms = cradleGame.getLanguageStrings().get("attack");
        attackButton = new TextButton( ms, BaseGame.textButtonStyle );
        attackButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;
                if (!GameRes.isResEnough(kingdomRes)) {

                    return false;


                }
                if (cradleGame.getDifficultyLevel()==0){
                    return false;
                }

                if (attackType == AttackType.AttackKingdom) {
                    GameRes.Gold -= kingdomRes.Gold;
                    GameRes.Wood -= kingdomRes.Wood;
                    GameRes.Bread -= kingdomRes.Bread;
                }

                if (cradleGame.getScreenGamePlay().getGameLevel()==1){

                        InputListener inputListener =new InputListener() {
                            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                                if (!(e instanceof InputEvent))
                                    return false;

                                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                                    return false;
                                cradleGame.getGameMapScreen().dialogBoxAttackTarget.setVisible(false);
                                cradleGame.setActivescreenGamePlay(attackType, attackedKingdom);
                                return true;
                            }
                        };
                    cradleGame.getGameMapScreen().dialogBoxAttackTarget.showWithOkButton(inputListener, attackedKingdom.getAttackTargetInfo().attackTypeInfo);

                }else {
                    cradleGame.setActivescreenGamePlay(attackType, attackedKingdom);
                }
                return true;
            }
        });
        attackButton.setPosition(Math.round(width*0.08f),20);
        addActor(attackButton);

        //Cancel Button
        ms = cradleGame.getLanguageStrings().get("cancel");
        cancelButton = new TextButton( ms, BaseGame.textButtonStyle );
        cancelButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                if (!(e instanceof InputEvent))
                    return false;

                if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                    return false;

                cradleGame.setActiveGameMapScreen(false,0);
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


    //shows hides attack button or arena
    public void setResEnoughState(boolean state, boolean mainKingdomAndAttackIsForbidden){
        if (!mainKingdomAndAttackIsForbidden) {
            if (state) {
                attackButton.setVisible(true);
                cancelButton.setVisible(true);
                labelAttackCost.setVisible(true);
                labelResNotEnough01.setVisible(false);
                labelResNotEnough02.setVisible(false);
                labelmainkingdomattackisforbidden.setVisible(false);
                arenaButton.setVisible(false);
                shopButton.setVisible(false);
                resultsActorForAttack.setVisible(true);
            } else {
                attackButton.setVisible(false);
                cancelButton.setVisible(false);
                labelAttackCost.setVisible(false);
                labelResNotEnough01.setVisible(true);
                labelResNotEnough02.setVisible(true);
                labelmainkingdomattackisforbidden.setVisible(false);
                arenaButton.setVisible(true);
                shopButton.setVisible(true);
                resultsActorForAttack.setVisible(true);
            }
        }else {
            attackButton.setVisible(false);
            cancelButton.setVisible(true);
            labelAttackCost.setVisible(false);
            labelResNotEnough01.setVisible(false);
            labelResNotEnough02.setVisible(false);
            labelmainkingdomattackisforbidden.setVisible(true);
            arenaButton.setVisible(false);
            shopButton.setVisible(false);
            resultsActorForAttack.setVisible(false);
        }

    }


    public void showDialog(boolean mainKingdomAndAttackIsForbidden) {

        setResEnoughState(GameRes.isResEnough(kingdomRes),mainKingdomAndAttackIsForbidden);
        setVisible(true);
    }


    @Override
    public void setX(float x) {
        super.setX(x);

    }
}
