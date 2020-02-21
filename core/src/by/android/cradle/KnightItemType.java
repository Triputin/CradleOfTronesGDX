package by.android.cradle;

import java.util.HashMap;
import java.util.Map;

public enum KnightItemType {
    Helmet(1),
    Gloves(2),
    Sword(3),
    Armor(4),
    Boots(5);

    private int value;
    private static Map map = new HashMap<>();

    private KnightItemType(int value) {
        this.value = value;
    }

    static {
        for (KnightItemType pageType : KnightItemType.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static KnightItemType valueOf(int pageType) {
        return (KnightItemType) map.get(pageType);
    }

    public int getValue() {
        return value;
    }

}
