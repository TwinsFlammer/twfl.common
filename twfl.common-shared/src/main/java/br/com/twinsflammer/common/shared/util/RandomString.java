package br.com.twinsflammer.common.shared.util;

import java.util.Random;

/**
 * @author SrGutyerrez
 */
public class RandomString {
    public static final Random RANDOM;
    private static final char[] APPEND;

    static {
        RANDOM = new Random();
        APPEND = new char[] { '§', '\0', '§', '\0', '§', '\0', '§', '\0', '§', '\0', '§', '\0', '§', '\0', '§', '\0', '§', '\0', '§', '\0' };
    }

    public static String randomString() {
        RandomString.APPEND[1] = (char)(48 + RandomString.RANDOM.nextInt(10));
        RandomString.APPEND[3] = (char)(48 + RandomString.RANDOM.nextInt(10));
        RandomString.APPEND[5] = (char)(48 + RandomString.RANDOM.nextInt(10));
        RandomString.APPEND[7] = (char)(48 + RandomString.RANDOM.nextInt(10));
        return new String(RandomString.APPEND);
    }
}
