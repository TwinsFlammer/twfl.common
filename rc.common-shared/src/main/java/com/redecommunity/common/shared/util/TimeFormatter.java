package com.redecommunity.common.shared.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class TimeFormatter {
    public static void main(String[] args) {
        Long time = 62500L;

        String formatted = TimeFormatter.format(time);

        System.out.println(formatted);
    }

    public static String format(Long time) {
        if (time == 0) return "nunca";

        Long days = TimeUnit.MILLISECONDS.toDays(time);
        Long hours = TimeUnit.MILLISECONDS.toHours(time) - (days * 24);
        Long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - (hours * 60);
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - (minutes * 60);
        Long millis = TimeUnit.MILLISECONDS.toMillis(time) - (seconds * 60);

        StringBuilder stringBuilder = new StringBuilder();

        if (days > 0) stringBuilder.append(days)
                .append("d")
                .append(" ");

        if (hours > 0) stringBuilder.append(hours)
                .append("h")
                .append(" ");

        if (minutes > 0) stringBuilder.append(minutes)
                .append("m")
                .append(" ");

        if (days == 0 && hours == 0 && minutes == 0 && seconds == 0 && millis < 1000) {
            double halfSeconds = millis / 1000D;

            NumberFormat numberFormat = new DecimalFormat("#.#");
            String value = numberFormat.format(TimeFormatter.roundDouble(halfSeconds));

            stringBuilder.append(value)
                    .append("s");
        } if (seconds > 0) stringBuilder.append(seconds)
                .append("s");

        String formatted = stringBuilder.toString();

        return formatted.isEmpty() ? "agora" : formatted;
    }

    private static double roundDouble(double base) {
        return new BigDecimal(base)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
}
