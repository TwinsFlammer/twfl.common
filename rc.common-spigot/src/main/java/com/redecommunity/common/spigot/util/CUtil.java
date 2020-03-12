package com.redecommunity.common.spigot.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by @SrGutyerrez
 */
public class CUtil {
    public static final Map<String, String> parseReplacements;
    public static final Pattern parsePattern;

    static {
        parseReplacements = Maps.newHashMap();

        parseReplacements.put("<empty>", "");
        parseReplacements.put("<black>", "\u00A70");
        parseReplacements.put("<navy>", "\u00A71");
        parseReplacements.put("<green>", "\u00A72");
        parseReplacements.put("<teal>", "\u00A73");
        parseReplacements.put("<red>", "\u00A74");
        parseReplacements.put("<purple>", "\u00A75");
        parseReplacements.put("<gold>", "\u00A76");
        parseReplacements.put("<orange>", "\u00A76");
        parseReplacements.put("<silver>", "\u00A77");
        parseReplacements.put("<gray>", "\u00A78");
        parseReplacements.put("<grey>", "\u00A78");
        parseReplacements.put("<blue>", "\u00A79");
        parseReplacements.put("<lime>", "\u00A7a");
        parseReplacements.put("<aqua>", "\u00A7b");
        parseReplacements.put("<rose>", "\u00A7c");
        parseReplacements.put("<pink>", "\u00A7d");
        parseReplacements.put("<yellow>", "\u00A7e");
        parseReplacements.put("<white>", "\u00A7f");
        parseReplacements.put("<magic>", "\u00A7k");
        parseReplacements.put("<bold>", "\u00A7l");
        parseReplacements.put("<strong>", "\u00A7l");
        parseReplacements.put("<strike>", "\u00A7m");
        parseReplacements.put("<strikethrough>", "\u00A7m");
        parseReplacements.put("<under>", "\u00A7n");
        parseReplacements.put("<underline>", "\u00A7n");
        parseReplacements.put("<italic>", "\u00A7o");
        parseReplacements.put("<em>", "\u00A7o");
        parseReplacements.put("<reset>", "\u00A7r");

        // Color by semantic functionality
        parseReplacements.put("<l>", "\u00A72");
        parseReplacements.put("<logo>", "\u00A72");
        parseReplacements.put("<a>", "\u00A76");
        parseReplacements.put("<art>", "\u00A76");
        parseReplacements.put("<n>", "\u00A77");
        parseReplacements.put("<notice>", "\u00A77");
        parseReplacements.put("<i>", "\u00A7e");
        parseReplacements.put("<info>", "\u00A7e");
        parseReplacements.put("<g>", "\u00A7a");
        parseReplacements.put("<good>", "\u00A7a");
        parseReplacements.put("<b>", "\u00A7c");
        parseReplacements.put("<bad>", "\u00A7c");

        parseReplacements.put("<k>", "\u00A7b");
        parseReplacements.put("<key>", "\u00A7b");

        parseReplacements.put("<v>", "\u00A7d");
        parseReplacements.put("<value>", "\u00A7d");
        parseReplacements.put("<h>", "\u00A7d");
        parseReplacements.put("<highlight>", "\u00A7d");

        parseReplacements.put("<c>", "\u00A7b");
        parseReplacements.put("<command>", "\u00A7b");
        parseReplacements.put("<p>", "\u00A73");
        parseReplacements.put("<parameter>", "\u00A73");
        parseReplacements.put("&&", "&");
        parseReplacements.put("§§", "§");

        for (int i = 48; i <= 122; i++) {
            char c = (char) i;
            parseReplacements.put("§" + c, "\u00A7" + c);
            parseReplacements.put("&" + c, "\u00A7" + c);
            if (i == 57) i = 96;
        }

        StringBuilder patternStringBuilder = new StringBuilder();
        for (String find : parseReplacements.keySet()) {
            patternStringBuilder.append('(');
            patternStringBuilder.append(Pattern.quote(find));
            patternStringBuilder.append(")|");
        }
        String patternString = patternStringBuilder.toString();
        patternString = patternString.substring(0, patternString.length() - 1); // Remove the last |
        parsePattern = Pattern.compile(patternString);
    }

    public static String parse(String string) {
        if (string == null) return null;
        StringBuffer returns = new StringBuffer();
        Matcher matcher = parsePattern.matcher(string);
        while (matcher.find()) {
            matcher.appendReplacement(returns, parseReplacements.get(matcher.group(0)));
        }
        matcher.appendTail(returns);
        return returns.toString();
    }

    public static String parse(String string, Object... args) {
        return String.format(parse(string), args);
    }

    public static ArrayList<String> parse(Collection<String> strings) {
        ArrayList<String> returns = new ArrayList<>(strings.size());
        for (String string : strings) {
            returns.add(parse(string));
        }
        return returns;
    }

    public static String implode(final Object[] list, final String glue, final String format) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < list.length; i++) {
            Object item = list[i];

            String string = item == null ? "NULL" : item.toString();

            if (i != 0) {
                stringBuilder.append(glue);
            }
            if (format != null) {
                stringBuilder.append(String.format(format, string));
            } else {
                stringBuilder.append(string);
            }
        }

        return stringBuilder.toString();
    }

    public static String implode(final Object[] list, final String glue) {
        return CUtil.implode(list, glue, null);
    }

    public static String implode(final Collection<?> coll, final String glue, String format) {
        return CUtil.implode(coll.toArray(new Object[0]), glue, format);
    }

    public static String implode(final Collection<?> coll, final String glue) {
        return CUtil.implode(coll, glue, null);
    }

    public static <K, V> Map<K, V> map(K key1, V value1, Object... objects) {
        Map<K, V> returns = Maps.newLinkedHashMap();

        returns.put(key1, value1);

        Iterator<Object> iterator = Arrays.asList(objects).iterator();
        while (iterator.hasNext()) {
            K key = (K) iterator.next();
            V value = (V) iterator.next();
            returns.put(key, value);
        }

        return returns;
    }

    public static <T> List<List<T>> rotateLeft(List<List<T>> rows) {
        List<List<T>> returns = transpose(rows);
        CUtil.flipVertically(returns);
        return returns;
    }

    public static <T> List<List<T>> transpose(List<List<T>> rows) {
        List<List<T>> returns = Lists.newArrayList();

        final Integer size = rows.get(0).size();

        for (int i = 0; i < size; i++) {
            List<T> col = Lists.newArrayList();

            for (List<T> row : rows) {
                col.add(row.get(i));
            }
        }

        return returns;
    }

    public static <T> void flipVertically(List<List<T>> rows) {
        Collections.reverse(rows);
    }
}
