package com.redecommunity.common.shared.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class TimeFormatter {
    public static String format(long time) {
        if (time == 0) return "nunca";

        Long day = TimeUnit.MILLISECONDS.toDays(time);
        Long hours = TimeUnit.MILLISECONDS.toHours(time) - (day * 24);
        Long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - (hours * 60);
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - (minutes * 60);

        StringBuilder sb = new StringBuilder();

        if (day > 0) {
            sb.append(day)
                    .append("d")
                    .append(" ");
        }
        if (hours > 0) {
            sb.append(hours)
                    .append("h")
                    .append(" ");
        }
        if (minutes > 0) {
            sb.append(minutes)
                    .append("m")
                    .append(" ");
        }
        if (seconds > -1) {
            System.out.println(seconds == 0 ? seconds.doubleValue() : seconds);

            sb.append(seconds == 0 ? seconds.doubleValue() : seconds)
                    .append("s");
        }

        String diff = sb.toString();

        return diff.isEmpty() ? "agora" : diff;
    }
}
