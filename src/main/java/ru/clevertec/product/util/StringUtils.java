package ru.clevertec.product.util;

public class StringUtils {

    public static boolean isEmpty(CharSequence line) {
        return line == null || line.isEmpty();
    }

    public static boolean isNotEmpty(CharSequence line) {
        return !isEmpty(line);
    }
}