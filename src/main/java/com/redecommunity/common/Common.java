package com.redecommunity.common;

/**
 * Created by @SrGutyerrez
 */
public class Common {
    private static Common instance;

    public Common() {
        Common.instance = this;
    }

    public static Common getInstance() {
        return Common.instance;
    }
}
