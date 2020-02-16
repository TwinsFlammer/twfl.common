package com.redefocus.common.shared.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class TimeFormatter {
    public static String format(Long time) {
        if (time == 0) return "nunca";

        Long day = TimeUnit.MILLISECONDS.toDays(time);
        Long hours = TimeUnit.MILLISECONDS.toHours(time) - (day * 24);
        Long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - (hours * 60);
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - (minutes * 60);

        StringBuilder stringBuilder = new StringBuilder();

        if (day > 0) stringBuilder.append(day)
                .append("d")
                .append(" ");

        if (hours > 0) stringBuilder.append(hours)
                .append("h")
                .append(" ");

        if (minutes > 0) stringBuilder.append(minutes)
                .append("m")
                .append(" ");

        if (seconds > -1) stringBuilder.append(seconds)
                .append("s");

        String formatted = stringBuilder.toString();

        return formatted.isEmpty() ? "agora" : formatted;
    }
}
