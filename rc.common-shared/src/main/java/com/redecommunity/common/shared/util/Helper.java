package com.redecommunity.common.shared.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    public static boolean isString(Object object) {
        return object instanceof String;
    }

    public static boolean isJSONArray(Object object) {
        return object instanceof JSONArray;
    }

    public static boolean isJSONObject(Object object) {
        return object instanceof JSONObject;
    }
}
