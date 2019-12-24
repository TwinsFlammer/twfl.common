package com.redecommunity.common.shared.util;

import com.google.common.collect.Lists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.List;

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

    public static String colorize(String message) {
        return message.replaceAll(
                "&",
                "§"
        );
    }

    public static List<String> fromArray(String... values) {
        List<String> results = Lists.newArrayList();
        Collections.addAll(results, values);
        results.remove("");
        return results;
    }

    public static String[] removeFirst(String[] args) {
        List<String> out = Helper.fromArray(args);

        if (!out.isEmpty()) {
            out.remove(0);
        }
        return Helper.toArray(out);
    }

    public static String[] toArray(List<String> list) {
        return list.toArray(new String[list.size()]);
    }

    public static String toMessage(String[] args) {
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < args.length; i++)
            message.append(args[i])
                    .append(" ");

        return message.toString();
    }
}
