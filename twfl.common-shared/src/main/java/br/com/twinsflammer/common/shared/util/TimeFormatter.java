package br.com.twinsflammer.common.shared.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class TimeFormatter {
    public static String formatExtended(Long time) {
        return TimeFormatter.format(time, true);
    }

    public static String formatMinimized(Long time) {
        return TimeFormatter.format(time, false);
    }

    @Deprecated
    public static String format(Long time) {
        return TimeFormatter.formatMinimized(time);
    }

    public static String format(Long time, Boolean extended) {
        if (time < 0) return "0s";

        Long days = TimeUnit.MILLISECONDS.toDays(time);
        Long hours = TimeUnit.MILLISECONDS.toHours(time) - (days * 24);
        Long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - (hours * 60);
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - (minutes * 60);
        Long millis = TimeUnit.MILLISECONDS.toMillis(time) - (seconds * 60);

        StringBuilder stringBuilder = new StringBuilder();

        if (days > 0) stringBuilder.append(days)
                .append(extended ? " dias" : "d")
                .append(" ");

        if (hours > 0) stringBuilder.append(hours)
                .append(extended ? " horas" : "h")
                .append(" ");

        if (minutes > 0) stringBuilder.append(minutes)
                .append(extended ? " minutos" : "m")
                .append(" ");

        if (days == 0 && hours == 0 && minutes == 0 && seconds == 0 && millis < 1000) {
            double halfSeconds = millis / 1000D;

            NumberFormat numberFormat = new DecimalFormat("#.#");
            String value = numberFormat.format(TimeFormatter.roundDouble(halfSeconds));

            stringBuilder.append(value)
                    .append(extended ? " segundos" : "s");
        }

        if (seconds > 0) stringBuilder.append(seconds)
                .append(extended ? " segundos" : "s");

        return stringBuilder.toString();
    }

    private static double roundDouble(double base) {
        return new BigDecimal(base)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
}
