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
    public static Map<String, String> parseReplacements;
    public static Pattern parsePattern;
    private static String titleizeLine;
    private static int titleizeBalance = -1;

    public static String implode(Object[] list, String glue, String format) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < list.length; ++i) {
            Object item = list[i];
            String str = (item == null) ? "NULL" : item.toString();
            if (i != 0) {
                ret.append(glue);
            }
            if (format != null) {
                ret.append(String.format(format, str));
            } else {
                ret.append(str);
            }
        }
        return ret.toString();
    }

    public static String implode(Object[] list, String glue) {
        return implode(list, glue, null);
    }

    public static String implode(Collection<?> coll, String glue, String format) {
        return implode(coll.toArray(new Object[0]), glue, format);
    }

    public static String implode(Collection<?> coll, String glue) {
        return implode(coll, glue, null);
    }

    public static String parse(String string) {
        StringBuffer ret = new StringBuffer();
        Matcher matcher = CUtil.parsePattern.matcher(string);
        while (matcher.find()) {
            matcher.appendReplacement(ret, CUtil.parseReplacements.get(matcher.group(0)));
        }
        matcher.appendTail(ret);
        return ret.toString();
    }

    public static String parse(String string, Object... args) {
        return String.format(parse(string), args);
    }

    public static ArrayList<String> parse(Collection<String> strings) {
        ArrayList<String> ret = new ArrayList<String>(strings.size());
        for (String string : strings) {
            ret.add(parse(string));
        }
        return ret;
    }

    public static <K, V> Map<K, V> map(K key1, V value1, Object... objects) {
        Map<K, V> ret = Maps.newLinkedHashMap();
        ret.put(key1, value1);
        Iterator<Object> iter = Arrays.asList(objects).iterator();
        while (iter.hasNext()) {
            K key2 = (K) iter.next();
            V value2 = (V) iter.next();
            ret.put(key2, value2);
        }
        return ret;
    }

//    public static String titleize(String str) {
//        String center = ".[ " + parse("<l>") + str + parse("<a>") + " ].";
//        int centerlen = ChatColor.stripColor(center).length();
//        int pivot = CUtil.titleizeLine.length() / 2;
//        int eatLeft = centerlen / 2 + 1;
//        int eatRight = centerlen - eatLeft - 1;
//        if (eatLeft < pivot) {
//            return parse("<a>") + CUtil.titleizeLine.substring(0, pivot - eatLeft) + center + CUtil.titleizeLine.substring(pivot + eatRight);
//        }
//        return parse("<a>") + center;
//    }

    public static String repeat(String string, int times) {
        StringBuilder ret = new StringBuilder(times);
        for (int i = 0; i < times; ++i) {
            ret.append(string);
        }
        return ret.toString();
    }

    public static <T> List<List<T>> rotateLeft(List<List<T>> rows) {
        List<List<T>> ret = transpose(rows);
        flipVertically(ret);
        return ret;
    }

    public static <T> List<List<T>> rotateRight(List<List<T>> rows) {
        List<List<T>> ret = transpose(rows);
        flipHorizontally(ret);
        return ret;
    }

    public static <T> List<List<T>> transpose(List<List<T>> rows) {
        List<List<T>> ret = Lists.newArrayList();
        for (int n = rows.get(0).size(), i = 0; i < n; ++i) {
            List<T> col = Lists.newArrayList();
            for (List<T> row : rows) {
                col.add(row.get(i));
            }
            ret.add(col);
        }
        return ret;
    }

    public static <T> void flipHorizontally(List<List<T>> rows) {
        for (List<T> row : rows) {
            Collections.reverse(row);
        }
    }

    public static <T> void flipVertically(List<List<T>> rows) {
        Collections.reverse(rows);
    }

    @SafeVarargs
    public static <T> List<T> list(T... items) {
        return Lists.newArrayList(Arrays.asList(items));
    }

    static {
        titleizeLine = repeat("_", 50);
        (parseReplacements = Maps.newHashMap()).put("<empty>", "");
        CUtil.parseReplacements.put("<black>", "§0");
        CUtil.parseReplacements.put("<navy>", "§1");
        CUtil.parseReplacements.put("<green>", "§2");
        CUtil.parseReplacements.put("<teal>", "§3");
        CUtil.parseReplacements.put("<red>", "§4");
        CUtil.parseReplacements.put("<purple>", "§5");
        CUtil.parseReplacements.put("<gold>", "§6");
        CUtil.parseReplacements.put("<orange>", "§6");
        CUtil.parseReplacements.put("<silver>", "§7");
        CUtil.parseReplacements.put("<gray>", "§8");
        CUtil.parseReplacements.put("<grey>", "§8");
        CUtil.parseReplacements.put("<blue>", "§9");
        CUtil.parseReplacements.put("<lime>", "§a");
        CUtil.parseReplacements.put("<aqua>", "§b");
        CUtil.parseReplacements.put("<rose>", "§c");
        CUtil.parseReplacements.put("<pink>", "§d");
        CUtil.parseReplacements.put("<yellow>", "§e");
        CUtil.parseReplacements.put("<white>", "§f");
        CUtil.parseReplacements.put("<magic>", "§k");
        CUtil.parseReplacements.put("<bold>", "§l");
        CUtil.parseReplacements.put("<strong>", "§l");
        CUtil.parseReplacements.put("<strike>", "§m");
        CUtil.parseReplacements.put("<strikethrough>", "§m");
        CUtil.parseReplacements.put("<under>", "§n");
        CUtil.parseReplacements.put("<underline>", "§n");
        CUtil.parseReplacements.put("<italic>", "§o");
        CUtil.parseReplacements.put("<em>", "§o");
        CUtil.parseReplacements.put("<reset>", "§r");
        CUtil.parseReplacements.put("<l>", "§2");
        CUtil.parseReplacements.put("<logo>", "§2");
        CUtil.parseReplacements.put("<a>", "§6");
        CUtil.parseReplacements.put("<art>", "§6");
        CUtil.parseReplacements.put("<n>", "§7");
        CUtil.parseReplacements.put("<notice>", "§7");
        CUtil.parseReplacements.put("<i>", "§e");
        CUtil.parseReplacements.put("<info>", "§e");
        CUtil.parseReplacements.put("<g>", "§a");
        CUtil.parseReplacements.put("<good>", "§a");
        CUtil.parseReplacements.put("<b>", "§c");
        CUtil.parseReplacements.put("<bad>", "§c");
        CUtil.parseReplacements.put("<k>", "§b");
        CUtil.parseReplacements.put("<key>", "§b");
        CUtil.parseReplacements.put("<v>", "§d");
        CUtil.parseReplacements.put("<value>", "§d");
        CUtil.parseReplacements.put("<h>", "§d");
        CUtil.parseReplacements.put("<highlight>", "§d");
        CUtil.parseReplacements.put("<c>", "§b");
        CUtil.parseReplacements.put("<command>", "§b");
        CUtil.parseReplacements.put("<p>", "§3");
        CUtil.parseReplacements.put("<parameter>", "§3");
        CUtil.parseReplacements.put("&&", "&");
        CUtil.parseReplacements.put("§§", "§");
        for (int i = 48; i <= 122; ++i) {
            char c = (char) i;
            CUtil.parseReplacements.put("§" + c, "§" + c);
            CUtil.parseReplacements.put("&" + c, "§" + c);
            if (i == 57) {
                i = 96;
            }
        }
        StringBuilder patternStringBuilder = new StringBuilder();
        for (String find : CUtil.parseReplacements.keySet()) {
            patternStringBuilder.append('(');
            patternStringBuilder.append(Pattern.quote(find));
            patternStringBuilder.append(")|");
        }
        String patternString = patternStringBuilder.toString();
        patternString = patternString.substring(0, patternString.length() - 1);
        parsePattern = Pattern.compile(patternString);
    }
}
