package org.example.userservice.shared.util;

public class RandomUtil {

    public static String randomPassword() {
        return Integer.toString((int) (Math.random() * 899_999 + 100_000));
    }
}
