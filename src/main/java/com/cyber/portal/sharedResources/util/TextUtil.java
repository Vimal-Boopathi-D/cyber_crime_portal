package com.cyber.portal.sharedResources.util;

import java.util.Arrays;
import java.util.List;

public class TextUtil {

    public static String clean(String text) {
        return text
                .toLowerCase()
                .replaceAll("[^a-z ]", "")
                .trim();
    }

    public static List<String> tokenize(String text) {
        return Arrays.asList(clean(text).split("\\s+"));
    }
}

