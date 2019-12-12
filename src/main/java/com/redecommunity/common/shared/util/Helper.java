package com.redecommunity.common.shared.util;

/**
 * Created by @SrGutyerrez
 */
public abstract class Helper {
    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
