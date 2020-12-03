package group.zealot.king.base.util;

public class IntegerUtil {
    /**
     * （都为null） || （都不为null && int值相同）  return true
     */
    public static boolean equals(Integer a, Integer b) {
        if (a != null || b != null) {
            if (a != null && b != null) {
                if (a.intValue() != b.intValue()) {//都不为null 且int值不同
                    return false;
                } else {//都不为null 且int值相同
                    return true;
                }
            } else {//有且仅有一个为null
                return false;
            }
        } else {//都为null
            return true;
        }
    }

    public static boolean equals(Integer a, int b) {
        return a != null && a == b;
    }

    public static boolean equals(int a, Integer b) {
        return b != null && a == b;
    }

    public static boolean equals(int a, int b) {
        return a == b;
    }
}
