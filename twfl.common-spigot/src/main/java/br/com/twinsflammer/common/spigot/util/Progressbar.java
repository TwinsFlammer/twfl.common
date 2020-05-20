package br.com.twinsflammer.common.spigot.util;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by @SrGutyerrez
 */
public class Progressbar {
    public static transient Progressbar HEALTHBAR_CLASSIC;
    private double quota;
    private int width;
    private String left;
    private String solid;
    private String between;
    private String empty;
    private String right;
    private double solidsPerEmpty;
    private String colorTag;
    private Map<Double, String> roofToColor;

    public Progressbar withQuota(double quota) {
        return new Progressbar(quota, this.width, this.left, this.solid, this.between, this.empty, this.right, this.solidsPerEmpty, this.colorTag, this.roofToColor);
    }

    public Progressbar withWidth(int width) {
        return new Progressbar(this.quota, width, this.left, this.solid, this.between, this.empty, this.right, this.solidsPerEmpty, this.colorTag, this.roofToColor);
    }

    public Progressbar withLeft(String left) {
        return new Progressbar(this.quota, this.width, left, this.solid, this.between, this.empty, this.right, this.solidsPerEmpty, this.colorTag, this.roofToColor);
    }

    public Progressbar withSolid(String solid) {
        return new Progressbar(this.quota, this.width, this.left, solid, this.between, this.empty, this.right, this.solidsPerEmpty, this.colorTag, this.roofToColor);
    }

    public Progressbar withBetween(String between) {
        return new Progressbar(this.quota, this.width, this.left, this.solid, between, this.empty, this.right, this.solidsPerEmpty, this.colorTag, this.roofToColor);
    }

    public Progressbar withEmpty(String empty) {
        return new Progressbar(this.quota, this.width, this.left, this.solid, this.between, empty, this.right, this.solidsPerEmpty, this.colorTag, this.roofToColor);
    }

    public Progressbar withRight(String right) {
        return new Progressbar(this.quota, this.width, this.left, this.solid, this.between, this.empty, right, this.solidsPerEmpty, this.colorTag, this.roofToColor);
    }

    public Progressbar withSolidsPerEmpty(double solidsPerEmpty) {
        return new Progressbar(this.quota, this.width, this.left, this.solid, this.between, this.empty, this.right, solidsPerEmpty, this.colorTag, this.roofToColor);
    }

    public Progressbar withColorTag(String colorTag) {
        return new Progressbar(this.quota, this.width, this.left, this.solid, this.between, this.empty, this.right, this.solidsPerEmpty, colorTag, this.roofToColor);
    }

    public Progressbar withRoofToColor(Map<Double, String> roofToColor) {
        return new Progressbar(this.quota, this.width, this.left, this.solid, this.between, this.empty, this.right, this.solidsPerEmpty, this.colorTag, roofToColor);
    }

    private Progressbar(double quota, int width, String left, String solid, String between, String empty, String right, double solidsPerEmpty, String colorTag, Map<Double, String> roofToColor) {
        this.quota = quota;
        this.width = width;
        this.left = left;
        this.solid = solid;
        this.between = between;
        this.empty = empty;
        this.right = right;
        this.solidsPerEmpty = solidsPerEmpty;
        this.colorTag = colorTag;
        this.roofToColor = Collections.unmodifiableMap(roofToColor);
    }

    public static Progressbar valueOf(double quota, int width, String left, String solid, String between, String empty, String right, double solidsPerEmpty, String colorTag, Map<Double, String> roofToColor) {
        return new Progressbar(quota, width, left, solid, between, empty, right, solidsPerEmpty, colorTag, roofToColor);
    }

    public String render() {
        return render(this.quota, this.width, this.left, this.solid, this.between, this.empty, this.right, this.solidsPerEmpty, this.colorTag, this.roofToColor);
    }

    public List<String> renderList() {
        return renderList(this.quota, this.width, this.left, this.solid, this.between, this.empty, this.right, this.solidsPerEmpty, this.colorTag, this.roofToColor);
    }

    public static String render(double quota, int width, String left, String solid, String between, String empty, String right, double solidsPerEmpty, String colorTag, Map<Double, String> roofToColor) {
        return CUtil.implode(renderList(quota, width, left, solid, between, empty, right, solidsPerEmpty, colorTag, roofToColor), "");
    }

    public static List<String> renderList(double quota, int width, String left, String solid, String between, String empty, String right, double solidsPerEmpty, String colorTag, Map<Double, String> roofToColor) {
        List<String> ret = Lists.newArrayList();
        quota = limit(quota);
        String color = pick(quota, roofToColor);
        int solidCount = (int) Math.ceil(width * quota);
        int emptyCount = (int) ((width - solidCount) / solidsPerEmpty);
        left = colorParse(left, colorTag, color);
        solid = colorParse(solid, colorTag, color);
        between = colorParse(between, colorTag, color);
        empty = colorParse(empty, colorTag, color);
        right = colorParse(right, colorTag, color);
        if (left != null) {
            ret.add(left);
        }
        if (solid != null) {
            for (int i = 1; i <= solidCount; ++i) {
                ret.add(solid);
            }
        }
        if (between != null) {
            ret.add(between);
        }
        if (empty != null) {
            for (int i = 1; i <= emptyCount; ++i) {
                ret.add(empty);
            }
        }
        if (right != null) {
            ret.add(right);
        }
        return ret;
    }

    public static String colorParse(String string, String colorTag, String color) {
        if (string == null) {
            return null;
        }
        string = string.replace(colorTag, color);
        string = CUtil.parse(string);
        return string;
    }

    public static double limit(double quota) {
        if (quota > 1.0) {
            return 1.0;
        }
        if (quota < 0.0) {
            return 0.0;
        }
        return quota;
    }

    public static <T> T pick(double quota, Map<Double, T> roofToValue) {
        Double currentRoof = null;
        T ret = null;
        for (Map.Entry<Double, T> entry : roofToValue.entrySet()) {
            double roof = entry.getKey();
            T value = entry.getValue();
            if (quota <= roof && (currentRoof == null || roof <= currentRoof)) {
                currentRoof = roof;
                ret = value;
            }
        }
        return ret;
    }

    public double getQuota() {
        return this.quota;
    }

    public int getWidth() {
        return this.width;
    }

    public String getLeft() {
        return this.left;
    }

    public String getSolid() {
        return this.solid;
    }

    public String getBetween() {
        return this.between;
    }

    public String getEmpty() {
        return this.empty;
    }

    public String getRight() {
        return this.right;
    }

    public double getSolidsPerEmpty() {
        return this.solidsPerEmpty;
    }

    public String getColorTag() {
        return this.colorTag;
    }

    public Map<Double, String> getRoofToColor() {
        return this.roofToColor;
    }

    static {
        HEALTHBAR_CLASSIC = valueOf(1.0, 30, "{c}[", "|", "&8", "|", "{c}]", 1.0, "{c}", CUtil.map(1.0, "&2", 0.8, "&a", 0.5, "&e", 0.4, "&6", 0.3, "&c", 0.2, "&4"));
    }
}
