package com.poiji.util;

/**
 * Created by hakan on 22/01/2017.
 */
public final class Casting {

    private Casting() {
    }

    public static Integer integerValue(String value) {
        try {
            return new Integer(value);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public static Long longValue(String value) {
        try {
            return new Long(value);
        } catch (NumberFormatException nfe) {
            return 0L;
        }
    }

    public static Short shortValue(String value) {
        try {
            return new Short(value);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public static Double doubleValue(String value) {
        try {
            return new Double(value);
        } catch (NumberFormatException nfe) {
            return 0d;
        }
    }

    public static Float floatValue(String value) {
        try {
            return new Float(value);
        } catch (NumberFormatException nfe) {
            return 0f;
        }
    }

    public static Byte byteValue(String value) {
        try {
            return new Byte(value);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }
}
