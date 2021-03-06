package by.android.cradle;

public enum AttackTypeInfo {
    SingleTimeClearUp, //Один игрок , цель - очистить поле от оранжевых клеток на время
    SingleTimeResources, // Один игрок , цель - собрать указанные ресурсы в заданном количестве на время
    SingleClearUp, // Один игрок , ограниченное число ходов! (возможность докупить)
    SingleResources, // Один игрок , ограниченное число ходов! (возможность докупить)
    DoubleClearUp, // Два игрока , ходы поочередно , ограниченное число! (возможность докупить), если собирает противник он укрепляет защиту клетки
    DoubleResources, // Два игрока , ходы поочередно , кто быстрее соберет цель по ресурсам
    DoubleFight // Два игорока, ходы поочередно, кто кого убъет путем сбора ресурсов и атаки
}
