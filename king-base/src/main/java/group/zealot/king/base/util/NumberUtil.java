package group.zealot.king.base.util;

public class NumberUtil {
    /**
     * （都为null） || （都不为null && int值相同）  return true
     */
    public static boolean equals(Number a, Number b) {
        if (a != null || b != null) {
            if (a != null && b != null) {
                if (a instanceof Byte && b instanceof Byte) {
                    if (a.byteValue() != b.byteValue()) {//都不为null 且值不同
                        return false;
                    } else {//都不为null 且值相同
                        return true;
                    }
                } else if (a instanceof Short && b instanceof Short) {
                    if (a.shortValue() != b.shortValue()) {//都不为null 且值不同
                        return false;
                    } else {//都不为null 且值相同
                        return true;
                    }
                } else if (a instanceof Integer && b instanceof Integer) {
                    if (a.intValue() != b.intValue()) {//都不为null 且值不同
                        return false;
                    } else {//都不为null 且值相同
                        return true;
                    }
                } else if (a instanceof Integer && b instanceof Long) {
                    if (a.intValue() != b.intValue()) {//都不为null 且值不同
                        return false;
                    } else {//都不为null 且值相同
                        return true;
                    }
                } else if (a instanceof Long && b instanceof Integer) {
                    if (a.intValue() != b.intValue()) {//都不为null 且值不同
                        return false;
                    } else {//都不为null 且值相同
                        return true;
                    }
                } else if (a instanceof Float && b instanceof Float) {
                    if (a.floatValue() != b.floatValue()) {//都不为null 且值不同
                        return false;
                    } else {//都不为null 且值相同
                        return true;
                    }
                } else if (a instanceof Float && b instanceof Double) {
                    if (a.floatValue() != b.floatValue()) {//都不为null 且值不同
                        return false;
                    } else {//都不为null 且值相同
                        return true;
                    }
                } else if (a instanceof Double && b instanceof Float) {
                    if (a.floatValue() != b.floatValue()) {//都不为null 且值不同
                        return false;
                    } else {//都不为null 且值相同
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {//有且仅有一个为null
            return false;
        }
    }

    public static boolean equals(Number a, int b) {
        if (a instanceof Integer || a instanceof Long) {
            return a.intValue() == b;
        } else {
            return false;
        }
    }

    public static boolean equals(int a, Number b) {
        if (b instanceof Integer || b instanceof Long) {
            return b.intValue() == a;
        } else {
            return false;
        }
    }


    public static boolean equals(Number a, long b) {
        if (a instanceof Integer || a instanceof Long) {
            return a.longValue() == b;
        } else {
            return false;
        }
    }

    public static boolean equals(long a, Number b) {
        if (b instanceof Integer || b instanceof Long) {
            return b.longValue() == a;
        } else {
            return false;
        }
    }


    public static boolean equals(Number a, float b) {
        if (a instanceof Float || a instanceof Double) {
            return a.floatValue() == b;
        } else {
            return false;
        }
    }

    public static boolean equals(float a, Number b) {
        if (b instanceof Float || b instanceof Double) {
            return b.floatValue() == a;
        } else {
            return false;
        }
    }

    public static boolean equals(Number a, double b) {
        if (a instanceof Float || a instanceof Double) {
            return a.doubleValue() == b;
        } else {
            return false;
        }
    }

    public static boolean equals(double a, Number b) {
        if (b instanceof Float || b instanceof Double) {
            return b.doubleValue() == a;
        } else {
            return false;
        }
    }

    public static boolean equals(int a, int b) {
        return a == b;
    }


    public static boolean equals(long a, long b) {
        return a == b;
    }

    public static boolean equals(double a, double b) {
        return a == b;
    }

    public static boolean equals(float a, float b) {
        return a == b;
    }
}
