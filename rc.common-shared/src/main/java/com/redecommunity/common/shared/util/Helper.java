package com.redecommunity.common.shared.util;

import com.google.common.collect.Lists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by @SrGutyerrez
 */
public abstract class Helper {
    public static Boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static Boolean isString(Object object) {
        return object instanceof String;
    }

    public static Boolean isJSONArray(Object object) {
        return object instanceof JSONArray;
    }

    public static Boolean isJSONObject(Object object) {
        return object instanceof JSONObject;
    }

    public static Boolean isURLValid(String urlString) {
        try {
            URL url = new URL(urlString);

            url.openConnection();

            return true;
        } catch (IOException exception) {
            return false;
        }
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

    public static String generateString(Integer length) {
        return Helper.generateString(false, length);
    }

    public static String generateString(Boolean uppercase, Integer length) {
        String characters = (uppercase ? "ABCDEFGHIJKLMNOPQRSTUVXYWZ" : "") + "abcdefghijklmnopqrstuvxywz0123456789";

        char[] array = characters.toCharArray();

        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            Integer index = random.nextInt(array.length);

            char character = array[index];

            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }

    public static String hash(String sampleText) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.update(sampleText.getBytes());

            byte[] bytes = messageDigest.digest();

            return DatatypeConverter.printHexBinary(bytes);
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
