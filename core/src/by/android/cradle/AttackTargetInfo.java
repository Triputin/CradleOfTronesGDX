package by.android.cradle;

public class AttackTargetInfo {
    public AttackTypeInfo attackTypeInfo;
    public KingdomRes kingdomRes;
    public int stepsQtty;
    KnightParamsForAttack knightParamsForAttack;

    public AttackTargetInfo(int kingdomId){
        knightParamsForAttack = new KnightParamsForAttack(1);
        kingdomRes = new KingdomRes();
        switch(kingdomId){
            case 0:
                attackTypeInfo = AttackTypeInfo.SingleTimeClearUp;
                break;
            case 1:
                attackTypeInfo = AttackTypeInfo.SingleTimeResources;
                kingdomRes.Wood = 20;
                kingdomRes.Gold = 15;
                kingdomRes.Bread = 0;
                break;
            case 2:
                attackTypeInfo = AttackTypeInfo.SingleTimeResources;
                kingdomRes.Wood = 15;
                kingdomRes.Gold = 20;
                kingdomRes.Bread = 0;
                break;
            case 3:
                attackTypeInfo = AttackTypeInfo.SingleResources;
                kingdomRes.Wood = 20;
                kingdomRes.Gold = 15;
                kingdomRes.Bread = 0;
                stepsQtty = 20;
                break;
            case 4:
                attackTypeInfo = AttackTypeInfo.SingleResources;
                kingdomRes.Wood = 20;
                kingdomRes.Gold = 30;
                kingdomRes.Bread = 20;
                stepsQtty = 30;
                break;
            case 5:
                attackTypeInfo = AttackTypeInfo.SingleClearUp;
                stepsQtty = 20;
                break;
            case 6:
                attackTypeInfo = AttackTypeInfo.SingleResources;
                kingdomRes.Wood = 20;
                kingdomRes.Gold = 0;
                kingdomRes.Bread = 20;
                stepsQtty = 20;
                break;
            case 7:
                attackTypeInfo = AttackTypeInfo.DoubleFight;
                knightParamsForAttack.HealthPoints = 50;
                stepsQtty = 40;
                break;
            case 10:
                attackTypeInfo = AttackTypeInfo.SingleTimeClearUp;
                break;
            case 11:
                attackTypeInfo = AttackTypeInfo.SingleTimeResources;
                kingdomRes.Wood = 30;
                kingdomRes.Gold = 25;
                kingdomRes.Bread = 0;
                break;
            case 12:
                attackTypeInfo = AttackTypeInfo.SingleTimeClearUp;
                break;
            case 13:
                attackTypeInfo = AttackTypeInfo.SingleClearUp;
                stepsQtty = 25;
                break;
            case 14:
                attackTypeInfo = AttackTypeInfo.SingleResources;
                kingdomRes.Wood = 30;
                kingdomRes.Gold = 40;
                kingdomRes.Bread = 30;
                stepsQtty = 30;
                break;
            case 15:
                attackTypeInfo = AttackTypeInfo.SingleClearUp;
                stepsQtty = 25;
                break;
            case 16:
                attackTypeInfo = AttackTypeInfo.SingleResources;
                kingdomRes.Wood = 40;
                kingdomRes.Gold = 0;
                kingdomRes.Bread = 40;
                stepsQtty = 20;
                break;
            case 17:
                attackTypeInfo = AttackTypeInfo.DoubleFight;
                knightParamsForAttack.HealthPoints = 80;
                stepsQtty = 40;
                break;

            case 20:
                attackTypeInfo = AttackTypeInfo.SingleTimeClearUp;
                break;
            case 21:
                attackTypeInfo = AttackTypeInfo.SingleTimeResources;
                kingdomRes.Wood = 40;
                kingdomRes.Gold = 25;
                kingdomRes.Bread = 0;
                break;
            case 22:
                attackTypeInfo = AttackTypeInfo.SingleTimeClearUp;
                break;
            case 23:
                attackTypeInfo = AttackTypeInfo.SingleResources;
                kingdomRes.Wood = 30;
                kingdomRes.Gold = 25;
                kingdomRes.Bread = 0;
                stepsQtty = 17;
                break;
            case 24:
                attackTypeInfo = AttackTypeInfo.SingleResources;
                kingdomRes.Wood = 35;
                kingdomRes.Gold = 40;
                kingdomRes.Bread = 30;
                stepsQtty = 25;
                break;
            case 25:
                attackTypeInfo = AttackTypeInfo.SingleClearUp;
                stepsQtty = 25;
                break;
            case 26:
                attackTypeInfo = AttackTypeInfo.SingleResources;
                kingdomRes.Wood = 50;
                kingdomRes.Gold = 0;
                kingdomRes.Bread = 50;
                stepsQtty = 18;
                break;
            case 27:
                attackTypeInfo = AttackTypeInfo.DoubleFight;
                knightParamsForAttack.HealthPoints = 100;
                stepsQtty = 40;
                break;

            case 30:
                attackTypeInfo = AttackTypeInfo.SingleTimeClearUp;
                break;
            case 31:
                attackTypeInfo = AttackTypeInfo.SingleTimeResources;
                kingdomRes.Wood = 50;
                kingdomRes.Gold = 30;
                kingdomRes.Bread = 0;
                break;
            case 32:
                attackTypeInfo = AttackTypeInfo.SingleTimeClearUp;
                break;
            case 33:
                attackTypeInfo = AttackTypeInfo.SingleResources;
                kingdomRes.Wood = 40;
                kingdomRes.Gold = 30;
                kingdomRes.Bread = 0;
                stepsQtty = 15;
                break;
            case 34:
                attackTypeInfo = AttackTypeInfo.SingleResources;
                kingdomRes.Wood = 40;
                kingdomRes.Gold = 50;
                kingdomRes.Bread = 35;
                stepsQtty = 25;
                break;
            case 35:
                attackTypeInfo = AttackTypeInfo.SingleClearUp;
                stepsQtty = 30;
                break;
            case 36:
                attackTypeInfo = AttackTypeInfo.SingleResources;
                kingdomRes.Wood = 60;
                kingdomRes.Gold = 0;
                kingdomRes.Bread = 60;
                stepsQtty = 15;
                break;
            case 37:
                attackTypeInfo = AttackTypeInfo.DoubleFight;
                knightParamsForAttack.HealthPoints = 150;
                stepsQtty = 40;
                break;


                default:
                    attackTypeInfo = AttackTypeInfo.SingleTimeClearUp;
        }

    }

    public AttackTargetInfo(AttackTargetInfo attackTargetInfo){
        attackTypeInfo = attackTargetInfo.attackTypeInfo;
        kingdomRes = new KingdomRes();
        kingdomRes.Wood = attackTargetInfo.kingdomRes.Wood;
        kingdomRes.Gold = attackTargetInfo.kingdomRes.Gold;
        kingdomRes.Bread = attackTargetInfo.kingdomRes.Bread;
        stepsQtty = attackTargetInfo.stepsQtty;
        knightParamsForAttack = new KnightParamsForAttack(attackTargetInfo.knightParamsForAttack.HealthPoints);

    }

}

